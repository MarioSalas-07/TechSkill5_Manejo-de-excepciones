package com.techskill4.shopall;

import com.techskill4.shopall.Controller.Public.UsuarioRolController;
import com.techskill4.shopall.Model.ErrorHandler;
import com.techskill4.shopall.Model.Rol;
import com.techskill4.shopall.Model.Usuario;
import com.techskill4.shopall.Model.UsuarioRol;
import com.techskill4.shopall.Services.UsuarioRolService;
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
public class Usuario_has_rolesServiceTest {
    @Mock
    private UsuarioRolService usuarioRolService;
    @InjectMocks
    private UsuarioRolController usuarioRolController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCrearUsuarioRolSuccessful(){
        UsuarioRol objUsuarioRol = new UsuarioRol();
        when(usuarioRolService.crearUsuarioRol(objUsuarioRol)).thenReturn(objUsuarioRol);

        UsuarioRol result = usuarioRolService.crearUsuarioRol(objUsuarioRol);

        assertNotNull(result);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCrearUsuarioRolError(){
        UsuarioRol objUsuarioRol=new UsuarioRol(); //sin datos validos
        when(usuarioRolService.crearUsuarioRol(objUsuarioRol)).thenThrow(DataIntegrityViolationException.class);

        usuarioRolService.crearUsuarioRol(objUsuarioRol);
    }

    @Test
    public void testObtenerUsuarioRols(){
        List<UsuarioRol> usuariosRoles = Arrays.asList(new UsuarioRol(), new UsuarioRol());
        when(usuarioRolService.obtenerUsuariosRol()).thenReturn(usuariosRoles);

        List<UsuarioRol> result = usuarioRolService.obtenerUsuariosRol();

        assertEquals(2, result.size());
    }

    @Test
    public void testObtenerUsuarioRolExistente() {
        int usuarioRolId = 1;
        UsuarioRol objUsuarioRol = new UsuarioRol();
        when(usuarioRolService.obtenerUsuarioRol(usuarioRolId)).thenReturn(objUsuarioRol);

        UsuarioRol result = usuarioRolService.obtenerUsuarioRol(usuarioRolId);

        assertNotNull(result);
    }

    @Test
    public void testObtenerUsuarioRolNoExistente() {
        int usuarioRolId = 1;
        when(usuarioRolService.obtenerUsuarioRol(usuarioRolId)).thenReturn(null);

        UsuarioRol result = usuarioRolService.obtenerUsuarioRol(usuarioRolId);

        assertNull(result);
    }

    @Test
    public void testActualizarUsuarioRolExistente(){
        Rol objRol = new Rol();
        objRol.setId(1L);

        Usuario objUsuario = new Usuario();
        objUsuario.setId(1L);

        UsuarioRol objUsuarioRol = new UsuarioRol();
        objUsuarioRol.setId(1L);
        objUsuarioRol.setRol(objRol);
        objUsuarioRol.setUsuario(objUsuario);

        // Simular el comportamiento
        when(usuarioRolService.actualizarUsuarioRol(objUsuarioRol)).thenReturn(objUsuarioRol);

        // Invocar el método
        ResponseEntity<?> response = usuarioRolController.actualizarUsuarioRol(objUsuarioRol.getId(), objUsuarioRol);

        // Verificar que la respuesta es correcta
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(usuarioRolService, times(1)).actualizarUsuarioRol(any(UsuarioRol.class));
    }

    @Test
    public void testActualizarUsuarioRolNoExistente(){
        // Arrange
        UsuarioRol objUsuarioRol = new UsuarioRol();

        // Act
        when(usuarioRolService.actualizarUsuarioRol(objUsuarioRol)).thenReturn(null);
        ResponseEntity<?> result = usuarioRolController.actualizarUsuarioRol(1L, objUsuarioRol);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

        ErrorHandler error = (ErrorHandler) result.getBody();
        assertNotNull(error);
        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Rol no encontrado con ID: 1", error.getMessage());

        // Verify
        verify(usuarioRolService, times(1)).actualizarUsuarioRol(objUsuarioRol);
    }

    @Test
    public void testEliminarUsuarioRolExitente(){
        ResponseEntity<?> result = usuarioRolController.eliminarUsuarioRol(1);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    public void testEliminarUsuarioRolNoExistente(){
        doThrow(new EmptyResultDataAccessException(1)).when(usuarioRolService).eliminarUsuarioRol(1);

        ResponseEntity<?> result = usuarioRolController.eliminarUsuarioRol(1);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        ErrorHandler error = (ErrorHandler) result.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Usuario rol no encontrada con ID: 1", error.getMessage());
    }
}
