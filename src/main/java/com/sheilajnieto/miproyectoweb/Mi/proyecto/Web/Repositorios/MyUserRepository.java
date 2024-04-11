package com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Repositorios;

import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Entidades.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyUserRepository extends JpaRepository<MyUser, Long> {

    Optional<MyUser> findByUsername(String username);
}
