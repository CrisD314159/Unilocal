package com.crisdevApps.Nebra.controladores;

import com.crisdevApps.Nebra.dto.inputDto.ImagenDTO;
import com.crisdevApps.Nebra.dto.outputDto.ErrorMessage;
import com.crisdevApps.Nebra.services.implementations.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/imagenes")
@RequiredArgsConstructor
public class ImagenesController {
    private final ImageService imagenesServicioImp;


    @PostMapping("/subir")
    public ResponseEntity<ErrorMessage<Map>> subir(@RequestParam("file") MultipartFile imagen)
            throws Exception{
        Map respuesta = imagenesServicioImp.UploadImage(imagen);
        return ResponseEntity.ok().body(new ErrorMessage<>(false, respuesta ));
    }
    @DeleteMapping("/eliminar")
    public ResponseEntity<ErrorMessage<Map>> eliminar(@RequestBody ImagenDTO imagenDTO) throws
            Exception{
        Map respuesta = imagenesServicioImp.DeleteImage( imagenDTO.id() );
        return ResponseEntity.ok().body(new ErrorMessage<>(false, respuesta ));
    }
}
