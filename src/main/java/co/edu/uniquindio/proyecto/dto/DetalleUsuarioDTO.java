package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.entities.Imagen;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record DetalleUsuarioDTO (
        String id,
        String nombre,

        Imagen fotoPerfil,

        String nickname,

        String email,

        String password,

        String ciudadResidencia
) {

}
