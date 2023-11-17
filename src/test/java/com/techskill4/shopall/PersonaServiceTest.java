package com.techskill4.shopall;

import com.techskill4.shopall.Controller.Public.PersonaController;
import com.techskill4.shopall.Model.ErrorHandler;
import com.techskill4.shopall.Model.Persona;
import com.techskill4.shopall.Services.PersonaService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PersonaServiceTest {
    @Mock
    private PersonaService personaService;
    @InjectMocks
    private PersonaController personaController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCrearPersonaSuccessful(){
        Persona objPersona = new Persona();
        when(personaService.crearPersona(objPersona)).thenReturn(objPersona);

        Persona result = personaService.crearPersona(objPersona);

        assertNotNull(result);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCrearPersonaError(){
        Persona objPersona=new Persona(); //Persona sin datos validos
        when(personaService.crearPersona(objPersona)).thenThrow(DataIntegrityViolationException.class);

        personaService.crearPersona(objPersona);
    }

    @Test
    public void testObtenerPersonas(){
        List<Persona> personas = Arrays.asList(new Persona(), new Persona());
        when(personaService.obtenerPersonas()).thenReturn(personas);

        List<Persona> result = personaService.obtenerPersonas();

        assertEquals(2, result.size());
    }

    @Test
    public void testObtenerPersonaExistente() {
        int personaId = 1;
        Persona objPersona = new Persona();
        when(personaService.obtenerPersona(personaId)).thenReturn(objPersona);

        Persona result = personaService.obtenerPersona(personaId);

        assertNotNull(result);
    }

    @Test
    public void testObtenerPersonaNoExistente() {
        int personaId = 1;
        when(personaService.obtenerPersona(personaId)).thenReturn(null);

        Persona result = personaService.obtenerPersona(personaId);

        assertNull(result);
    }

    @Test
    public void testActualizarPersonaExistente(){
        // Crear una persona de prueba
        Persona objPersona = new Persona();
        objPersona.setId(1L);
        objPersona.setNombre("Demo nombre ACTUALIZADO");
        objPersona.setApellido_paterno("Demo apellido paterno");
        objPersona.setApellido_materno("Demo apellido materno");
        objPersona.setCalle("Demo calle");
        objPersona.setNum_ext(101);
        objPersona.setNum_int(101);
        objPersona.setCp(30000);
        objPersona.setCiudad("Demo ciudad");
        objPersona.setTelefono("4771234567");
        objPersona.setColonia("Demo colonia");

        // Simular el comportamiento de personaRepository.save()
        when(personaService.actualizarPersona(objPersona)).thenReturn(objPersona);

        // Invocar el método actualizarPersona()
        ResponseEntity<?> result = personaController.actualizarPersona(objPersona.getId(), objPersona);

        // Verificar que la respuesta es correcta
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        Persona personaActualizada = (Persona) result.getBody();
        assertEquals("Demo nombre ACTUALIZADO", personaActualizada.getNombre());
        assertEquals(1L, personaActualizada.getId().longValue());
        verify(personaService, times(1)).actualizarPersona(any(Persona.class));
    }

    @Test
    public void testActualizarPersonaNoExistente(){
        // Arrange
        Persona personaNoExistente = new Persona();

        // Act
        when(personaService.actualizarPersona(personaNoExistente)).thenReturn(null);
        ResponseEntity<?> result = personaController.actualizarPersona(1L, personaNoExistente);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

        ErrorHandler error = (ErrorHandler) result.getBody();
        assertNotNull(error);
        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Persona no encontrada con ID: 1", error.getMessage());

        // Verify
        verify(personaService, times(1)).actualizarPersona(personaNoExistente);
    }

    @Test
    public void testEliminarPersonaExitente(){
        ResponseEntity<?> result = personaController.eliminarPersona(1);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    public void testEliminarPersonaNoExistente(){
        doThrow(new EmptyResultDataAccessException(1)).when(personaService).eliminarPersona(1);

        ResponseEntity<?> result = personaController.eliminarPersona(1);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        ErrorHandler error = (ErrorHandler) result.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Persona no encontrada con ID: 1", error.getMessage());
    }
}
