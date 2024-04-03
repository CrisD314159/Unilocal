package co.edu.uniquindio.proyecto.model.entities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public abstract class Cuenta {
    private String codigo;
    private String email;
    private String username;
    private String password;
}
