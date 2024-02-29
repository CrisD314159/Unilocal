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
public class Usuario {
    @Id
    private String codigo;
    private String nombre;
    private String fotoDePerfil;
    private String direccion;
    private ArrayList<Comentario> comentarios;
    private ArrayList<Lugar> listaLugares;
    private ArrayList<Denuncia> denuncias;
}
