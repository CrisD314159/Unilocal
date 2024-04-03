package co.edu.uniquindio.proyecto.model.documents;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Comentario implements Serializable {
    @Id
    private String codigo;
    private String titulo;
    private int calificacion;
    private String contenido;
    private Usuario usuario;
    private Lugar lugar;
    private String idNegocio;
    private String idUsuario;
    private String respuesta;

}
