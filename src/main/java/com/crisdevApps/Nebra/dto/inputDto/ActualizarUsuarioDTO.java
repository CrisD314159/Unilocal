package com.crisdevApps.Nebra.dto.inputDto;

import jakarta.validation.constraints.NotBlank;

public record ActualizarUsuarioDTO (
        @NotBlank String id,

       @NotBlank String nombre,
        String fotoPerfil,

       @NotBlank String nickname,

       @NotBlank String ciudadResidencia
) {


}
