package com.industrial.pasantias.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.industrial.pasantias.Servicio.UserDetailService;
import com.industrial.pasantias.Servicio.UsuarioService;

@Configuration
public class SecurityConfiguration {


    @Autowired
    private UserDetailService userService; 
    //Confuguración de enrutamiento para el login 
  /*   @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){

      return (web)-> web.ignoring().requestMatchers("/**"); 
      
    }*/

    @Bean 
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/login", "/css/**", "/js/**", "/img/**", "/reports/**", "/vendor/**", "/scss/**").permitAll().anyRequest().authenticated()).formLogin(form->
        form.loginPage("/login").loginProcessingUrl("/access").defaultSuccessUrl("/inicio", true).failureUrl("/login?error=true").permitAll()).logout(
            logout-> logout.logoutUrl("/logout").logoutSuccessUrl("/login").permitAll()
        ).csrf().disable().cors(); 
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }  


  /*  @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder()); 
    }*/
/*
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }*/
}
