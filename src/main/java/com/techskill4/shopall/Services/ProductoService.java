package com.techskill4.shopall.Services;

import com.techskill4.shopall.JpaRepository.CategoriaProductoRepository;
import com.techskill4.shopall.JpaRepository.ProductoRepository;
import com.techskill4.shopall.Model.CategoriaProducto;
import com.techskill4.shopall.Model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerProductos(){
        return productoRepository.findAll();
    }

    public Producto obtenerProducto(int id){
        return productoRepository.findById(id).orElse(null);
    }

    public Producto crearProducto(Producto producto){
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(Producto producto){
        return productoRepository.save(producto);
    }

    public void eliminarProducto(int id){
        productoRepository.deleteById(id);
    }
}
