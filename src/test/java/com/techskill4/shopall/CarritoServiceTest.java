package com.techskill4.shopall;

import com.techskill4.shopall.Controller.Protected.CarritoController;
import com.techskill4.shopall.Model.*;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
public class CarritoServiceTest {
    @Mock
    private CarritoService carritoService;
    @InjectMocks
    private CarritoController carritoController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCrearCarritoSuccessful(){
        Carrito objCarrito = new Carrito();
        when(carritoService.crearCarrito(objCarrito)).thenReturn(objCarrito);

        Carrito result = carritoService.crearCarrito(objCarrito);

        assertNotNull(result);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCrearCarritoError(){
        Carrito objCarrito=new Carrito(); //sin datos validos
        when(carritoService.crearCarrito(objCarrito)).thenThrow(DataIntegrityViolationException.class);

        carritoService.crearCarrito(objCarrito);
    }

    @Test
    public void testObtenerCarritoExistente() {
        int carritoId = 1;
        Carrito objCarrito = new Carrito();
        when(carritoService.obtenerCarritoPorCarrito(carritoId)).thenReturn(objCarrito);

        Carrito result = carritoService.obtenerCarritoPorCarrito(carritoId);

        assertNotNull(result);
    }

    @Test
    public void testObtenerCarritoNoExistente() {
        int carritoId = 1;
        when(carritoService.obtenerCarritoPorCarrito(carritoId)).thenReturn(null);

        Carrito result = carritoService.obtenerCarritoPorCarrito(carritoId);

        assertNull(result);
    }
}