package com.crisdevApps.Nebra.services.implementations;

import com.crisdevApps.Nebra.dto.inputDto.ChangePasswordDTO;
import com.crisdevApps.Nebra.dto.inputDto.EmailDTO;
import com.crisdevApps.Nebra.exceptions.ValidationException;
import com.crisdevApps.Nebra.model.User;
import com.crisdevApps.Nebra.model.enums.UserState;
import com.crisdevApps.Nebra.repositories.UserRepository;
import com.crisdevApps.Nebra.services.interfaces.IAccountService;
import com.crisdevApps.Nebra.services.interfaces.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService implements IAccountService {

    private final UserRepository userRepository;
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void DeleteAccount(UUID userId){
        User user = userService.FindValidUserById(userId);

        user.setUserState(UserState.INACTIVE);

        userRepository.save(user);
    }

    @Override
    public void ChangePassword(ChangePasswordDTO changePasswordDTO) {
        User user = userService.FindValidUserByEmail(changePasswordDTO.email());

        if(!changePasswordDTO.code().equals(user.getRecoveryAccountToken()))
            throw new ValidationException("Invalid email or code");

        user.setPassword(passwordEncoder.encode(changePasswordDTO.newPassword()));

        userRepository.save(user);
    }

    @Override
    public void SendRecoveryLink(String email) {
        User user = userService.FindValidUserByEmail(email);


        emailServicioImp.SendEmail(new EmailDTO("Recuperar contraseña",
                "<h1>Hola!! Haz click en el siguiente botón para reestablecer la contraseña de tu cuenta en Unilocal</h1>" +
                        "<a href=\"http://localhost:4200/recuperar-contrasenia/"+ user.getRecoveryAccountToken()+"\"><button>Recuperar contraseña</button></a>", user.getEmail()));

    }
}
