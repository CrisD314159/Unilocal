package co.edu.uniquindio.proyecto.model.services.interfaces;

import co.edu.uniquindio.proyecto.dto.LoginDTO;
import co.edu.uniquindio.proyecto.dto.TokenDTO;

public interface AutenticacionServicio {
    TokenDTO iniciarSesionCliente(LoginDTO loginDTO) throws Exception;
    TokenDTO iniciarSesionAdmin(LoginDTO loginDTO)throws  Exception;
}
