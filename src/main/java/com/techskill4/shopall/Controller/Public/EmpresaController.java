package com.techskill4.shopall.Controller.Public;

import com.techskill4.shopall.Model.Empresa;
import com.techskill4.shopall.Model.ErrorHandler;
import com.techskill4.shopall.Services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/api/empresa")
public class EmpresaController {
    @Autowired
    private EmpresaService empresaService;

    @GetMapping
    public ResponseEntity<?> obtenerEmpresas(){
        try {
            List<Empresa> empresas = empresaService.obtenerEmpresas();
            return new ResponseEntity<>(empresas, HttpStatus.OK);
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
    public ResponseEntity<?> obtenerEmpresa(@PathVariable int id){
        try {
            Empresa empresa = empresaService.obtenerEmpresa(id);
            return new ResponseEntity<>(empresa, HttpStatus.OK);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.NOT_FOUND.value(),
                    "Empresa no encontrada con ID: " + id,
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> crearEmpresa(@RequestBody Empresa empresa){
        try {
            Empresa crearEmpresa = empresaService.crearEmpresa(empresa);
            return new ResponseEntity<>(crearEmpresa, HttpStatus.CREATED);
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
    public ResponseEntity<?> actualizarEmpresa(@PathVariable Long id, @RequestBody Empresa empresa){
        try {
            empresa.setId(id);
            Empresa actualizarEmpresa = empresaService.actualizarEmpresa(empresa);
            return new ResponseEntity<>(actualizarEmpresa, HttpStatus.CREATED);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.NOT_FOUND.value(),
                    "Empresa no encontrada con ID: " + id,
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEmpresa(@PathVariable int id){
        try {
            empresaService.eliminarEmpresa(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.NOT_FOUND.value(),
                    "Empresa no encontrada con ID: " + id,
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }
}
