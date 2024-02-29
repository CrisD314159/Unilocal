package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.model.Denuncia;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DenunciaRepo extends MongoRepository<Denuncia, String> {
}
