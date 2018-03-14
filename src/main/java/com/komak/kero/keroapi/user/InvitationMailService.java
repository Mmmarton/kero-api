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

  @Value("${home.link}")
  private String home;

  @Autowired
  private JavaMailSender mailSender;

  public void sendInvitation(User user, String inviter) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
      message.setFrom(new InternetAddress(sender, personal));
      message.setTo(user.getEmail());
      message.setSubject(subject);
      message.setText(
          generateInvitation(user.getEmail(), user.getFirstName(), user.getUsername(), inviter),
          true);
      mailSender.send(mimeMessage);
    }
    catch (MessagingException e) {
      LOG.error("Failed to send email.", e);
    }
    catch (IOException e) {
      LOG.error("Failed read email resources.", e);
    }
  }

  private String generateInvitation(String emailTo, String target, String inviteCode,
      String inviter) throws IOException {
    String invitationLink = home + "register/" + emailTo + "/" + inviteCode;
    String message;
    InputStream resourceInputStream = null;
    try {
      Resource resource = new ClassPathResource("emailTemplate.html");
      resourceInputStream = resource.getInputStream();
      Scanner scanner = new Scanner(resourceInputStream);
      StringBuilder builder = new StringBuilder();
      while (scanner.hasNextLine()) {
        builder.append(scanner.nextLine());
      }
      message = builder.toString();
    }
    catch (IOException e) {
      LOG.error("IO error.", e);
      throw e;
    }
    finally {
      try {
        if (resourceInputStream != null) {
          resourceInputStream.close();
        }
      }
      catch (Exception e) {
        LOG.error("File operation error", e);
      }
    }

    return message.replace("REGISTER", invitationLink)
        .replace("HOME", home)
        .replace("EMAIL", emailTo)
        .replace("INVITER", inviter)
        .replace("TARGET", target);
  }
}
