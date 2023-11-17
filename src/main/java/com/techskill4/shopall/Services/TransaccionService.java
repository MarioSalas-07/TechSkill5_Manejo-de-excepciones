package com.techskill4.shopall.Services;

import com.techskill4.shopall.JpaRepository.RolReposotory;
import com.techskill4.shopall.JpaRepository.TransaccionRepository;
import com.techskill4.shopall.Model.Rol;
import com.techskill4.shopall.Model.Transaccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransaccionService {
    @Autowired
    private TransaccionRepository transaccionRepository;

    public List<Transaccion> obtenerTransaccionesByUsuario(Long id){
        return transaccionRepository.findByCarritoUsuarioId(id);
    }
    public Transaccion crearTransaccion(Transaccion transaccion){
        return transaccionRepository.save(transaccion);
    }
}