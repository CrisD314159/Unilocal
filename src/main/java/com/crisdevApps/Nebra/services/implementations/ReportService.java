package com.crisdevApps.Nebra.services.implementations;

import com.crisdevApps.Nebra.dto.inputDto.CrearDenunciaDTO;
import com.crisdevApps.Nebra.dto.outputDto.DetalleDenuncia;
import com.crisdevApps.Nebra.dto.outputDto.DetalleUsuarioDTO;
import com.crisdevApps.Nebra.dto.inputDto.EmailDTO;
import com.crisdevApps.Nebra.model.Report;
import com.crisdevApps.Nebra.model.Business;
import com.crisdevApps.Nebra.model.enums.BusinessState;
import com.crisdevApps.Nebra.model.enums.ReportState;
import com.crisdevApps.Nebra.services.interfaces.IReportService;
import com.crisdevApps.Nebra.repositories.ReportRepository;
import com.crisdevApps.Nebra.repositories.BusinessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService implements IReportService {

    private final ReportRepository reportRepository;
    private final BusinessRepository businessRepository;
    private final EmailService emailServicioImp;
    private final UserService usuarioServicioImp;
    @Override
    public boolean CreateReport(CrearDenunciaDTO crearDenunciaDTO) throws Exception {

        Report report = new Report();
        report.setEstadoDenuncia(ReportState.PENDING);
        report.setIdlugar(crearDenunciaDTO.idNegocio());
        report.setIdUsuario(crearDenunciaDTO.idUsuario());
        report.setMotivo(crearDenunciaDTO.motivo());

        try {
            reportRepository.save(report);
        } catch (Exception e) {
            throw new Exception("Ocurrió un success con la base de datos");
        }
        return true;
    }

    @Override
    public boolean AcceptReport(String idDenuncia) throws Exception {
        Optional<Report> denunciaOptional = reportRepository.findByIdDenunciaAndEstadoDenuncia(idDenuncia, ReportState.PENDING);
        if (denunciaOptional.isEmpty()){
            throw new Exception("No se pudo encontrar la report");
        }

        Report report = denunciaOptional.get();
        String idLugar = report.getIdlugar();
        Optional<Business> lugarOptional = businessRepository.findById(idLugar);
        if (lugarOptional.isEmpty()) throw new Exception("Business no encontrado");
        Business business = lugarOptional.get();
        business.setEstadoLugar(BusinessState.INACTIVE);
        report.setEstadoDenuncia(ReportState.ACCEPTED);

        try {
            businessRepository.save(business);
            reportRepository.save(report);


        } catch (Exception e) {
            throw new Exception("Ocurrió un success con el servidor");
        }
        DetalleUsuarioDTO detalleUsuarioDTO = usuarioServicioImp.GetUserProfile(report.getIdUsuario());
        emailServicioImp.SendEmail(new EmailDTO("Report aceptada", "Su report ha sido aceptada exitosamente", detalleUsuarioDTO.email()));
        return true;
    }

    @Override
    public boolean RejectReport(String idDenuncia) throws Exception {
        Optional<Report> denunciaOptional = reportRepository.findByIdDenunciaAndEstadoDenuncia(idDenuncia, ReportState.PENDING);
        if (denunciaOptional.isEmpty()){
            throw new Exception("No se pudo encontrar la report");
        }

        Report report = denunciaOptional.get();
        if (report.getEstadoDenuncia() == ReportState.REJECTED){
            throw new Exception("La report ya habia sido rechazada");
        }

        report.setEstadoDenuncia(ReportState.REJECTED);

        try {
            reportRepository.save(report);
        } catch (Exception e) {
            throw new Exception("Ocurrió un success con el servidor");
        }
        DetalleUsuarioDTO detalleUsuarioDTO = usuarioServicioImp.GetUserProfile(report.getIdUsuario());
        emailServicioImp.SendEmail(new EmailDTO("Report Rechazada", "Su report ha sido rechazada", detalleUsuarioDTO.email()));
        return true;
    }

    @Override
    public List<DetalleDenuncia> GetReports() {
        ArrayList<Report> reports = reportRepository.findAllNotRejected(ReportState.PENDING);
        return reports.stream().map(d -> new DetalleDenuncia(d.getCodigo(), d.getIdUsuario(), d.getIdlugar(), d.getMotivo())).toList();

    }

    @Override
    public List<DetalleDenuncia> GetRejectedReports() throws Exception {
        ArrayList<Report> reports = reportRepository.findAllRejected(ReportState.REJECTED);
        return reports.stream().map(d -> new DetalleDenuncia(d.getCodigo(), d.getIdUsuario(), d.getIdlugar(), d.getMotivo())).toList();

    }

    @Override
    public DetalleDenuncia GetSpecificReport(String codigo) throws Exception {
        Optional<Report> denunciaOptional = reportRepository.findById(codigo);
        if (denunciaOptional.isEmpty()){
            throw new Exception("No se pudo encontrar la report");
        }
        Report report = denunciaOptional.get();
        return new DetalleDenuncia(report.getCodigo(), report.getIdUsuario(), report.getIdlugar(), report.getMotivo());
    }


}
