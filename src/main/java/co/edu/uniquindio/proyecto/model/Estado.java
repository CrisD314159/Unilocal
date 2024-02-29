package co.edu.uniquindio.proyecto.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Estado {
    private TipoEstado estado;
    private Moderador moderador;
    private Lugar lugar;

}
