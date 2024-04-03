package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dto.EmailDTO;
import co.edu.uniquindio.proyecto.model.services.implementations.EmailServicioImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServicioTest {

    @Autowired
    private EmailServicioImp emailServicioImp;

    @Test
    public void enviarEmailTest(){
        EmailDTO emailDTO = new EmailDTO("Correo de prueba", "Este es un correo de prueba", "vargasloaizacristian@gmail.com");
        try {
            emailServicioImp.enviarCorreo(emailDTO);
            Assertions.assertTrue(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
