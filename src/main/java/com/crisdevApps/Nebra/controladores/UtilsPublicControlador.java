package com.crisdevApps.Nebra.controladores;

import com.crisdevApps.Nebra.dto.inputDto.ChangePasswordDTO;
import com.crisdevApps.Nebra.dto.outputDto.GetUserProfileDTO;
import com.crisdevApps.Nebra.dto.outputDto.ErrorMessage;
import com.crisdevApps.Nebra.services.implementations.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/utils")
@RequiredArgsConstructor
public class UtilsPublicControlador {
    private final UserService usuarioServicioImp;


    @PutMapping("/recuperar/cambiar-password")
    public ResponseEntity<ErrorMessage<String>> restablecerPassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO) throws Exception {
        usuarioServicioImp.ChangePassword(changePasswordDTO);
        return ResponseEntity.ok().body(new ErrorMessage<>(false, "Contraseña cambiada exitosamente"));
    }

    @PostMapping("/recuperar/enviar-link/{correo}")
    public ResponseEntity<ErrorMessage<String>> enviarLinkPassword(@PathVariable String correo) throws Exception {
        usuarioServicioImp.SendRecoveryLink(correo);
        return ResponseEntity.ok().body(new ErrorMessage<>(false, "Link enviado exitosamente"));
    }

    @GetMapping("/recuperar/get-cliente/{codigo}")
    public ResponseEntity<ErrorMessage<GetUserProfileDTO>> obtenerCliente(@PathVariable String codigo) throws Exception{
        return ResponseEntity.ok().body(new ErrorMessage<>(false,  usuarioServicioImp.GetUserProfile(codigo)));
    }
}
