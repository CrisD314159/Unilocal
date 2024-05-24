package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.CambioPasswordDTO;
import co.edu.uniquindio.proyecto.dto.DetalleUsuarioDTO;
import co.edu.uniquindio.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyecto.model.services.implementations.UsuarioServicioImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/utils")
@RequiredArgsConstructor
public class UtilsPublicControlador {
    private final UsuarioServicioImp usuarioServicioImp;


    @PutMapping("/recuperar/cambiar-password")
    public ResponseEntity<MensajeDTO<String>> restablecerPassword(@Valid @RequestBody CambioPasswordDTO cambioPasswordDTO) throws Exception {
        usuarioServicioImp.cambiarContrasenia(cambioPasswordDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Contraseña cambiada exitosamente"));
    }

    @PostMapping("/recuperar/enviar-link/{correo}")
    public ResponseEntity<MensajeDTO<String>> enviarLinkPassword(@PathVariable String correo) throws Exception {
        usuarioServicioImp.enviarLinkRecuperacion(correo);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Link enviado exitosamente"));
    }

    @GetMapping("/recuperar/get-cliente/{codigo}")
    public ResponseEntity<MensajeDTO<DetalleUsuarioDTO>> obtenerCliente(@PathVariable String codigo) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,  usuarioServicioImp.obtenerUsuario(codigo)));
    }
}
