package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyecto.dto.RegistroClienteDTO;
import co.edu.uniquindio.proyecto.model.services.implementations.UsuarioServicioImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sign-up")
@RequiredArgsConstructor
public class RegistrarseControlador {
    private final UsuarioServicioImp usuarioServicioImp;

    @PostMapping("/registrar-cliente")
    public ResponseEntity<MensajeDTO<String>> registrarUsuario(@Valid @RequestBody RegistroClienteDTO registroClienteDTO)throws Exception{
        usuarioServicioImp.registrase(registroClienteDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Usuario registrado exitosamente"));
    }
}
