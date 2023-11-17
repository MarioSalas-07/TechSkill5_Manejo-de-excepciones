package com.techskill4.shopall.Services;

import com.techskill4.shopall.JpaRepository.CarritoProductosRepository;
import com.techskill4.shopall.JpaRepository.CarritoRepository;
import com.techskill4.shopall.JpaRepository.ProductoRepository;
import com.techskill4.shopall.Model.Carrito;
import com.techskill4.shopall.Model.CarritoProductos;
import com.techskill4.shopall.Model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarritoProductosService {
    @Autowired
    private CarritoProductosRepository carritoProductosRepository;
    @Autowired
    private CarritoRepository carritoRepository;

    public Optional<Carrito> obtenerCarrito(int id){
        return carritoRepository.findById(id);
    }
    public List<CarritoProductos> obtenerProductosCarrito(Long id, int estatus){
        return carritoProductosRepository.findProductosByCarritoIdAndEstatus(id,estatus);
    }

    public CarritoProductos asignarProducto(CarritoProductos producto){
        return carritoProductosRepository.save(producto);
    }

    public void actualizarCantidadProducto(Long carritoId, Long productoId, int nuevaCantidad){
        carritoProductosRepository.actualizarCantidadProductoEnCarrito(carritoId, productoId, nuevaCantidad);
    }

    public void eliminarProductoDelCarrito(int id){
        carritoProductosRepository.deleteById(id);
    }
}
