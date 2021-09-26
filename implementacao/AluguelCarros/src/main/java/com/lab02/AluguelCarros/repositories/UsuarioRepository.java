package com.lab02.AluguelCarros.repositories;

import java.util.Optional;

import com.lab02.AluguelCarros.models.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("select u from Usuario u where u.login = ?1")
    Optional<Usuario> findByLogin(String login);
}
