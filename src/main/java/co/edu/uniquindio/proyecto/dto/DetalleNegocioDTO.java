package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.entities.Coordenada;
import co.edu.uniquindio.proyecto.model.entities.Horario;
import co.edu.uniquindio.proyecto.model.entities.Imagen;
import co.edu.uniquindio.proyecto.model.enums.Categoria;

import java.util.ArrayList;

public record DetalleNegocioDTO (
        String id,
        String nombre,
        String descripcion,
        ArrayList<Imagen> imagenes,
        ArrayList<String> telefonos,
        Categoria categoria,
        Coordenada ubicacion,
        ArrayList<Horario> horarios,
        String idUsuario
) {

}
