package com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.JWT;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//Clase para validar el token creado en la clase JsonWebToken y el usuario (userDetails) que lo envía
@Component
public class FiltroJWT extends OncePerRequestFilter {

    @Autowired //inyectamos la dependencia de la clase JsonWebToken para poder utilizar sus métodos y que solo haya una instancia de la clase en el contenedor de Spring
    private JsonWebToken jsonWebToken;

    @Autowired
    private ServicioDetallesUsuario servicioDetallesUsuario;

    private String nombreUsuario = null;

    Claims claims = null;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().matches("/milogin | /homeweb | usuarios | usuarios/nuevo | usuarios/guardar | usuarios/editar | usuarios/eliminar")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader("Authorization");
            String token = null;

            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7); //para obtener el token sin la palabra Bearer
                nombreUsuario = jsonWebToken.extraerNombreUsuario(token);
                claims = jsonWebToken.extraerClaims(token);
            }

            if(nombreUsuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails detallesUsuario = servicioDetallesUsuario.loadUserByUsername(nombreUsuario);
                if(jsonWebToken.validarToken(token, detallesUsuario)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(detallesUsuario, null, detallesUsuario.getAuthorities());
                    new WebAuthenticationDetailsSource().buildDetails(request);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }

            filterChain.doFilter(request, response);
        }
    }

    public Boolean esAdmin() {
        return "admin".equalsIgnoreCase((String) claims.get("rol"));
    }

    public Boolean esUser() {
        return "user".equalsIgnoreCase((String) claims.get("rol"));
    }

    public String obtenerUsuarioActual() {
        return nombreUsuario;
    }
}
