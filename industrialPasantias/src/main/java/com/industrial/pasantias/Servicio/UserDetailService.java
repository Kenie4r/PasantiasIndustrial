package com.industrial.pasantias.Servicio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.industrial.pasantias.Model.Usuario;
import com.industrial.pasantias.Repository.UsuarioRepository;

@Service
public class UserDetailService implements UserDetailsService  {

    
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado: " + username));

        System.out.println("Se encontro a " + usuario.getUsername()); 

        List<GrantedAuthority> authorities = new ArrayList<>();
        

        String roleName = null; 
        switch (usuario.getIdRol().getID_ROL()) {
            case 1:
                roleName = "ROLE_ADMIN"; 
                break;
            
            default:
                roleName = "ROLE_USER"; 
                break;
        }
        authorities.add(new SimpleGrantedAuthority(roleName)); 


       // final var authorities =; 
        return org.springframework.security.core.userdetails.User.builder()
        .username(usuario.getUsername())
        .password(usuario.getPassword())
        .authorities(authorities)
        .build();
    }

}
