package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.CrearComentarioDTO;
import co.edu.uniquindio.proyecto.dto.DetalleComentario;
import co.edu.uniquindio.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyecto.model.services.implementations.ComentarioServicioImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
@RequiredArgsConstructor
public class ComentarioControlador {
    private final ComentarioServicioImp comentarioServicioImp;

    @PostMapping("/crear-comentario")
    public ResponseEntity<MensajeDTO<String>> crearComentario(@Valid @RequestBody CrearComentarioDTO crearComentarioDTO) throws  Exception{
        comentarioServicioImp.crearComentario(crearComentarioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Comentario creado"));
    }

    @PostMapping("/responder-comentario/{codigo}")
    public ResponseEntity<MensajeDTO<String>> responderComentario(@RequestBody String respuesta, @PathVariable String codigo) throws  Exception{
        comentarioServicioImp.responderComentario(codigo, respuesta);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Respuesta enviada"));
    }

    @GetMapping("/obtener-comentarios/{codigo}")
    public ResponseEntity<MensajeDTO<List<DetalleComentario>>> obtenerComentarios(@PathVariable String codigo) throws  Exception{

        return ResponseEntity.ok().body(new MensajeDTO<>(false, comentarioServicioImp.listarComentariosNegocio(codigo)));
    }

    @GetMapping("/obtener-promedio/{codigo}")
    public ResponseEntity<MensajeDTO<Integer>> obtenerPromedio(@PathVariable String codigo) throws  Exception{

        return ResponseEntity.ok().body(new MensajeDTO<>(false, comentarioServicioImp.calcularPromedioCalificaciones(codigo)));
    }




}
