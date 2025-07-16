package com.crisdevApps.Nebra.test;

import com.crisdevApps.Nebra.dto.inputDto.CreateReportDTO;
import com.crisdevApps.Nebra.services.implementations.ReportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReportServicioTest {

    @Autowired
    private ReportService denunciaServicioImp;

    @Test
    public void crearDenunciaTest(){
        CreateReportDTO createReportDTO = new CreateReportDTO(
                "66078d1c68de9f284821bfaf",
                "66098099c213596ba18c73c3",
               "El lugar tiene images explicitas, ademas de que no existe"
        );

        boolean resultado;

        try {
            resultado = denunciaServicioImp.CreateReport(createReportDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Assertions.assertTrue(resultado);
    }

    @Test
    public void aceptarDenunciaTest() throws Exception {
        boolean resultado = denunciaServicioImp.AcceptReport("6609dda5ec7f1777d253ba0c");
        Assertions.assertTrue(resultado);
    }

    @Test
    public void rechazarDenunciaTest() throws Exception {
        boolean resultado = denunciaServicioImp.RejectReport("6609dda5ec7f1777d253ba0c");
        Assertions.assertTrue(resultado);
    }
}
