package com.crisdevApps.Nebra.services.implementations;

import com.crisdevApps.Nebra.dto.inputDto.LoginDTO;
import com.crisdevApps.Nebra.dto.outputDto.TokenDTO;
import com.crisdevApps.Nebra.exceptions.EntityNotFoundException;
import com.crisdevApps.Nebra.exceptions.UnauthorizedException;
import com.crisdevApps.Nebra.exceptions.ValidationException;
import com.crisdevApps.Nebra.model.Session;
import com.crisdevApps.Nebra.model.User;
import com.crisdevApps.Nebra.model.enums.UserState;
import com.crisdevApps.Nebra.security.JWTUtil;
import com.crisdevApps.Nebra.services.interfaces.IAuthService;
import com.crisdevApps.Nebra.repositories.UserRepository;
import com.crisdevApps.Nebra.services.interfaces.IUserService;
import com.crisdevApps.Nebra.services.interfaces.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final SessionRepository sessionRepository;
    private final JWTUtil jwtUtil;
    @Override
    public TokenDTO Login(LoginDTO loginDTO) {
        User user = userService.FindValidUserByEmail(loginDTO.email());

        if(user.isThirdPartyUser()) throw  new ValidationException("Use your Google account to sign in");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password())
        );

        if(authentication.isAuthenticated()){
            String token = jwtUtil.GenerateToken(user.getId(), user.getEmail(), false, null, user.getUserRole());
            String refresh = CreateSession(user);

            return new TokenDTO(token, refresh);
        }

        throw new UnauthorizedException("Invalid Credentials");
    }


    @Override
    public String CreateSession(User user) {
        Session session = Session.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(7))
                .build();
        sessionRepository.saveAndFlush(session);

        return jwtUtil.GenerateToken(user.getId(), user.getEmail(),
                true, session.getId().toString(), user.getUserRole());
    }


}
