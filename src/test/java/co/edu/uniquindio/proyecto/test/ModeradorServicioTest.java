package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dto.RegistroModeradorDTO;
import co.edu.uniquindio.proyecto.model.services.implementations.ModeradorServicioImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ModeradorServicioTest {
    @Autowired
    private ModeradorServicioImp moderadorServicioImp;

    @Test
    public void aceptarLugarTest(){
        boolean respuesta;
        try {
            respuesta = moderadorServicioImp.aprobarLugar("6609e1b81bdc893649825c23");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Assertions.assertTrue(respuesta);
    }

    @Test
    public void rechazarLugarTest(){
        boolean respuesta;
        try {
            respuesta = moderadorServicioImp.rechazarLugar("6609e1b81bdc893649825c23");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(respuesta);
    }

    @Test
    public void registrarModerador(){
        RegistroModeradorDTO registroModeradorDTO = new RegistroModeradorDTO("crisvargas", "vargasloaizacristian@gmail.com","cris123","Cristian Vargas" );
        boolean respuesta;
        try {
            respuesta = moderadorServicioImp.registrase(registroModeradorDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(respuesta);
    }



}
