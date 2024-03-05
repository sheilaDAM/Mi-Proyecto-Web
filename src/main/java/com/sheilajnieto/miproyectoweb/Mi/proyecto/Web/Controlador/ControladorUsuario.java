package com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Controlador;

import ch.qos.logback.core.model.Model;
import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Servicios.Implementaciones.ServicioUsuarioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControladorUsuario {

    @Autowired
    private ServicioUsuarioImpl servicioUsuarioImpl;

    /*
    @GetMapping("/menulogin")
    public String iniciarSesion() {
        return "menulogin";
    }
     */

    @GetMapping("/homeweb")
    public String verPaginaWeb(Model modelo) {
        return "homeweb";
    }
}
