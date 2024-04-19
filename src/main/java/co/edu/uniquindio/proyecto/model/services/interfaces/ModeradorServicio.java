package co.edu.uniquindio.proyecto.model.services.interfaces;

import co.edu.uniquindio.proyecto.dto.CambioPasswordDTO;
import co.edu.uniquindio.proyecto.dto.LoginDTO;

public interface ModeradorServicio extends CuentaServicio{
    boolean aprobarLugar(String idLugar) throws Exception;
    boolean rechazarLugar(String idLugar) throws Exception;
    boolean eliminarUsuario(String idUsuario) throws Exception;


}
