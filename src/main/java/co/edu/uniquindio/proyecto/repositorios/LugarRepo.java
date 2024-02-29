package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.model.Lugar;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LugarRepo extends MongoRepository<Lugar, String> {
}
