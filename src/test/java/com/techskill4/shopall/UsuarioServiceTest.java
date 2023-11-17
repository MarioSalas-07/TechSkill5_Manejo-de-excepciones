package com.techskill4.shopall;

import com.techskill4.shopall.Controller.Public.UsuarioController;
import com.techskill4.shopall.Model.Empresa;
import com.techskill4.shopall.Model.ErrorHandler;
import com.techskill4.shopall.Model.Persona;
import com.techskill4.shopall.Model.Usuario;
import com.techskill4.shopall.Services.UsuarioService;
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
public class UsuarioServiceTest {
    @Mock
    private UsuarioService usuarioService;
    @InjectMocks
    private UsuarioController usuarioController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCrearUsuarioSuccessful(){
        Usuario objUsuario = new Usuario();
        when(usuarioService.crearUsuario(objUsuario)).thenReturn(objUsuario);

        Usuario result = usuarioService.crearUsuario(objUsuario);

        assertNotNull(result);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCrearUsuarioError(){
        Usuario objUsuario=new Usuario(); //sin datos validos
        when(usuarioService.crearUsuario(objUsuario)).thenThrow(DataIntegrityViolationException.class);

        usuarioService.crearUsuario(objUsuario);
    }

    @Test
    public void testObtenerUsuarios(){
        List<Usuario> usuarios = Arrays.asList(new Usuario(), new Usuario());
        when(usuarioService.obtenerUsuarios()).thenReturn(usuarios);

        List<Usuario> result = usuarioService.obtenerUsuarios();

        assertEquals(2, result.size());
    }

    @Test
    public void testObtenerUsuarioExistente() {
        int usuarioId = 1;
        Usuario objUsuario = new Usuario();
        when(usuarioService.obtenerUsuario(usuarioId)).thenReturn(objUsuario);

        Usuario result = usuarioService.obtenerUsuario(usuarioId);

        assertNotNull(result);
    }

    @Test
    public void testObtenerUsuarioNoExistente() {
        int usuarioId = 1;
        when(usuarioService.obtenerUsuario(usuarioId)).thenReturn(null);

        Usuario result = usuarioService.obtenerUsuario(usuarioId);

        assertNull(result);
    }

    @Test
    public void testActualizarUsuarioExistente(){
        Persona objPersona = new Persona();
        objPersona.setId(1L);

        Empresa objEmpresa = new Empresa();
        objEmpresa.setId(1L);

        Usuario objUsuario = new Usuario();
        objUsuario.setId(1L);
        objUsuario.setUsuario("Demo nombre ACTUALIZADO");
        objUsuario.setContrasenia("4777743529");
        objUsuario.setCorreo_electronico("Demo direccion");
        objUsuario.setPersona(objPersona);
        objUsuario.setEmpresa(objEmpresa);

        // Simular el comportamiento
        when(usuarioService.actualizarUsuario(objUsuario)).thenReturn(objUsuario);

        // Invocar el método
        ResponseEntity<?> response = usuarioController.actualizarUsuario(objUsuario.getId(), objUsuario);

        // Verificar que la respuesta es correcta
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Usuario usuarioActualizada = (Usuario) response.getBody();
        assertEquals("Demo nombre ACTUALIZADO", usuarioActualizada.getUsuario());
        assertEquals(1L, usuarioActualizada.getId().longValue());
        verify(usuarioService, times(1)).actualizarUsuario(any(Usuario.class));
    }

    @Test
    public void testActualizarUsuarioNoExistente(){
        // Arrange
        Usuario objUsuario = new Usuario();

        // Act
        when(usuarioService.actualizarUsuario(objUsuario)).thenReturn(null);
        ResponseEntity<?> result = usuarioController.actualizarUsuario(1L, objUsuario);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

        ErrorHandler error = (ErrorHandler) result.getBody();
        assertNotNull(error);
        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Usuario no encontrado con ID: 1", error.getMessage());

        // Verify
        verify(usuarioService, times(1)).actualizarUsuario(objUsuario);
    }

    @Test
    public void testEliminarUsuarioExitente(){
        ResponseEntity<?> result = usuarioController.eliminarUsuario(1);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    public void testEliminarUsuarioNoExistente(){
        doThrow(new EmptyResultDataAccessException(1)).when(usuarioService).eliminarUsuario(1);

        ResponseEntity<?> result = usuarioController.eliminarUsuario(1);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        ErrorHandler error = (ErrorHandler) result.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("usuario no encontrada con ID: 1", error.getMessage());
    }
}
