package com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.ConfiguracionVista;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Configuration
public class ConfiguracionMvcWeb implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {
         // "/403" - diseño página Error de Acceso Denegado (Forbidden)
        viewControllerRegistry.addViewController("/403").setViewName("403");
        // "/login" - diseño Página de Inicio de Sesión (login) personalizada
        viewControllerRegistry.addViewController("/login").setViewName("login");
    }

}
