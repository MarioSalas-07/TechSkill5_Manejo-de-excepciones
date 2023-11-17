package com.techskill4.shopall.Controller.Public;

import com.techskill4.shopall.Model.ErrorHandler;
import com.techskill4.shopall.Model.Persona;
import com.techskill4.shopall.Model.Usuario;
import com.techskill4.shopall.Services.PersonaService;
import com.techskill4.shopall.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/api/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<?> obtenerUsuarios(){
        try {
            List<Usuario> usuarios = usuarioService.obtenerUsuarios();
            return new ResponseEntity<>(usuarios, HttpStatus.OK);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable int id){
        try {
            Usuario usuario = usuarioService.obtenerUsuario(id);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.NOT_FOUND.value(),
                    "Usuario no encontrada con ID: " + id,
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario){
        try {
            Usuario crearUsuario = usuarioService.crearUsuario(usuario);
            return new ResponseEntity<>(crearUsuario, HttpStatus.CREATED);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        try {
            usuario.setId(id);
            Usuario actualizarUsuario = usuarioService.actualizarUsuario(usuario);
            return new ResponseEntity<>(actualizarUsuario, HttpStatus.CREATED);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.NOT_FOUND.value(),
                    "Usuario no encontrada con ID: " + id,
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable int id){
        try {
            usuarioService.eliminarUsuario(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.NOT_FOUND.value(),
                    "usuario no encontrada con ID: " + id,
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }
}
