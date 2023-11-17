package com.techskill4.shopall.JpaRepository;

import com.techskill4.shopall.Model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
    List<Comentario> findByProductoId(Long productoId);
}
