package com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Entidades;
/*
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    @OneToMany(mappedBy = "rol")
    @JsonBackReference("usuario-rol")
    private Set<MyUser> usuarios;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role(Long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public Role() {

    }

    public Role(String name) {
        super();
        this.name = name;
    }


}
*/