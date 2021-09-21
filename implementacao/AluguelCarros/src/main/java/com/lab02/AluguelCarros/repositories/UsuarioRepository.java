package com.lab02.AluguelCarros.repositories;

import com.lab02.AluguelCarros.models.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
}
