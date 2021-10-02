package com.lab02.AluguelCarros.controllers;

import java.util.List;
import java.net.URI;

import com.lab02.AluguelCarros.dto.PedidoNovoDTO;
import com.lab02.AluguelCarros.models.Cliente;
import com.lab02.AluguelCarros.models.Pedido;
import com.lab02.AluguelCarros.models.Veiculo;
import com.lab02.AluguelCarros.models.enums.PedidoStatus;
import com.lab02.AluguelCarros.services.ClienteService;
import com.lab02.AluguelCarros.services.PedidoService;
import com.lab02.AluguelCarros.services.VeiculoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;
    @Autowired
    private VeiculoService veiculoService;
    @Autowired
    private ClienteService clienteService;
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody PedidoNovoDTO pedidoNovoDTO) {
        Veiculo veiculo = veiculoService.find(pedidoNovoDTO.getVeiculoid());
        Cliente cliente = clienteService.find(pedidoNovoDTO.getClienteid());
        
        Pedido obj = new Pedido(null, PedidoStatus.PENDENTE); // Ao criar status é pendente
        obj.setVeiculo(veiculo);
        obj.setCliente(cliente);

        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}").buildAndExpand(obj.getId()).toUri(); // Para retornar a URL dessa nova Pedido salva no header
        return ResponseEntity.created(uri).build(); // .created() gerar status 201
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.find(id);
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Pedido> buscar(@PathVariable Integer id) {
        Pedido obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Pedido>> buscarTodos() {
        List<Pedido> veiculos = service.findAll();
        return ResponseEntity.ok().body(veiculos);
    }

}
