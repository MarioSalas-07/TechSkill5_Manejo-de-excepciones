package com.techskill4.shopall.Controller.Protected;

import com.techskill4.shopall.Model.Carrito;
import com.techskill4.shopall.Model.CarritoProductos;
import com.techskill4.shopall.Model.ErrorHandler;
import com.techskill4.shopall.Services.CarritoProductosService;
import com.techskill4.shopall.Services.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/protected/api/carritoProductos")
public class CarritoProductosController {
    @Autowired
    private CarritoProductosService carritoProductosService;
    @Autowired
    private CarritoService carritoService;

    @GetMapping("/{id}")
    public ResponseEntity<List<CarritoProductos>> obtenerProductosCarrito(@PathVariable Long id){
        try {
            List<CarritoProductos> productos = carritoProductosService.obtenerProductosCarrito(id, 0);
            long id_carrito = id;
            Carrito carrito = carritoService.obtenerCarritoPorCarrito((int) id_carrito);

            BigDecimal costoTotal = productos.stream()
                    .map(producto -> BigDecimal.valueOf(producto.getProducto().getPrecio()).multiply(new BigDecimal(producto.getCantidad())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            System.out.println("costoTotal: "+ costoTotal);

            carrito.setPrecio_total(costoTotal);
            carritoService.actualizarCarrito(carrito);
            return new ResponseEntity<>(productos, HttpStatus.OK);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.NOT_FOUND.value(),
                    "Producto no encontrada con ID: " + id,
                    System.currentTimeMillis()
            );
            return new ResponseEntity(error, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> asignarProducto(@RequestBody CarritoProductos carritoProductos){
        try {
            CarritoProductos asignar = carritoProductosService.asignarProducto(carritoProductos);
            return new ResponseEntity<>(asignar, HttpStatus.CREATED);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/carrito/{carritoId}/producto/{productoId}")
    public ResponseEntity<?> actualizarCantidadProducto(@PathVariable Long carritoId, @PathVariable Long productoId,
                                                        @RequestParam int nuevaCantidad){
        try {
            carritoProductosService.actualizarCantidadProducto(carritoId, productoId, nuevaCantidad);
            return new ResponseEntity<>("Cantidad del producto en el carrito actualizada con éxito", HttpStatus.CREATED);
        }catch (Exception e){
            ErrorHandler error = new ErrorHandler(
                    HttpStatus.NOT_FOUND.value(),
                    "Cantidad no actualizada con ID: " + carritoId,
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> eliminarProductoDelCarrito(@PathVariable int id){
        try {
            carritoProductosService.eliminarProductoDelCarrito(id);
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
