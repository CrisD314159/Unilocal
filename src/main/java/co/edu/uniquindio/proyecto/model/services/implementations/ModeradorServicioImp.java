package co.edu.uniquindio.proyecto.model.services.implementations;

import co.edu.uniquindio.proyecto.dto.CambioPasswordDTO;
import co.edu.uniquindio.proyecto.dto.EmailDTO;
import co.edu.uniquindio.proyecto.dto.LoginDTO;
import co.edu.uniquindio.proyecto.dto.TokenDTO;
import co.edu.uniquindio.proyecto.model.documents.Lugar;
import co.edu.uniquindio.proyecto.model.documents.Moderador;
import co.edu.uniquindio.proyecto.model.documents.Usuario;
import co.edu.uniquindio.proyecto.model.enums.EstadoLugar;
import co.edu.uniquindio.proyecto.model.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.model.services.interfaces.ModeradorServicio;
import co.edu.uniquindio.proyecto.repositorios.LugarRepo;
import co.edu.uniquindio.proyecto.repositorios.ModeradorRepo;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Override
    public boolean eliminarCuenta(String idUsuario) throws Exception {
        Optional<Moderador> moderadorOptional = moderadorRepo.findById(idUsuario);

        if (moderadorOptional.isEmpty()){
            throw new Exception("El moderador no ha sido encontrado");
        }

        Moderador moderador = moderadorOptional.get();

        if (moderador.getRegistro() == EstadoRegistro.INACTIVO){
            throw new Exception("El moderador ya se encuentra inactivo");
        }

        moderador.setRegistro(EstadoRegistro.INACTIVO);

        try {
            moderadorRepo.save(moderador);

        }catch (Exception e){
            throw new Exception("Hubo algún error con la base de datos");
        }

        return true;
    }

    @Override
    public boolean cambiarContrasenia(CambioPasswordDTO cambioPasswordDTO) throws Exception {
        return false;
    }

    @Override
    public boolean enviarLinkRecuperacion(String correo) throws Exception {
        return false;
    }

    @Override
    public TokenDTO iniciarSesion(LoginDTO loginDTO) throws Exception {
        return false;
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
    public boolean rechazarLugar(String idLugar, String mensaje) throws Exception{
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

}
