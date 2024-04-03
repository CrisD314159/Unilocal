package co.edu.uniquindio.proyecto.model.documents;

import co.edu.uniquindio.proyecto.model.enums.EstadoDenuncia;
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
public class Denuncia implements Serializable {
    @Id
    private String codigo;
    private Usuario usuario;
    private Lugar lugar;
    private String idUsuario;
    private String idlugar;
    private String motivo;
    private EstadoDenuncia estadoDenuncia;
    private String respuesta;
}
