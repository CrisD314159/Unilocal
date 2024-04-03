package co.edu.uniquindio.proyecto.model.documents;

import co.edu.uniquindio.proyecto.model.entities.Coordenada;
import co.edu.uniquindio.proyecto.model.entities.Horario;
import co.edu.uniquindio.proyecto.model.entities.Imagen;
import co.edu.uniquindio.proyecto.model.entities.Revision;
import co.edu.uniquindio.proyecto.model.enums.Categoria;
import co.edu.uniquindio.proyecto.model.enums.EstadoLugar;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;

@Document
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Lugar implements Serializable {
    @Id
    private String codigo;
    private ArrayList<Imagen> imagenes;
    private String descripcion;
    private String nombre;
    private ArrayList<String> telefonos;
    private Categoria categoria;
    private Coordenada ubicacion;
    private Usuario usuario;
    private ArrayList<Comentario> comentarios;
    private ArrayList<Revision> listaRevisiones;
    private EstadoLugar estadoLugar;
    private ArrayList<Horario> horarios;
    private String idUsuario;

}
