//  Copyright 2017 The keepTry Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package stack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;

public class ArithmeticExpressionEvaluator {
    /**
     * <pre>
     * a + b - e * f * g
     * ->
     * "a b + e f * g * -"
     *
     * 1 + 2 - 3 * 4 / 5
     * ( 1 * ( 2 + 2 ) - ( 3 - 4 ) / 5 )
     *
     * http://codinghelmet.com/?path=exercises/expression-evaluator
     *
     * 1> table of boolean[][].
     * 2> using 2 stacks
     * 3> single-digit number and multi-digit number in arithmetic expression
     * 4> wrap with open/close parenthesis
     * 5> close parenthesis action.
     */
    int evaluate(String[] arithmeticExpression) {
        return 0; // todo
    }

    /**
     * <pre>
     * 1> Implement an arithmetic expression parentheses that can evaluate strings like
     * (1+2)*(3+(4*5)) to 69.
     * Assume: only add, subtract, multiply, and divide operation.
     * Assume: every binary operation a+b must be wrapped in a set of parentheses (a+b)
     * Assume: may have space ' ' in the expression.
     * Assume: The number is no-negative number and may be no-single digit number
     *
     * 2> Allow
     * multiple operations within a single set of parentheses,
     * or multiple sets of wrapping parentheses,
     * allowing for operator precedence:
     * * and / are higher precedence than + and -
     * e.g.
     * evaluating ((1+2))*(3+4*5) into 69
     *
     * 3> allow negative integer in parentheses.
     * 4> allow negative integer not in parentheses.
     * 5> allow float
     *
     * 6> allow variables in the expression, e.g. evaluating (x+2)*(3+y*5) into a object f, and
     * later call f(x,y)=>f(1,4) to return 69
     *
     * 7> Limiting the only operations to +, extend your code to expand the symbolic
     * expression, e.g. converting (y+2)*(3+x*5) into 5yx + 3y + 10x + 6
     */
    private static Float operation(Character ope, Float r, Float l) throws Exception {
        switch (ope) {
            case '+':
                return r + l;
            case '-':
                return l - r;
            case '*':
                return r * l;
            case '/':
                if (r == 0) throw new IllegalArgumentException("Cannot divided by 0");
                return l / r;
            default:
                return 0f;
        }
    }

    private static void valid(String exp) throws Exception {
        if (exp.contains("+-")
                || exp.contains("--")
                || exp.contains("*-")
                || exp.contains("/-")
                || exp.contains("++")
                || exp.contains("-+")
                || exp.contains("*+")
                || exp.contains("/+")
                || exp.contains("+*")
                || exp.contains("-*")
                || exp.contains("**")
                || exp.contains("/*")
                || exp.contains("+/")
                || exp.contains("-/")
                || exp.contains("*/")
                || exp.contains("//")) {
            throw new Exception(
                    "does not support express contains:"
                            + " ++, -+,*+,/+; +-, --,*-,/-; +*, -*,**,/*; +/, -/,*/,//");
        }
    }

