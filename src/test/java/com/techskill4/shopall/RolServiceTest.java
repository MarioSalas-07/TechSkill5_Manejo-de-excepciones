package com.techskill4.shopall;

import com.techskill4.shopall.Controller.Public.RolController;
import com.techskill4.shopall.Model.*;
import com.techskill4.shopall.Services.RolService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
public class RolServiceTest {
    @Mock
    private RolService rolService;
    @InjectMocks
    private RolController rolController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCrearRolSuccessful(){
        Rol objRol = new Rol();
        when(rolService.crearRol(objRol)).thenReturn(objRol);

        Rol result = rolService.crearRol(objRol);

        assertNotNull(result);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCrearRolError(){
        Rol objRol=new Rol(); //sin datos validos
        when(rolService.crearRol(objRol)).thenThrow(DataIntegrityViolationException.class);

        rolService.crearRol(objRol);
    }

    @Test
    public void testObtenerRols(){
        List<Rol> roles = Arrays.asList(new Rol(), new Rol());
        when(rolService.obtenerRoles()).thenReturn(roles);

        List<Rol> result = rolService.obtenerRoles();

        assertEquals(2, result.size());
    }

    @Test
    public void testObtenerRolExistente() {
        int rolId = 1;
        Rol objRol = new Rol();
        when(rolService.obtenerRol(rolId)).thenReturn(objRol);

        Rol result = rolService.obtenerRol(rolId);

        assertNotNull(result);
    }

    @Test
    public void testObtenerRolNoExistente() {
        int rolId = 1;
        when(rolService.obtenerRol(rolId)).thenReturn(null);

        Rol result = rolService.obtenerRol(rolId);

        assertNull(result);
    }

    @Test
    public void testActualizarRolExistente(){
        Rol objRol = new Rol();
        objRol.setId(1L);
        objRol.setNombre("Demo nombre ACTUALIZADO");

        // Simular el comportamiento
        when(rolService.actualizarRol(objRol)).thenReturn(objRol);

        // Invocar el método
        ResponseEntity<?> response = rolController.actualizarRol(objRol.getId(), objRol);

        // Verificar que la respuesta es correcta
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Rol personaActualizada = (Rol) response.getBody();
        assertEquals("Demo nombre ACTUALIZADO", personaActualizada.getNombre());
        assertEquals(1L, personaActualizada.getId().longValue());
        verify(rolService, times(1)).actualizarRol(any(Rol.class));
    }

    @Test
    public void testActualizarRolNoExistente(){
        // Arrange
        Rol objRol = new Rol();

        // Act
        when(rolService.actualizarRol(objRol)).thenReturn(null);
        ResponseEntity<?> result = rolController.actualizarRol(1L, objRol);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

        ErrorHandler error = (ErrorHandler) result.getBody();
        assertNotNull(error);
        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Rol no encontrado con ID: 1", error.getMessage());

        // Verify
        verify(rolService, times(1)).actualizarRol(objRol);
    }

    @Test
    public void testEliminarRolExitente(){
        ResponseEntity<?> result = rolController.eliminarRol(1);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    public void testEliminarRolNoExistente(){
        doThrow(new EmptyResultDataAccessException(1)).when(rolService).eliminarRol(1);

        ResponseEntity<?> result = rolController.eliminarRol(1);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        ErrorHandler error = (ErrorHandler) result.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Rol no encontrada con ID: 1", error.getMessage());
    }
}
