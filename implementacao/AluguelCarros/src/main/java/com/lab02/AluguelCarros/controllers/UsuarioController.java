package com.lab02.AluguelCarros.controllers;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import com.lab02.AluguelCarros.controllers.exceptions.DataIntegrityException;
import com.lab02.AluguelCarros.controllers.exceptions.ObjectNotFoundException;
import com.lab02.AluguelCarros.models.Usuario;
import com.lab02.AluguelCarros.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repo;
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody Usuario obj) {
        obj.setId(null); // Pois se vir com id, vai atualizar ao invés de criar um novo
        obj = repo.save(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}").buildAndExpand(obj.getId()).toUri(); // Para retornar a URL dessa nova Usuario salva no header
        return ResponseEntity.created(uri).build(); // .created() gerar status 201
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody Usuario obj, @PathVariable Integer id) {
        obj.setId(id); // Para garantir que o ID da requisição é o mesmo do corpo
        Optional<Usuario> objProcurado = repo.findById(id); 
        objProcurado.orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName())
        );
        obj.setNome( obj.getNome()!=null ? obj.getNome() : objProcurado.get().getNome() );
        obj.setLogin( obj.getLogin()!=null ? obj.getLogin() : objProcurado.get().getLogin() );
        obj.setSenha( obj.getSenha()!=null ? obj.getSenha() : objProcurado.get().getSenha() );
        obj = repo.save(obj);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<Usuario> objProcurado = repo.findById(id); 
        objProcurado.orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName())
        );
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos.");
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Usuario> buscar(@PathVariable Integer id) {
        Optional<Usuario> obj = repo.findById(id); 
        obj.orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName())
        ); 
        return ResponseEntity.ok().body(obj.get());
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public ResponseEntity<Void> login(@RequestBody Map<String, String> json) {
        String login = json.get("login");
        String senha = json.get("senha");
        
        Optional<Usuario> obj = repo.findByLogin(login); 
        obj.orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Login: " + login + ", Tipo: " + Usuario.class.getName())
        ); 

        ResponseEntity<Void> entity = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if(obj.get().getSenha().equals(senha))
            entity = new ResponseEntity<>(HttpStatus.OK);
        return entity;
    }

}
