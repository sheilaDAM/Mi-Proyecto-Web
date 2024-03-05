package com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Servicios.Implementaciones;

import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Controlador.DTO.UsuarioDTO;
import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Entidades.Rol;
import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Entidades.Usuario;
import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Repositorios.RepositorioUsuario;
import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Servicios.ServicioUsuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioUsuarioImpl implements ServicioUsuario {


    private RepositorioUsuario repositorioUsuario;


    public ServicioUsuarioImpl(RepositorioUsuario repositorioUsurio) {
        super();
        this.repositorioUsuario = repositorioUsurio;
    }


    private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
    }

    @Override
    public Usuario guardar(UsuarioDTO registroDTO) {
        return null;
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return repositorioUsuario.findAll();
    }
}
