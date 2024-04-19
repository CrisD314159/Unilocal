package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.DetalleDenuncia;
import co.edu.uniquindio.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyecto.model.services.implementations.DenunciaServicioImp;
import co.edu.uniquindio.proyecto.model.services.implementations.ModeradorServicioImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/moderadores")
@RequiredArgsConstructor
public class ModeradorControlador {
    private final ModeradorServicioImp moderadorServicioImp;
    private final DenunciaServicioImp denunciaServicioImp;

    @PostMapping("/aprobar-lugar/{codigo}")
    public ResponseEntity<MensajeDTO<String>> aprobarLugar(@PathVariable String codigo) throws Exception{
        moderadorServicioImp.aprobarLugar(codigo);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "EL negocio fue aprobado exitosamente"));
    }

    @PostMapping("/rechazar-lugar/{codigo}")
    public ResponseEntity<MensajeDTO<String>> rechazarLugar (@PathVariable String codigo) throws Exception{
        moderadorServicioImp.rechazarLugar(codigo);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "El lugar fue rechazado exitosamente"));
    }

    @DeleteMapping("/eliminar-usuario/{codigo}")
    public ResponseEntity<MensajeDTO<String>> eliminarUsuario (@PathVariable String codigo) throws Exception{
        moderadorServicioImp.eliminarUsuario(codigo);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "El usuario fue eliminado exitosamente"));
    }

    @GetMapping("/listar-denuncias")
    public  ResponseEntity<MensajeDTO<List<DetalleDenuncia>>> listarDenuncias() throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, denunciaServicioImp.listarDenuncias()));
    }


}
