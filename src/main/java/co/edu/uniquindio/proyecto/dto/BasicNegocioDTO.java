package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.entities.Coordenada;
import co.edu.uniquindio.proyecto.model.entities.Horario;
import co.edu.uniquindio.proyecto.model.enums.Categoria;
import co.edu.uniquindio.proyecto.model.enums.EstadoLugar;

import java.util.ArrayList;
import java.util.List;

public record BasicNegocioDTO(
        String codigoNegocio,
        String nombre,
        String descripcion,
        List<String> imagenes,
        ArrayList<String> telefonos,
        Categoria tipoNegocio,
        Coordenada ubicacion,
        ArrayList<Horario> horarios,
        String idUsuario

) {

}
