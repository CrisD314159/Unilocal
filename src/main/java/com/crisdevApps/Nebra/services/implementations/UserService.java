package com.crisdevApps.Nebra.services.implementations;

import co.crisdevApps.Nebra.dto.*;
import co.edu.uniquindio.proyecto.dto.*;
import com.crisdevApps.Nebra.dto.inputDto.*;
import com.crisdevApps.Nebra.dto.outputDto.*;
import com.crisdevApps.Nebra.exceptions.EntityNotFoundException;
import com.crisdevApps.Nebra.exceptions.UnauthorizedException;
import com.crisdevApps.Nebra.model.Business;
import com.crisdevApps.Nebra.model.User;
import com.crisdevApps.Nebra.model.Image;
import com.crisdevApps.Nebra.model.enums.BusinessState;
import com.crisdevApps.Nebra.model.enums.UserState;
import com.crisdevApps.Nebra.repositories.UserRepository;
import com.crisdevApps.Nebra.services.interfaces.IUserService;
import com.crisdevApps.Nebra.repositories.BusinessRepository;
import com.crisdevApps.Nebra.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final ImageService imagenesServicioImp;
    private final JWTUtils jwtUtils;
    @Override
    public boolean DeleteAccount(String idUsuario) throws Exception {
        Optional<User> usuarioOptional = userRepository.findById(idUsuario);

        if (usuarioOptional.isEmpty()){
            throw  new Exception("No existe ningun cliente");
        }

        User user = usuarioOptional.get();

        if (user.getRegistro() == UserState.INACTIVE){
            throw  new Exception("El user ya se encuentra inactivo");
        }
        user.setRegistro(UserState.INACTIVE);

        try{
            userRepository.save(user);
        }catch (Exception e){
            throw new Exception("Ocurrio un success con la base de datos");
        }
        return true;
    }

    @Override
    public boolean ChangePassword(ChangePasswordDTO changePasswordDTO) throws Exception {
        Optional<User> usuarioOptional = userRepository.findById(changePasswordDTO.idUsuario());
        if (usuarioOptional.isEmpty()){
            throw new Exception("User no encontrado");
        }
        User user = usuarioOptional.get();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncriptada = passwordEncoder.encode(changePasswordDTO.passwordNuevo() );

        user.setPassword(passwordEncriptada);

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean SendRecoveryLink(String correo) throws Exception {
        Optional<User> usuarioOptional = userRepository.findByEmail(correo);
        if (usuarioOptional.isEmpty()){
            throw new Exception("El user no existe");
        }

        User user = usuarioOptional.get();
        emailServicioImp.SendEmail(new EmailDTO("Recuperar contraseña",
                "<h1>Hola!! Haz click en el siguiente botón para reestablecer la contraseña de tu cuenta en Unilocal</h1>" +
                        "<a href=\"http://localhost:4200/recuperar-contrasenia/"+ user.getCodigo()+"\"><button>Recuperar contraseña</button></a>", user.getEmail()));

        return true;
    }


    @Override
    public TokenDTO iniciarSesion(LoginDTO loginDTO) throws Exception {
        Optional<User> clienteOptional = userRepository.findByEmail(loginDTO.email());
        if (clienteOptional.isEmpty()) {
            throw new Exception("El correo no se encuentra registrado");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User cliente = clienteOptional.get();
        if( !passwordEncoder.matches(loginDTO.password(), cliente.getPassword()) ) {
            throw new Exception("La contraseña es incorrecta");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("rol", "CLIENTE");
        map.put("nombre", cliente.getNombre());
        map.put("id", cliente.getCodigo());
        return new TokenDTO( jwtUtils.generarToken(cliente.getEmail(), map) );
    }

    @Override
    public boolean SignUp(RegistroClienteDTO registroClienteDTO) throws Exception {

        if (existeEmail(registroClienteDTO.email())){
            throw new Exception("El email ya existe");
        }

        if (existeNickname(registroClienteDTO.nickname())){
            throw new Exception("El nickname ya existe");
        }

        // Hay que hashear la contraseña

        // En este espacio se llama al metodo de guardar imagen, y se deben guardar la url y el id
       // Map imagenInfo = imagenesServicioImp.UploadImage(registroClienteDTO.fotoPerfil());

        //File file = new File(registroClienteDTO.fotoPerfil());
        //InputStream inputStream = new FileInputStream(file);
        //MockMultipartFile multipartFile = new MockMultipartFile("imagen", file.getName(), "image/jpeg", inputStream);

        //Map imagenInfo = imagenesServicioImp.UploadImage(registroClienteDTO.fotoPerfil());
        //Image imagen = new Image((String) imagenInfo.get("secure_url"), (String) imagenInfo.get("public_id"));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncriptada = passwordEncoder.encode(registroClienteDTO.password() );

        User user = new User();
        user.setNombre(registroClienteDTO.nombre());
        user.setFotoDePerfil(new Image(registroClienteDTO.fotoPerfil(), null));
        user.setDireccion(registroClienteDTO.ciudadResidencia());
        user.setRegistro(UserState.ACTIVE);
        user.setEmail(registroClienteDTO.email());
        user.setUsername(registroClienteDTO.nickname());
        user.setPassword(passwordEncriptada);
        user.setFavoritos(new ArrayList<>());
        try{
            userRepository.save(user);
        }catch (Exception e){
            throw new Exception("Hay un fallo en el servidor");
        }
        emailServicioImp.SendEmail(new EmailDTO("Bienvenid@ a Unilocal", "Tu cuenta ha sido creada exitosamente", user.getEmail()));
        return true;



    }

    private boolean existeNickname(String nickname) {
        User user = userRepository.findByUsername(nickname);
        return user != null;

    }

    private boolean existeEmail(String email) {
        Optional<User> usuario = userRepository.findByEmail(email);
        return usuario.isPresent();
    }

    @Override
    public boolean EditProfile(ActualizarUsuarioDTO actualizarUsuarioDTO) throws Exception {
        Optional<User> usuarioOptional = userRepository.findById(actualizarUsuarioDTO.id());

        if (usuarioOptional.isEmpty()){
            throw  new Exception("No existe ningun cliente");
        }

        User user = usuarioOptional.get();

        if (user.getRegistro() == UserState.INACTIVE) {
            throw new Exception("El user está inactivo");

        }

        //File file = new File(actualizarUsuarioDTO.fotoPerfil());
        //InputStream inputStream = new FileInputStream(file);
        //MockMultipartFile multipartFile = new MockMultipartFile("imagen", file.getName(), "image/jpeg", inputStream);

       // imagenesServicioImp.DeleteImage(user.getFotoDePerfil().getId());
        //Map imagenInfo = imagenesServicioImp.UploadImage(actualizarUsuarioDTO.fotoPerfil());
        //Image imagen = new Image((String) imagenInfo.get("secure_url"), (String) imagenInfo.get("public_id"));

        user.setNombre(actualizarUsuarioDTO.nombre());
        user.setUsername(actualizarUsuarioDTO.nickname());
        user.setDireccion(actualizarUsuarioDTO.ciudadResidencia());
        if (actualizarUsuarioDTO.fotoPerfil() != null){
            user.setFotoDePerfil(new Image(actualizarUsuarioDTO.fotoPerfil(), null));
        }


        try{
            userRepository.save(user);
        }catch (Exception e){
            throw new Exception("Error del servidor");
        }
        emailServicioImp.SendEmail(new EmailDTO("Cuenta actualizada", "Tu cuenta ha sido actualizada exitosamente", user.getEmail()));

        return true;
    }

    @Override
    public DetalleUsuarioDTO GetUserProfile(String id) throws Exception {
        Optional<User> usuario = userRepository.findById(id);

        if (usuario.isEmpty()){
            throw new Exception("El usuario no existe");
        }

        User user = usuario.get();

        return new DetalleUsuarioDTO(
                user.getCodigo(),
                user.getNombre(),
                user.getFotoDePerfil(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getDireccion()
        );
    }

    @Override
    public List<ItemUsuarioDTO> GetUsers(int pagina) {
        ArrayList<User> users = (ArrayList<User>) userRepository.findAll();

        return users.stream().filter(c -> c.getRegistro() == UserState.ACTIVE).map(c ->
                new ItemUsuarioDTO(
                        c.getCodigo(),
                        c.getNombre(),
                        c.getUsername(),
                        c.getEmail(),
                        c.getDireccion()
                )

                ).toList();
        //List<User> users = usuarioRepo.findByEstado(UserState.ACTIVE);
    }

    @Override
    public boolean archivarLugar(String idLugar) throws Exception {
        Optional<Business> lugarOptional = businessRepository.findById(idLugar);

        if (lugarOptional.isEmpty()){
            throw new Exception("El business no pudo ser encontrado");
        }

        Business business = lugarOptional.get();
        business.setEstadoLugar(BusinessState.ARCHIVADO);

        try {
            businessRepository.save(business);
        }catch (Exception e){
            throw new Exception("Hubo un success con la base de datos");
        }
        return true;
    }

    @Override
    public List<BasicNegocioDTO> obtenerLugaresArchivados(String codigo) throws Exception {
        ArrayList<Business> businessPage = businessRepository.findByIdUsuario(codigo, BusinessState.ARCHIVADO);
        return businessPage.stream().map(c ->
                new BasicNegocioDTO(
                        c.getCodigo(),
                        c.getNombre(),
                        c.getDescripcion(),
                        c.getImagenes().stream().map(Image::getLink).toList(),
                        c.getTelefonos(),
                        c.getCategoria(),
                        c.getUbicacion(),
                        c.getHorarios(),
                        c.getIdUsuario()

                )
        ).toList();

    }

    @Override
    public boolean republicarLugar(String codigo) throws Exception {
        Optional<Business> lugarOptional = businessRepository.findById(codigo);

        if (lugarOptional.isEmpty()){
            throw new Exception("El business no existe");
        }

        Business business = lugarOptional.get();
        if (!(business.getEstadoLugar() == BusinessState.ARCHIVADO)) {
            throw new Exception("El business no se encuentra archivado");
        }

        business.setEstadoLugar(BusinessState.ACTIVE);

        try {
            businessRepository.save(business);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public void AddBusinessToUserFavorites(String codigo, String idNegocio) throws Exception{
        Optional<User> usuarioOptional = userRepository.findById(codigo);
        Optional<Business> lugarOptional = businessRepository.findById(idNegocio);
        if (usuarioOptional.isEmpty() || lugarOptional.isEmpty()){
            throw new Exception("El user o el lugar no existen");
        }
        User user = usuarioOptional.get();
        user.getFavoritos().add(lugarOptional.get().getCodigo());
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);

        }

    }

    @Override
    public void RemoveBusinessFromUserFavorites(String codigo, String idNegocio) throws Exception{
        Optional<User> usuarioOptional = userRepository.findById(codigo);
        Optional<Business> lugarOptional = businessRepository.findById(idNegocio);
        if (usuarioOptional.isEmpty() || lugarOptional.isEmpty()){
            throw new Exception("El user o el lugar no existen");
        }
        User user = usuarioOptional.get();
        user.getFavoritos().remove(lugarOptional.get().getCodigo());
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);

        }

    }

    @Override
    public List<ObtenerNegocioDTO> GetUserFavoriteBusiness(String codigo) throws Exception{
        Optional<User> usuarioOptional = userRepository.findById(codigo);
        if (usuarioOptional.isEmpty()){
            throw new Exception("El user no existe");
        }
        User user = usuarioOptional.get();
        return extractPlaces(user.getFavoritos()) ;

    }

    @Override
    public boolean SearchFavoriteBusiness(String codigo, String idNegocio) throws Exception {
        Optional<User> usuarioOptional = userRepository.findById(codigo);
        if (usuarioOptional.isEmpty()){
            throw new Exception("El user no existe");
        }
        User user = usuarioOptional.get();
        return user.getFavoritos().contains(idNegocio);
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

    private List<ObtenerNegocioDTO> extractPlaces(ArrayList<String> lugares) throws Exception{
        List<ObtenerNegocioDTO> lugaresDTO = new ArrayList<>();
        for (String negocio : lugares) {
            Optional<Business> lugarOptional = businessRepository.findById(negocio);
            if (lugarOptional.isEmpty()){
                throw new Exception("El business no existe");
            }
            Business business = lugarOptional.get();
            lugaresDTO.add(
                    new ObtenerNegocioDTO(
                            business.getCodigo(),
                            business.getNombre(),
                            business.getDescripcion(),
                            business.getImagenes().stream().map(Image::getLink).toList(),
                            business.getTelefonos(),
                            business.getCategoria(),
                            business.getUbicacion(),
                            business.getHorarios(),
                            business.getIdUsuario()
                    )
            );


        }
        return lugaresDTO;

    }


}
