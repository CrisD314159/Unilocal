package com.crisdevApps.Nebra.controladores;

import com.crisdevApps.Nebra.dto.outputDto.GetBusinessDTO;
import com.crisdevApps.Nebra.dto.outputDto.ErrorMessage;
import com.crisdevApps.Nebra.dto.outputDto.ObtenerNegocioDTO;
import co.edu.uniquindio.proyecto.dto.*;
import com.crisdevApps.Nebra.model.enums.BusinessCategory;
import com.crisdevApps.Nebra.model.enums.BusinessState;
import com.crisdevApps.Nebra.services.implementations.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/lugares")
@RequiredArgsConstructor
public class LugarPublicControlador {
    private final BusinessService lugarServicioImp;



    @GetMapping("/search/categoria/{businessCategory}")
    public ResponseEntity<ErrorMessage<List<GetBusinessDTO>>> filtrarPorCategoria (@PathVariable BusinessCategory businessCategory) throws Exception {

        return ResponseEntity.ok().body(new ErrorMessage<>(false,lugarServicioImp.FilterBusinessByCategory(businessCategory)));

    }


    @GetMapping("/search/query/{busqueda}")
    public ResponseEntity<ErrorMessage<List<GetBusinessDTO>>> filtrarPorBusqueda (@PathVariable String busqueda) throws Exception {

        return ResponseEntity.ok().body(new ErrorMessage<>(false,lugarServicioImp.SearchBusiness(busqueda, )));

    }


    @GetMapping("/usuario-lugares/{codigo}")
    public ResponseEntity<ErrorMessage<List<GetBusinessDTO>>> obtenerLugaresUsuario (@PathVariable String codigo) throws Exception {

        return ResponseEntity.ok().body(new ErrorMessage<>(false,lugarServicioImp.GetUserBusiness(codigo)));

    }

    @GetMapping("/buscar-lugar/{codigo}")
    public ResponseEntity<ErrorMessage<ObtenerNegocioDTO>> obtenerLugar(@PathVariable String codigo) throws Exception{

        return ResponseEntity.ok().body(new ErrorMessage<>(false,lugarServicioImp.obtenerLugarDetalle(codigo) ));

    }


    @GetMapping("/obtener-lugares")
    public ResponseEntity<ErrorMessage<List<GetBusinessDTO>>> obtenerLugares() throws Exception{

        return ResponseEntity.ok().body(new ErrorMessage<>(false,lugarServicioImp.FilterBusinessByState(BusinessState.ACTIVE) ));

    }




}
