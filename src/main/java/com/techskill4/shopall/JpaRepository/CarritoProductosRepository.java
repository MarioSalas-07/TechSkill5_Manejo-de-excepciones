package com.techskill4.shopall.JpaRepository;

import com.techskill4.shopall.Model.Carrito;
import com.techskill4.shopall.Model.CarritoProductos;
import com.techskill4.shopall.Model.Producto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarritoProductosRepository extends JpaRepository<CarritoProductos, Integer> {
    List<CarritoProductos> findProductosByCarritoIdAndEstatus(Long id_carrito, int estatus);
    @Modifying
    @Query("UPDATE CarritoProductos cp SET cp.cantidad = :nuevaCantidad " +
            "WHERE cp.carrito.id = :carritoId AND cp.producto.id = :productoId")
    @Transactional
    void actualizarCantidadProductoEnCarrito(@Param("carritoId") Long id_carrito, @Param("productoId") Long id_producto,
                                             @Param("nuevaCantidad") int nuevaCantidad);
}
