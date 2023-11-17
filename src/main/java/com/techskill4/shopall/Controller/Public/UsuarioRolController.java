package com.techskill4.shopall.Controller.Public;

import com.techskill4.shopall.Model.ErrorHandler;
import com.techskill4.shopall.Model.Rol;
import com.techskill4.shopall.Model.UsuarioRol;
import com.techskill4.shopall.Services.RolService;
import com.techskill4.shopall.Services.UsuarioRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/api/usuarioRol")
public class UsuarioRolController {
    @Autowired
    private UsuarioRolService usuarioRolService;

    @GetMapping
    public ResponseEntity<?> obtenerUsuariosRol(){
        try {
            List<UsuarioRol> usuarioRol = usuarioRolService.obtenerUsuariosRol();
            return new ResponseEntity<>(usuarioRol, HttpStatus.OK);
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
    public ResponseEntity<?> obtenerUsuarioRol(@PathVariable int id){
        try {
            UsuarioRol usuarioRol = usuarioRolService.obtenerUsuarioRol(id);
            return new ResponseEntity<>(usuarioRol, HttpStatus.OK);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.NOT_FOUND.value(),
                    "Usuario rol no encontrada con ID: " + id,
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> crearUsuarioRol(@RequestBody UsuarioRol usuarioRol){
        try {
            UsuarioRol crearRol = usuarioRolService.crearUsuarioRol(usuarioRol);
            return new ResponseEntity<>(crearRol, HttpStatus.CREATED);
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
    public ResponseEntity<?> actualizarUsuarioRol(@PathVariable Long id, @RequestBody UsuarioRol usuarioRol){
        try {
            usuarioRol.setId(id);
            UsuarioRol actualizarRol = usuarioRolService.actualizarUsuarioRol(usuarioRol);
            return new ResponseEntity<>(actualizarRol, HttpStatus.CREATED);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.NOT_FOUND.value(),
                    "Usuario rol no encontrada con ID: " + id,
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuarioRol(@PathVariable int id){
        try {
            usuarioRolService.eliminarUsuarioRol(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.NOT_FOUND.value(),
                    "Usuario rol no encontrada con ID: " + id,
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }
}
