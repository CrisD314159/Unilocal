package co.edu.uniquindio.proyecto.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Comentario {
    private String codigo;
    private String titulo;
    private int calificacion;
    private String contenido;
    private Usuario usuario;
    private Lugar lugar;
    private String respuesta;

}
