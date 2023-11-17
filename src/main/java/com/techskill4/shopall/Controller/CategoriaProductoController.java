package com.techskill4.shopall.Controller;

import com.techskill4.shopall.Model.CategoriaProducto;
import com.techskill4.shopall.Model.ErrorHandler;
import com.techskill4.shopall.Services.CategoriaProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoriaProductoController {
    @Autowired
    private CategoriaProductoService categoriaProductoService;

    @GetMapping("/public/api/categoriaProducto")
    public ResponseEntity<?> obtenerCategoriasProductos(){
        try {
            List<CategoriaProducto> categoriaProducto = categoriaProductoService.obtenerCategoriasProductos();
            return new ResponseEntity<>(categoriaProducto, HttpStatus.OK);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/public/api/categoriaProducto/{id}")
    public ResponseEntity<?> obtenerCategoriaProducto(@PathVariable int id){
        try {
            CategoriaProducto categoriaProducto = categoriaProductoService.obtenerCategoriaProducto(id);
            return new ResponseEntity<>(categoriaProducto, HttpStatus.OK);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.NOT_FOUND.value(),
                    "Categoria no encontrada con ID: " + id,
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/protected/api/categoriaProducto")
    public ResponseEntity<?> crearCategoriaProducto(@RequestBody CategoriaProducto categoriaProducto){
        try {
            CategoriaProducto crearCategoriaProducto = categoriaProductoService.crearCategoriaProducto(categoriaProducto);
            return new ResponseEntity<>(crearCategoriaProducto, HttpStatus.CREATED);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/protected/api/categoriaProducto/{id}")
    public ResponseEntity<?> actualizarCategoriaProducto(@PathVariable Long id, @RequestBody CategoriaProducto categoriaProducto){
        try {
            categoriaProducto.setId(id);
            CategoriaProducto actualizarCategoriaProducto = categoriaProductoService.actualizarCategoriaProducto(categoriaProducto);
            return new ResponseEntity<>(actualizarCategoriaProducto, HttpStatus.CREATED);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.NOT_FOUND.value(),
                    "Vendedor no encontrada con ID: " + id,
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/protected/api/categoriaProducto/{id}")
    public ResponseEntity<?> eliminarCategoriaProducto(@PathVariable int id){
        try {
            categoriaProductoService.eliminarCategoriaProducto(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.NOT_FOUND.value(),
                    "Categoria no encontrada con ID: " + id,
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }
}