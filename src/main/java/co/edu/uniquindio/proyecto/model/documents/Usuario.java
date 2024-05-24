package co.edu.uniquindio.proyecto.model.documents;

import co.edu.uniquindio.proyecto.model.documents.Comentario;
import co.edu.uniquindio.proyecto.model.documents.Denuncia;
import co.edu.uniquindio.proyecto.model.documents.Lugar;
import co.edu.uniquindio.proyecto.model.entities.Cuenta;
import co.edu.uniquindio.proyecto.model.entities.Imagen;
import co.edu.uniquindio.proyecto.model.enums.EstadoRegistro;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;

@Document
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString
@Getter
@Setter
public class Usuario extends Cuenta implements Serializable {
    @Id
    @EqualsAndHashCode.Include
    private String codigo;
    private String nombre;
    private Imagen fotoDePerfil;
    private String direccion;
    private ArrayList<Comentario> comentarios;
    private ArrayList<Lugar> listaLugares;
    private ArrayList<Denuncia> denuncias;
    private EstadoRegistro registro;
    private ArrayList<String> recomendaciones;
    private ArrayList<String> favoritos;

}
