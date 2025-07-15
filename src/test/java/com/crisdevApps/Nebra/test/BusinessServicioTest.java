package com.crisdevApps.Nebra.test;

import com.crisdevApps.Nebra.dto.inputDto.UpdateBusinessDTO;
import com.crisdevApps.Nebra.dto.inputDto.CreateBusinessDTO;
import com.crisdevApps.Nebra.dto.outputDto.GetBusinessDTO;
import com.crisdevApps.Nebra.dto.outputDto.ObtenerNegocioDTO;
import com.crisdevApps.Nebra.model.Coordinate;
import com.crisdevApps.Nebra.model.Schedule;
import com.crisdevApps.Nebra.model.enums.BusinessCategory;
import com.crisdevApps.Nebra.model.enums.BusinessState;
import com.crisdevApps.Nebra.services.implementations.BusinessService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BusinessServicioTest {

    @Autowired
    private BusinessService lugarServicioImp;

    @Test
    public void crearLugarTest() throws IOException {
        ArrayList<String> telefonos = new ArrayList<>();
        ArrayList<MultipartFile> imagenes = new ArrayList<>();
        ArrayList<Schedule> schedules = new ArrayList<>();
        File file = new File("src/test/resources/Javascript.png");
        InputStream inputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("imagen", file.getName(), "image/jpeg", inputStream);

        schedules.add(new Schedule("Lunes", "7:00am", "9:00pm"));
        telefonos.add("3122341232");
        imagenes.add(multipartFile);
        CreateBusinessDTO createBusinessDTO = new CreateBusinessDTO(
                "Este es un hotel ubicado en el centro de la ciudad",
                "HostLeeping",
                telefonos,
                imagenes,
                BusinessCategory.HOTEL,
                new Coordinate("123.1", "49944.2"),
                "660976c557c6105686a33bc9",
                schedules


        );
        boolean respuesta = false;
        try {
            respuesta = lugarServicioImp.CreateBusiness(createBusinessDTO, );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(respuesta);


    }

    @Test
    public void actualizarnegocioTest() throws IOException {
        ArrayList<String> telefonos = new ArrayList<>();
        ArrayList<MultipartFile> imagenes = new ArrayList<>();
        ArrayList<Schedule> schedules = new ArrayList<>();
        File file = new File("src/test/resources/python.png");
        InputStream inputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("imagen", file.getName(), "image/jpeg", inputStream);

        schedules.add(new Schedule("Lunes", "7:00am", "9:00pm"));
        telefonos.add("3122341232");
        imagenes.add(multipartFile);
        UpdateBusinessDTO updateBusinessDTO = new UpdateBusinessDTO(
                "66098099c213596ba18c73c3",
                "Este es un negocio de comida rápida ubicado en el centro de la ciudad",
                "FastFoood",
                telefonos,
                imagenes,
                new Coordinate("92.1", "31.2"),
                schedules


        );

        boolean respuesta;
        try {
            respuesta =  lugarServicioImp.UpdateBusiness(updateBusinessDTO, );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(respuesta);


    }

    @Test
    public void eliminarLugarTest(){
        boolean respuesta;
        try {
           respuesta = lugarServicioImp.DeleteBusiness("66098099c213596ba18c73c3", );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(respuesta);

    }

    @Test
    public void buscarLugarTest(){
        List<GetBusinessDTO> negocioDTOS = lugarServicioImp.SearchBusiness("Fast", );
        System.out.println(negocioDTOS);
        if (negocioDTOS.isEmpty()){
            Assertions.fail();
        }
        Assertions.assertTrue(true);
    }

    @Test
    public void buscarLugarEstadoTest() throws Exception {
        List<GetBusinessDTO> negocioDTOS = lugarServicioImp.FilterBusinessByState(BusinessState.INACTIVE);
        System.out.println(negocioDTOS);

        if (negocioDTOS.isEmpty()){
            Assertions.fail();
        }
        Assertions.assertTrue(true);
    }

    @Test
    public void obtenerLugarDetalleTest() throws Exception {
        ObtenerNegocioDTO negocioDTO = lugarServicioImp.obtenerLugarDetalle("66098099c213596ba18c73c3");

        System.out.println(negocioDTO);
        Assertions.assertNotNull(negocioDTO);
    }

    @Test
    public void obtenerLugaresCliente() throws Exception {
        List<ObtenerNegocioDTO> negocioDTOS = lugarServicioImp.GetAvailableBusiness("660976c557c6105686a33bc9");
        if (negocioDTOS.isEmpty()){
            Assertions.fail();
        }

        System.out.println(negocioDTOS);
        Assertions.assertTrue(true);
    }


}
