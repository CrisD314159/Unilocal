package com.crisdevApps.Nebra.dto.inputDto;

import com.crisdevApps.Nebra.model.Coordinate;
import com.crisdevApps.Nebra.model.Image;
import com.crisdevApps.Nebra.model.Schedule;
import com.crisdevApps.Nebra.model.enums.BusinessCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.UUID;

public record CreateBusinessDTO(
        @NotBlank String description,
        @NotBlank String name,
        @NotEmpty String phoneContact,
        ArrayList<Image> images,
        BusinessCategory category,
        Coordinate location,
        @NotEmpty ArrayList<Schedule> scheduleList
) {

}
