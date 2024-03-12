package com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Servicios;

import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Controlador.DTO.UsuarioDTO;
import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Entidades.Usuario;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ServicioUsuario {

   // public Usuario guardar(Usuario registro);
    public List<Usuario> listarUsuarios();
    public Iterable<Usuario> obtenerUsuarios();
    public Usuario obtenerUsuarioPorId(Long id);
    public void eliminarUsuario(Long id);
    public Usuario findByNombreUsuario(String nombreUsuario);
    ResponseEntity<String> registrar(Map<String, String> requestMap);

}






