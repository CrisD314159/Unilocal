package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.model.documents.Usuario;
import co.edu.uniquindio.proyecto.model.enums.EstadoRegistro;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepo extends MongoRepository<Usuario, String> {


    Optional<Usuario> findByEmail(String email); // Si especificamo el metodo bien no necesitamos el query

// Reformat user account
    @Query("{'email': ?0, 'password': ?1}")
    Usuario findByEmailAndPassword(String email, String password);

    //Para busquedas
    ArrayList<Usuario> findByNombreContains(String letra);

    Usuario findByUsername(String nickname);

    List<Usuario> findByRegistro(EstadoRegistro estadoRegistro);
}
