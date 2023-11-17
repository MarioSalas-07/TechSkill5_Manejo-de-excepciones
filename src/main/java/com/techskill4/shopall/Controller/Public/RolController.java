package com.techskill4.shopall.Controller.Public;

import com.techskill4.shopall.Model.ErrorHandler;
import com.techskill4.shopall.Model.Rol;
import com.techskill4.shopall.Model.Usuario;
import com.techskill4.shopall.Services.RolService;
import com.techskill4.shopall.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/api/rol")
public class RolController {
    @Autowired
    private RolService rolService;

    @GetMapping
    public ResponseEntity<?> obtenerRoles(){
        try {
            List<Rol> roles = rolService.obtenerRoles();
            return new ResponseEntity<>(roles, HttpStatus.OK);
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
    public ResponseEntity<?> obtenerRol(@PathVariable int id){
        try {
            Rol rol = rolService.obtenerRol(id);
            return new ResponseEntity<>(rol, HttpStatus.OK);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.NOT_FOUND.value(),
                    "Rol no encontrada con ID: " + id,
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> crearRol(@RequestBody Rol rol){
        try {
            Rol crearRol = rolService.crearRol(rol);
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
    public ResponseEntity<?> actualizarRol(@PathVariable Long id, @RequestBody Rol rol){
        try {
            rol.setId(id);
            Rol actualizarRol = rolService.actualizarRol(rol);
            return new ResponseEntity<>(actualizarRol, HttpStatus.CREATED);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.NOT_FOUND.value(),
                    "Rol no encontrada con ID: " + id,
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarRol(@PathVariable int id){
        try {
            rolService.eliminarRol(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.NOT_FOUND.value(),
                    "Rol no encontrada con ID: " + id,
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }
}
