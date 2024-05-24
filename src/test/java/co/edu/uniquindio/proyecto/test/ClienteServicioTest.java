package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dto.ActualizarUsuarioDTO;
import co.edu.uniquindio.proyecto.dto.DetalleUsuarioDTO;
import co.edu.uniquindio.proyecto.dto.RegistroClienteDTO;
import co.edu.uniquindio.proyecto.model.services.implementations.UsuarioServicioImp;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@SpringBootTest
public class ClienteServicioTest {

    @Autowired
    private UsuarioServicioImp usuarioServicio;





    @Test
    public void encontrarUsuarioTest(){
        DetalleUsuarioDTO usuario = null;
        try {
            usuario = usuarioServicio.obtenerUsuario("66078d1c68de9f284821bfaf");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(usuario);
        Assertions.assertNotNull(usuario);

    }
    @Test
    public void eliminarCuentaTest(){
        boolean respuesta = false;
        try {
            respuesta = usuarioServicio.eliminarCuenta("66078d1c68de9f284821bfaf");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(respuesta);
    }


    @Test
    public void recuperarContraseniaTest(){
        boolean respuesta;
        try {
            respuesta = usuarioServicio.enviarLinkRecuperacion("davidclaff4@gmail.com");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Assertions.assertTrue(respuesta);
    }

}
