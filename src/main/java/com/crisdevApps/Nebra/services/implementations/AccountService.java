package com.crisdevApps.Nebra.services.implementations;

import com.crisdevApps.Nebra.dto.inputDto.ChangePasswordDTO;
import com.crisdevApps.Nebra.exceptions.ValidationException;
import com.crisdevApps.Nebra.model.User;
import com.crisdevApps.Nebra.repositories.UserRepository;
import com.crisdevApps.Nebra.services.interfaces.IAccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService implements IAccountService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public boolean DeleteAccount(String idUsuario) throws Exception {
        return false;
    }

    @Override
    public void ChangePassword(ChangePasswordDTO changePasswordDTO) {
        User user = userService.FindValidUserByEmail(changePasswordDTO.email());

        if(!changePasswordDTO.code().equals(user.getVerificationCode()))
            throw new ValidationException("Invalid email or code");

        user.setPassword(passwordEncoder.encode(changePasswordDTO.newPassword()));

        userRepository.save(user);
    }

    @Override
    public void SendRecoveryLink(String correo) throws Exception {

    }
}
