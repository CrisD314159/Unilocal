package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dto.ActualizarNegocioDTO;
import co.edu.uniquindio.proyecto.dto.CrearNegocioDTO;
import co.edu.uniquindio.proyecto.dto.DetalleNegocioDTO;
import co.edu.uniquindio.proyecto.dto.ObtenerNegocioDTO;
import co.edu.uniquindio.proyecto.model.documents.Lugar;
import co.edu.uniquindio.proyecto.model.entities.Coordenada;
import co.edu.uniquindio.proyecto.model.entities.Horario;
import co.edu.uniquindio.proyecto.model.entities.Imagen;
import co.edu.uniquindio.proyecto.model.enums.Categoria;
import co.edu.uniquindio.proyecto.model.enums.EstadoLugar;
import co.edu.uniquindio.proyecto.model.services.implementations.LugarServicioImp;
import jakarta.validation.constraints.NotEmpty;
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
public class LugarServicioTest {

    @Autowired
    private LugarServicioImp lugarServicioImp;

    @Test
    public void crearLugarTest() throws IOException {
        ArrayList<String> telefonos = new ArrayList<>();
        ArrayList<MultipartFile> imagenes = new ArrayList<>();
        ArrayList<Horario> horarios = new ArrayList<>();
        File file = new File("src/test/resources/Javascript.png");
        InputStream inputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("imagen", file.getName(), "image/jpeg", inputStream);

        horarios.add(new Horario("Lunes", "7:00am", "9:00pm"));
        telefonos.add("3122341232");
        imagenes.add(multipartFile);
        CrearNegocioDTO crearNegocioDTO= new CrearNegocioDTO(
                "Este es un hotel ubicado en el centro de la ciudad",
                "HostLeeping",
                telefonos,
                imagenes,
                Categoria.HOTEL,
                new Coordenada("123.1", "49944.2"),
                "660976c557c6105686a33bc9",
                horarios


        );
        boolean respuesta = false;
        try {
            respuesta = lugarServicioImp.crearLugar(crearNegocioDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(respuesta);


    }

    @Test
    public void actualizarnegocioTest() throws IOException {
        ArrayList<String> telefonos = new ArrayList<>();
        ArrayList<MultipartFile> imagenes = new ArrayList<>();
        ArrayList<Horario> horarios = new ArrayList<>();
        File file = new File("src/test/resources/python.png");
        InputStream inputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("imagen", file.getName(), "image/jpeg", inputStream);

        horarios.add(new Horario("Lunes", "7:00am", "9:00pm"));
        telefonos.add("3122341232");
        imagenes.add(multipartFile);
        ActualizarNegocioDTO actualizarNegocioDTO= new ActualizarNegocioDTO(
                "66098099c213596ba18c73c3",
                "Este es un negocio de comida rápida ubicado en el centro de la ciudad",
                "FastFoood",
                telefonos,
                imagenes,
                new Coordenada("92.1", "31.2"),
                horarios


        );

        boolean respuesta;
        try {
            respuesta =  lugarServicioImp.actualizarNegocio(actualizarNegocioDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(respuesta);


    }

    @Test
    public void eliminarLugarTest(){
        boolean respuesta;
        try {
           respuesta = lugarServicioImp.eliminarNegocio("66098099c213596ba18c73c3");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(respuesta);

    }

    @Test
    public void buscarLugarTest(){
        List<DetalleNegocioDTO> negocioDTOS = lugarServicioImp.buscarLugar("Fast");
        System.out.println(negocioDTOS);
        if (negocioDTOS.isEmpty()){
            Assertions.fail();
        }
        Assertions.assertTrue(true);
    }

    @Test
    public void buscarLugarEstadoTest() throws Exception {
        List<DetalleNegocioDTO> negocioDTOS = lugarServicioImp.filtrarPorEstado(EstadoLugar.INACTIVO);
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
        List<ObtenerNegocioDTO> negocioDTOS = lugarServicioImp.listarLugaresCliente("660976c557c6105686a33bc9");
        if (negocioDTOS.isEmpty()){
            Assertions.fail();
        }

        System.out.println(negocioDTOS);
        Assertions.assertTrue(true);
    }


}
