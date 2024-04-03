package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dto.CrearDenunciaDTO;
import co.edu.uniquindio.proyecto.model.services.implementations.DenunciaServicioImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DenunciaServicioTest {

    @Autowired
    private DenunciaServicioImp denunciaServicioImp;

    @Test
    public void crearDenunciaTest(){
        CrearDenunciaDTO crearDenunciaDTO = new CrearDenunciaDTO(
                "66078d1c68de9f284821bfaf",
                "66098099c213596ba18c73c3",
               "El lugar tiene imagenes explicitas, ademas de que no existe"
        );

        boolean resultado;

        try {
            resultado = denunciaServicioImp.crearDenuncia(crearDenunciaDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Assertions.assertTrue(resultado);
    }

    @Test
    public void aceptarDenunciaTest() throws Exception {
        boolean resultado = denunciaServicioImp.aceptarDenuncia("6609dda5ec7f1777d253ba0c");
        Assertions.assertTrue(resultado);
    }

    @Test
    public void rechazarDenunciaTest() throws Exception {
        boolean resultado = denunciaServicioImp.rechazarDenuncia("6609dda5ec7f1777d253ba0c");
        Assertions.assertTrue(resultado);
    }
}
