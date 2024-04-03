package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.documents.Lugar;
import co.edu.uniquindio.proyecto.model.documents.Usuario;

public record DetalleComentario(
         String codigo,
         String titulo,
         int calificacion,
         String contenido,
         String idNegocio,
         String idUsuario,
         String nombreUsuario,
         String respuesta
) {
}
