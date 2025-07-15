package com.crisdevApps.Nebra.test;

import com.crisdevApps.Nebra.dto.inputDto.EmailDTO;
import com.crisdevApps.Nebra.services.implementations.EmailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IEmailServiceTest {

    @Autowired
    private EmailService emailServicioImp;

    @Test
    public void enviarEmailTest(){
        EmailDTO emailDTO = new EmailDTO("Correo de prueba", "Este es un correo de prueba", "vargasloaizacristian@gmail.com");
        try {
            emailServicioImp.SendEmail(emailDTO);
            Assertions.assertTrue(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
