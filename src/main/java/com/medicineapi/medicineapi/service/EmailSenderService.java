package com.medicineapi.medicineapi.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
 
@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender emailSender;
 
    public void enviarSenhaTemporaria(String destinatario, String senhaTemporaria) throws MessagingException {
 
        MimeMessage message = emailSender.createMimeMessage();
 
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(destinatario);
        helper.setSubject("Sua senha temporária foi gerada");
 
        String mensagem = "Olá, \n\n Sua solicitação foi aceita pelo ADMIN!\n\n Sua senha temporária é: " + senhaTemporaria + "\n\nPor favor, faça login com essa senha e defina uma nova senha assim que possível.";
        helper.setText(mensagem);
 
        emailSender.send(message);
        System.out.println("foi o email!!!");
    }
}
