package com.lab02.AluguelCarros;

import java.util.Arrays;

import com.lab02.AluguelCarros.models.Usuario;
import com.lab02.AluguelCarros.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AluguelCarrosApplication implements CommandLineRunner {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public static void main(String[] args) {
		SpringApplication.run(AluguelCarrosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Usuario usuario1 = new Usuario(null, "Gui");
		Usuario usuario2 = new Usuario(null, "Henriq");
		Usuario usuario3 = new Usuario(null, "Lucas");
		usuarioRepository.saveAll(Arrays.asList(usuario1, usuario2, usuario3));
	}

}
