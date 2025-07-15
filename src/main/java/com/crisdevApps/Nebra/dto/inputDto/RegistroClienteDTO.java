package com.crisdevApps.Nebra.dto.inputDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record RegistroClienteDTO (
        @NotBlank @Length(max = 50) String nombre,

        String fotoPerfil,

        @NotBlank String nickname,

       @NotBlank @Email @Length(max = 100) String email,

       @NotBlank @Length(min = 5) String password,

       @NotBlank String ciudadResidencia

) {


}
