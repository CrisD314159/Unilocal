package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.entities.Coordenada;
import co.edu.uniquindio.proyecto.model.entities.Horario;
import co.edu.uniquindio.proyecto.model.enums.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public record ActualizarNegocioDTO(

        @NotBlank String id,
        @NotBlank String descripcion,
        @NotBlank String nombre,
        @NotEmpty ArrayList<String> telefonos,
        @NotEmpty ArrayList<MultipartFile> imagenes,
        Coordenada ubicacion,///
        @NotEmpty ArrayList<Horario> horarios
) {
}
