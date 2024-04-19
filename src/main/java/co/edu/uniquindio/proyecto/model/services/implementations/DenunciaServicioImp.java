package co.edu.uniquindio.proyecto.model.services.implementations;

import co.edu.uniquindio.proyecto.dto.CrearDenunciaDTO;
import co.edu.uniquindio.proyecto.dto.DetalleDenuncia;
import co.edu.uniquindio.proyecto.dto.DetalleUsuarioDTO;
import co.edu.uniquindio.proyecto.dto.EmailDTO;
import co.edu.uniquindio.proyecto.model.documents.Denuncia;
import co.edu.uniquindio.proyecto.model.documents.Lugar;
import co.edu.uniquindio.proyecto.model.enums.EstadoDenuncia;
import co.edu.uniquindio.proyecto.model.enums.EstadoLugar;
import co.edu.uniquindio.proyecto.model.services.interfaces.DenunciaServicio;
import co.edu.uniquindio.proyecto.repositorios.DenunciaRepo;
import co.edu.uniquindio.proyecto.repositorios.LugarRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DenunciaServicioImp implements DenunciaServicio {

    private final DenunciaRepo denunciaRepo;
    private final LugarRepo lugarRepo;
    private final EmailServicioImp emailServicioImp;
    private final UsuarioServicioImp usuarioServicioImp;
    @Override
    public boolean crearDenuncia(CrearDenunciaDTO crearDenunciaDTO) throws Exception {

        Denuncia denuncia = new Denuncia();
        denuncia.setEstadoDenuncia(EstadoDenuncia.ESPERA);
        denuncia.setIdlugar(crearDenunciaDTO.idNegocio());
        denuncia.setIdUsuario(crearDenunciaDTO.idUsuario());
        denuncia.setMotivo(crearDenunciaDTO.motivo());

        try {
            denunciaRepo.save(denuncia);
        } catch (Exception e) {
            throw new Exception("Ocurrió un error con la base de datos");
        }
        return true;
    }

    @Override
    public boolean aceptarDenuncia(String idDenuncia) throws Exception {
        Optional<Denuncia> denunciaOptional = denunciaRepo.findByIdDenunciaAndEstadoDenuncia(idDenuncia, EstadoDenuncia.ESPERA);
        if (denunciaOptional.isEmpty()){
            throw new Exception("No se pudo encontrar la denuncia");
        }

        Denuncia denuncia = denunciaOptional.get();
        String idLugar = denuncia.getIdlugar();
        Optional<Lugar> lugarOptional = lugarRepo.findById(idLugar);
        if (lugarOptional.isEmpty()) throw new Exception("Lugar no encontrado");
        Lugar lugar = lugarOptional.get();
        lugar.setEstadoLugar(EstadoLugar.INACTIVO);
        denuncia.setEstadoDenuncia(EstadoDenuncia.ACEPTADA);

        try {
            lugarRepo.save(lugar);
            denunciaRepo.save(denuncia);


        } catch (Exception e) {
            throw new Exception("Ocurrió un error con el servidor");
        }
        DetalleUsuarioDTO detalleUsuarioDTO = usuarioServicioImp.obtenerUsuario(denuncia.getIdUsuario());
        emailServicioImp.enviarCorreo(new EmailDTO("Denuncia aceptada", "Su denuncia ha sido aceptada exitosamente", detalleUsuarioDTO.email()));
        return true;
    }

    @Override
    public boolean rechazarDenuncia(String idDenuncia) throws Exception {
        Optional<Denuncia> denunciaOptional = denunciaRepo.findByIdDenunciaAndEstadoDenuncia(idDenuncia, EstadoDenuncia.ESPERA);
        if (denunciaOptional.isEmpty()){
            throw new Exception("No se pudo encontrar la denuncia");
        }

        Denuncia denuncia = denunciaOptional.get();
        if (denuncia.getEstadoDenuncia() == EstadoDenuncia.RECHAZADA){
            throw new Exception("La denuncia ya habia sido rechazada");
        }

        denuncia.setEstadoDenuncia(EstadoDenuncia.RECHAZADA);

        try {
            denunciaRepo.save(denuncia);
        } catch (Exception e) {
            throw new Exception("Ocurrió un error con el servidor");
        }
        DetalleUsuarioDTO detalleUsuarioDTO = usuarioServicioImp.obtenerUsuario(denuncia.getIdUsuario());
        emailServicioImp.enviarCorreo(new EmailDTO("Denuncia Rechazada", "Su denuncia ha sido rechazada", detalleUsuarioDTO.email()));
        return true;
    }

    @Override
    public List<DetalleDenuncia> listarDenuncias() {
        ArrayList<Denuncia> denuncias = denunciaRepo.findAllNotRejected(EstadoDenuncia.ESPERA);
        return denuncias.stream().map(d -> new DetalleDenuncia(d.getCodigo(), d.getIdUsuario(), d.getIdlugar(), d.getMotivo())).toList();

    }

    @Override
    public DetalleDenuncia obtenerDenuncia(String codigo) throws Exception {
        Optional<Denuncia> denunciaOptional = denunciaRepo.findById(codigo);
        if (denunciaOptional.isEmpty()){
            throw new Exception("No se pudo encontrar la denuncia");
        }
        Denuncia denuncia = denunciaOptional.get();
        return new DetalleDenuncia(denuncia.getCodigo(), denuncia.getIdUsuario(), denuncia.getIdlugar(), denuncia.getMotivo());
    }
}
