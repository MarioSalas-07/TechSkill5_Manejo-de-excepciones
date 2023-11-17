package com.techskill4.shopall.Services;

import com.techskill4.shopall.JpaRepository.PersonaRepository;
import com.techskill4.shopall.JpaRepository.UsuarioRepository;
import com.techskill4.shopall.Model.Persona;
import com.techskill4.shopall.Model.Usuario;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuario(int id){
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario crearUsuario(Usuario usuario){
        String ecryptarContrasenia = BCrypt.hashpw(usuario.getContrasenia(), BCrypt.gensalt());
        usuario.setContrasenia(ecryptarContrasenia);
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(int id){
        usuarioRepository.deleteById(id);
    }
}
