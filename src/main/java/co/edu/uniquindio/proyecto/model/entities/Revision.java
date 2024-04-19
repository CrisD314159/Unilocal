package co.edu.uniquindio.proyecto.model.entities;

import co.edu.uniquindio.proyecto.model.documents.Lugar;
import co.edu.uniquindio.proyecto.model.documents.Moderador;
import co.edu.uniquindio.proyecto.model.enums.TipoEstado;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Revision {
    private TipoEstado estado;
    private String moderador;
    private String lugar;
    private String motivo;

}
