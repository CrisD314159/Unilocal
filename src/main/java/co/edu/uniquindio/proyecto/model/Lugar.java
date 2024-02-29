package co.edu.uniquindio.proyecto.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Lugar {
    @Id
    private String codigo;
    private ArrayList<String> imagenes;
    private String descripcion;
    private String nombre;
    private ArrayList<String> telefonos;
    private Categoria categoria;
    private Coordenada ubicacion;
    private Usuario usuario;
    private ArrayList<Comentario> comentarios;

    private ArrayList<Estado> listaEstados;
    private EstadoLugar estadoLugar;
    private ArrayList<Horario> horarios;

}
