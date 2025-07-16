package com.crisdevApps.Nebra.dto.inputDto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public record StoreBusinessImagesDTO(
        List<MultipartFile> images,
        UUID businessId
) {
}
