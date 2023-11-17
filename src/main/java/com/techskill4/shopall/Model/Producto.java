package com.techskill4.shopall.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private float precio;
    private int cantidad;
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private CategoriaProducto categoriaProducto;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}