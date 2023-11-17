package com.techskill4.shopall.Controller.Public;

import com.techskill4.shopall.Model.TokenInfo;
import com.techskill4.shopall.Model.Usuario;
import com.techskill4.shopall.Security.Services.JwtUtilService;
import com.techskill4.shopall.Security.Services.UserSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class TokenController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserSecurity usuarioDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

    @GetMapping("/mensaje")
    public ResponseEntity<?> getMensaje() {
        logger.info("Obteniendo el mensaje");

        var auth =  SecurityContextHolder.getContext().getAuthentication();
        logger.info("Datos del Usuario: {}", auth.getPrincipal());
        logger.info("Datos de los Roles {}", auth.getAuthorities());
        logger.info("Esta autenticado {}", auth.isAuthenticated());

        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("contenido", "Hola Peru");
        return ResponseEntity.ok(mensaje);
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getMensajeAdmin() {

        var auth =  SecurityContextHolder.getContext().getAuthentication();
        logger.info("Datos del Usuario: {}", auth.getPrincipal());
        logger.info("Datos de los Permisos {}", auth.getAuthorities());
        logger.info("Esta autenticado {}", auth.isAuthenticated());

        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("contenido", "Hola Admin");


        return ResponseEntity.ok(mensaje);
    }

    @GetMapping("/public")
    public ResponseEntity<?> getMensajePublico() {
        var auth =  SecurityContextHolder.getContext().getAuthentication();
        logger.info("Datos del Usuario: {}", auth.getPrincipal());
        logger.info("Datos de los Permisos {}", auth.getAuthorities());
        logger.info("Esta autenticado {}", auth.isAuthenticated());

        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("contenido", "Hola. esto es publico");
        return ResponseEntity.ok(mensaje);
    }

    @PostMapping("/public/authenticate")
    public ResponseEntity<TokenInfo> authenticate(@RequestBody Usuario authenticationReq) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationReq.getUsuario(),
                            authenticationReq.getContrasenia()));

            UserDetails userDetails = usuarioDetailsService.loadUserByUsername(
                    authenticationReq.getUsuario());

            String jwt = jwtUtilService.generateJwtToken(authentication);

            return ResponseEntity.ok(new TokenInfo(jwt));
        } catch (AuthenticationException e) {

            logger.info("Autenticando al usuario {}", authenticationReq.getUsuario() + "Autenticando al password " +authenticationReq.getContrasenia());
            logger.info(e.getMessage());
            return null;
        }
    }
}
