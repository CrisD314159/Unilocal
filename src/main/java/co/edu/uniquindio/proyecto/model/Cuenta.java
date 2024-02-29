package co.edu.uniquindio.proyecto.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Cuenta {
    private String codigo;
    private String email;
    private String username;
    private String password;
}
