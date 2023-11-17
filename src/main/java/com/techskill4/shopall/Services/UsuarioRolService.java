package com.techskill4.shopall.Services;

import com.techskill4.shopall.JpaRepository.RolReposotory;
import com.techskill4.shopall.JpaRepository.UsuarioRepository;
import com.techskill4.shopall.JpaRepository.UsuarioRolRepository;
import com.techskill4.shopall.Model.Rol;
import com.techskill4.shopall.Model.UsuarioRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioRolService {
    @Autowired
    private UsuarioRolRepository usuarioRolRepository;

    public List<UsuarioRol> obtenerUsuariosRol(){
        return usuarioRolRepository.findAll();
    }

    public UsuarioRol obtenerUsuarioRol(int id){
        return usuarioRolRepository.findById(id).orElse(null);
    }

    public UsuarioRol crearUsuarioRol(UsuarioRol rol){
        return usuarioRolRepository.save(rol);
    }

    public UsuarioRol actualizarUsuarioRol(UsuarioRol rol){
        return usuarioRolRepository.save(rol);
    }

    public void eliminarUsuarioRol(int id){
        usuarioRolRepository.deleteById(id);
    }
}