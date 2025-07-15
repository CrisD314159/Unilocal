package com.crisdevApps.Nebra.services.interfaces;

import com.crisdevApps.Nebra.dto.inputDto.ChangePasswordDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountService {
    boolean DeleteAccount(String idUsuario)  throws  Exception;

    void ChangePassword(ChangePasswordDTO changePasswordDTO)  throws  Exception;

    void SendRecoveryLink(String correo)  throws  Exception;

}
