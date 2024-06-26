package co.edu.uniquindio.proyecto.model.services.implementations;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documents.Comentario;
import co.edu.uniquindio.proyecto.model.documents.Lugar;
import co.edu.uniquindio.proyecto.model.documents.Usuario;
import co.edu.uniquindio.proyecto.model.entities.Imagen;
import co.edu.uniquindio.proyecto.model.entities.Revision;
import co.edu.uniquindio.proyecto.model.enums.Categoria;
import co.edu.uniquindio.proyecto.model.enums.EstadoLugar;
import co.edu.uniquindio.proyecto.model.services.interfaces.LugarServicio;
import co.edu.uniquindio.proyecto.repositorios.ComentarioRepo;
import co.edu.uniquindio.proyecto.repositorios.LugarRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
    private final ComentarioRepo comentarioRepo;
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

    private ArrayList<Imagen> almacenarImagenes(ArrayList<String> imagenes) throws Exception {
        ArrayList<Imagen> listaImagenes = new ArrayList<>();


        for(String item: imagenes){
            Imagen imagen = new Imagen(item, null);
            listaImagenes.add(imagen);
        }

        return  listaImagenes;
    }




    private Lugar getLugar(CrearNegocioDTO crearNegocioDTO) throws Exception {

        Lugar lugar = new Lugar();
        lugar.setEstadoLugar(EstadoLugar.ESPERA);
        lugar.setHorarios(crearNegocioDTO.horarios());
        lugar.setCategoria(crearNegocioDTO.tipoNegocio());
        lugar.setDescripcion(crearNegocioDTO.descripcion());
        lugar.setTelefonos(crearNegocioDTO.telefonos());
        lugar.setNombre(crearNegocioDTO.nombre());
        lugar.setUbicacion(crearNegocioDTO.ubicacion());
        lugar.setIdUsuario(crearNegocioDTO.codigoCliente());
        lugar.setImagenes(almacenarImagenes(crearNegocioDTO.imagenes()));
        lugar.setListaRevisiones(new ArrayList<Revision>());
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
        lugar.setUbicacion(actualizarNegocioDTO.ubicacion());
        lugar.setNombre(actualizarNegocioDTO.nombre());
        lugar.setDescripcion(actualizarNegocioDTO.descripcion());
        lugar.setTelefonos(actualizarNegocioDTO.telefonos());
        if(!actualizarNegocioDTO.imagenes().isEmpty()){
            lugar.setImagenes(almacenarImagenes(actualizarNegocioDTO.imagenes()));
        }

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
                {
                    try {
                        return new DetalleNegocioDTO(
                                c.getCodigo(),
                                c.getNombre(),
                                c.getDescripcion(),
                                c.getImagenes().stream().map(Imagen::getLink).toList(),
                                c.getTelefonos(),
                                c.getCategoria(),
                                c.getUbicacion(),
                                c.getHorarios(),
                                calcularPromedioCalificaciones(c.getCodigo()),
                                c.getIdUsuario(),
                                c.getEstadoLugar()
                        );
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                ).toList();
    }

    @Override
    public List<DetalleNegocioDTO> filtrarPorEstado(EstadoLugar estadoLugar) throws Exception {
        ArrayList<Lugar> lugarPage = lugarRepo.findByEstadoLugar(estadoLugar);
       return lugarPage.stream().map(c ->

               {
                   try {
                       return new DetalleNegocioDTO(
                               c.getCodigo(),
                               c.getNombre(),
                               c.getDescripcion(),
                               c.getImagenes().stream().map(Imagen::getLink).toList(),
                               c.getTelefonos(),
                               c.getCategoria(),
                               c.getUbicacion(),
                               c.getHorarios(),
                               calcularPromedioCalificaciones(c.getCodigo()),
                               c.getIdUsuario(),
                               c.getEstadoLugar()

                       );
                   } catch (Exception e) {
                       throw new RuntimeException(e);
                   }
               }
               ).toList();
    }

    @Override
    public List<DetalleNegocioDTO> filtrarPorCategoria(Categoria categoria) throws Exception {
        ArrayList<Lugar> lugarPage = lugarRepo.findByCategoria(categoria);
        return lugarPage.stream().map(c ->
                {
                    try {
                        return new DetalleNegocioDTO(
                                c.getCodigo(),
                                c.getNombre(),
                                c.getDescripcion(),
                                c.getImagenes().stream().map(Imagen::getLink).toList(),
                                c.getTelefonos(),
                                c.getCategoria(),
                                c.getUbicacion(),
                                c.getHorarios(),
                                calcularPromedioCalificaciones(c.getCodigo()),
                                c.getIdUsuario(),
                                c.getEstadoLugar()

                        );
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        ).toList();
    }

    @Override
    public List<DetalleNegocioDTO> listarLugaresPropietario(String idPropietario) throws Exception {
        ArrayList<Lugar> lugarPage = lugarRepo.findByIdUsuario(idPropietario, EstadoLugar.ACTIVO);
        return lugarPage.stream().map(c ->
                {
                    try {
                        return new DetalleNegocioDTO(
                                c.getCodigo(),
                                c.getNombre(),
                                c.getDescripcion(),
                                c.getImagenes().stream().map(Imagen::getLink).toList(),
                                c.getTelefonos(),
                                c.getCategoria(),
                                c.getUbicacion(),
                                c.getHorarios(),
                                calcularPromedioCalificaciones(c.getCodigo()),
                                c.getIdUsuario(),
                                c.getEstadoLugar()

                        );
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
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
        Optional<Lugar> lugarOptional = lugarRepo.findLugar(idLugar, EstadoLugar.ACTIVO);
        if (lugarOptional.isEmpty()){
            throw new Exception("Lugar no encontrado");
        }
        Lugar lugar = lugarOptional.get();
        return new ObtenerNegocioDTO(
                lugar.getCodigo(),
                lugar.getNombre(),
                lugar.getDescripcion(),
                lugar.getImagenes().stream().map(Imagen::getLink).toList(),
                lugar.getTelefonos(),
                lugar.getCategoria(),
                lugar.getUbicacion(),
                lugar.getHorarios(),
                lugar.getIdUsuario()
        );
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

    @Override
    public String obtenerEmailUsuarioNegocio(String codigoLugar) throws Exception {
        Optional<Lugar> lugarOptional = lugarRepo.findById(codigoLugar);
        if (lugarOptional.isEmpty()){
            throw new Exception("El lugar no existe");
        }
        Lugar lugar = lugarOptional.get();
        DetalleUsuarioDTO usuarioOptional =  usuarioServicioImp.obtenerUsuario(lugar.getIdUsuario());
        return usuarioOptional.email();
    }

    @Override
    public List<ObtenerNegocioDTO> obtenerLugares() throws Exception {
     return null;
    }

    public ObtenerNegocioDTO obtenerLugarDetalleModerador(String codigo) throws Exception {
        Optional<Lugar> lugarOptional = lugarRepo.findById(codigo);
        if (lugarOptional.isEmpty()){
            throw new Exception("Lugar no encontrado");
        }
        Lugar lugar = lugarOptional.get();
        return new ObtenerNegocioDTO(
                lugar.getCodigo(),
                lugar.getNombre(),
                lugar.getDescripcion(),
                lugar.getImagenes().stream().map(Imagen::getLink).toList(),
                lugar.getTelefonos(),
                lugar.getCategoria(),
                lugar.getUbicacion(),
                lugar.getHorarios(),
                lugar.getIdUsuario()
        );
    }

    public int calcularPromedioCalificaciones(String idLugar) throws Exception {
        List<DetalleComentario> detalleComentarioList = listarComentariosNegocio(idLugar);
        int sumaCalificaciones = 0;
        for (DetalleComentario detalle: detalleComentarioList){
            sumaCalificaciones = sumaCalificaciones + detalle.calificacion();
        }
        if (detalleComentarioList.isEmpty()){
            return 0;
        }
        return (sumaCalificaciones/detalleComentarioList.size());
    }

    public List<DetalleComentario> listarComentariosNegocio(String idNegocio) throws Exception {
        ArrayList<Comentario> comentarioPage = comentarioRepo.findByIdNegocio(idNegocio);
        return comentarioPage.stream().map(c->
                new DetalleComentario(
                        c.getCodigo(),
                        c.getTitulo(),
                        c.getCalificacion(),
                        c.getContenido(),
                        c.getIdNegocio(),
                        c.getIdUsuario(),
                        "",
                        c.getRespuesta()

                )
        ).toList();
    }
}
