package co.edu.uniquindio.proyecto.model.services.interfaces;

import co.edu.uniquindio.proyecto.dto.CrearDenunciaDTO;

public interface DenunciaServicio {

    boolean crearDenuncia(CrearDenunciaDTO crearDenunciaDTO) throws Exception;
    boolean aceptarDenuncia(String idDenuncia) throws Exception;
    boolean rechazarDenuncia(String idDenuncia) throws Exception;

}
