package com.crisdevApps.Nebra.controladores;

import com.crisdevApps.Nebra.dto.outputDto.GetReportDTO;
import com.crisdevApps.Nebra.dto.outputDto.ErrorMessage;
import com.crisdevApps.Nebra.services.implementations.ReportService;
import com.crisdevApps.Nebra.services.implementations.ModeratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/moderadores")
@RequiredArgsConstructor
public class ModeradorControlador {
    private final ModeratorService moderadorServicioImp;
    private final ReportService denunciaServicioImp;

    @PostMapping("/aprobar-lugar/{codigo}")
    public ResponseEntity<ErrorMessage<String>> aprobarLugar(@PathVariable String codigo) throws Exception{
        moderadorServicioImp.ApproveBusiness(codigo);
        return ResponseEntity.ok().body(new ErrorMessage<>(false, "EL negocio fue aprobado exitosamente"));
    }

    @PostMapping("/rechazar-lugar/{codigo}")
    public ResponseEntity<ErrorMessage<String>> rechazarLugar (@PathVariable String codigo) throws Exception{
        moderadorServicioImp.RejectBusiness(codigo);
        return ResponseEntity.ok().body(new ErrorMessage<>(false, "El lugar fue rechazado exitosamente"));
    }

    @DeleteMapping("/eliminar-usuario/{codigo}")
    public ResponseEntity<ErrorMessage<String>> eliminarUsuario (@PathVariable String codigo) throws Exception{
        moderadorServicioImp.DeleteBusiness(codigo);
        return ResponseEntity.ok().body(new ErrorMessage<>(false, "El usuario fue eliminado exitosamente"));
    }

    @GetMapping("/listar-denuncias")
    public  ResponseEntity<ErrorMessage<List<GetReportDTO>>> listarDenuncias() throws Exception {
        return ResponseEntity.ok().body(new ErrorMessage<>(false, denunciaServicioImp.GetReports()));
    }


}
