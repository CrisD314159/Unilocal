package com.crisdevApps.Nebra.repositories;

import com.crisdevApps.Nebra.dto.outputDto.ObtenerNegocioDTO;
import com.crisdevApps.Nebra.model.Business;
import com.crisdevApps.Nebra.model.User;
import com.crisdevApps.Nebra.model.enums.BusinessCategory;
import com.crisdevApps.Nebra.model.enums.BusinessState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusinessRepository extends JpaRepository<Business, UUID> {

    Business findByName(String name);

    Optional<Business> findByIdAndUserOwner(UUID id, User owner);

    boolean existsByNameAndUserOwner(String name, User owner);


    Page<Business> findByCategoryAndBusinessState (BusinessCategory category, BusinessState state, Pageable pageable);

    Page<Business> findByBusinessState (BusinessState state, Pageable pageable);


    Page<Business> findByNameIsLikeAndBusinessState(String name, BusinessState state, Pageable pageable);


    Page<Business> findByUserOwnerAndBusinessState(User owner, BusinessState state, Pageable pageable);


    Optional<Business> findByIdAndBusinessState(UUID id, BusinessState state);


    List<Business> encontrarPropietarios (String codigoCliente);



    Optional<ObtenerNegocioDTO> findByIdAndEstadoLugar(String id, BusinessState businessState);


    List<ObtenerNegocioDTO> findLugaresUsuario(String idCliente, BusinessState businessState);

    ArrayList<Business> findByCategoria(BusinessCategory businessCategory);

}
