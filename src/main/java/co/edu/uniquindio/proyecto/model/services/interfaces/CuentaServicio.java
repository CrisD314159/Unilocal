package co.edu.uniquindio.proyecto.model.services.interfaces;

import co.edu.uniquindio.proyecto.dto.CambioPasswordDTO;
import co.edu.uniquindio.proyecto.dto.LoginDTO;
import co.edu.uniquindio.proyecto.dto.TokenDTO;

public interface CuentaServicio {
    boolean eliminarCuenta(String idUsuario)  throws  Exception;

    boolean cambiarContrasenia(CambioPasswordDTO cambioPasswordDTO)  throws  Exception;

    boolean enviarLinkRecuperacion(String correo)  throws  Exception;

    TokenDTO iniciarSesion(LoginDTO loginDTO)  throws  Exception;
}
