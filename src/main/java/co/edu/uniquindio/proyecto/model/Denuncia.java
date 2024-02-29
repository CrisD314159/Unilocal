package co.edu.uniquindio.proyecto.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Denuncia {
    @Id
    private String codigo;
    private Usuario usuario;
    private Lugar lugar;
    private String motivo;
    private EstadoDenuncia estadoDenuncia;
}
