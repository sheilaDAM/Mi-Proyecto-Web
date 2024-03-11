package com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Repositorios;

import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {

    public Usuario obtenerUsuarioPorEmail(String nombreUsuario);
    public Usuario obtenerUsuarioPorNombreUsuario(String nombreUsuario);
}
