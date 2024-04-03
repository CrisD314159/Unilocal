package co.edu.uniquindio.proyecto.model.documents;

import co.edu.uniquindio.proyecto.model.entities.Cuenta;
import co.edu.uniquindio.proyecto.model.enums.EstadoRegistro;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString
@Getter
@Setter
public class Moderador extends Cuenta implements Serializable {
    @Id
    @EqualsAndHashCode.Include
    private String codigo;
    private String nombre;
    private EstadoRegistro registro;
}
