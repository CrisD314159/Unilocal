package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dto.CrearComentarioDTO;
import co.edu.uniquindio.proyecto.dto.DetalleComentario;
import co.edu.uniquindio.proyecto.model.services.implementations.ComentarioServicioImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ComentarioServicioTest {

    @Autowired
    private ComentarioServicioImp comentarioServicioImp;



    @Test
    public void crearComentarioTest(){
        CrearComentarioDTO crearComentarioDTO = new CrearComentarioDTO(
                "66098099c213596ba18c73c3",
                "66078d1c68de9f284821bfaf",
                "Que Malo",
                "Que comida tan maluca",
                2
        );
        boolean resultado;
        try {
            resultado = comentarioServicioImp.crearComentario(crearComentarioDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Assertions.assertTrue(resultado);
    }

    @Test
    public void eliminarComentarioTest(){

    }

    @Test
    public void responderComentarioTest(){
        boolean resultado;
        try {
            resultado = comentarioServicioImp.responderComentario("6609d34752956f065a2701d1", "Gracias por tus comentarios");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Assertions.assertTrue(resultado);
    }

    @Test
    public void listarComentariosNegocioTest() throws Exception {
        List<DetalleComentario> detalleComentarioList = comentarioServicioImp.listarComentariosNegocio("66098099c213596ba18c73c3");
        if (detalleComentarioList.isEmpty()) Assertions.fail();
        System.out.println(detalleComentarioList);
        Assertions.assertTrue(true);

    }

    @Test
    public void obtenerPromedioTest() throws Exception {
        int promedio = comentarioServicioImp.calcularPromedioCalificaciones("66098099c213596ba18c73c3");
        System.out.println(promedio);
        Assertions.assertEquals(3, promedio);
    }
}
