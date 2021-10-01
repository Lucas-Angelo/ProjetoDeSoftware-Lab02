package com.lab02.AluguelCarros;

import java.util.Arrays;

import com.lab02.AluguelCarros.models.Cliente;
import com.lab02.AluguelCarros.models.Pedido;
import com.lab02.AluguelCarros.models.enums.PedidoStatus;
import com.lab02.AluguelCarros.repositories.ClienteRepository;
import com.lab02.AluguelCarros.repositories.PedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AluguelCarrosApplication implements CommandLineRunner {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private PedidoRepository pedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(AluguelCarrosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Cliente cliente1 = new Cliente(null, "Gui", "gui", "supersenha");
		Cliente cliente2 = new Cliente(null, "Henrique", "henrique", "supersenha");
		Cliente cliente3 = new Cliente(null, "Lucas", "lucas", "supersenha");
		clienteRepository.saveAll(Arrays.asList(cliente1, cliente2, cliente3));

		Pedido pedido1 = new Pedido(null, PedidoStatus.APROVADO);
		Pedido pedido2 = new Pedido(null, PedidoStatus.NEGADO);

		cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));
		pedido1.setCliente(cliente1);
		pedido2.setCliente(cliente1);

		pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
	}

}
