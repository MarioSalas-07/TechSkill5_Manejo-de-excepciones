package com.techskill4.shopall.Services;

import com.techskill4.shopall.JpaRepository.ComentarioRepository;
import com.techskill4.shopall.JpaRepository.EmpresaRepository;
import com.techskill4.shopall.Model.Comentario;
import com.techskill4.shopall.Model.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioService {
    @Autowired
    private ComentarioRepository comentarioRepository;

    public List<Comentario> obtenerComentariosByProductoId(Long productoId){
        return comentarioRepository.findByProductoId(productoId);
    }

    public Comentario obtenerComentario(int id){
        return comentarioRepository.findById(id).orElse(null);
    }

    public Comentario crearComentario(Comentario comentario){
        return comentarioRepository.save(comentario);
    }

    public Comentario actualizarComentario(Comentario comentario){
        return comentarioRepository.save(comentario);
    }

    public void eliminarComentario(int id){
        comentarioRepository.deleteById(id);
    }
}
