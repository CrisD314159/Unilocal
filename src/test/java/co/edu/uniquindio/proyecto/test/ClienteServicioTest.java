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
    public void registrarseTest() throws Exception {
        File file = new File("src/test/resources/Javascript.png");
        InputStream inputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("imagen", file.getName(), "image/jpeg", inputStream);
        RegistroClienteDTO registroClienteDTO = new RegistroClienteDTO(
                "Cristian David Vargas",

                multipartFile,

                "crisvargas1234",

                "davidclaff4@gmail.com",

               "cris123",

                "Medellin, Colombia"
        );

        boolean respuesta = usuarioServicio.registrase(registroClienteDTO);
        Assertions.assertTrue(respuesta);
    }

    @Test
    public void editarperfilTest() throws IOException {
        File file = new File("src/test/resources/Javascript.png");
        InputStream inputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("imagen", file.getName(), "image/jpeg", inputStream);
        ActualizarUsuarioDTO actualizarUsuarioDTO = new ActualizarUsuarioDTO(
                "660976c557c6105686a33bc9",

                "David Vargas",

                multipartFile,

                "cris123",

                "cris123",

                "Armenia, Colombia"
        );
        boolean respuesta = false;
        try {
             respuesta = usuarioServicio.editarPerfil(actualizarUsuarioDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(respuesta);


    }

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
