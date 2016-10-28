//  Copyright 2016 The Sawdust Open Source Project
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

/**
 * https://paragonie.com/blog/2015/06/preventing-xss-vulnerabilities-in-php-everything-you-need-know
 * 1  Use Content-Security-Policy headers and HTTPS-only cookies.
 * 2 Your first line of defense against XSS attacks should be filtering any tainted information
 *   before inserting them in the DOM not before storing it in a database.
 * 3 If you can avoid accepting actual HTML by opting for Markdown, etc. then don't accept HTML.
 * 4 If you're using a templating engine such as Twig, use {% autoescape %} directives and |e filters where appropriate.
 *   {% autoescape %} should be prioritized over escaping every variable.
 * 5 If you're not using a templating engine and need to safely render user-provided HTML,
 *   use HTML Purifier. Feel free to leverage caching for optimization, but keep an intact copy on-hand.
 * 6 Otherwise, use noHTML() and leave nothing to chance.
 *
 * https://www.owasp.org/index.php/XSS_(Cross_Site_Scripting)_Prevention_Cheat_Sheet#XSS_Prevention_Rules_Summary
 * https://en.wikipedia.org/wiki/Cross-site_scripting#Preventive_measures
 */
package security.cross_site_scripting_XSS;