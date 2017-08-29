//  Copyright 2017 The KeepTry Open Source Project
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

package java_io;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CatchMailList {
  private static final String oldCandidatesFile =
      "/home/bzu/Desktop/noteSublines/interview/maillist.txt";
  private static final String mailBodyInHTML =
      "/home/bzu/Desktop/noteSublines/interview/mailbody.html";
  private static final String pstr = "[a-zA-Z0-9_.+-]+@[a-zA-Z0-9_.+-]+";
  private static final String urlFirstPart =
      "https://resume.corp.fortinet.com/core/elasticsearch_api.php"
          + "?type=1"
          + "&view_type=0"
          + "&sort=last_updated"
          + "&dir=DESC"
          + "&search_type=2"
          + "&job_id=4796"
          + "&function_area=any"
          + "&hire_manager=Zhao%2C+Hai"
          + "&show_status=any"
          + "&search=%2Bspring+CA"
          + "&sortChanged="
          + "&project_id=0"
          + "&btnName=filter";

  private static Set<String> currentCandidates() throws MalformedURLException, IOException {
    Set<String> mails = new TreeSet<>(Comparator.naturalOrder());
    Pattern p = Pattern.compile(pstr);
    for (int page = 0; page < 6; page++) {
      String url = urlFirstPart + String.format("&page_number=%d", page);
      System.out.println(url);
      URLConnection connection = new URL(url).openConnection();
      connection.setRequestProperty(
          "Cookie",
          "_ceg.s=otxtk0; _ceg.u=otxtk0; AMCV_ED8739F75677FE917F000101%40AdobeOrg=307333927%7CMCIDTS%7C17379%7CMCMID%7C45568674877141407264600553071192543353%7CMCAAMLH-1502079840%7C9%7CMCAAMB-1502079840%7CNRX38WO0n5BH8Th-nqAG_A%7CMCOPTOUT-1501482240s%7CNONE%7CMCAID%7CNONE; _ga=GA1.2.1493650771.1499818428; perm_login=1; MANTIS_STRING_COOKIE=0ace7f4798469e103d21fe94ea47ffc0e7bf42a1cd04e4e7ec964f72c98c67f1; MANTIS_VIEW_ALL_COOKIE1=%7B%22show_status%22%3A%22any%22%2C%22per_page%22%3A50%2C%22highlight_changed%22%3A6%2C%22reporter_id%22%3A%22any%22%2C%22handler_id%22%3A%22any%22%2C%22sort%22%3A%22last_updated%22%2C%22dir%22%3A%22DESC%22%2C%22search%22%3A%22%22%2C%22job_id%22%3A%22any%22%2C%22function_area%22%3A%22any%22%2C%22hire_manager%22%3A%22any%22%2C%22include_closed_job%22%3A0%2C%22search_email%22%3A%22%22%2C%22reference%22%3A%22%22%2C%22fullname%22%3A%22any%22%2C%22address%22%3A%22%22%2C%22email%22%3A%22%22%2C%22source_id%22%3A%22%22%2C%22search_type%22%3A1%2C%22group_id%22%3A%22any%22%2C%22my_candidates%22%3A%22%22%2C%22cookie_version%22%3A%22v4%22%7D");
      try (BufferedReader in =
          new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
          Matcher m = p.matcher(inputLine);
          while (m.find()) {
            mails.add(m.group());
          }
        }
      } catch (IOException e) {
        throw e;
      }
    }
    return mails;
  }

  private static Set<String> getOldCandidates() {
    try (BufferedReader br = new BufferedReader(new FileReader(new File(oldCandidatesFile)))) {

      Set<String> mails = new HashSet<>();
      String line;
      while ((line = br.readLine()) != null) {
        mails.addAll(Arrays.asList(line.split(",")));
      }
      System.out.println("Old candidates: " + mails.size());
      return mails;
    } catch (Exception e) {
      e.printStackTrace();
      return Collections.emptySet();
    }
  }

  private static String currentNewCandiates() throws Exception {
    Set<String> r = currentCandidates();
    r.removeAll(getOldCandidates());

    Iterator it = r.iterator();
    StringBuilder sb = new StringBuilder();
    while (it.hasNext()) {
      sb.append(it.next()).append(",");
    }
    System.out.println("\n\nNew Candidates: " + r.size());
    return sb.toString();
  }

  private static String getMailHTMLBody(String fileNanme) throws Exception {
    try (BufferedReader br = new BufferedReader(new FileReader(new File(fileNanme)))) {
      String line;
      StringBuilder sb = new StringBuilder();
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }
      return sb.toString();
    } catch (Exception e) {
      throw e;
    }
  }

  private static void appendNewCandidatesEails(String emails, String fileToAppend)
      throws Exception {
    // Files.write(Paths.get(oldCandidatesFile), tos.getBytes(), StandardOpenOption.APPEND);
    boolean append = true;
    try (PrintWriter pw =
        new PrintWriter(new BufferedWriter(new FileWriter(new File(fileToAppend), append)))) {
      pw.append(emails);
      pw.flush();
      pw.close();
    } catch (Exception e) {
      throw e;
    }
  }

  private static void mailOA(String tos, boolean save) throws Exception {
    System.out.println("new candidates: \n" + tos);
    String serverName = "mail.fortinet.com";
    //
    Properties props = System.getProperties();
    props.setProperty("mail.smtp.host", serverName);
    props.put("mail.smtp.socketFactory.port", "465");
    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.port", "465");
    //
    Session session =
        Session.getDefaultInstance(
            props,
            new javax.mail.Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("bzu", "FtBr123#");
              }
            });
    try {
      MimeMessage mimeMess = new MimeMessage(session);
      mimeMess.setFrom(new InternetAddress("bzu@fortinet.com"));
      System.out.println("not send emails to them");
      mimeMess.addRecipient(
          Message.RecipientType.BCC, new InternetAddress("bzu@fortinet.com")); // tos
      mimeMess.setSubject(
          "OA interview invitation from Fortinet for the AMRD - 2013: Jr. Java Software Engineer position");
      MimeMultipart multipart = new MimeMultipart();
      BodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setContent(getMailHTMLBody(mailBodyInHTML), "text/html");
      multipart.addBodyPart(messageBodyPart);
      mimeMess.setContent(multipart);
      // Transport.send(mimeMess);
    } catch (MessagingException e) {
      throw e;
    }

    if (save && tos != null) {
      appendNewCandidatesEails(tos, oldCandidatesFile);
    }
  }

  public static void main(String args[]) throws Exception {
     mailOA(currentNewCandiates(), false);
  }
}
