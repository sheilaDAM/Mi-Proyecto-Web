package com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Seguridad.JWT;

import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Controlador.DTO.UsuarioDTO;
import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Entidades.Usuario;
import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Servicios.Implementaciones.ServicioUsuarioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Service
public class ServicioDetallesUsuario implements UserDetailsService {

    @Autowired
    private ServicioUsuarioImpl servicioUsuario;

    private Usuario detalleUsuario;

    //Método que se encarga de cargar el usuario logueado por su nombre de usuario
    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        log.info("Entró en cargar usuario por nombre de usuario", nombreUsuario);
        detalleUsuario = servicioUsuario.findByNombreUsuario(nombreUsuario);
        if (!Objects.isNull(detalleUsuario)) {
            return new org.springframework.security.core.userdetails.User(detalleUsuario.getNombreUsuario(), detalleUsuario.getPassword(), new ArrayList<>());
        }else {
            throw new UsernameNotFoundException("Usuario/a no encontrado/a.");
        }
    }

    public Usuario obtenerDetalleUsuario() {
        return detalleUsuario;
    }
}
