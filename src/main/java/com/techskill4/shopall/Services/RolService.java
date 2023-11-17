package com.techskill4.shopall.Services;

import com.techskill4.shopall.JpaRepository.RolReposotory;
import com.techskill4.shopall.JpaRepository.UsuarioRepository;
import com.techskill4.shopall.Model.Rol;
import com.techskill4.shopall.Model.Usuario;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {
    @Autowired
    private RolReposotory rolReposotory;

    public List<Rol> obtenerRoles(){
        return rolReposotory.findAll();
    }

    public Rol obtenerRol(int id){
        return rolReposotory.findById(id).orElse(null);
    }

    public Rol crearRol(Rol rol){
        return rolReposotory.save(rol);
    }

    public Rol actualizarRol(Rol rol){
        return rolReposotory.save(rol);
    }

    public void eliminarRol(int id){
        rolReposotory.deleteById(id);
    }
}
