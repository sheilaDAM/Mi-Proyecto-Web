package com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Servicios;

import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Controlador.DTO.UsuarioDTO;
import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Entidades.Usuario;

import java.util.List;

public interface ServicioUsuario {

    public Usuario guardar(UsuarioDTO registroDTO);
    public List<Usuario> listarUsuarios();
    public Iterable<Usuario> obtenerUsuarios();
    public Usuario obtenerUsuarioPorId(Long id);
    public void eliminarUsuario(Long id);
    public Usuario obtenerUsuarioPorEmail(String nombreUsuario);
    public Usuario obtenerUsuarioPorNombreUsuario(String nombreUsuario);

}






