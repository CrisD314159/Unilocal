package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.CrearDenunciaDTO;
import co.edu.uniquindio.proyecto.dto.DetalleDenuncia;
import co.edu.uniquindio.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyecto.model.services.implementations.DenunciaServicioImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/denuncias")
@RequiredArgsConstructor
public class DenunciaControlador {
    private final DenunciaServicioImp denunciaServicioImp;

    @PostMapping("/crear-denuncia")
    public ResponseEntity<MensajeDTO<String>> crearDenuncia(@Valid @RequestBody CrearDenunciaDTO crearDenunciaDTO) throws Exception {
        denunciaServicioImp.crearDenuncia(crearDenunciaDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "La denuncua fue creada con exito"));
    }

    @PostMapping("/aceptar-denuncia/{codigo}")
    public ResponseEntity<MensajeDTO<String>> aceptarDenuncia(@PathVariable String codigo) throws Exception {
        denunciaServicioImp.aceptarDenuncia(codigo);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Denuncia aceptada"));
    }

    @PostMapping("/rechazar-denuncia/{codigo}")
    public ResponseEntity<MensajeDTO<String>> rechazarDenuncia(@PathVariable String codigo) throws Exception {
        denunciaServicioImp.rechazarDenuncia(codigo);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Denuncia rechazada"));
    }

    @GetMapping("/obtener-denuncia/{codigo}")
    public ResponseEntity<MensajeDTO<DetalleDenuncia>> obtenerDenuncia(@PathVariable String codigo) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, denunciaServicioImp.obtenerDenuncia(codigo)));
    }

    @GetMapping("/obtener-denuncias")
    public ResponseEntity<MensajeDTO<List<DetalleDenuncia>>> obtenerDenuncias() throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, denunciaServicioImp.listarDenuncias()));
    }

    @GetMapping("/obtener-denuncias-rechazadas")
    public ResponseEntity<MensajeDTO<List<DetalleDenuncia>>> obtenerDenunciasRechazadas() throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, denunciaServicioImp.listarDenunciasRechazadas()));
    }



}
