package co.edu.uniquindio.proyecto.model.services.interfaces;

import co.edu.uniquindio.proyecto.dto.CrearDenunciaDTO;
import co.edu.uniquindio.proyecto.dto.DetalleDenuncia;

import java.util.List;

public interface DenunciaServicio {

    boolean crearDenuncia(CrearDenunciaDTO crearDenunciaDTO) throws Exception;
    boolean aceptarDenuncia(String idDenuncia) throws Exception;
    boolean rechazarDenuncia(String idDenuncia) throws Exception;

    List<DetalleDenuncia> listarDenuncias();

    DetalleDenuncia obtenerDenuncia(String codigo) throws Exception;
}