    public static Float parse(String exp) throws Exception {
        valid(exp);
        Stack<Character> ops = new Stack<>();
        Stack<Float> numbers = new Stack<>();

        for (int i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) == ' ') continue;
            char c = exp.charAt(i);
            if (c == '-' || c == '+' || c == '*' || c == '/' || c == '(') {
                ops.push(c);
            } else if (c >= '0' && c <= '9') { // May not be single digit
                int lastIndex = i - 1;
                StringBuilder builder = new StringBuilder();
                boolean isFloat = false;
                while (i < exp.length() && ('0' <= (c = exp.charAt(i)) && c <= '9' || c == '.')) {
                    if (c == '.' && !isFloat) {
                        isFloat = true;
                    }
                    builder.append(exp.charAt(i++));
                }
                i--;

                Float curNum = Float.parseFloat(builder.toString());

                if (0 <= lastIndex && exp.charAt(lastIndex) == '-') { // negative
                    ops.pop();
                    numbers.push(-curNum);
                    if (0 <= lastIndex - 1 && exp.charAt(lastIndex - 1) != '(') {
                        ops.push('+');
                    }
                } else {
                    numbers.push(curNum);
                    tryApplyMulOrDiv(ops, numbers);
                }

            } else if (c == ')') {
                while (ops.peek() != '(')
                    numbers.push(operation(ops.pop(), numbers.pop(), numbers.pop()));
                ops.pop();
                tryApplyMulOrDiv(ops, numbers);
            } else {
                throw new Exception("invalid content");
            }
        }

        while (!ops.isEmpty()) numbers.push(operation(ops.pop(), numbers.pop(), numbers.pop()));
        return numbers.peek();
    }

    private static Float parseWithVariables(String exp, Map<Character, Float> variables)
            throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < exp.length(); i++) {
            Character c = exp.charAt(i);
            if (c == ' ') continue;
            if (variables.containsKey(c)) {
                sb.append(variables.get(c));
                continue;
            }
            sb.append(c);
        }
        return parse(sb.toString());
    }

    private static void tryApplyMulOrDiv(Stack<Character> ops, Stack<Float> numbers)
            throws Exception {
        if (!ops.isEmpty() && (ops.peek() == '*' || ops.peek() == '/')) {
            numbers.push(operation(ops.pop(), numbers.pop(), numbers.pop()));
        }
    }

    private static String convert(String exp) {
        exp = Pattern.compile(" ").matcher(exp).replaceAll("");

        int index = exp.indexOf(")*(");
        if (index > 0) {
            int s = exp.lastIndexOf('(', index);
            int e = exp.indexOf(')', index + 1);

            StringBuilder r = new StringBuilder();
            r.append(exp.substring(0, s));
            if (e + 1 < exp.length() && exp.charAt(e + 1) == '*') {
                r.append('(');
                r.append(expand(exp.substring(s + 1, index), exp.substring(index + 3, e)));
                r.append(')');
            } else {
                r.append(expand(exp.substring(s + 1, index), exp.substring(index + 3, e)));
            }
            r.append(exp.substring(e + 1, exp.length()));
            return r.toString();
        }
        return exp;
    }

    private static String expand(String l, String r) {
        String[] ls = l.split("\\+"); // assume only '+' operation
        String[] rs = r.split("\\+"); // assume only '+' operation
        StringBuilder expanded = new StringBuilder();
        for (int i = 0; i < ls.length; i++) {
            for (int j = 0; j < rs.length; j++) {
                expanded.append(readFor(reOrderAndConcise(ls[i] + "*" + rs[j]), true));
                if (!(i == ls.length - 1 && j == rs.length - 1)) {
                    expanded.append("+");
                }
            }
        }

        return expanded.toString();
    }

    // Assume contains only '*' operation
    private static String reOrderAndConcise(String in) {
        String[] ins = in.split("\\*");
        Arrays.sort(ins);
        StringBuilder sb = new StringBuilder();
        Float tmp = 1f;
        int i = 0;
        for (i = 0; i < ins.length; i++) {
            if ('0' <= ins[i].charAt(0) && ins[i].charAt(0) <= '9') {
                tmp *= Float.parseFloat(ins[i]);
            } else {
                break;
            }
        }
        sb.append(tmp);
        while (i < ins.length) {
            sb.append("*");
            sb.append(ins[i]);
            i++;
        }
        return sb.toString();
    }

    private static String readFor(String in, boolean forHuman) {
        if (forHuman) { // assume separated by '*'
            return in.replaceAll("\\*", "");
        } else { //TODO assume it is like 9.0xy.
            return in;
        }
    }

    //--------------------------------------------

    private static void test(String express, Float expected) throws Exception {
        Float r = parse(express);
        System.out.println(
                r.equals(expected)
                        ? "Success"
                        : r + " is wrong. Right answer is " + express + "=" + expected);
    }

    private static void testWithVariables(String express, Map<Character, Float> vs, Float expected)
            throws Exception {
        Float r = parseWithVariables(express, vs);
        System.out.println(
                r.equals(expected)
                        ? "Success "
                        : r + " is wrong. Right answer is " + express + "=" + expected);
    }

    private static void testConvert(String expre, String expected) {
        String r = convert(expre);
        System.out.println(
                r.equals(expected)
                        ? "Success"
                        : r + " is wrong. Right answer is " + expre + "=" + expected);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("1>:");
        test("(( 1+2)*(3+(4*5)))", 69f);
        test("((( 1+2)*(3+(4*5))) + 11)", 80f);

        System.out.println("2>:");
        test("((1+2))*(3+4*5)", 69f);
        test("((1+2))*(4*5+3)", 69f);
        test("(1+2)*(3+(4*5))", 69f);
        test("(1+2)*(3+4*5)", 69f);
        test("(1+2)*(4*5+3)", 69f);
        test("(1+2*4-3)", 6f);
        test("1+2*(4-3)", 3f);

        System.out.println("3>:");
        System.out.println("4>:");
        test("1+2*(-4-3)", -13f);
        test("1+2*(-3)-1", -6f);
        test("2-3", -1f);
        test("2-(2-3)", 3f);
        test("2-(-3*2)", 8f);
        test("2*(-3*2)+6", -6f);

        System.out.println("5>:");
        test("1+2*(-3.5)-1", -7f);
        test("1 + 2 - 3 * 4 / 5", 1 + 2 - 3 * 4 / 5f);
        test(" ( 1 * ( 2 + 2 ) - ( 3 - 4 ) / 5 )", 4.2f);

        System.out.println("6>:");
        testWithVariables(
                "  (x+2)*(3+y*5)",
                new HashMap<Character, Float>() {
                    {
                        put('x', 1f);
                        put('y', 2f);
                    }
                },
                39f);

        System.out.println("7>:");
        testConvert("( y + 2 ) * ( 3+x*5)", "3.0y+5.0xy+6.0+10.0x");
    }
}
