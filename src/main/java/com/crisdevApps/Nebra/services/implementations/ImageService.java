package com.crisdevApps.Nebra.services.implementations;

import com.crisdevApps.Nebra.services.interfaces.IImageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service

public class ImageService implements IImageService {

    private final Cloudinary cloudinary;

    public ImageService(){
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dkezm5gwi");
        config.put("api_key", "841136685712121");
        config.put("api_secret", "UOCmr3pW38VCG4NFgBicB4rzKcQ");
        cloudinary = new Cloudinary(config);
    }

    private File convertir(MultipartFile imagen) throws IOException {
        File file = File.createTempFile(imagen.getOriginalFilename(), null);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(imagen.getBytes());
        fos.close();
        return file;
    }
    @Override
    public Map UploadImage(MultipartFile imagen) throws Exception {
        File file = convertir(imagen);
        return cloudinary.uploader().upload(file, ObjectUtils.asMap("folder", "unilocal"));
    }

    @Override
    public Map DeleteImage(String idImagen) throws Exception {
        return cloudinary.uploader().destroy(idImagen, ObjectUtils.emptyMap());
    }
}
