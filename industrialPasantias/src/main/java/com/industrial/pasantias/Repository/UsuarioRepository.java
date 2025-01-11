package com.industrial.pasantias.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.industrial.pasantias.Model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Buscar usuario por correo o nombre de usuario
    Usuario findByCorreoOrUsername(String correo, String username);

    Optional<Usuario> findByUsername(String username);

    @Query(value = "SELECT * FROM USUARIO WHERE USERNAME = :username", nativeQuery = true)
    Usuario encontrarPorUsername(String username);
}
