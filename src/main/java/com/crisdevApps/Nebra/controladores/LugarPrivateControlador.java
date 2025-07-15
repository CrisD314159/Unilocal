package com.crisdevApps.Nebra.controladores;

import co.crisdevApps.Nebra.dto.*;
import co.edu.uniquindio.proyecto.dto.*;
import com.crisdevApps.Nebra.dto.inputDto.UpdateBusinessDTO;
import com.crisdevApps.Nebra.dto.inputDto.CreateBusinessDTO;
import com.crisdevApps.Nebra.dto.outputDto.GetBusinessDTO;
import com.crisdevApps.Nebra.dto.outputDto.ErrorMessage;
import com.crisdevApps.Nebra.dto.outputDto.ObtenerNegocioDTO;
import com.crisdevApps.Nebra.model.enums.BusinessState;
import com.crisdevApps.Nebra.services.implementations.BusinessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lugares")
@RequiredArgsConstructor
public class LugarPrivateControlador {
    private final BusinessService lugarServicioImp;

    @PostMapping("/crear-lugar")
    public ResponseEntity<ErrorMessage<String>> crearLugar(@Valid @RequestBody CreateBusinessDTO createBusinessDTO) throws Exception{
        lugarServicioImp.CreateBusiness(createBusinessDTO, );
        return ResponseEntity.ok().body(new ErrorMessage<>(false, "Business creado exitosamente"));
    }

    @PutMapping("/actualizar-lugar")
    public ResponseEntity<ErrorMessage<String>> actualizarLugar (@Valid @RequestBody UpdateBusinessDTO updateBusinessDTO) throws Exception{
        lugarServicioImp.UpdateBusiness(updateBusinessDTO, );
        return ResponseEntity.ok().body(new ErrorMessage<>(false, "Negocio actualizado exitosamente"));
    }

    @PutMapping("/archivar-lugar/{codigo}")
    public ResponseEntity<ErrorMessage<String>> actualizarLugar (@PathVariable String codigo) throws Exception{
        lugarServicioImp.ArchiveBusiness(codigo);
        return ResponseEntity.ok().body(new ErrorMessage<>(false, "Negocio archivado exitosamente"));
    }

    @DeleteMapping("/eliminar/{codigo}")
    public ResponseEntity<ErrorMessage<String>> eliminarLugar(@PathVariable String codigo)throws Exception{
        lugarServicioImp.DeleteBusiness(codigo, );
        return ResponseEntity.ok().body(new ErrorMessage<>(false, "Negocio eliminado exitosamente"));
    }

    @GetMapping("/moderator/get-place/{codigo}")
    public ResponseEntity<ErrorMessage<ObtenerNegocioDTO>> obtenerNegocioModerador(@PathVariable String codigo) throws Exception {
        return ResponseEntity.ok().body(new ErrorMessage<>(false, lugarServicioImp.obtenerLugarDetalleModerador(codigo)));
    }

    @GetMapping("/search/estado/{estado}")
    public ResponseEntity<ErrorMessage<List<GetBusinessDTO>>> filtrarPorEstado (@PathVariable BusinessState estado) throws Exception {

        return ResponseEntity.ok().body(new ErrorMessage<>(false,lugarServicioImp.FilterBusinessByState(estado)));

    }
}
