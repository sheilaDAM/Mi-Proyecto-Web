package com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;


@Configuration
public class ConfiguracionSeguridadWeb {
    @Bean
    public SecurityFilterChain defaultSecurityfilterChain(HttpSecurity http)throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/milogin")
                        .defaultSuccessUrl("/homeweb", true) //esta url es a la que se redirigirá si el inicio de sesión es exitoso
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .exceptionHandling(e -> e
                        .accessDeniedPage("/403")
                );
        // ...
        return http.build();
    }


    @Bean
    public SecurityFilterChain customFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(
                auth -> auth
                        .requestMatchers("/usuarios").hasAnyRole("ADMIN")
                        .requestMatchers("/usuarios/nuevo").hasAnyRole("ADMIN")
                        .requestMatchers("/usuarios/guardar").hasAnyRole("ADMIN")
                        .requestMatchers("/usuarios/editar/{id}").hasAnyRole("ADMIN")
                         .anyRequest().authenticated())

                .formLogin(form -> form
                        .loginPage("/milogin")
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .exceptionHandling(e -> e.accessDeniedPage("/403"));

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
}

