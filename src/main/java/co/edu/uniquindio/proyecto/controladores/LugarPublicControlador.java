package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.enums.Categoria;
import co.edu.uniquindio.proyecto.model.enums.EstadoLugar;
import co.edu.uniquindio.proyecto.model.services.implementations.LugarServicioImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/lugares")
@RequiredArgsConstructor
public class LugarPublicControlador {
    private final LugarServicioImp lugarServicioImp;



    @GetMapping("/search/categoria/{categoria}")
    public ResponseEntity<MensajeDTO<List<DetalleNegocioDTO>>> filtrarPorCategoria (@PathVariable Categoria categoria) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false,lugarServicioImp.filtrarPorCategoria(categoria)));

    }


    @GetMapping("/search/query/{busqueda}")
    public ResponseEntity<MensajeDTO<List<DetalleNegocioDTO>>> filtrarPorBusqueda (@PathVariable String busqueda) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false,lugarServicioImp.buscarLugar(busqueda)));

    }


    @GetMapping("/usuario-lugares/{codigo}")
    public ResponseEntity<MensajeDTO<List<DetalleNegocioDTO>>> obtenerLugaresUsuario (@PathVariable String codigo) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false,lugarServicioImp.listarLugaresPropietario(codigo)));

    }

    @GetMapping("/buscar-lugar/{codigo}")
    public ResponseEntity<MensajeDTO<ObtenerNegocioDTO>> obtenerLugar(@PathVariable String codigo) throws Exception{

        return ResponseEntity.ok().body(new MensajeDTO<>(false,lugarServicioImp.obtenerLugarDetalle(codigo) ));

    }


    @GetMapping("/obtener-lugares")
    public ResponseEntity<MensajeDTO<List<DetalleNegocioDTO>>> obtenerLugares() throws Exception{

        return ResponseEntity.ok().body(new MensajeDTO<>(false,lugarServicioImp.filtrarPorEstado(EstadoLugar.ACTIVO) ));

    }




}
