package com.techskill4.shopall.Services;

import com.techskill4.shopall.JpaRepository.CategoriaProductoRepository;
import com.techskill4.shopall.Model.CategoriaProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaProductoService {
    @Autowired
    private CategoriaProductoRepository categoriaProductoRepository;

    public List<CategoriaProducto> obtenerCategoriasProductos(){
        return categoriaProductoRepository.findAll();
    }

    public CategoriaProducto obtenerCategoriaProducto(int id){
        return categoriaProductoRepository.findById(id).orElse(null);
    }

    public CategoriaProducto crearCategoriaProducto(CategoriaProducto categoriaProducto){
        return categoriaProductoRepository.save(categoriaProducto);
    }

    public CategoriaProducto actualizarCategoriaProducto(CategoriaProducto categoriaProducto){
        return categoriaProductoRepository.save(categoriaProducto);
    }

    public void eliminarCategoriaProducto(int id){
        categoriaProductoRepository.deleteById(id);
    }
}
