package com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Repositorios;

import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioRol extends JpaRepository<Rol, Long> {

   // public Optional encontrarPorNombreUsuario(String nombreUsuario);
    //public Optional encontrarPorIdYPassword(Long id, String password);

}
