package com.crisdevApps.Nebra.services.implementations;

import com.crisdevApps.Nebra.dto.inputDto.ChangePasswordDTO;
import com.crisdevApps.Nebra.dto.inputDto.EmailDTO;
import com.crisdevApps.Nebra.dto.inputDto.CreateModeratorDTO;
import com.crisdevApps.Nebra.model.User;
import com.crisdevApps.Nebra.model.enums.UserRole;
import com.crisdevApps.Nebra.model.enums.UserState;
import com.crisdevApps.Nebra.services.interfaces.IModeratorService;
import com.crisdevApps.Nebra.repositories.BusinessRepository;
import com.crisdevApps.Nebra.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ModeratorService implements IModeratorService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailServicioImp;

    @Override
    public void CreateModerator(CreateModeratorDTO createModeratorDTO) throws Exception {

        User user = User.builder()
                .isThirdPartyUser(false)
                .userRole(UserRole.MODERATOR)
                .userState(UserState.ACTIVE)
                .location("Nebra Admin")
                .email(createModeratorDTO.email())
                .name(createModeratorDTO.name())
                .password(passwordEncoder.encode(createModeratorDTO.password()))
                .build();
        userRepository.save(user);

        emailServicioImp.SendEmail(new EmailDTO("Bienvenid@ a Unilocal", "Tu cuenta ha sido creada exitosamente", user.getEmail()));
    }
}
