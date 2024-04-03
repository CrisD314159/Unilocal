package co.edu.uniquindio.proyecto.model.services.interfaces;

import co.edu.uniquindio.proyecto.dto.CrearComentarioDTO;
import co.edu.uniquindio.proyecto.dto.DetalleComentario;
import co.edu.uniquindio.proyecto.model.documents.Comentario;

import java.util.ArrayList;
import java.util.List;

public interface ComentarioServicio {
    boolean crearComentario(CrearComentarioDTO crearComentarioDTO) throws Exception;

    boolean responderComentario(String idComentario, String respuesta) throws Exception;

    List<DetalleComentario> listarComentariosNegocio(String idNegocio) throws Exception;

    int calcularPromedioCalificaciones(String idLugar) throws Exception;

}
