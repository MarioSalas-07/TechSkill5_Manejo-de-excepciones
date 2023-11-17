package com.techskill4.shopall.Controller.Protected;

import com.techskill4.shopall.Model.*;
import com.techskill4.shopall.Services.CarritoProductosService;
import com.techskill4.shopall.Services.CarritoService;
import com.techskill4.shopall.Services.ProductoService;
import com.techskill4.shopall.Services.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/protected/api/transaccion")
public class TransaccionController {
    @Autowired
    private TransaccionService transaccionService;
    @Autowired
    private CarritoService carritoService;
    @Autowired
    private CarritoProductosService carritoProductosService;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerTransacciones(@PathVariable Long usuarioId){
        try {
            List<Transaccion> transaccion = transaccionService.obtenerTransaccionesByUsuario(usuarioId);
            return new ResponseEntity<>(transaccion, HttpStatus.OK);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/carritoTransaccion/{carritoId}")
    public ResponseEntity<?> obtenerProductosTransaccion(@PathVariable Long carritoId){
        try {
            List<CarritoProductos> productos = carritoProductosService.obtenerProductosCarrito(carritoId, 1);
            return new ResponseEntity<>(productos, HttpStatus.OK);
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
    public ResponseEntity<?> realizarTransaccion(@RequestBody Transaccion transaccion){
        try {
            Carrito carrito = carritoService.obtenerCarritoPorUsuario(transaccion.getCarrito().getId());
            List<CarritoProductos> productos = carritoProductosService.obtenerProductosCarrito(carrito.getId(), 0);
            Date fechaActual = new Date();

            transaccion.setMonto_total(carrito.getPrecio_total());
            transaccion.setFecha(fechaActual);

            int estatus = 1;
            for (CarritoProductos producto : productos){
                producto.setEstatus((byte) estatus);
                carritoProductosService.asignarProducto(producto);
            }

            carritoService.actualizarCarrito(carrito);
            Transaccion realizarTransaccion = transaccionService.crearTransaccion(transaccion);
            return new ResponseEntity<>(realizarTransaccion, HttpStatus.CREATED);
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
