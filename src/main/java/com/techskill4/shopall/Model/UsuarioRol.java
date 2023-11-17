package com.techskill4.shopall.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Usuario_has_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRol {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
