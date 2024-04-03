package co.edu.uniquindio.proyecto.model.entities;

import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Horario {
    private String dia;
    private String horaApertura;
    private String horaCierre;

}
