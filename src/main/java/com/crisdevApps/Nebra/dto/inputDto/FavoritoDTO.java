package com.crisdevApps.Nebra.dto.inputDto;

import jakarta.validation.constraints.NotBlank;

public record FavoritoDTO(
        @NotBlank String idUsuario,
       @NotBlank String idNegocio

) {
}
