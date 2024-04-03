package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.model.documents.Comentario;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ComentarioRepo extends MongoRepository<Comentario, String> {

    ArrayList<Comentario> findByIdNegocio(String idNegocio);
}
