package com.techskill4.shopall;

import com.techskill4.shopall.Controller.Protected.CarritoProductosController;
import com.techskill4.shopall.Model.*;
import com.techskill4.shopall.Services.CarritoProductosService;
import com.techskill4.shopall.Services.CarritoService;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
public class Carrito_ProductosServiceTest {
    @Mock
    private CarritoProductosService carritoProductosService;
    @Mock
    private CarritoService carritoService;
    @InjectMocks
    private CarritoProductosController carritoProductosController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCrearCarritoProductosSuccessful(){
        CarritoProductos objCarritoProductos = new CarritoProductos();
        when(carritoProductosService.asignarProducto(objCarritoProductos)).thenReturn(objCarritoProductos);

        CarritoProductos result = carritoProductosService.asignarProducto(objCarritoProductos);

        assertNotNull(result);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCrearCarritoProductosError(){
        CarritoProductos objCarritoProductos=new CarritoProductos(); //sin datos validos
        when(carritoProductosService.asignarProducto(objCarritoProductos)).thenThrow(DataIntegrityViolationException.class);

        carritoProductosService.asignarProducto(objCarritoProductos);
    }

    @Test
    public void testObtenerCarritoProductosExistente() {
        // Configurar el comportamiento simulado de carritoProductosService
        List<CarritoProductos> productosMock = new ArrayList<>();  // Puedes agregar productos de prueba aquí
        when(carritoProductosService.obtenerProductosCarrito(anyLong(), anyInt())).thenReturn(productosMock);

        // Configurar el comportamiento simulado de carritoService
        Carrito carritoMock = new Carrito();  // Puedes configurar un carrito de prueba aquí
        when(carritoService.obtenerCarritoPorCarrito(anyInt())).thenReturn(carritoMock);

        // Llamar al método del controlador que deseas probar
        ResponseEntity<List<CarritoProductos>> respuesta = carritoProductosController.obtenerProductosCarrito(1L);

        // Verificar que la respuesta es exitosa (HttpStatus.OK)
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());

        // Verificar que el servicio de carritoProductosService fue invocado con los parámetros correctos
        verify(carritoProductosService).obtenerProductosCarrito(eq(1L), eq(0));

        // Verificar que el servicio de carritoService fue invocado con los parámetros correctos
        verify(carritoService).obtenerCarritoPorCarrito(anyInt());

        // Puedes agregar más verificaciones según tus requisitos
    }

    @Test
    public void testActualizarCarritoProductosExistente(){
        // Configurar el comportamiento del servicio
        doNothing().when(carritoProductosService).actualizarCantidadProducto(anyLong(), anyLong(), anyInt());

        // Llamar al método del controlador
        ResponseEntity<?> respuesta = carritoProductosController.actualizarCantidadProducto(1L, 2L, 5);

        // Verificar que se llamó al servicio con los parámetros correctos
        verify(carritoProductosService).actualizarCantidadProducto(1L, 2L, 5);

        // Verificar que la respuesta es HTTP 201 CREATED
        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
    }

    @Test
    public void testActualizarCarritoProductosNoExistente(){
        // Configurar el comportamiento del servicio para lanzar una excepción
        doThrow(new RuntimeException("Error al actualizar cantidad")).when(carritoProductosService)
                .actualizarCantidadProducto(anyLong(), anyLong(), anyInt());

        // Llamar al método del controlador
        ResponseEntity<?> respuesta = carritoProductosController.actualizarCantidadProducto(1L, 2L, 5);

        // Verificar que se llamó al servicio con los parámetros correctos
        verify(carritoProductosService).actualizarCantidadProducto(1L, 2L, 5);

        // Verificar que la respuesta es HTTP 404 NOT FOUND
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    public void testEliminarCarritoProductosExitente(){
        ResponseEntity<?> result = carritoProductosController.eliminarProductoDelCarrito(1);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    public void testEliminarCarritoProductosNoExistente(){
        doThrow(new EmptyResultDataAccessException(1)).when(carritoProductosService).eliminarProductoDelCarrito(1);

        ResponseEntity<?> result = carritoProductosController.eliminarProductoDelCarrito(1);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        ErrorHandler error = (ErrorHandler) result.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Producto no encontrada con ID: 1", error.getMessage());
    }
}
