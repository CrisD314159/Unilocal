package com.crisdevApps.Nebra.controladores;

import com.crisdevApps.Nebra.dto.inputDto.CreateCommentDTO;
import com.crisdevApps.Nebra.dto.outputDto.DetalleComentario;
import com.crisdevApps.Nebra.dto.outputDto.ErrorMessage;
import com.crisdevApps.Nebra.services.implementations.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
@RequiredArgsConstructor
public class ComentarioControlador {
    private final CommentService comentarioServicioImp;

    @PostMapping("/crear-comentario")
    public ResponseEntity<ErrorMessage<String>> crearComentario(@Valid @RequestBody CreateCommentDTO createCommentDTO) throws  Exception{
        comentarioServicioImp.CreateComment(createCommentDTO);
        return ResponseEntity.ok().body(new ErrorMessage<>(false, "Comment creado"));
    }

    @PostMapping("/responder-comentario/{codigo}")
    public ResponseEntity<ErrorMessage<String>> responderComentario(@RequestBody String respuesta, @PathVariable String codigo) throws  Exception{
        comentarioServicioImp.AnswerComment(codigo, respuesta);
        return ResponseEntity.ok().body(new ErrorMessage<>(false, "Respuesta enviada"));
    }

    @GetMapping("/obtener-comentarios/{codigo}")
    public ResponseEntity<ErrorMessage<List<DetalleComentario>>> obtenerComentarios(@PathVariable String codigo) throws  Exception{

        return ResponseEntity.ok().body(new ErrorMessage<>(false, comentarioServicioImp.GetBusinessComments(codigo)));
    }

    @GetMapping("/obtener-promedio/{codigo}")
    public ResponseEntity<ErrorMessage<Integer>> obtenerPromedio(@PathVariable String codigo) throws  Exception{

        return ResponseEntity.ok().body(new ErrorMessage<>(false, comentarioServicioImp.CalculateBusinessAverageScore(codigo)));
    }




}
