package com.techskill4.shopall;

import com.techskill4.shopall.Controller.ProductoController;
import com.techskill4.shopall.Model.CategoriaProducto;
import com.techskill4.shopall.Model.ErrorHandler;
import com.techskill4.shopall.Model.Producto;
import com.techskill4.shopall.Model.Usuario;
import com.techskill4.shopall.Services.ProductoService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
public class ProductoServiceTest {
    @Mock
    private ProductoService productoService;
    @InjectMocks
    private ProductoController productoController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCrearProductoSuccessful(){
        Producto objProducto = new Producto();
        when(productoService.crearProducto(objProducto)).thenReturn(objProducto);

        Producto result = productoService.crearProducto(objProducto);

        assertNotNull(result);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCrearProductoError(){
        Producto objProducto=new Producto(); //sin datos validos
        when(productoService.crearProducto(objProducto)).thenThrow(DataIntegrityViolationException.class);

        productoService.crearProducto(objProducto);
    }

    @Test
    public void testObtenerProductos(){
        List<Producto> categoriasProductos = Arrays.asList(new Producto(), new Producto());
        when(productoService.obtenerProductos()).thenReturn(categoriasProductos);

        List<Producto> result = productoService.obtenerProductos();

        assertEquals(2, result.size());
    }

    @Test
    public void testObtenerProductoExistente() {
        int productoId = 1;
        Producto objProducto = new Producto();
        when(productoService.obtenerProducto(productoId)).thenReturn(objProducto);

        Producto result = productoService.obtenerProducto(productoId);

        assertNotNull(result);
    }

    @Test
    public void testObtenerProductoNoExistente() {
        int productoId = 1;
        when(productoService.obtenerProducto(productoId)).thenReturn(null);

        Producto result = productoService.obtenerProducto(productoId);

        assertNull(result);
    }

    @Test
    public void testActualizarProductoExistente(){
        CategoriaProducto objCategoriaProducto = new CategoriaProducto();
        objCategoriaProducto.setId(1L);

        Usuario objUsuario = new Usuario();
        objUsuario.setId(1L);

        Producto objProducto = new Producto();
        objProducto.setId(1L);
        objProducto.setNombre("Demo nombre ACTUALIZADO");
        objProducto.setDescripcion("Demo desripcion");
        objProducto.setPrecio(100);
        objProducto.setCantidad(20);
        objProducto.setCategoriaProducto(objCategoriaProducto);
        objProducto.setUsuario(objUsuario);

        // Simular el comportamiento
        when(productoService.actualizarProducto(objProducto)).thenReturn(objProducto);

        // Invocar el método
        ResponseEntity<?> response = productoController.actualizarProducto(objProducto.getId(), objProducto);

        // Verificar que la respuesta es correcta
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Producto productoActualizada = (Producto) response.getBody();
        assertEquals("Demo nombre ACTUALIZADO", productoActualizada.getNombre());
        assertEquals(1L, productoActualizada.getId().longValue());
        verify(productoService, times(1)).actualizarProducto(any(Producto.class));
    }

    @Test
    public void testActualizarProductoNoExistente(){
        // Arrange
        Producto objProducto = new Producto();

        // Act
        when(productoService.actualizarProducto(objProducto)).thenReturn(null);
        ResponseEntity<?> result = productoController.actualizarProducto(1L, objProducto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

        ErrorHandler error = (ErrorHandler) result.getBody();
        assertNotNull(error);
        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Rol no encontrado con ID: 1", error.getMessage());

        // Verify
        verify(productoService, times(1)).actualizarProducto(objProducto);
    }

    @Test
    public void testEliminarProductoExitente(){
        ResponseEntity<?> result = productoController.eliminarProducto(1);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    public void testEliminarProductoNoExistente(){
        doThrow(new EmptyResultDataAccessException(1)).when(productoService).eliminarProducto(1);

        ResponseEntity<?> result = productoController.eliminarProducto(1);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        ErrorHandler error = (ErrorHandler) result.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Producto no encontrada con ID: 1", error.getMessage());
    }
}