package com.industrial.pasantias.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.industrial.pasantias.Model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Buscar usuario por correo o nombre de usuario
    Usuario findByCorreoOrUsername(String correo, String username);
}
