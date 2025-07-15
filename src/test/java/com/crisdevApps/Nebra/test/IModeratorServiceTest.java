package com.crisdevApps.Nebra.test;

import com.crisdevApps.Nebra.dto.inputDto.CreateModeratorDTO;
import com.crisdevApps.Nebra.services.implementations.ModeratorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IModeratorServiceTest {
    @Autowired
    private ModeratorService moderadorServicioImp;

    @Test
    public void aceptarLugarTest(){
        boolean respuesta;
        try {
            respuesta = moderadorServicioImp.ApproveBusiness("6609e1b81bdc893649825c23");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Assertions.assertTrue(respuesta);
    }

    @Test
    public void rejectBusinessTest(){
        boolean respuesta;
        try {
            respuesta = moderadorServicioImp.RejectBusiness("6609e1b81bdc893649825c23");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(respuesta);
    }

    @Test
    public void registrarModerador(){
        CreateModeratorDTO createModeratorDTO = new CreateModeratorDTO("crisvargas", "vargasloaizacristian@gmail.com","cris123","Cristian Vargas" );
        boolean respuesta;
        try {
            respuesta = moderadorServicioImp.CreateModerator(createModeratorDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(respuesta);
    }



}
