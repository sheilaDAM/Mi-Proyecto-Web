package com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.ConfiguracionVista;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Configuration
public class ConfiguracionMvcWeb implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {
        viewControllerRegistry.addViewController("/403").setViewName("403");
        viewControllerRegistry.addViewController("/login").setViewName("login");
    }

}
