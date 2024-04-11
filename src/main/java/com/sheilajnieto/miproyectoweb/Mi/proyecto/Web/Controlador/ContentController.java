package com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Controlador;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {

    @GetMapping("/homeweb")
    public String handleWelcome() {
        return "homeweb";
    }

    @GetMapping("/usuarios")
    public String handleAdminHome() {
        return "usuarios";
    }


    @GetMapping("/milogin")
    public String handleLogin() {
        return "milogin";
    }
}