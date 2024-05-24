package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.enums.EstadoLugar;
import co.edu.uniquindio.proyecto.model.services.implementations.LugarServicioImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lugares")
@RequiredArgsConstructor
public class LugarPrivateControlador {
    private final LugarServicioImp lugarServicioImp;

    @PostMapping("/crear-lugar")
    public ResponseEntity<MensajeDTO<String>> crearLugar(@Valid @RequestBody CrearNegocioDTO crearNegocioDTO) throws Exception{
        lugarServicioImp.crearLugar(crearNegocioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Lugar creado exitosamente"));
    }

    @PutMapping("/actualizar-lugar")
    public ResponseEntity<MensajeDTO<String>> actualizarLugar (@Valid @RequestBody ActualizarNegocioDTO actualizarNegocioDTO) throws Exception{
        lugarServicioImp.actualizarNegocio(actualizarNegocioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Negocio actualizado exitosamente"));
    }

    @PutMapping("/archivar-lugar/{codigo}")
    public ResponseEntity<MensajeDTO<String>> actualizarLugar (@PathVariable String codigo) throws Exception{
        lugarServicioImp.archivarLugar(codigo);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Negocio archivado exitosamente"));
    }

    @DeleteMapping("/eliminar/{codigo}")
    public ResponseEntity<MensajeDTO<String>> eliminarLugar(@PathVariable String codigo)throws Exception{
        lugarServicioImp.eliminarNegocio(codigo);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Negocio eliminado exitosamente"));
    }

    @GetMapping("/moderator/get-place/{codigo}")
    public ResponseEntity<MensajeDTO<ObtenerNegocioDTO>> obtenerNegocioModerador(@PathVariable String codigo) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, lugarServicioImp.obtenerLugarDetalleModerador(codigo)));
    }

    @GetMapping("/search/estado/{estado}")
    public ResponseEntity<MensajeDTO<List<DetalleNegocioDTO>>> filtrarPorEstado (@PathVariable EstadoLugar estado) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false,lugarServicioImp.filtrarPorEstado(estado)));

    }
}
