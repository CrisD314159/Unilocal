package com.crisdevApps.Nebra.test;

import com.crisdevApps.Nebra.dto.inputDto.CreateCommentDTO;
import com.crisdevApps.Nebra.dto.outputDto.DetalleComentario;
import com.crisdevApps.Nebra.services.implementations.CommentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CommentServicioTest {

    @Autowired
    private CommentService comentarioServicioImp;



    @Test
    public void crearComentarioTest(){
        CreateCommentDTO createCommentDTO = new CreateCommentDTO(
                "66098099c213596ba18c73c3",
                "66078d1c68de9f284821bfaf",
                "Que Malo",
                "Que comida tan maluca",
                2
        );
        boolean resultado;
        try {
            resultado = comentarioServicioImp.CreateComment(createCommentDTO);
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
            resultado = comentarioServicioImp.AnswerComment("6609d34752956f065a2701d1", "Gracias por tus comentarios");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Assertions.assertTrue(resultado);
    }

    @Test
    public void listarComentariosNegocioTest() throws Exception {
        List<DetalleComentario> detalleComentarioList = comentarioServicioImp.GetBusinessComments("66098099c213596ba18c73c3");
        if (detalleComentarioList.isEmpty()) Assertions.fail();
        System.out.println(detalleComentarioList);
        Assertions.assertTrue(true);

    }

    @Test
    public void obtenerPromedioTest() throws Exception {
        int promedio = comentarioServicioImp.CalculateBusinessAverageScore("66098099c213596ba18c73c3");
        System.out.println(promedio);
        Assertions.assertEquals(3, promedio);
    }
}
