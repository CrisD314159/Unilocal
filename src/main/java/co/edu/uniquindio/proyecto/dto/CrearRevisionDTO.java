package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CrearRevisionDTO (
        @NotBlank String idNegocio,
        @NotBlank String idModerador,
        @NotBlank @Length(max = 200) String motivo
){

}
