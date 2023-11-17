package com.techskill4.shopall;

import com.techskill4.shopall.Controller.Protected.ComentarioController;
import com.techskill4.shopall.Model.*;
import com.techskill4.shopall.Services.ComentarioService;
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
public class ComentariosServiceTest {
    @Mock
    private ComentarioService comentarioService;
    @InjectMocks
    private ComentarioController comentarioController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCrearComentarioSuccessful(){
        Comentario objComentario = new Comentario();
        when(comentarioService.crearComentario(objComentario)).thenReturn(objComentario);

        Comentario result = comentarioService.crearComentario(objComentario);

        assertNotNull(result);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCrearComentarioError(){
        Comentario objComentario=new Comentario(); //sin datos validos
        when(comentarioService.crearComentario(objComentario)).thenThrow(DataIntegrityViolationException.class);

        comentarioService.crearComentario(objComentario);
    }

    @Test
    public void testObtenerComentarios(){
        List<Comentario> comentarios = Arrays.asList(new Comentario(), new Comentario());
        when(comentarioService.obtenerComentariosByProductoId(1L)).thenReturn(comentarios);

        List<Comentario> result = comentarioService.obtenerComentariosByProductoId(1L);

        assertEquals(2, result.size());
    }

    @Test
    public void testObtenerComentarioExistente() {
        int empresaId = 1;
        Comentario objComentario = new Comentario();
        when(comentarioService.obtenerComentario(empresaId)).thenReturn(objComentario);

        Comentario result = comentarioService.obtenerComentario(empresaId);

        assertNotNull(result);
    }

    @Test
    public void testObtenerComentarioNoExistente() {
        int empresaId = 1;
        when(comentarioService.obtenerComentario(empresaId)).thenReturn(null);

        Comentario result = comentarioService.obtenerComentario(empresaId);

        assertNull(result);
    }

    @Test
    public void testActualizarComentarioExistente(){
        Usuario objUsuario=new Usuario();
        objUsuario.setId(1L);

        Producto objProducto=new Producto();
        objProducto.setId(1L);

        Comentario objComentario = new Comentario();
        objComentario.setId(1L);
        objComentario.setCalificacion(5);
        objComentario.setComentario("DEMO DEL COMENTARIO ACTUALIZADO");
        objComentario.setUsuario(objUsuario);
        objComentario.setProducto(objProducto);

        // Simular el comportamiento de
        when(comentarioService.actualizarComentario(objComentario)).thenReturn(objComentario);

        // Invocar el método
        ResponseEntity<?> response = comentarioController.actualizarComentario(objComentario.getId(), objComentario);

        // Verificar que la respuesta es correcta
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Comentario comentarioActualizado = (Comentario) response.getBody();
        assertEquals("DEMO DEL COMENTARIO ACTUALIZADO", comentarioActualizado.getComentario());
        assertEquals(1L, comentarioActualizado.getId().longValue());
        verify(comentarioService, times(1)).actualizarComentario(any(Comentario.class));
    }

    @Test
    public void testActualizarComentarioNoExistente(){
        // Arrange
        Comentario objComentario = new Comentario();

        // Act
        when(comentarioService.actualizarComentario(objComentario)).thenReturn(null);
        ResponseEntity<?> result = comentarioController.actualizarComentario(1L, objComentario);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

        ErrorHandler error = (ErrorHandler) result.getBody();
        assertNotNull(error);
        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Empresa no encontrada con ID: 1", error.getMessage());

        // Verify
        verify(comentarioService, times(1)).actualizarComentario(objComentario);
    }

    @Test
    public void testEliminarComentarioExitente(){
        ResponseEntity<?> result = comentarioController.eliminarComentario(1);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    public void testEliminarComentarioNoExistente(){
        doThrow(new EmptyResultDataAccessException(1)).when(comentarioService).eliminarComentario(1);

        ResponseEntity<?> result = comentarioController.eliminarComentario(1);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        ErrorHandler error = (ErrorHandler) result.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Comentario no encontrada con ID: 1", error.getMessage());
    }
}
