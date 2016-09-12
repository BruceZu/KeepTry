/**
 * <pre>
 *
 * DP:
 *   can be thought of as 'smart' recursion.
 *   overlap parts can be cached
 *   would solve all dependent subproblems and then select one that would lead to optimal solution.
 *   You try all the places, but you store the previous result.
 *
 *   But, in dynamic programming, in general, to solve a given problem, we need to solve subproblems,
 *   then combine the solutions of the subproblems to reach an overall solution. So,
 *   dynamic always gives global optimal solution.
 *
 * @see <a href="https://www.quora.com/Greedy-algorithm-vs-dynamic-programming-Whats-the-difference"> from quora</a>
 * @see <a href="http://stackoverflow.com/questions/13713572/how-is-dynamic-programming-different-from-greedy-algorithms"> from stackoverflow</a>
 * @see <a href="http://www.mysmu.edu/faculty/hclau/greedy-dp.pdf"> ad  </a>
 */
package dp;