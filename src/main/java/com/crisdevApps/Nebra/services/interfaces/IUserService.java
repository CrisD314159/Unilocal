package com.crisdevApps.Nebra.services.interfaces;

import com.crisdevApps.Nebra.dto.inputDto.ActualizarUsuarioDTO;
import com.crisdevApps.Nebra.dto.inputDto.RegistroClienteDTO;
import com.crisdevApps.Nebra.dto.outputDto.DetalleUsuarioDTO;
import com.crisdevApps.Nebra.dto.outputDto.ItemUsuarioDTO;
import com.crisdevApps.Nebra.dto.outputDto.ObtenerNegocioDTO;
import com.crisdevApps.Nebra.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IUserService extends IAccountService {

    boolean SignUp(RegistroClienteDTO registroClienteDTO) throws  Exception;

    boolean EditProfile(ActualizarUsuarioDTO actualizarUsuarioDTO)  throws  Exception;

    DetalleUsuarioDTO GetUserProfile(String id) throws Exception;

    List<ItemUsuarioDTO> GetUsers(int pagina);

    void AddBusinessToUserFavorites(String codigo, String idNegocio) throws Exception;

    void RemoveBusinessFromUserFavorites(String codigo, String idNegocio) throws Exception;

    List<ObtenerNegocioDTO> GetUserFavoriteBusiness(String codigo) throws Exception;

    boolean SearchFavoriteBusiness(String codigo, String idNegocio) throws Exception;

    User FindValidUserByEmail(String email);

    User FindValidUserById(UUID id);
}
