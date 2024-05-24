package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public record ActualizarUsuarioDTO (
        @NotBlank String id,

       @NotBlank String nombre,
        String fotoPerfil,

       @NotBlank String nickname,

       @NotBlank String ciudadResidencia
) {


}
