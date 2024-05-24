package co.edu.uniquindio.proyecto.model.services.interfaces;

import co.edu.uniquindio.proyecto.dto.*;

import java.util.List;

public interface UsuarioServicio extends CuentaServicio{

    boolean registrase(RegistroClienteDTO registroClienteDTO) throws  Exception;

    boolean editarPerfil(ActualizarUsuarioDTO actualizarUsuarioDTO)  throws  Exception;

    DetalleUsuarioDTO obtenerUsuario(String id) throws Exception;

    List<ItemUsuarioDTO> obtenerUsuarios(int pagina);

    boolean archivarLugar(String idLugar)  throws  Exception;

   List<BasicNegocioDTO> obtenerLugaresArchivados(String codigo) throws Exception;


    boolean republicarLugar(String codigo) throws Exception;

    void agregarFavoritos(String codigo, String idNegocio) throws Exception;

    void quitarFavorito(String codigo, String idNegocio) throws Exception;

    List<ObtenerNegocioDTO> obtenerFavorito(String codigo) throws Exception;

    boolean buscarFavorito(String codigo, String idNegocio) throws Exception;
}
