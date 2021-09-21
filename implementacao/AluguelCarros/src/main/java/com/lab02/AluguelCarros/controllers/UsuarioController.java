package com.lab02.AluguelCarros.controllers;

import java.util.Optional;

import com.lab02.AluguelCarros.controllers.exceptions.ObjectNotFoundException;
import com.lab02.AluguelCarros.models.Usuario;
import com.lab02.AluguelCarros.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repo;
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Usuario> buscar(@PathVariable Integer id) {
        Optional<Usuario> obj = repo.findById(id); 
        obj.orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName())
        ); 
        return ResponseEntity.ok().body(obj.get());
    }

}
