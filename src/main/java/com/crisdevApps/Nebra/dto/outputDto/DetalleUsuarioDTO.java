package com.crisdevApps.Nebra.dto.outputDto;

import com.crisdevApps.Nebra.model.Image;

public record DetalleUsuarioDTO (
        String id,
        String nombre,

        Image fotoPerfil,

        String nickname,

        String email,

        String password,

        String ciudadResidencia
) {

}
