package com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Seguridad;

import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Seguridad.JWT.FiltroJWT;
import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Seguridad.JWT.ServicioDetallesUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad {

    @Autowired
    private ServicioDetallesUsuario servicioDetallesUsuario;

    @Autowired
    private FiltroJWT filtroJWT;


    //------------------------------------ Deprecated ------------------------------------
    //Para codificar la contraseña
    /*
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
    */
    //------------------------------------ fin Deprecated ------------------------------------


    /*
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

     */

    //------------------------------------ Mejor uso frente al deprecated ------------------------------------
    //vamos a resolver encoders para que no de error en la clase delegatingPasswordEncoder
    private final Map<String, PasswordEncoder> encoders = new HashMap<>();
    //Map.Entry<String, PasswordEncoder> entry = Map.entry("bcrypt", delegatingPasswordEncoder());
    Map.Entry<String, PasswordEncoder> entry2 = Map.entry("noop", NoOpPasswordEncoder.getInstance());
    //Map.Entry<String, PasswordEncoder> entry3 = Map.entry("pbkdf2", delegatingPasswordEncoder());
    //Map.Entry<String, PasswordEncoder> entry4 = Map.entry("scrypt", delegatingPasswordEncoder());
    //Map.Entry<String, PasswordEncoder> entry5 = Map.entry("sha256", delegatingPasswordEncoder());
    //Map.Entry<String, PasswordEncoder> entry6 = Map.entry("argon2", delegatingPasswordEncoder());

    public DelegatingPasswordEncoder delegatingPasswordEncoder() {
        return new DelegatingPasswordEncoder("bcrypt", encoders);
    }
    //------------------------------------ fin Mejor uso frente al deprecated ------------------------------------


    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/milogin").permitAll()
                                .requestMatchers("/homeweb").hasAnyRole("USER")
                                .requestMatchers("/usuarios", "/usuarios/nuevo", "/usuarios/guardar", "/usuarios/editar/{id}", "/usuarios/eliminar/{id}").hasAnyRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .logout(logout -> logout.permitAll())
                .exceptionHandling(e -> e.accessDeniedPage("/403"));
        httpSecurity.addFilterBefore(filtroJWT, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();


    }

    //Vamos a configurar el Authentication Manager, que es el encargado de decidir qué usuario puede acceder, gestiona los permisos para los usuarios
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


}
