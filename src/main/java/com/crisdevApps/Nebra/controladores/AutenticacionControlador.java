package com.crisdevApps.Nebra.controladores;

import com.crisdevApps.Nebra.dto.inputDto.LoginDTO;
import com.crisdevApps.Nebra.dto.outputDto.ErrorMessage;
import com.crisdevApps.Nebra.dto.outputDto.TokenDTO;
import com.crisdevApps.Nebra.services.implementations.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AutenticacionControlador {

    private final AuthService autenticacionServicioImp;
    @PostMapping("/login-cliente")
    public ResponseEntity<ErrorMessage<TokenDTO>> iniciarSesionCliente(@Valid @RequestBody LoginDTO loginDTO) throws Exception {
        TokenDTO tokenDTO = autenticacionServicioImp.Login(loginDTO);
        return ResponseEntity.ok().body(new ErrorMessage<>(false, tokenDTO));
    }

    @PostMapping("/login-moderador")
    public ResponseEntity<ErrorMessage<TokenDTO>> iniciarSesionModerador(@Valid @RequestBody LoginDTO loginDTO) throws Exception {
        TokenDTO tokenDTO = autenticacionServicioImp.iniciarSesionAdmin(loginDTO);
        return ResponseEntity.ok().body(new ErrorMessage<>(false, tokenDTO));
    }
}
