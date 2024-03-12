package com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    /*
    @ManyToMany(mappedBy = "roles")
    private Set<Usuario> usuarios;
     */

    @OneToMany(mappedBy = "rol")
    @JsonBackReference("usuario-rol")
    private Set<Usuario> usuarios;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Rol(Long id, String nombre) {
        super();
        this.id = id;
        this.nombre = nombre;
    }

    public Rol() {

    }

    public Rol(String nombre) {
        super();
        this.nombre = nombre;
    }


}
