package com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Controlador;

import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Entidades.MyUser;
import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Repositorios.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private MyUserRepository myUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register/newUser")
    public MyUser createUser(@RequestBody MyUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return myUserRepository.save(user);
    }
}
