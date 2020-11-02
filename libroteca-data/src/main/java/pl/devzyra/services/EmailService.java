package pl.devzyra.services;

import pl.devzyra.model.entities.UserEntity;

import javax.mail.MessagingException;

public interface EmailService {

    void sendHtmlMail(UserEntity user) throws MessagingException;

}
