package co.edu.uniquindio.proyecto.model.services.implementations;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documents.Lugar;
import co.edu.uniquindio.proyecto.model.documents.Usuario;
import co.edu.uniquindio.proyecto.model.entities.Imagen;
import co.edu.uniquindio.proyecto.model.enums.EstadoLugar;
import co.edu.uniquindio.proyecto.model.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.model.services.interfaces.UsuarioServicio;
import co.edu.uniquindio.proyecto.repositorios.LugarRepo;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepo;
import co.edu.uniquindio.proyecto.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioServicioImp implements UsuarioServicio {

    private final UsuarioRepo usuarioRepo;
    private final LugarRepo lugarRepo;
    private final EmailServicioImp emailServicioImp;
    private final ImagenesServicioImp imagenesServicioImp;
    private final JWTUtils jwtUtils;
    @Override
    public boolean eliminarCuenta(String idUsuario) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioRepo.findById(idUsuario);

        if (usuarioOptional.isEmpty()){
            throw  new Exception("No existe ningun cliente");
        }

        Usuario usuario = usuarioOptional.get();

        if (usuario.getRegistro() == EstadoRegistro.INACTIVO){
            throw  new Exception("El usuario ya se encuentra inactivo");
        }
        usuario.setRegistro(EstadoRegistro.INACTIVO);

        try{
            usuarioRepo.save(usuario);
        }catch (Exception e){
            throw new Exception("Ocurrio un error con la base de datos");
        }
        return true;
    }

    @Override
    public boolean cambiarContrasenia(CambioPasswordDTO cambioPasswordDTO) throws Exception {
        return false;
    }

    @Override
    public boolean enviarLinkRecuperacion(String correo) throws Exception {
        Optional<Usuario> usuario = usuarioRepo.findByEmail(correo);

        if (usuario.isEmpty()){
            throw new Exception("El email no esta registrado");
        }

        //Asunto cuerpo destino

        emailServicioImp.enviarCorreo(new EmailDTO("Cambio de contraseña Unilocal", "Para cambiar la contraseña accede al link: link", correo));
        return true;
    }


    @Override
    public TokenDTO iniciarSesion(LoginDTO loginDTO) throws Exception {
        Optional<Usuario> clienteOptional = usuarioRepo.findByEmail(loginDTO.correo());
        if (clienteOptional.isEmpty()) {
            throw new Exception("El correo no se encuentra registrado");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Usuario cliente = clienteOptional.get();
        if( !passwordEncoder.matches(loginDTO.contrasenia(), cliente.getPassword()) ) {
            throw new Exception("La contraseña es incorrecta");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("rol", "CLIENTE");
        map.put("nombre", cliente.getNombre());
        map.put("id", cliente.getCodigo());
        return new TokenDTO( jwtUtils.generarToken(cliente.getEmail(), map) );
    }

    @Override
    public boolean registrase(RegistroClienteDTO registroClienteDTO) throws Exception {

        if (existeEmail(registroClienteDTO.email())){
            throw new Exception("El email ya existe");
        }

        if (existeNickname(registroClienteDTO.nickname())){
            throw new Exception("El nickname ya existe");
        }

        // Hay que hashear la contraseña

        // En este espacio se llama al metodo de guardar imagen, y se deben guardar la url y el id
       // Map imagenInfo = imagenesServicioImp.subirImagen(registroClienteDTO.fotoPerfil());

        Map imagenInfo = imagenesServicioImp.subirImagen(registroClienteDTO.fotoPerfil());
        Imagen imagen = new Imagen((String) imagenInfo.get("secure_url"), (String) imagenInfo.get("public_id"));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncriptada = passwordEncoder.encode(registroClienteDTO.password() );

        Usuario usuario = new Usuario();
        usuario.setNombre(registroClienteDTO.nombre());
        usuario.setFotoDePerfil(imagen);
        usuario.setDireccion(registroClienteDTO.ciudadResidencia());
        usuario.setRegistro(EstadoRegistro.ACTIVO);
        usuario.setEmail(registroClienteDTO.email());
        usuario.setUsername(registroClienteDTO.nickname());
        usuario.setPassword(passwordEncriptada);
        try{
            usuarioRepo.save(usuario);
        }catch (Exception e){
            throw new Exception("Hay un fallo en el servidor");
        }
        emailServicioImp.enviarCorreo(new EmailDTO("Bienvenico a Unilocal", "Tu cuenta ha sido creada exitosamente", usuario.getEmail()));
        return true;



    }

    private boolean existeNickname(String nickname) {
        Usuario usuario = usuarioRepo.findByUsername(nickname);
        return usuario != null;

    }

    private boolean existeEmail(String email) {
        Optional<Usuario> usuario = usuarioRepo.findByEmail(email);
        return usuario.isPresent();
    }

    @Override
    public boolean editarPerfil(ActualizarUsuarioDTO actualizarUsuarioDTO) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioRepo.findById(actualizarUsuarioDTO.id());

        if (usuarioOptional.isEmpty()){
            throw  new Exception("No existe ningun cliente");
        }

        Usuario usuario = usuarioOptional.get();

        if (usuario.getRegistro() == EstadoRegistro.INACTIVO) {
            throw new Exception("El usuario está inactivo");
        }
        imagenesServicioImp.eliminarImagen(usuario.getFotoDePerfil().getId());
        Map imagenInfo = imagenesServicioImp.subirImagen(actualizarUsuarioDTO.fotoPerfil());
        Imagen imagen = new Imagen((String) imagenInfo.get("secure_url"), (String) imagenInfo.get("public_id"));

        usuario.setNombre(actualizarUsuarioDTO.nombre());
        usuario.setUsername(actualizarUsuarioDTO.nickname());
        usuario.setDireccion(actualizarUsuarioDTO.ciudadResidencia());
        usuario.setFotoDePerfil(imagen);

        try{
            usuarioRepo.save(usuario);
        }catch (Exception e){
            throw new Exception("Error del servidor");
        }
        emailServicioImp.enviarCorreo(new EmailDTO("Cuenata actualizada", "Tu cuenta ha sido actualizada exitosamente", usuario.getEmail()));

        return true;
    }

    @Override
    public DetalleUsuarioDTO obtenerUsuario(String id) throws Exception {
        Optional<Usuario> usuario = usuarioRepo.findById(id);

        if (usuario.isEmpty()){
            throw new Exception("El usuario no existe");
        }

        Usuario user = usuario.get();

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
    public List<ItemUsuarioDTO> obtenerUsuarios(int pagina) {
        Page<Usuario> usuarios = usuarioRepo.findAll(PageRequest.of(pagina, 10));

        return usuarios.stream().filter(c -> c.getRegistro() == EstadoRegistro.ACTIVO).map(c ->
                new ItemUsuarioDTO(
                        c.getNombre(),
                        c.getUsername(),
                        c.getEmail(),
                        c.getDireccion()
                )

                ).toList();
        //List<Usuario> usuarios = usuarioRepo.findByEstado(EstadoRegistro.ACTIVO);
    }

    @Override
    public boolean archivarLugar(String idLugar) throws Exception {
        Optional<Lugar> lugarOptional = lugarRepo.findById(idLugar);

        if (lugarOptional.isEmpty()){
            throw new Exception("El lugar no pudo ser encontrado");
        }

        Lugar lugar = lugarOptional.get();
        lugar.setEstadoLugar(EstadoLugar.ARCHIVADO);

        try {
            lugarRepo.save(lugar);
        }catch (Exception e){
            throw new Exception("Hubo un error con la base de datos");
        }
        return true;
    }


    // falta el de republicar un lugar de nuevo
}
