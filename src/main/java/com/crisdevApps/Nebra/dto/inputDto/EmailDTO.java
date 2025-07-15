package com.crisdevApps.Nebra.dto.inputDto;

public record EmailDTO (
        String asunto,
        String cuerpo,
        String destinatario
) {
}
