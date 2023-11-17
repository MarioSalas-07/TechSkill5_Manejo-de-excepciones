package com.techskill4.shopall;

import com.techskill4.shopall.Controller.CategoriaProductoController;
import com.techskill4.shopall.Model.*;
import com.techskill4.shopall.Services.CategoriaProductoService;
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
public class CatgoriasProductosServiceTest {
    @Mock
    private CategoriaProductoService categoriaProductoService;
    @InjectMocks
    private CategoriaProductoController categoriaProductoController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCrearCategoriaProductoSuccessful(){
        CategoriaProducto objCategoriaProducto = new CategoriaProducto();
        when(categoriaProductoService.crearCategoriaProducto(objCategoriaProducto)).thenReturn(objCategoriaProducto);

        CategoriaProducto result = categoriaProductoService.crearCategoriaProducto(objCategoriaProducto);

        assertNotNull(result);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCrearCategoriaProductoError(){
        CategoriaProducto objCategoriaProducto=new CategoriaProducto(); //sin datos validos
        when(categoriaProductoService.crearCategoriaProducto(objCategoriaProducto)).thenThrow(DataIntegrityViolationException.class);

        categoriaProductoService.crearCategoriaProducto(objCategoriaProducto);
    }

    @Test
    public void testObtenerCategoriaProductos(){
        List<CategoriaProducto> categoriasProductos = Arrays.asList(new CategoriaProducto(), new CategoriaProducto());
        when(categoriaProductoService.obtenerCategoriasProductos()).thenReturn(categoriasProductos);

        List<CategoriaProducto> result = categoriaProductoService.obtenerCategoriasProductos();

        assertEquals(2, result.size());
    }

    @Test
    public void testObtenerCategoriaProductoExistente() {
        int categoriaProductos = 1;
        CategoriaProducto objCategoriaProducto = new CategoriaProducto();
        when(categoriaProductoService.obtenerCategoriaProducto(categoriaProductos)).thenReturn(objCategoriaProducto);

        CategoriaProducto result = categoriaProductoService.obtenerCategoriaProducto(categoriaProductos);

        assertNotNull(result);
    }

    @Test
    public void testObtenerCategoriaProductoNoExistente() {
        int categoriaProductos = 1;
        when(categoriaProductoService.obtenerCategoriaProducto(categoriaProductos)).thenReturn(null);

        CategoriaProducto result = categoriaProductoService.obtenerCategoriaProducto(categoriaProductos);

        assertNull(result);
    }

    @Test
    public void testActualizarCategoriaProductoExistente(){
        CategoriaProducto objCategoriaProducto = new CategoriaProducto();
        objCategoriaProducto.setId(1L);
        objCategoriaProducto.setNombre("Demo nombre ACTUALIZADO");
        objCategoriaProducto.setDescripcion("Demo desripcion");

        // Simular el comportamiento
        when(categoriaProductoService.actualizarCategoriaProducto(objCategoriaProducto)).thenReturn(objCategoriaProducto);

        // Invocar el método
        ResponseEntity<?> response = categoriaProductoController.actualizarCategoriaProducto(objCategoriaProducto.getId(), objCategoriaProducto);

        // Verificar que la respuesta es correcta
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        CategoriaProducto categoriaProductoActualizada = (CategoriaProducto) response.getBody();
        assertEquals("Demo nombre ACTUALIZADO", categoriaProductoActualizada.getNombre());
        assertEquals(1L, categoriaProductoActualizada.getId().longValue());
        verify(categoriaProductoService, times(1)).actualizarCategoriaProducto(any(CategoriaProducto.class));
    }

    @Test
    public void testActualizarCategoriaProductoNoExistente(){
        // Arrange
        CategoriaProducto objCategoriaProducto = new CategoriaProducto();

        // Act
        when(categoriaProductoService.actualizarCategoriaProducto(objCategoriaProducto)).thenReturn(null);
        ResponseEntity<?> result = categoriaProductoController.actualizarCategoriaProducto(1L, objCategoriaProducto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

        ErrorHandler error = (ErrorHandler) result.getBody();
        assertNotNull(error);
        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Rol no encontrado con ID: 1", error.getMessage());

        // Verify
        verify(categoriaProductoService, times(1)).actualizarCategoriaProducto(objCategoriaProducto);
    }

    @Test
    public void testEliminarCategoriaProductoExitente(){
        ResponseEntity<?> result = categoriaProductoController.eliminarCategoriaProducto(1);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    public void testEliminarCategoriaProductoNoExistente(){
        doThrow(new EmptyResultDataAccessException(1)).when(categoriaProductoService).eliminarCategoriaProducto(1);

        ResponseEntity<?> result = categoriaProductoController.eliminarCategoriaProducto(1);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        ErrorHandler error = (ErrorHandler) result.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Categoria no encontrada con ID: 1", error.getMessage());
    }
}
