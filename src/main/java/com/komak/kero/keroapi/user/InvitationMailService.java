package com.komak.kero.keroapi.user;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class InvitationMailService {

  private static final Logger LOG = Logger.getLogger(InvitationMailService.class);

  @Value("${invitation.sender.email}")
  private String sender;

  @Value("${invitation.sender.personal}")
  private String personal;

  @Value("${invitation.subject}")
  private String subject;

  @Value("${invitation.link}")
  private String link;

  @Autowired
  private JavaMailSender mailSender;

  public void sendInvitation(String emailTo, String inviteCode) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
      message.setFrom(new InternetAddress(sender, personal));
      message.setTo(emailTo);
      message.setSubject(subject);
      message.setText(generateInvitation(emailTo, inviteCode), true);
      mailSender.send(mimeMessage);
    }
    catch (MessagingException e) {
      LOG.error("Failed to send email.", e);
    }
    catch (IOException e) {
      LOG.error("Failed read email resources.", e);
    }
  }

  private String generateInvitation(String emailTo, String inviteCode) throws IOException {
    String invitationLink = link + emailTo + "/" + inviteCode;

    Resource resource = new ClassPathResource("invitation.html");
    InputStream resourceInputStream = resource.getInputStream();
    Scanner scanner = new Scanner(resourceInputStream);
    StringBuilder builder = new StringBuilder();
    while (scanner.hasNextLine()) {
      builder.append(scanner.nextLine());
    }
    String message = builder.toString();
    resourceInputStream.close();

    return message.replace("*", invitationLink);
  }
}
