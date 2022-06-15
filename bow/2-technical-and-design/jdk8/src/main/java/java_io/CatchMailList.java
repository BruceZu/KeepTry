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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CatchMailList {
  //    $ ls -l /home/bzu/Desktop/noteSublines-Mapping-what/interview/ | awk  -F ' ' '{printf( $9
  // "\n")}'
  //
  //    candidates
  //    mailbody_2007.html
  //    mailbody_2007.md
  //    mailbody.html
  //    mailbody.md
  //    maillist_2007_cur.txt
  //    maillist_2007_old.txt
  //    maillist_cur.txt
  //    maillist_old.txt

  private static final String MAIL_PATH = "/home/bzu/Desktop/3inchesBloodArrow/interview/";
  private static final String MAILS_OLD = MAIL_PATH + "maillist_old.txt";
  private static final String MAILS_2007_OLD = MAIL_PATH + "maillist_2007_old.txt";

  private static final String MAILS_CUR = MAIL_PATH + "maillist_cur.txt";
  private static final String MAILS_2007_CUR = MAIL_PATH + "maillist_2007_cur.txt";

  private static final String MAIL_BODY_2007 = MAIL_PATH + "mailbody_2007.html";
  private static final String MAIL_BODY = MAIL_PATH + "mailbody.html";

  private static final String pstr = "[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)+";
  private static final String URL_BASE =
      "https://resume.corp.xxx.com/core/elasticsearch_api.php"
          + "?type=1"
          + "&view_type=0"
          + "&sort=last_updated"
          + "&dir=DESC"
          + "&search_type=2"
          + "&function_area=R%26D"
          + "&hire_manager=Zhao%2C+Hai"
          + "&show_status=any"
          + "&search=%2Bspring+CA"
          + "&sortChanged="
          + "&project_id=0"
          + "&btnName=filter";
  private static final String COOKIE =
      "perm_login=1; MANTIS_STRING_COOKIE=0ace7f4798469e103d21fe94ea47ffc0e7bf42a1cd04e4e7ec964f72c98c67f1; MANTIS_PROJECT_COOKIE=0; MANTIS_VIEW_ALL_COOKIE1=%7B%22show_status%22%3A%22any%22%2C%22per_page%22%3A50%2C%22highlight_changed%22%3A6%2C%22reporter_id%22%3A%22any%22%2C%22handler_id%22%3A%22any%22%2C%22sort%22%3A%22last_updated%22%2C%22dir%22%3A%22DESC%22%2C%22search%22%3A%22%2Bspring+CA%22%2C%22job_id%22%3A%224796%22%2C%22function_area%22%3A%22R%26D%22%2C%22hire_manager%22%3A%22Zhao%2C+Hai%22%2C%22include_closed_job%22%3A%220%22%2C%22search_email%22%3A%22%22%2C%22reference%22%3A%22%22%2C%22fullname%22%3A%22any%22%2C%22address%22%3A%22%22%2C%22email%22%3A%22%22%2C%22source_id%22%3A%22%22%2C%22search_type%22%3A%222%22%2C%22group_id%22%3A%22any%22%2C%22my_candidates%22%3A%22%22%2C%22cookie_version%22%3A%22v4%22%7D";

  private static final String URL = URL_BASE + "&job_id=4796";

  private static final String URL_2007 = URL_BASE + "&job_id=4648";

  private static Set<String> currentCandidates(boolean isSenior)
      throws MalformedURLException, IOException {
    Set<String> mails = new TreeSet<>(Comparator.naturalOrder());
    Pattern p = Pattern.compile(pstr);
    for (int page = 0; page < 6; page++) {
      String url = isSenior ? URL_2007 : URL;
      url += String.format("&page_number=%d", page);
      // System.out.println(url);
      URLConnection connection = new URL(url).openConnection();
      connection.setRequestProperty("Cookie", COOKIE);
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

  private static Set<String> getOldCandidates(boolean isSenior) {
    try (BufferedReader br =
        new BufferedReader(new FileReader(new File(isSenior ? MAILS_2007_OLD : MAILS_OLD)))) {

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

  private static String currentNewCandiates(boolean isSenior) throws Exception {
    Set<String> r = currentCandidates(isSenior);
    r.removeAll(getOldCandidates(isSenior));

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

  private static void keepCurCandidatesEails(String emails, boolean isSenior) throws IOException {
    String pathToSave = isSenior ? MAILS_2007_CUR : MAILS_CUR;
    Files.write(Paths.get(pathToSave), emails.getBytes(), StandardOpenOption.WRITE);
    System.out.println("Saved current new candidate to " + pathToSave);
  }

  private static void appendToOldCandidatesEails(String newCandidates, boolean isSenior)
      throws Exception {
    String pathToAppend = isSenior ? MAILS_2007_OLD : MAILS_OLD;
    boolean append = true;
    try (PrintWriter pw =
        new PrintWriter(new BufferedWriter(new FileWriter(new File(pathToAppend), append)))) {
      pw.append(newCandidates);
      pw.flush();
      pw.close();
      System.out.println("Appened current new candidate to " + pathToAppend);
    } catch (Exception e) {
      throw e;
    }
  }

  private static void mailOA(String toCurs, boolean dryrun, boolean isSenior) throws Exception {

    String serverName = "mail.xxx.com";
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
      mimeMess.setFrom(new InternetAddress("bzu@xxx.com"));
      mimeMess.addRecipients(
          Message.RecipientType.BCC,
          InternetAddress.parse(dryrun ? "bzu@xxx.com" : toCurs + "bzu@xxx.com")); // tos
      mimeMess.setSubject(
          String.format(
              "OA interview invitation from xxx for the AMRD - %d: %sr. Java Software Engineer position",
              isSenior ? 2007 : 2013, isSenior ? "S" : "J"));
      MimeMultipart multipart = new MimeMultipart();
      BodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setContent(
          getMailHTMLBody(isSenior ? MAIL_BODY_2007 : MAIL_BODY), "text/html");
      multipart.addBodyPart(messageBodyPart);
      mimeMess.setContent(multipart);

      Transport.send(mimeMess);

    } catch (MessagingException e) {
      throw e;
    }
  }

  public static void whereAre(String mails) {
    try {
      Set<String> candiCS = currentCandidates(true);
      Set<String> candiCJ = currentCandidates(true);
      Set<String> candiOS = getOldCandidates(false);
      Set<String> candiOJ = getOldCandidates(false);
      String[] they = mails.split(",");
      for (String c : they) {
        if (candiCS.contains(c)) {
          System.out.println(c + " in the current progressing of Senior position");
        } else if (candiCJ.contains(c)) {
          System.out.println(c + " in the current progressing of Junior position");
        } else if (candiOS.contains(c)) {
          System.out.println(c + " in processed for the Senior position");
        } else if (candiOJ.contains(c)) {
          System.out.println(c + " in processed for the Junior position");
        } else {
          String curS = currentNewCandiates(true);
          if (curS.contains(c)) {
            System.out.println(c + " is in the current process for position of  Senior");
          } else {
            String curJ = currentNewCandiates(false);
            if (curJ.contains(c)) {

              System.out.println(c + " is in the current process for position of Junior");
            } else {
              System.out.println(
                  c
                      + "maybe not in resume DB or is not pick up because this resume does not match the standard");
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void recruiting(boolean isSenior, boolean dryrun, boolean append, boolean keepCur)
      throws Exception {
    String toCurs = currentNewCandiates(isSenior);
    System.out.println("new candidates: \n" + toCurs);

    mailOA(toCurs, dryrun, isSenior);
    if (append) {
      appendToOldCandidatesEails(toCurs, isSenior);
    }
    if (keepCur) {
      keepCurCandidatesEails(toCurs, isSenior);
    }
  }

  public static void main(String args[]) throws Exception {
    String they = "zhyue@xxx.edu,shawnxxy@xxx.com,liukahn@xxx.com";
    whereAre(they);

    // recruiting(false, true, false, false);
  }
}
