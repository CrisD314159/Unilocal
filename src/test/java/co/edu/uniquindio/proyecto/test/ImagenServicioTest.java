package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.model.services.implementations.ImagenesServicioImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@SpringBootTest
public class ImagenServicioTest {

    @Autowired
    private ImagenesServicioImp imagenesServicioImp;

    @Test
    public void subirImagenTest() throws Exception {
        File file = new File("src/test/resources/Javascript.png");
        InputStream inputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("imagen", file.getName(), "image/jpeg", inputStream);
        Map resultado = imagenesServicioImp.subirImagen(multipartFile);

        System.out.println(resultado);
        Assertions.assertNotNull(resultado);
    }


    @Test
    public void eliminarImagen() throws Exception {
        Map respuesta = imagenesServicioImp.eliminarImagen("unilocal/revrxbzc9cn7b9mtny5w");

        System.out.println(respuesta);
        Assertions.assertNotNull(respuesta);
    }
}
