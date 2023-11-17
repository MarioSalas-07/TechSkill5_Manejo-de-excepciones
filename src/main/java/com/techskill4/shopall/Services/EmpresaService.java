package com.techskill4.shopall.Services;

import com.techskill4.shopall.JpaRepository.EmpresaRepository;
import com.techskill4.shopall.Model.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;

    public List<Empresa> obtenerEmpresas(){
        return empresaRepository.findAll();
    }

    public Empresa obtenerEmpresa(int id){
        return empresaRepository.findById(id).orElse(null);
    }

    public Empresa crearEmpresa(Empresa comprador){
        return empresaRepository.save(comprador);
    }

    public Empresa actualizarEmpresa(Empresa comprador){
        return empresaRepository.save(comprador);
    }

    public void eliminarEmpresa(int id){
        empresaRepository.deleteById(id);
    }
}
