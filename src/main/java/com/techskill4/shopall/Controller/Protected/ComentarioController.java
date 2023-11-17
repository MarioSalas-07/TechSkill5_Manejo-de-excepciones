package com.techskill4.shopall.Controller.Protected;

import com.techskill4.shopall.Model.Comentario;
import com.techskill4.shopall.Model.Empresa;
import com.techskill4.shopall.Model.ErrorHandler;
import com.techskill4.shopall.Services.ComentarioService;
import com.techskill4.shopall.Services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/protected/api/comentario")
public class ComentarioController {
    @Autowired
    private ComentarioService comentarioService;

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<?> obtenerComentariosByProductoId(@PathVariable Long productoId){
        try {
            List<Comentario> comentarios = comentarioService.obtenerComentariosByProductoId(productoId);
            return new ResponseEntity<>(comentarios, HttpStatus.OK);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> crearComentario(@RequestBody Comentario comentario){
        try {
            Comentario crearComentario = comentarioService.crearComentario(comentario);
            return new ResponseEntity<>(crearComentario, HttpStatus.CREATED);
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
    public ResponseEntity<?> actualizarComentario(@PathVariable Long id, @RequestBody Comentario comentario){
        try {
            comentario.setId(id);
            Comentario actualizarComentario = comentarioService.actualizarComentario(comentario);
            return new ResponseEntity<>(actualizarComentario, HttpStatus.CREATED);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.NOT_FOUND.value(),
                    "Comentario no encontrada con ID: " + id,
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarComentario(@PathVariable int id){
        try {
            comentarioService.eliminarComentario(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.NOT_FOUND.value(),
                    "Comentario no encontrada con ID: " + id,
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }
}
