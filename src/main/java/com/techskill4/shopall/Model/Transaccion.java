package com.techskill4.shopall.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Transacciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal monto_total;
    private String direccion_envio;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fecha;
    @ManyToOne
    @JoinColumn(name = "id_carrito")
    private Carrito carrito;
}