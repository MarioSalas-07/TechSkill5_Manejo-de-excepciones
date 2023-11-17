package com.techskill4.shopall.JpaRepository;

import com.techskill4.shopall.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}
