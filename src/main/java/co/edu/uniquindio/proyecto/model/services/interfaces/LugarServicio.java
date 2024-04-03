package co.edu.uniquindio.proyecto.model.services.interfaces;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documents.Lugar;
import co.edu.uniquindio.proyecto.model.enums.EstadoLugar;

import java.util.ArrayList;
import java.util.List;

public interface LugarServicio {

    boolean crearLugar(CrearNegocioDTO crearNegocioDTO) throws Exception;

    boolean actualizarNegocio(ActualizarNegocioDTO actualizarNegocioDTO) throws Exception;

    boolean eliminarNegocio(String idNegocio) throws Exception;

    List<DetalleNegocioDTO> buscarLugar(String busqueda) throws Exception;

    List<DetalleNegocioDTO> filtrarPorEstado(EstadoLugar estadoLugar) throws Exception;

    List<DetalleNegocioDTO>  listarLugaresPropietario(String idPropietario) throws Exception;

    boolean cambiarEstado(String idLugar, EstadoLugar estadoLugar) throws Exception;

    boolean archivarLugar(String idLugar) throws Exception;

    boolean crearRevision(CrearRevisionDTO crearRevisionDTO) throws Exception; /////

    boolean obtenerUbicacion(String idLugar) throws Exception;

    List<ObtenerNegocioDTO> listarLugaresCliente(String idCliente) throws Exception;




}
