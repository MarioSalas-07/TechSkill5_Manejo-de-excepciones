package com.techskill4.shopall.Services;

import com.techskill4.shopall.JpaRepository.PersonaRepository;
import com.techskill4.shopall.Model.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaService {
    @Autowired
    private PersonaRepository personaRepository;

    public List<Persona> obtenerPersonas(){
        return personaRepository.findAll();
    }

    public Persona obtenerPersona(int id){
        return personaRepository.findById(id).orElse(null);
    }

    public Persona crearPersona(Persona persona){
        return personaRepository.save(persona);
    }

    public Persona actualizarPersona(Persona persona){
        return personaRepository.save(persona);
    }

    public void eliminarPersona(int id){
        personaRepository.deleteById(id);
    }
}
