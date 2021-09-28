package com.lab02.AluguelCarros.services;

import java.util.List;
import java.util.Optional;

import com.lab02.AluguelCarros.controllers.exceptions.DataIntegrityException;
import com.lab02.AluguelCarros.controllers.exceptions.ObjectNotFoundException;
import com.lab02.AluguelCarros.models.Usuario;
import com.lab02.AluguelCarros.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repo;

    public Usuario find(Integer id) {
        Optional<Usuario> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
    }

    public Usuario findByLogin(String login) {
        Optional<Usuario> obj = repo.findByLogin(login);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Login: " + login + ", Tipo: " + Usuario.class.getName()));
    }

    public Usuario insert(Usuario obj) {
        obj.setId(null); // Pois se vir com id, vai atualizar ao invés de criar um novo
        return repo.save(obj);
    }

    public Usuario update(Usuario obj) {
        find(obj.getId());
        return repo.save(obj);
    }

    public void deleteById(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma usuario que possui produtos.");
        }
    }

    public List<Usuario> findAll() {
        return repo.findAll();
    }

}