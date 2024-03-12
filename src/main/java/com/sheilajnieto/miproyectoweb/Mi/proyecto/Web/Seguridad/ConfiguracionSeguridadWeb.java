package com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Seguridad;

import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Seguridad.JWT.FiltroJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;


@Configuration
public class ConfiguracionSeguridadWeb {

    @Autowired
    private FiltroJWT filtroJWT;

    @Bean
    public SecurityFilterChain customFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(
                auth -> auth
                        .requestMatchers("/milogin").permitAll()
                        .requestMatchers("/usuarios").hasAnyRole("ADMIN")
                        .requestMatchers("/usuarios/nuevo").hasAnyRole("ADMIN")
                        .requestMatchers("/usuarios/guardar").hasAnyRole("ADMIN")
                        .requestMatchers("/usuarios/editar/{id}").hasAnyRole("ADMIN")
                        .requestMatchers("/usuarios/eliminar/{id}").hasAnyRole("ADMIN")
                         .anyRequest().authenticated())

                .formLogin(form -> form
                        .loginPage("/milogin")
                        .defaultSuccessUrl("/homeweb", true) //esta url es a la que se redirigirá si el inicio de sesión es exitoso
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .exceptionHandling(e -> e.accessDeniedPage("/403"));
        httpSecurity.addFilterBefore(filtroJWT, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcDaoImpl jdbcDao = new JdbcDaoImpl();
        jdbcDao.setDataSource(dataSource);
        return jdbcDao;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
