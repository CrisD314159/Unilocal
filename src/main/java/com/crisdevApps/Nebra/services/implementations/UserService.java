package com.crisdevApps.Nebra.services.implementations;


import com.crisdevApps.Nebra.dto.inputDto.*;
import com.crisdevApps.Nebra.dto.outputDto.*;
import com.crisdevApps.Nebra.exceptions.EntityNotFoundException;
import com.crisdevApps.Nebra.exceptions.UnauthorizedException;
import com.crisdevApps.Nebra.exceptions.ValidationException;
import com.crisdevApps.Nebra.mappers.BusinessMapper;
import com.crisdevApps.Nebra.mappers.UserMapper;
import com.crisdevApps.Nebra.model.Business;
import com.crisdevApps.Nebra.model.User;
import com.crisdevApps.Nebra.model.enums.UserRole;
import com.crisdevApps.Nebra.model.enums.UserState;
import com.crisdevApps.Nebra.repositories.UserRepository;
import com.crisdevApps.Nebra.services.interfaces.IBusinessService;
import com.crisdevApps.Nebra.services.interfaces.IUserService;
import com.crisdevApps.Nebra.repositories.BusinessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;
    private final EmailService emailServicioImp;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final IBusinessService businessService;
    private final BusinessMapper businessMapper;
    private final ImageService imagenesServicioImp;

    @Override
    public void SignUp(CreateUserDTO createUserDTO){

        if (UserExistsByEmail(createUserDTO.email())){
            throw new ValidationException("User already exists");
        }

        User user = User.builder()
                .password(passwordEncoder.encode(createUserDTO.password()))
                .name(createUserDTO.name())
                .email(createUserDTO.email())
                .location(createUserDTO.location())
                .userState(UserState.NOT_VERIFIED)
                .userRole(UserRole.USER)
                .isThirdPartyUser(false)
                .businessList(new ArrayList<>())
                .comments(new ArrayList<>())
                .favoriteBusiness(new ArrayList<>())
                .profilePicture(createUserDTO.profilePicture())
                .reports(new ArrayList<>())
                .build();

        userRepository.save(user);
        emailServicioImp.SendEmail(new EmailDTO("Bienvenid@ a Unilocal", "Tu cuenta ha sido creada exitosamente", user.getEmail()));
    }


    private boolean UserExistsByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }


    @Override
    public void EditProfile(UpdateUserDTO updateUserDTO) {
        User user = FindValidUserById(updateUserDTO.id());

        //File file = new File(updateUserDTO.fotoPerfil());
        //InputStream inputStream = new FileInputStream(file);
        //MockMultipartFile multipartFile = new MockMultipartFile("imagen", file.getName(), "image/jpeg", inputStream);

       // imagenesServicioImp.DeleteImage(user.getFotoDePerfil().getId());
        //Map imagenInfo = imagenesServicioImp.UploadImage(updateUserDTO.fotoPerfil());
        //Image imagen = new Image((String) imagenInfo.get("secure_url"), (String) imagenInfo.get("public_id"));

        user.setName(updateUserDTO.name());
        user.setLocation(updateUserDTO.location());

        userRepository.save(user);

    }

    @Override
    public GetUserProfileDTO GetUserProfile(UUID userId){
        User user = FindValidUserById(userId);

        return userMapper.toDTO(user, user.getBusinessList().size());
    }

    @Override
    public List<GetUserProfileDTO> GetUsers(String search, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<User> users = userRepository.findByNameIsLikeAndUserState(search, UserState.ACTIVE, pageable);

        return users.stream().map(user -> userMapper.toDTO(user, user.getBusinessList().size())).toList();

    }


    @Override
    public User FindValidUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) throw new EntityNotFoundException("User not found");
        User userFound = user.get();
        IsUserValid(userFound);
        return userFound;
    }

    @Override
    public User FindValidUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw new EntityNotFoundException("User not found");
        User userFound = user.get();
        IsUserValid(userFound);
        return user.get();
    }

    private void IsUserValid(User user){
        if(user.getUserState().equals(UserState.INACTIVE))
            throw new EntityNotFoundException("User not found");
        if(user.getUserState().equals(UserState.NOT_VERIFIED))
            throw new UnauthorizedException("User not found");
    }



}
