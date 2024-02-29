package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.model.Moderador;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeradorRepo extends MongoRepository<Moderador, String> {
}
