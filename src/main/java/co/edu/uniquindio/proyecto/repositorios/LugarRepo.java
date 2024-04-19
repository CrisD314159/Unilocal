package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.dto.ObtenerNegocioDTO;
import co.edu.uniquindio.proyecto.model.documents.Lugar;
import co.edu.uniquindio.proyecto.model.enums.Categoria;
import co.edu.uniquindio.proyecto.model.enums.EstadoLugar;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface LugarRepo extends MongoRepository<Lugar, String> {

    Lugar findByNombre(String nombre);


    ArrayList<Lugar> findByEstadoLugar(EstadoLugar estadoLugar);

    @Query("{'nombre': ?0, 'estadoLugar': ?1}")
    ArrayList<Lugar> findByNombreIsLike(String nombre, EstadoLugar activo);

    @Query("{'idUsuario': ?0, 'estadoLugar': ?1}")
    ArrayList<Lugar> findByIdUsuario(String idPropietario, EstadoLugar activo);

    @Query("{'_id': ?0, 'estadoLugar': ?1}")
    Optional<Lugar> findLugar(String id, EstadoLugar activo);

    @Aggregation({"{$match: {idUsuario: ?0} }"})
    List<Lugar> encontrarPropietarios (String codigoCliente);


    @Aggregation(
            {"{$match: {_id: ?0, estadoLugar: ?1}}",
            "{$lookup: {from: 'usuario', localField:'idUsuario', foreignField: '_id', as: 'user'}}",
            "{$project: { nombre: '$nombre', descripcion: '$descripcion', imagenes: '$imagenes', telefonos: '$telefonos', categoria: '$categoria', ubicacion: '$ubicacion', horarios: '$horarios', nombreUsuario: '$user.nombre' } }"
    })
    Optional<ObtenerNegocioDTO> findByIdAndEstadoLugar(String id, EstadoLugar estadoLugar);

    @Aggregation(
            {"{$match: {idUsuario: ?0, estadoLugar: ?1}}",
                    "{$lookup: {from: 'usuario', localField:'idUsuario', foreignField: '_id', as: 'user'}}",
                    "{$project: { nombre: '$nombre', descripcion: '$descripcion', imagenes: '$imagenes', telefonos: '$telefonos', categoria: '$categoria', ubicacion: '$ubicacion', horarios: '$horarios', nombreUsuario: '$user.nombre' } }"
            })
    List<ObtenerNegocioDTO> findLugaresUsuario(String idCliente, EstadoLugar estadoLugar);

    ArrayList<Lugar> findByCategoria(Categoria categoria);
}
