package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;

public record CrearComentarioDTO (
       @NotBlank String idNegocio,
       @NotBlank String idUsuario,
       @NotBlank String titulo,
       @NotBlank @Length(max = 200) String contenido,
       @PositiveOrZero @Max(5) int calificacion
) {

}
