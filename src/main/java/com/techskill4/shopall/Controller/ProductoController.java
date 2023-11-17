package com.techskill4.shopall.Controller;

import com.techskill4.shopall.Model.CategoriaProducto;
import com.techskill4.shopall.Model.ErrorHandler;
import com.techskill4.shopall.Model.Producto;
import com.techskill4.shopall.Services.CategoriaProductoService;
import com.techskill4.shopall.Services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping("/public/api/producto")
    public ResponseEntity<?> obtenerProductos(){
        try {
            List<Producto> producto = productoService.obtenerProductos();
            return new ResponseEntity<>(producto, HttpStatus.OK);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/public/api/producto/{id}")
    public ResponseEntity<?> obtenerProducto(@PathVariable int id){
        try {
            Producto producto = productoService.obtenerProducto(id);
            return new ResponseEntity<>(producto, HttpStatus.OK);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.NOT_FOUND.value(),
                    "Producto no encontrada con ID: " + id,
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/protected/api/producto")
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto){
        try {
            Producto crearProducto = productoService.crearProducto(producto);
            return new ResponseEntity<>(crearProducto, HttpStatus.CREATED);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/protected/api/producto/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto){
        try {
            producto.setId(id);
            Producto actualizarProducto = productoService.actualizarProducto(producto);
            return new ResponseEntity<>(actualizarProducto, HttpStatus.CREATED);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.NOT_FOUND.value(),
                    "Vendedor no encontrada con ID: " + id,
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/protected/api/producto/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable int id){
        try {
            productoService.eliminarProducto(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.NOT_FOUND.value(),
                    "Producto no encontrada con ID: " + id,
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }
}
