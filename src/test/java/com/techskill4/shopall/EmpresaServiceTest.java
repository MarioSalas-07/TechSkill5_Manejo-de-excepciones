package com.techskill4.shopall;

import com.techskill4.shopall.Controller.Public.EmpresaController;
import com.techskill4.shopall.Model.Empresa;
import com.techskill4.shopall.Model.ErrorHandler;
import com.techskill4.shopall.Services.EmpresaService;
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
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
public class EmpresaServiceTest {
    @Mock
    private EmpresaService empresaService;
    @InjectMocks
    private EmpresaController empresaController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCrearEmpresaSuccessful(){
        Empresa objEmpresa = new Empresa();
        when(empresaService.crearEmpresa(objEmpresa)).thenReturn(objEmpresa);

        Empresa result = empresaService.crearEmpresa(objEmpresa);

        assertNotNull(result);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCrearEmpresaError(){
        Empresa objEmpresa=new Empresa(); //sin datos validos
        when(empresaService.crearEmpresa(objEmpresa)).thenThrow(DataIntegrityViolationException.class);

        empresaService.crearEmpresa(objEmpresa);
    }

    @Test
    public void testObtenerEmpresas(){
        List<Empresa> empresas = Arrays.asList(new Empresa(), new Empresa());
        when(empresaService.obtenerEmpresas()).thenReturn(empresas);

        List<Empresa> result = empresaService.obtenerEmpresas();

        assertEquals(2, result.size());
    }

    @Test
    public void testObtenerEmpresaExistente() {
        int empresaId = 1;
        Empresa objEmpresa = new Empresa();
        when(empresaService.obtenerEmpresa(empresaId)).thenReturn(objEmpresa);

        Empresa result = empresaService.obtenerEmpresa(empresaId);

        assertNotNull(result);
    }

    @Test
    public void testObtenerEmpresaNoExistente() {
        int empresaId = 1;
        when(empresaService.obtenerEmpresa(empresaId)).thenReturn(null);

        Empresa result = empresaService.obtenerEmpresa(empresaId);

        assertNull(result);
    }

    @Test
    public void testActualizarEmpresaExistente(){
        Empresa objEmpresa = new Empresa();
        objEmpresa.setId(1L);
        objEmpresa.setNombre("Demo nombre ACTUALIZADO");
        objEmpresa.setWhatsApp("4777743529");
        objEmpresa.setDireccion("Demo direccion");
        objEmpresa.setStatus((byte) 1);
        objEmpresa.setDescripcion("Demo desripcion");
        objEmpresa.setImagen("Demo de la ruta de imagen");

        // Simular el comportamiento de
        when(empresaService.actualizarEmpresa(objEmpresa)).thenReturn(objEmpresa);

        // Invocar el método
        ResponseEntity<?> response = empresaController.actualizarEmpresa(objEmpresa.getId(), objEmpresa);

        // Verificar que la respuesta es correcta
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Empresa personaActualizada = (Empresa) response.getBody();
        assertEquals("Demo nombre ACTUALIZADO", personaActualizada.getNombre());
        assertEquals(1L, personaActualizada.getId().longValue());
        verify(empresaService, times(1)).actualizarEmpresa(any(Empresa.class));
    }

    @Test
    public void testActualizarEmpresaNoExistente(){
        // Arrange
        Empresa objEmpresa = new Empresa();

        // Act
        when(empresaService.actualizarEmpresa(objEmpresa)).thenReturn(null);
        ResponseEntity<?> result = empresaController.actualizarEmpresa(1L, objEmpresa);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

        ErrorHandler error = (ErrorHandler) result.getBody();
        assertNotNull(error);
        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Empresa no encontrada con ID: 1", error.getMessage());

        // Verify
        verify(empresaService, times(1)).actualizarEmpresa(objEmpresa);
    }

    @Test
    public void testEliminarEmpresaExitente(){
        ResponseEntity<?> result = empresaController.eliminarEmpresa(1);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    public void testEliminarEmpresaNoExistente(){
        doThrow(new EmptyResultDataAccessException(1)).when(empresaService).eliminarEmpresa(1);

        ResponseEntity<?> result = empresaController.eliminarEmpresa(1);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        ErrorHandler error = (ErrorHandler) result.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Empresa no encontrada con ID: 1", error.getMessage());
    }
}