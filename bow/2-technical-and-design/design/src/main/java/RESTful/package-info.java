/**
 * <pre>
 * Java Coding Assignment
 * Overview
 * We'd like you to create a RESTful web service in Java. Please read the instruction below carefully.
 * The service you develop should be deployable by a standard WAR file.
 *
 * Requirements
 * The service has two endpoints.
 *      One is to take a Unicode string as a parameter and register it in the service
 *      The other is to take a string id and return all registered strings that match with the string id
 *          String id is a number calculated as follows:
 *          String id is a sum of each character's id
 *          A character id is a sum of the current and previous character's Unicode code point
 *          The character id of the first character in the string is the character's Unicode code point
 *          If the current and previous characters are the same, the character id is the current character's Unicode code point
 *          String id must be calculated without using a loop
 *          Example:
 *              "abc" => 97 + (97 + 98) + (98 + 99)
 *              "abbc" => 97 + (97 + 98) + (98) + (98 + 99)
 *
 *      The registered strings must be persisted so that the strings are still available after a reboot
 *      Use a file for the persistent medium
 *      For easier troubleshooting, the file must be a text file
 *
 *      The REST APIs accept and produce JSON
 *
 *      The service doesn't have to be designed to work reliably in the clustered environment,
 *      but it must be reliable as a single node service.
 *
 *      The code must be thoroughly tested by unit tests
 *
 * Deliverables
 * Source code
 * Build and deployment instruction
 * REST API documentation
 * If you have a github account, you can create a repo there.
 * Otherwise send us your deliverables in a zip file
 * We expect a production grade code, not a throw-away test code
 */
package RESTful;