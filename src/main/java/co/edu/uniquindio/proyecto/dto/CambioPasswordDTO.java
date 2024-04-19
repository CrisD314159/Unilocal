package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CambioPasswordDTO (
        @NotBlank @Length(min=5) String passwordNuevo,
        @NotBlank String idUsuario,
        @NotBlank String email
) {
}
