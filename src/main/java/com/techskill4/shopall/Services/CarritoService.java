package com.techskill4.shopall.Services;

import com.techskill4.shopall.JpaRepository.CarritoRepository;
import com.techskill4.shopall.JpaRepository.ProductoRepository;
import com.techskill4.shopall.Model.Carrito;
import com.techskill4.shopall.Model.CategoriaProducto;
import com.techskill4.shopall.Model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarritoService {
    @Autowired
    private CarritoRepository carritoRepository;

    public Carrito obtenerCarritoPorUsuario(Long id){
        return carritoRepository.findByUsuarioId(id).orElse(null);
    }

    public Carrito obtenerCarritoPorCarrito(int id){
        Optional<Carrito> carrito = carritoRepository.findById(id);

        if(carrito.isPresent()){
            return carrito.get();
        }else{
            return null;
        }
    }

    public Carrito crearCarrito(Carrito carrito){
        return carritoRepository.save(carrito);
    }

    public boolean usuarioExiste(Carrito carrito){
        Long idUsuario = carrito.getUsuario().getId();

        Optional<Carrito> carritoExistente = carritoRepository.findByUsuarioId(idUsuario);
        return carritoExistente.isPresent();
    }

    public Carrito actualizarCarrito(Carrito carrito){
        return carritoRepository.save(carrito);
    }
}
