package com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Entidades;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "surnames")
    private String surnames;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    /*
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rol_id", referencedColumnName = "id") //rol-id es el campo de la tabla usuario que hace referencia al id de la tabla rol
    @JsonBackReference("usuario-rol")
     */
    @Column(name = "role")
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;

    }

    public void setRole(String role) {
        this.role = role;
    }


    public MyUser(Long id, String nombre, String apellidos, String email, String password, String role) {
        super();
        this.id = id;
        this.name = nombre;
        this.surnames = apellidos;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public MyUser(String nombre, String apellidos, String email, String password, String role) {
        super();
        this.name = nombre;
        this.surnames = apellidos;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public MyUser() {

    }

}
