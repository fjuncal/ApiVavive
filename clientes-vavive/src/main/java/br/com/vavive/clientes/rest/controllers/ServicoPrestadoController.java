package br.com.vavive.clientes.rest.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.vavive.clientes.model.entity.Cliente;
import br.com.vavive.clientes.model.entity.ServicoPrestado;
import br.com.vavive.clientes.model.repository.ClienteRepository;
import br.com.vavive.clientes.model.repository.ServicoPrestadoRepository;
import br.com.vavive.clientes.rest.dto.ServicoPrestadoDTO;
import br.com.vavive.clientes.util.BigDecimalConverter;

@RestController
@RequestMapping("api/servicos-prestados")
public class ServicoPrestadoController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ServicoPrestadoRepository servicoPrestadoRepository;
	
	@Autowired
	private BigDecimalConverter bigDecimalConverter;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ServicoPrestado salvar(@RequestBody ServicoPrestadoDTO dto) {
		LocalDate data = LocalDate.parse(dto.getDataAtividade(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Integer idCliente = dto.getIdCliente();
		
		Cliente cliente = clienteRepository.findById(idCliente)
											.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST , "Cliente inexistente"));
		
		ServicoPrestado servicoPrestado = new ServicoPrestado();
		servicoPrestado.setObservacao(dto.getObservacao());
		servicoPrestado.setDataAtividade(data);
		servicoPrestado.setCliente(cliente);
		servicoPrestado.setValor(bigDecimalConverter.converterBigDecimal((dto.getValor())));
		servicoPrestado.setNome(dto.getNome());
		servicoPrestado.setDuracao(dto.getDuracao());
		servicoPrestado.setHora(dto.getHora());
		servicoPrestado.setNota(dto.getNota());
		servicoPrestado.setResponsavel(dto.getResponsavel());
		
		return servicoPrestadoRepository.save(servicoPrestado);
	}
	
	@GetMapping
	public List<ServicoPrestado> pesquisar(@RequestParam(value = "nome", required = false, defaultValue = "") String nome, @RequestParam(value ="mes", required = false) Integer mes){
		return servicoPrestadoRepository.findByNomeClienteAndMes("%"+ nome + "%", mes);
	}
}
