package com.crisdevApps.Nebra.services.implementations;

import com.crisdevApps.Nebra.dto.inputDto.EmailDTO;
import com.crisdevApps.Nebra.services.interfaces.IEmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {

    private final JavaMailSender javaMailSender;
    @Override
    public void SendEmail(EmailDTO emailDTO) throws Exception {
        MimeMessage mensaje = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje);
        helper.setSubject(emailDTO.asunto());
        helper.setText(emailDTO.cuerpo(), true);
        helper.setTo(emailDTO.destinatario());
        helper.setFrom("no_reply@dominio.com");
        javaMailSender.send(mensaje);

    }
}
