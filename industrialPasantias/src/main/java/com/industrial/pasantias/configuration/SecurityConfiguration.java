package com.industrial.pasantias.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
public class SecurityConfiguration {

    //Confuguración de enrutamiento para el login 
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){

        return (web)-> web.ignoring().requestMatchers("/**"); 
    }

    

}
