package com.crisdevApps.Nebra.test;

import com.crisdevApps.Nebra.dto.inputDto.UpdateUserDTO;
import com.crisdevApps.Nebra.dto.outputDto.GetUserProfileDTO;
import com.crisdevApps.Nebra.dto.inputDto.CreateUserDTO;
import com.crisdevApps.Nebra.services.implementations.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.*;

@SpringBootTest
public class ClienteServicioTest {

    @Autowired
    private UserService usuarioServicio;


    @Test
    public void registrarseTest() throws Exception {
        File file = new File("src/test/resources/Javascript.png");
        InputStream inputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("imagen", file.getName(), "image/jpeg", inputStream);
        CreateUserDTO createUserDTO = new CreateUserDTO(
                "Cristian David Vargas",

                multipartFile,

                "crisvargas1234",

                "davidclaff4@gmail.com",

               "cris123",

                "Medellin, Colombia"
        );

        boolean respuesta = usuarioServicio.SignUp(createUserDTO);
        Assertions.assertTrue(respuesta);
    }

    @Test
    public void editarperfilTest() throws IOException {
        File file = new File("src/test/resources/Javascript.png");
        InputStream inputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("imagen", file.getName(), "image/jpeg", inputStream);
        UpdateUserDTO updateUserDTO = new UpdateUserDTO(
                "660976c557c6105686a33bc9",

                "David Vargas",

                multipartFile,

                "cris123",

                "cris123",

                "Armenia, Colombia"
        );
        boolean respuesta = false;
        try {
             respuesta = usuarioServicio.EditProfile(updateUserDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(respuesta);


    }

    @Test
    public void encontrarUsuarioTest(){
        GetUserProfileDTO usuario = null;
        try {
            usuario = usuarioServicio.GetUserProfile("66078d1c68de9f284821bfaf");
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
            respuesta = usuarioServicio.DeleteAccount("66078d1c68de9f284821bfaf");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(respuesta);
    }


    @Test
    public void recuperarContraseniaTest(){
        boolean respuesta;
        try {
            respuesta = usuarioServicio.SendRecoveryLink("davidclaff4@gmail.com");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Assertions.assertTrue(respuesta);
    }

}
