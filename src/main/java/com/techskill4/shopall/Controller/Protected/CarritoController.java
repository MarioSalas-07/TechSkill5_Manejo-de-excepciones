package com.techskill4.shopall.Controller.Protected;

import com.techskill4.shopall.Model.Carrito;
import com.techskill4.shopall.Model.ErrorHandler;
import com.techskill4.shopall.Model.UsuarioRol;
import com.techskill4.shopall.Services.CarritoService;
import com.techskill4.shopall.Services.UsuarioRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/protected/api/carrito")
public class CarritoController {
    @Autowired
    private CarritoService carritoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCarrito(@PathVariable Long id){
        try {
            Carrito carrito = carritoService.obtenerCarritoPorUsuario(id);
            return new ResponseEntity<>(carrito, HttpStatus.OK);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.NOT_FOUND.value(),
                    "Carrito rol no encontrada con ID: " + id,
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> crearCarrito(@RequestBody Carrito carrito){
        try {
            if (!carritoService.usuarioExiste(carrito)){
                Carrito crearCarrito = carritoService.crearCarrito(carrito);
                return new ResponseEntity<>(crearCarrito, HttpStatus.CREATED);
            }else{
                ErrorHandler error = new ErrorHandler(
                        HttpStatus.BAD_REQUEST.value(),
                        "El usuario ya tiene su carrito",
                        System.currentTimeMillis()
                );
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
