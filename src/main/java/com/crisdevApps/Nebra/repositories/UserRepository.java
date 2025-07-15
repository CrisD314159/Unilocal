package com.crisdevApps.Nebra.repositories;

import com.crisdevApps.Nebra.model.User;
import com.crisdevApps.Nebra.model.enums.UserRole;
import com.crisdevApps.Nebra.model.enums.UserState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {


    Optional<User> findByEmail(String email); // Si especificamo el metodo bien no necesitamos el query

    //Para busquedas
    ArrayList<User> findByNombreContains(String letra);

    Optional<User> findByIdAndUserRole(UUID id, UserRole userRole);

    User findByUsername(String nickname);

    List<User> findByRegistro(UserState userState);
}
