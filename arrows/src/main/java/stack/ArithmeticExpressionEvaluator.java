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
}
