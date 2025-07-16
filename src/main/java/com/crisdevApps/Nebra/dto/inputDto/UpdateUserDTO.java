package com.crisdevApps.Nebra.dto.inputDto;

import com.crisdevApps.Nebra.model.Image;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record UpdateUserDTO(
        @NotEmpty UUID id,

       @NotBlank String name,

        Image profilePicture,

       @NotBlank String location
) {


}
