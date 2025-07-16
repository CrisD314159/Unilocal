package com.crisdevApps.Nebra.controladores;

import com.crisdevApps.Nebra.dto.inputDto.CreateReportDTO;
import com.crisdevApps.Nebra.dto.outputDto.GetReportDTO;
import com.crisdevApps.Nebra.dto.outputDto.ErrorMessage;
import com.crisdevApps.Nebra.services.implementations.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/denuncias")
@RequiredArgsConstructor
public class DenunciaControlador {
    private final ReportService denunciaServicioImp;

    @PostMapping("/crear-denuncia")
    public ResponseEntity<ErrorMessage<String>> crearDenuncia(@Valid @RequestBody CreateReportDTO createReportDTO) throws Exception {
        denunciaServicioImp.CreateReport(createReportDTO);
        return ResponseEntity.ok().body(new ErrorMessage<>(false, "La denuncua fue creada con exito"));
    }

    @PostMapping("/aceptar-denuncia/{codigo}")
    public ResponseEntity<ErrorMessage<String>> aceptarDenuncia(@PathVariable String codigo) throws Exception {
        denunciaServicioImp.AcceptReport(codigo);
        return ResponseEntity.ok().body(new ErrorMessage<>(false, "Report aceptada"));
    }

    @PostMapping("/rechazar-denuncia/{codigo}")
    public ResponseEntity<ErrorMessage<String>> rechazarDenuncia(@PathVariable String codigo) throws Exception {
        denunciaServicioImp.RejectReport(codigo);
        return ResponseEntity.ok().body(new ErrorMessage<>(false, "Report rechazada"));
    }

    @GetMapping("/obtener-denuncia/{codigo}")
    public ResponseEntity<ErrorMessage<GetReportDTO>> obtenerDenuncia(@PathVariable String codigo) throws Exception {

        return ResponseEntity.ok().body(new ErrorMessage<>(false, denunciaServicioImp.GetSpecificReport(codigo)));
    }

    @GetMapping("/obtener-denuncias")
    public ResponseEntity<ErrorMessage<List<GetReportDTO>>> obtenerDenuncias() throws Exception {

        return ResponseEntity.ok().body(new ErrorMessage<>(false, denunciaServicioImp.GetReports()));
    }

    @GetMapping("/obtener-denuncias-rechazadas")
    public ResponseEntity<ErrorMessage<List<GetReportDTO>>> obtenerDenunciasRechazadas() throws Exception {

        return ResponseEntity.ok().body(new ErrorMessage<>(false, denunciaServicioImp.GetRejectedReports()));
    }



}
