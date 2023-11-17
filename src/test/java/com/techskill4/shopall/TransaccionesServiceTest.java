package com.techskill4.shopall;

import com.techskill4.shopall.Controller.Protected.TransaccionController;
import com.techskill4.shopall.Model.ErrorHandler;
import com.techskill4.shopall.Model.Transaccion;
import com.techskill4.shopall.Services.CarritoProductosService;
import com.techskill4.shopall.Services.TransaccionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
public class TransaccionesServiceTest {
    @Mock
    private TransaccionService transaccionService;

    @Mock
    private CarritoProductosService carritoProductosService;

    @InjectMocks
    private TransaccionController transaccionController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCrearCarritoProductosSuccessful(){
        Transaccion objTransaccion = new Transaccion();
        when(transaccionService.crearTransaccion(objTransaccion)).thenReturn(objTransaccion);

        Transaccion result = transaccionService.crearTransaccion(objTransaccion);

        assertNotNull(result);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCrearCarritoProductosError(){
        Transaccion objTransaccion=new Transaccion(); //sin datos validos
        when(transaccionService.crearTransaccion(objTransaccion)).thenThrow(DataIntegrityViolationException.class);

        transaccionService.crearTransaccion(objTransaccion);
    }

    @Test
    public void testObtenerTransaccionesExitoso() {
        // Simular el comportamiento de transaccionService.obtenerTransaccionesByUsuario() para un escenario exitoso
        when(transaccionService.obtenerTransaccionesByUsuario(anyLong())).thenReturn(Arrays.asList(new Transaccion(), new Transaccion()));

        // Invocar el método obtenerTransacciones() del controlador
        ResponseEntity<?> respuesta = transaccionController.obtenerTransacciones(1L);

        // Verificar que la respuesta sea correcta
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        List<Transaccion> transacciones = (List<Transaccion>) respuesta.getBody();
        assertNotNull(transacciones);
        assertEquals(2, transacciones.size());

        // Verificar que el servicio fue invocado exactamente una vez
        verify(transaccionService).obtenerTransaccionesByUsuario(anyLong());
    }

    @Test
    public void testObtenerTransaccionesNoExitoso() {
        // Simular el comportamiento de transaccionService.obtenerTransaccionesByUsuario() para un escenario no exitoso
        when(transaccionService.obtenerTransaccionesByUsuario(anyLong())).thenThrow(new RuntimeException("Error en el servicio"));

        // Invocar el método obtenerTransacciones() del controlador
        ResponseEntity<?> respuesta = transaccionController.obtenerTransacciones(1L);

        // Verificar que la respuesta indica un fallo en la obtención de transacciones
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, respuesta.getStatusCode());
        ErrorHandler error = (ErrorHandler) respuesta.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), error.getStatus());
        assertEquals("Error en el servicio", error.getMessage());

        // Verificar que el servicio fue invocado exactamente una vez
        verify(transaccionService, times(1)).obtenerTransaccionesByUsuario(anyLong());
    }
}
