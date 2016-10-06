/**
 *  <pre>
 *      http://stackoverflow.com/questions/15811411/high-cpu-utilization-in-java-application-why
 * Thread Dump
 * percent ??
 * profiler
 *
 * run top -H and get PID of the thread with highest CPU.
 * http://www.tech-faq.com/how-to-use-the-unix-top-command.html
 * convert the PID to hex.
 * look for thread with the matching HEX PID in your thread dump.
 *
 * http://javadrama.blogspot.com/2012/02/why-is-java-eating-my-cpu.html
 * http://karunsubramanian.com/java/4-things-you-need-to-know-about-cpu-utilization-of-your-java-application/
 * https://dzone.com/articles/high-cpu-troubleshooting-guide
 * https://www.ibm.com/developerworks/community/blogs/aimsupport/entry/investigating_high_cpu_for_java_processes_on_linux_aix_hpux_solaris_windows_identifying_the_suspects?lang=en
 * http://code.nomad-labs.com/2010/11/18/identifying-which-java-thread-is-consuming-most-cpu/
 * https://benohead.com/java-debugging-100-precent-cpu-usage/
 * http://xmlandmore.blogspot.com/2013/09/how-to-troubleshoot-high-cpu-usage-of.html
 * http://www.javaworld.com/article/2077361/learn-java/profiling-cpu-usage-from-within-a-java-application.html
 *
 * https://blog.dataloop.io/2015/07/25/monitoring-java-apps-with-nagios-graphite-and-statsd/
 */
package debug_cpu_issue;