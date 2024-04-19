package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.services.implementations.UsuarioServicioImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteControlador {
    private final UsuarioServicioImp usuarioServicioImp;

    @DeleteMapping("/eliminar-usuario/{codigo}")
    public ResponseEntity<MensajeDTO<String>> eliminarCuenta(@PathVariable String codigo) throws Exception{
        usuarioServicioImp.eliminarCuenta(codigo);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "El usuario se eliminó correctamente"));
    }

    @PutMapping("/actualizar-cliente")
    public ResponseEntity<MensajeDTO<String>> actualizarUsuario(@Valid @RequestBody ActualizarUsuarioDTO actualizarUsuarioDTO)throws Exception{
        usuarioServicioImp.editarPerfil(actualizarUsuarioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "El usuario ha sido actualizado"));
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<MensajeDTO<DetalleUsuarioDTO>> obtenerCliente(@PathVariable String codigo) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,  usuarioServicioImp.obtenerUsuario(codigo)));
    }

    @GetMapping("/get-all")
    public ResponseEntity<MensajeDTO<List<ItemUsuarioDTO>>> obtenerClientes() throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,  usuarioServicioImp.obtenerUsuarios(1)));
    }

    @GetMapping("/obtener-archivados/{codigo}")
    public ResponseEntity<MensajeDTO<List<DetalleNegocioDTO>>> obtenerLugaresArchivados(@PathVariable String codigo) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false, usuarioServicioImp.obtenerLugaresArchivados(codigo)));

    }

    @PostMapping("/republicar-negocio/{codigo}")
    public ResponseEntity<MensajeDTO<String>> republicarNegocio(@PathVariable String codigo) throws Exception{
        usuarioServicioImp.republicarLugar(codigo);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "El negocio fue re plublicado"));

    }









}
