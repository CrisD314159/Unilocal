package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public record ActualizarUsuarioDTO (
        @NotBlank String id,

       @NotBlank String nombre,
        MultipartFile fotoPerfil,

       @NotBlank String nickname,

       @NotBlank @Length String password,

       @NotBlank String ciudadResidencia
) {


}
