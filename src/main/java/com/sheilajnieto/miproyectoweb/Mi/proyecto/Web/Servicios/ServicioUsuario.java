package com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Servicios;

import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Controlador.DTO.UsuarioDTO;
import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Entidades.Usuario;

import java.util.List;

public interface ServicioUsuario {

    public Usuario guardar(UsuarioDTO registroDTO);

    public List<Usuario> listarUsuarios();

}






