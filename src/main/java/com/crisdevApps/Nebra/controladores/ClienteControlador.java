package com.crisdevApps.Nebra.controladores;

import co.crisdevApps.Nebra.dto.*;
import co.edu.uniquindio.proyecto.dto.*;
import com.crisdevApps.Nebra.dto.inputDto.UpdateUserDTO;
import com.crisdevApps.Nebra.dto.inputDto.FavoritoDTO;
import com.crisdevApps.Nebra.dto.outputDto.*;
import com.crisdevApps.Nebra.services.implementations.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteControlador {
    private final UserService usuarioServicioImp;

    @DeleteMapping("/eliminar-usuario/{codigo}")
    public ResponseEntity<ErrorMessage<String>> eliminarCuenta(@PathVariable String codigo) throws Exception{
        usuarioServicioImp.DeleteAccount(codigo);
        return ResponseEntity.ok().body(new ErrorMessage<>(false, "El usuario se eliminó correctamente"));
    }

    @PutMapping("/actualizar-cliente")
    public ResponseEntity<ErrorMessage<String>> actualizarUsuario(@Valid @RequestBody UpdateUserDTO updateUserDTO)throws Exception{
        usuarioServicioImp.EditProfile(updateUserDTO);
        return ResponseEntity.ok().body(new ErrorMessage<>(false, "El usuario ha sido actualizado"));
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ErrorMessage<GetUserProfileDTO>> obtenerCliente(@PathVariable String codigo) throws Exception{
        return ResponseEntity.ok().body(new ErrorMessage<>(false,  usuarioServicioImp.GetUserProfile(codigo)));
    }

    @GetMapping("/get-all")
    public ResponseEntity<ErrorMessage<List<ItemUsuarioDTO>>> obtenerClientes() throws Exception{
        return ResponseEntity.ok().body(new ErrorMessage<>(false,  usuarioServicioImp.GetUsers(1)));
    }

    @GetMapping("/obtener-archivados/{codigo}")
    public ResponseEntity<ErrorMessage<List<BasicNegocioDTO>>> obtenerLugaresArchivados(@PathVariable String codigo) throws Exception{
        return ResponseEntity.ok().body(new ErrorMessage<>(false, usuarioServicioImp.obtenerLugaresArchivados(codigo)));

    }

    @PostMapping("/republicar-negocio/{codigo}")
    public ResponseEntity<ErrorMessage<String>> republicarNegocio(@PathVariable String codigo) throws Exception{
        usuarioServicioImp.republicarLugar(codigo);
        return ResponseEntity.ok().body(new ErrorMessage<>(false, "El negocio fue re plublicado"));

    }

    @PostMapping("/agregar-favoritos")
    public ResponseEntity<ErrorMessage<String>> agregarFavoritos(@RequestBody FavoritoDTO favoritoDTO) throws Exception{
        usuarioServicioImp.AddBusinessToUserFavorites(favoritoDTO.idUsuario(), favoritoDTO.idNegocio());
        return ResponseEntity.ok().body(new ErrorMessage<>(false, "El negocio fue agregado a favoritos"));

    }

    @PutMapping("/quitar-favorito/{codigo}")
    public ResponseEntity<ErrorMessage<String>> quitarFavoritos(@PathVariable String codigo, @RequestBody String idNegocio) throws Exception{
        usuarioServicioImp.RemoveBusinessFromUserFavorites(codigo, idNegocio);
        return ResponseEntity.ok().body(new ErrorMessage<>(false, "El negocio fue agregado a favoritos"));

    }

    @GetMapping("/obtener-favoritos/{codigo}")
    public ResponseEntity<ErrorMessage<List<ObtenerNegocioDTO>>> obtenerFavoritos(@PathVariable String codigo) throws Exception{
        return ResponseEntity.ok().body(new ErrorMessage<>(false, usuarioServicioImp.GetUserFavoriteBusiness(codigo)));

    }

    @PostMapping("/buscar-favorito/{codigo}")
    public ResponseEntity<ErrorMessage<Boolean>> buscarFavorito(@PathVariable String codigo, @RequestBody String idNegocio) throws Exception{

        return ResponseEntity.ok().body(new ErrorMessage<>(false, usuarioServicioImp.SearchFavoriteBusiness(codigo, idNegocio)));

    }









}
