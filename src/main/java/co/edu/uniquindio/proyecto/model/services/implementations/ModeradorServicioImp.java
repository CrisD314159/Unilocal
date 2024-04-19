package co.edu.uniquindio.proyecto.model.services.implementations;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documents.Lugar;
import co.edu.uniquindio.proyecto.model.documents.Moderador;
import co.edu.uniquindio.proyecto.model.documents.Usuario;
import co.edu.uniquindio.proyecto.model.entities.Imagen;
import co.edu.uniquindio.proyecto.model.entities.Revision;
import co.edu.uniquindio.proyecto.model.enums.EstadoLugar;
import co.edu.uniquindio.proyecto.model.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.model.enums.TipoEstado;
import co.edu.uniquindio.proyecto.model.services.interfaces.ModeradorServicio;
import co.edu.uniquindio.proyecto.repositorios.LugarRepo;
import co.edu.uniquindio.proyecto.repositorios.ModeradorRepo;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepo;
import co.edu.uniquindio.proyecto.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ModeradorServicioImp implements ModeradorServicio {

    private final ModeradorRepo moderadorRepo;
    private final LugarRepo lugarRepo;
    private final UsuarioRepo usuarioRepo;
    private final EmailServicioImp emailServicioImp;
    private final UsuarioServicioImp usuarioServicioImp;
    private final JWTUtils jwtUtils;
    @Override
    public boolean eliminarCuenta(String idUsuario) throws Exception {
       return false;
    }

    @Override
    public boolean cambiarContrasenia(CambioPasswordDTO cambioPasswordDTO) throws Exception {
        Optional<Moderador> moderadorOptional = moderadorRepo.findById(cambioPasswordDTO.idUsuario());
        if (moderadorOptional.isEmpty()){
            throw new Exception("Usuario no encontrado");
        }
        Moderador moderador = moderadorOptional.get();

        moderador.setPassword(cambioPasswordDTO.passwordNuevo());

        try {
            moderadorRepo.save(moderador);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean enviarLinkRecuperacion(String correo) throws Exception {
        Optional<Moderador> moderadorOptional = moderadorRepo.findByEmail(correo);
        if (moderadorOptional.isEmpty()){
            throw new Exception("El usuario no existe");
        }

        Moderador moderador = moderadorOptional.get();
        emailServicioImp.enviarCorreo(new EmailDTO("Recuperar contraseña",
                "<h1>Hola!! Haz click en el siguiente botón para reestablecer la contraseña de tu cuenta en Unilocal</h1>" +
                        "<a href=\"http://localhost:4200/recuperar-contrasenia/"+moderador.getCodigo()+"\"><button>Recuperar contraseña</button></a>", moderador.getEmail()));

        return true;
    }

    @Override
    public TokenDTO iniciarSesion(LoginDTO loginDTO) throws Exception {
        Optional<Moderador> clienteOptional = moderadorRepo.findByEmail(loginDTO.email());
        if (clienteOptional.isEmpty()) {
            throw new Exception("El correo no se encuentra registrado");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Moderador moderador = clienteOptional.get();
        if( !passwordEncoder.matches(loginDTO.password(), moderador.getPassword()) ) {
            throw new Exception("La contraseña es incorrecta");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("rol", "CLIENTE");
        map.put("nombre", moderador.getNombre());
        map.put("id", moderador.getCodigo());
        return new TokenDTO( jwtUtils.generarToken(moderador.getEmail(), map) );
    }

    @Override
    public boolean aprobarLugar(String idLugar) throws Exception{
        Optional<Lugar> lugarOptional = lugarRepo.findById(idLugar);

        if (lugarOptional.isEmpty()){
            throw new Exception("El lugar no pudo ser encontrado");
        }

        Lugar lugar = lugarOptional.get();
        Optional<Usuario> usuarioOptional = usuarioRepo.findById(lugar.getIdUsuario());
        Usuario usuario = usuarioOptional.get();

        if (lugar.getEstadoLugar() != EstadoLugar.ESPERA){
            throw new Exception("Este lugar se encuentra activo o rechazado");
        }
        lugar.setEstadoLugar(EstadoLugar.ACTIVO);
        Revision revision = new Revision(TipoEstado.APROBADO, "Unilocal Moderators", lugar.getCodigo(), "El lugar cumple con las condiciones de Unilocal, por lo tanto ya se encuentra activo" );
        lugar.getListaRevisiones().add(revision);

        try {
            lugarRepo.save(lugar);
            emailServicioImp.enviarCorreo(new EmailDTO("Lugar Aprobado",
                    "Tu lugar ha sido aprovado en Unilical, felicitaciones!", usuario.getEmail()));
        }catch (Exception e){
            throw new Exception("Hubo un error con la base de datos");
        }
        return true;
    }

    @Override
    public boolean rechazarLugar(String idLugar) throws Exception{
        Optional<Lugar> lugarOptional = lugarRepo.findById(idLugar);

        if (lugarOptional.isEmpty()){
            throw new Exception("El lugar no pudo ser encontrado");
        }

        Lugar lugar = lugarOptional.get();
        Optional<Usuario> usuarioOptional = usuarioRepo.findById(lugar.getIdUsuario());
        Usuario usuario = usuarioOptional.get();
        if (lugar.getEstadoLugar() != EstadoLugar.ESPERA){
            throw new Exception("Este lugar se encuentra activo o inactivo");
        }
        lugar.setEstadoLugar(EstadoLugar.INACTIVO);
        Revision revision = new Revision(TipoEstado.DESAPROBADO, "Unilocal Moderators", lugar.getCodigo(), "El lugar no cumple con los terminos y condiciones de Unilocal" );
        lugar.getListaRevisiones().add(revision);

        try {
            lugarRepo.save(lugar);
            emailServicioImp.enviarCorreo(new EmailDTO("Lugar rechazado",
                    "El lugar ha sido rechazado ya que no cumple nuestros terminos y condiciones", usuario.getEmail()));
        }catch (Exception e){
            throw new Exception("Hubo un error con la base de datos");
        }
        return true;
    }

    @Override
    public boolean eliminarUsuario(String idUsuario) throws Exception {
        return usuarioServicioImp.eliminarCuenta(idUsuario);
    }


    // Registro del moderador (No tiene endpoint)
    public boolean registrase(RegistroModeradorDTO registroModeradorDTO) throws Exception {


        // Hay que hashear la contraseña

        // En este espacio se llama al metodo de guardar imagen, y se deben guardar la url y el id
        // Map imagenInfo = imagenesServicioImp.subirImagen(registroClienteDTO.fotoPerfil());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncriptada = passwordEncoder.encode(registroModeradorDTO.password() );

        Moderador usuario = new Moderador();
        usuario.setNombre(registroModeradorDTO.nombre());
        usuario.setEmail(registroModeradorDTO.email());
        usuario.setUsername(registroModeradorDTO.username());
        usuario.setPassword(passwordEncriptada);
        try{
            moderadorRepo.save(usuario);
        }catch (Exception e){
            throw new Exception("Hay un fallo en el servidor");
        }
        emailServicioImp.enviarCorreo(new EmailDTO("Bienvenid@ a Unilocal", "Tu cuenta ha sido creada exitosamente", usuario.getEmail()));
        return true;



    }

}
