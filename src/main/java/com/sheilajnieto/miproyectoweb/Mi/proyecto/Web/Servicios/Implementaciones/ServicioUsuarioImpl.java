package com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Servicios.Implementaciones;

import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Controlador.DTO.UsuarioDTO;
import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Entidades.Rol;
import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Entidades.Usuario;
import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Repositorios.RepositorioUsuario;
import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Servicios.ServicioUsuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ServicioUsuarioImpl implements ServicioUsuario {

    private RepositorioUsuario repositorioUsuario;


    public ServicioUsuarioImpl(RepositorioUsuario repositorioUsurio) {
        super();
        this.repositorioUsuario = repositorioUsurio;
    }


    private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
    }

/*
    @Override
    public Usuario guardar(Usuario usuario) {
        return repositorioUsuario.save(usuario);
    }

 */

    @Override
    public List<Usuario> listarUsuarios() {
        return repositorioUsuario.findAll();
    }

    @Override
    public Iterable<Usuario> obtenerUsuarios() {
        return repositorioUsuario.findAll();
    }

    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        return repositorioUsuario.findById(id).orElse(null);
    }

    @Override
    public void eliminarUsuario(Long id) {
        repositorioUsuario.deleteById(id);
    }

    @Override
    public Usuario findByNombreUsuario(String nombreUsuario) {
        return repositorioUsuario.findByNombreUsuario(nombreUsuario);
    }

    private Boolean validarRegistroMap(Map<String, String> requestMap) {
        if (requestMap.containsKey("nombre") && requestMap.containsKey("apellidos")
                && requestMap.containsKey("email") && requestMap.containsKey("nombreUsuario")
                && requestMap.containsKey("password") && requestMap.containsKey("user")) {
            return true;
        }
        return false;
    }


    private Usuario getUserFromMap(Map<String, String> requestMap) {
        Usuario usuario = new Usuario();
        usuario.setNombre(requestMap.get("nombre"));
        usuario.setApellidos(requestMap.get("apellidos"));
        usuario.setEmail(requestMap.get("email"));
        usuario.setNombreUsuario(requestMap.get("nombreUsuario"));
        usuario.setPassword(requestMap.get("password"));
        usuario.setRol(new Rol(requestMap.get("rol")));
        return usuario;
    }

    @Override
    public ResponseEntity<String> registrar(Map<String, String> requestMap) {
        log.info("Resgistro de un usuario{}", requestMap);
        try {
            if (validarRegistroMap(requestMap)) {
                Usuario usuario = repositorioUsuario.findByNombreUsuario(requestMap.get("nombreUsuario"));
                if (Objects.isNull(usuario)) {
                    Usuario usuarioNuevo = getUserFromMap(requestMap);
                    repositorioUsuario.save(usuarioNuevo);
                    return ResponseEntity.ok().body("Usuario registrado con éxito");
                } else {
                    return ResponseEntity.badRequest().body("El usuario ya existe");
                }
            }else {
                return ResponseEntity.badRequest().body("Error en los datos al registrar usuario");
            }
        }catch (Exception e) {
            log.error("Error al registrar usuario", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar usuario");
        }
    }
}
