package com.techskill4.shopall.JpaRepository;

import com.techskill4.shopall.Model.Transaccion;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion, Integer> {
    List<Transaccion> findByCarritoUsuarioId(Long usuarioId);
}
