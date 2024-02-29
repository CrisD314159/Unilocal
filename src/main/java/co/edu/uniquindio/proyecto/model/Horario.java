package co.edu.uniquindio.proyecto.model;

import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Horario {
    private ArrayList<String> dias;
    private String horaApertura;
    private String horaCierre;

}
