package com.industrial.pasantias.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration {

    //Confuguración de enrutamiento para el login 
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){

        return (web)-> web.ignoring().requestMatchers("/**"); 
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }  
}
