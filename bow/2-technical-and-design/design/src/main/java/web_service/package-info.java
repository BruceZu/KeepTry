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
 * <pre>
 * The W3C defines a Web service generally as:
 * a software system designed to support interoperable machine-to-machine interaction over a network.[1]
 * Web services may use SOAP over HTTP protocol
 *
 * In a 2004 document, the W3C extended the definition:
 * We can identify two major classes of Web services:
 * REST-compliant Web services, in which the primary purpose of the service is to manipulate representations of Web resources using a uniform set of stateless operations.
 * Arbitrary Web services, in which the service may expose an arbitrary set of operations.[2]
 *
 * XML is the data format used to contain the data and provide metadata around it,
 * SOAP (Simple Object Access Protocol)
 *      is used to transfer the data,
 * WSDL (Web Services Description Language)
 *      is used for describing the services available and
 * UDDI (Universal Description, Discovery and Integration)
 *      lists what services are available.
 *
 * WSDL , which has a .wsdl extension.
 * (Proposals for Autonomous Web Services (AWS) seek to develop more flexible Web services which do not rely on strict rules.[3])
 * A directory called UDDI defines
 * which software system should be contacted for which type of data.
 * So when one software system needs one particular report/data,
 * it would go to the UDDI and find out which other system it can contact for receiving that data.
 *
 * Once the software system finds out which other system it should contact,
 * it would then contact that system using a special protocol called SOAP .
 * The service provider system would first validate the data request by referring to the WSDL file,
 * and then process the request and send the data under the SOAP protocol.
 *
 * Web services architecture: the service provider sends a WSDL file to UDDI.
 *
 * The service requester contacts UDDI to find out who is the provider for the data it needs,
 * and then it contacts the service provider using the SOAP protocol.
 *
 * The service provider validates the service request and sends structured data in an XML file,
 * using the SOAP protocol.
 *
 * This XML file would be validated again by the service requester using an XSD file.
 *
 * Critics of non-RESTful Web services often complain that
 * 1  complex[8] and based upon large software vendors or integrators, rather than typical open source implementations.
 * 2  performance due to 'Web services' use of XML as a message format and SOAP/HTTP in enveloping and transporting.[9]
 *
 * A Web API is a development in Web services where emphasis has been moving to simpler representational state transfer (REST)
 * based communications.[4]
 * RESTful APIs do not require XML-based Web service protocols (SOAP and WSDL) to support their interfaces.
 */
package web_service;