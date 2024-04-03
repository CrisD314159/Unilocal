package co.edu.uniquindio.proyecto.model.services.implementations;

import co.edu.uniquindio.proyecto.dto.CrearComentarioDTO;
import co.edu.uniquindio.proyecto.dto.DetalleComentario;
import co.edu.uniquindio.proyecto.dto.DetalleUsuarioDTO;
import co.edu.uniquindio.proyecto.model.documents.Comentario;
import co.edu.uniquindio.proyecto.model.documents.Usuario;
import co.edu.uniquindio.proyecto.model.services.interfaces.ComentarioServicio;
import co.edu.uniquindio.proyecto.repositorios.ComentarioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ComentarioServicioImp implements ComentarioServicio {

    private final ComentarioRepo comentarioRepo;
    private final UsuarioServicioImp usuarioServicioImp;
    @Override
    public boolean crearComentario(CrearComentarioDTO crearComentarioDTO) throws Exception {

        Comentario comentario = new Comentario();
        comentario.setIdNegocio(crearComentarioDTO.idNegocio());
        comentario.setContenido(crearComentarioDTO.contenido());
        comentario.setIdUsuario(crearComentarioDTO.idUsuario());
        comentario.setTitulo(crearComentarioDTO.titulo());
        comentario.setCalificacion(crearComentarioDTO.calificacion());
        comentario.setRespuesta("");

        try{
            comentarioRepo.save(comentario);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean responderComentario(String idComentario, String respuesta) throws Exception {
        Optional<Comentario> comentarioOptional = comentarioRepo.findById(idComentario);

        if (comentarioOptional.isEmpty()){
            throw new Exception("El comentario no pudo ser encontrado");
        }

        Comentario comentario = comentarioOptional.get();
        comentario.setRespuesta(respuesta);

        try{
            comentarioRepo.save(comentario);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
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
                        obtenerNombreUsuario(c.getIdUsuario()),
                        c.getRespuesta()

                )
                ).toList();
    }

    private String obtenerNombreUsuario(String idUsuario) {
        DetalleUsuarioDTO usuario;
        try {
           usuario = usuarioServicioImp.obtenerUsuario(idUsuario);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return usuario.nombre();
    }


    // Ponerlo en lugar
    @Override
    public int calcularPromedioCalificaciones(String idLugar) throws Exception {
        List<DetalleComentario> detalleComentarioList = listarComentariosNegocio(idLugar);
        int sumaCalificaciones = 0;
        for (DetalleComentario detalle: detalleComentarioList){
            sumaCalificaciones = sumaCalificaciones + detalle.calificacion();
        }
        return (sumaCalificaciones/detalleComentarioList.size());
    }
}
