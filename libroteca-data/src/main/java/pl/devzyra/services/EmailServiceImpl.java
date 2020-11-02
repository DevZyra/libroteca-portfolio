package pl.devzyra.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.devzyra.model.entities.UserEntity;
import pl.devzyra.model.entities.VerificationTokenEntity;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private final VerificationTokenService verificationTokenService;
    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;

    @Value("${DNS}")
    private String dns;

    public EmailServiceImpl(VerificationTokenService verificationTokenService, TemplateEngine templateEngine, JavaMailSender javaMailSender) {
        this.verificationTokenService = verificationTokenService;
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
    }


    @Override
    public void sendHtmlMail(UserEntity user) throws MessagingException {
        VerificationTokenEntity verificationToken = verificationTokenService.getByUser(user);


        if (verificationToken != null) {
            String token = verificationToken.getToken();
            Context context = new Context();

            // Setting url with DNS and Token as query param
            String link = String.format("http://%s:8080/activation?token=%s", dns, token);

            context.setVariable("title", "Welcome to Libroteca! Please verify your email address");
            context.setVariable("link", link);

            // passing variables to HTML template
            String body = templateEngine.process("activation", context);

            // Sending msg
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(user.getEmail());
            helper.setSubject("Libroteca e-mail verification");
            helper.setText(body, true);

            javaMailSender.send(message);
        }
    }
}
