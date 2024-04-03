package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.model.documents.Denuncia;
import co.edu.uniquindio.proyecto.model.enums.EstadoDenuncia;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DenunciaRepo extends MongoRepository<Denuncia, String> {

    @Query("{'_id': ?0, 'estadoDenuncia': ?1}")
    Optional<Denuncia> findByIdDenunciaAndEstadoDenuncia(String id, EstadoDenuncia estadoDenuncia);
}
