package com.crisdevApps.Nebra.services.interfaces;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Repository
public interface IImageService {

    Map UploadImage(MultipartFile imagen) throws Exception;
    Map DeleteImage(String idImagen) throws Exception;
}
