package com.crisdevApps.Nebra.services.interfaces;

import com.crisdevApps.Nebra.dto.inputDto.CrearDenunciaDTO;
import com.crisdevApps.Nebra.dto.outputDto.DetalleDenuncia;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReportService {

    boolean CreateReport(CrearDenunciaDTO crearDenunciaDTO) throws Exception;
    boolean AcceptReport(String idDenuncia) throws Exception;
    boolean RejectReport(String idDenuncia) throws Exception;

    List<DetalleDenuncia> GetReports();

    DetalleDenuncia GetSpecificReport(String codigo) throws Exception;

    List<DetalleDenuncia> GetRejectedReports() throws Exception;
}
