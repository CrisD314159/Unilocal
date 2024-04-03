package co.edu.uniquindio.proyecto.model.services.implementations;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documents.Lugar;
import co.edu.uniquindio.proyecto.model.entities.Imagen;
import co.edu.uniquindio.proyecto.model.enums.EstadoLugar;
import co.edu.uniquindio.proyecto.model.services.interfaces.LugarServicio;
import co.edu.uniquindio.proyecto.repositorios.LugarRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LugarServicioImp implements LugarServicio {

    private final UsuarioServicioImp usuarioServicioImp;
    private final LugarRepo lugarRepo;
    private final ImagenesServicioImp imagenesServicioImp;
    @Override
    public boolean crearLugar(CrearNegocioDTO crearNegocioDTO) throws Exception {
        
        if (existeNombreLugar(crearNegocioDTO.nombre())){
            throw new Exception("Ya existe un negocio con ese nombre");
        }

        Lugar lugar = getLugar(crearNegocioDTO);

        try {
            lugarRepo.save(lugar);
        } catch (Exception e) {
            throw new Exception("Hubo un error con la base de datos");
        }

        return true;
    }

    private ArrayList<Imagen> almacenarImagenes(ArrayList<MultipartFile> imagenes) throws Exception {
        ArrayList<Imagen> listaImagenes = new ArrayList<>();

        for(MultipartFile multipartFile: imagenes){
            Map imagenMap = imagenesServicioImp.subirImagen(multipartFile);
            Imagen imagen = new Imagen((String) imagenMap.get("secure_url"), (String) imagenMap.get("public_id"));
            listaImagenes.add(imagen);
        }
        return  listaImagenes;
    }




    private Lugar getLugar(CrearNegocioDTO crearNegocioDTO) throws Exception {

        Lugar lugar = new Lugar();
        lugar.setEstadoLugar(EstadoLugar.ESPERA);
        lugar.setHorarios(crearNegocioDTO.horarios());
        lugar.setCategoria(crearNegocioDTO.categoria());
        lugar.setDescripcion(crearNegocioDTO.descripcion());
        lugar.setTelefonos(crearNegocioDTO.telefonos());
        lugar.setNombre(crearNegocioDTO.nombre());
        lugar.setUbicacion(crearNegocioDTO.ubicacion());
        lugar.setIdUsuario(crearNegocioDTO.idUsuario());
        lugar.setImagenes(almacenarImagenes(crearNegocioDTO.imagenes()));
        return lugar;
    }

    private boolean existeNombreLugar(String nombre) {
        Lugar lugar = lugarRepo.findByNombre(nombre);
        return lugar != null;
    }

    @Override
    public boolean actualizarNegocio(ActualizarNegocioDTO actualizarNegocioDTO) throws Exception {
        Optional<Lugar> lugarOptional = lugarRepo.findById(actualizarNegocioDTO.id());

        if (lugarOptional.isEmpty()){
            throw new Exception("El lugar no fue encontrado");
        }

        Lugar lugar = lugarOptional.get();
        lugar.setHorarios(actualizarNegocioDTO.horarios());
        lugar.setImagenes(almacenarImagenes(actualizarNegocioDTO.imagenes()));
        lugar.setUbicacion(actualizarNegocioDTO.ubicacion());
        lugar.setNombre(actualizarNegocioDTO.nombre());
        lugar.setDescripcion(actualizarNegocioDTO.descripcion());
        lugar.setTelefonos(actualizarNegocioDTO.telefonos());

        try{
            lugarRepo.save(lugar);
        } catch (Exception e) {
            throw new Exception("Hubo un error con la base de datos");
        }

        return true;
    }

    @Override
    public boolean eliminarNegocio(String idNegocio) throws Exception {
        Optional<Lugar> lugarOptional = lugarRepo.findById(idNegocio);

        if (lugarOptional.isEmpty()){
            throw new Exception("El lugar no fue encontrado");
        }

        Lugar lugar = lugarOptional.get();

        if (lugar.getEstadoLugar() == EstadoLugar.INACTIVO){
            throw new Exception("El lugar ya se encuentra eliminado");
        }
        lugar.setEstadoLugar(EstadoLugar.INACTIVO);


        try{
            lugarRepo.save(lugar);
        } catch (Exception e) {
            throw new Exception("Hubo un error con la base de datos");
        }

        return true;
    }

    @Override
    public List<DetalleNegocioDTO> buscarLugar(String busqueda) {
        ArrayList<Lugar> lugarPage = lugarRepo.findByNombreIsLike(busqueda, EstadoLugar.ACTIVO);
        return lugarPage.stream().map(c ->
                new DetalleNegocioDTO(
                        c.getCodigo(),
                        c.getNombre(),
                        c.getDescripcion(),
                        c.getImagenes(),
                        c.getTelefonos(),
                        c.getCategoria(),
                        c.getUbicacion(),
                        c.getHorarios(),
                        c.getIdUsuario()
                )
                ).toList();
    }

    @Override
    public List<DetalleNegocioDTO> filtrarPorEstado(EstadoLugar estadoLugar) throws Exception {
        ArrayList<Lugar> lugarPage = lugarRepo.findByEstadoLugar(estadoLugar);
       return lugarPage.stream().map(c ->
               new DetalleNegocioDTO(
                       c.getCodigo(),
                       c.getNombre(),
                       c.getDescripcion(),
                       c.getImagenes(),
                       c.getTelefonos(),
                       c.getCategoria(),
                       c.getUbicacion(),
                       c.getHorarios(),
                       c.getIdUsuario()

               )
               ).toList();
    }

    @Override
    public List<DetalleNegocioDTO> listarLugaresPropietario(String idPropietario) throws Exception {
        ArrayList<Lugar> lugarPage = lugarRepo.findByIdUsuario(idPropietario, EstadoLugar.ACTIVO);
        return lugarPage.stream().map(c ->
                new DetalleNegocioDTO(
                        c.getCodigo(),
                        c.getNombre(),
                        c.getDescripcion(),
                        c.getImagenes(),
                        c.getTelefonos(),
                        c.getCategoria(),
                        c.getUbicacion(),
                        c.getHorarios(),
                        c.getIdUsuario()

                )
        ).toList();
    }

    @Override
    public boolean cambiarEstado(String idLugar, EstadoLugar estadoLugar) throws Exception {
        Lugar lugar = obtenerLugar(idLugar);

        try{
            lugar.setEstadoLugar(estadoLugar);
            lugarRepo.save(lugar);

        } catch (Exception e) {
            throw new Exception(e);
        }
        return false;
    }

    private Lugar obtenerLugar(String idLugar) throws Exception {
        Optional<Lugar> lugarOptional = lugarRepo.findById(idLugar);
        if (lugarOptional.isEmpty()){
            throw new Exception("Lugar no encontrado");
        }

        return lugarOptional.get();

    }

    public ObtenerNegocioDTO obtenerLugarDetalle(String idLugar) throws Exception {
        Optional<ObtenerNegocioDTO> lugarOptional = lugarRepo.findByIdAndEstadoLugar(idLugar, EstadoLugar.ACTIVO);
        if (lugarOptional.isEmpty()){
            throw new Exception("Lugar no encontrado");
        }

        return lugarOptional.get();

    }

    @Override
    public boolean archivarLugar(String idLugar) throws Exception {
        // llamar a la implementacion de este metodo
        usuarioServicioImp.archivarLugar(idLugar);
        return false;
    }


    @Override
    public boolean crearRevision(CrearRevisionDTO crearRevisionDTO) throws Exception {
        return false;
    }

    @Override
    public boolean obtenerUbicacion(String idLugar) throws Exception {
        return false;
    }

    @Override
    public List<ObtenerNegocioDTO> listarLugaresCliente(String idCliente) throws Exception {
        return lugarRepo.findLugaresUsuario(idCliente, EstadoLugar.ACTIVO);
    }
}
