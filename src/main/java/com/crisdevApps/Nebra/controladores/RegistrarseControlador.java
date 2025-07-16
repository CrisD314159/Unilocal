package com.crisdevApps.Nebra.controladores;

import com.crisdevApps.Nebra.dto.outputDto.ErrorMessage;
import com.crisdevApps.Nebra.dto.inputDto.CreateUserDTO;
import com.crisdevApps.Nebra.services.implementations.UserService;
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
    private final UserService usuarioServicioImp;

    @PostMapping("/registrar-cliente")
    public ResponseEntity<ErrorMessage<String>> registrarUsuario(@Valid @RequestBody CreateUserDTO createUserDTO)throws Exception{
        usuarioServicioImp.SignUp(createUserDTO);
        return ResponseEntity.ok().body(new ErrorMessage<>(false, "User registrado exitosamente"));
    }
}
