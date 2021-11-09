package br.com.vavive.clientes.service.planilha.factory;

import static br.com.vavive.clientes.service.planilha.util.PlanilhaUtils.getValorCampo;

import java.util.List;

import org.springframework.util.StringUtils;

import br.com.vavive.clientes.model.entity.Cliente;
import br.com.vavive.clientes.model.entity.Endereco;
import br.com.vavive.clientes.service.planilha.entity.CampoPlanilhaEnum;

public class ClienteFactory {
	
	public static Cliente criar(List<String> cabecalho, List<String> dados) {

		Cliente cliente = new Cliente();

		String nome = getValorCampo(cabecalho, dados, CampoPlanilhaEnum.NOME_CLIENTE);
		cliente.setNome(nome);
		
		String observacao = adicionarObservacao("", "Identificador na planilha", getValorCampo(cabecalho, dados, CampoPlanilhaEnum.IDENTIFICADOR));

		String cpf = getValorCampo(cabecalho, dados, CampoPlanilhaEnum.IDENTIFICACAO_FISCAL_CLIENTE);
		cliente.setCpf(StringUtils.isEmpty(cpf) ? null : cpf.trim());
		
		String telefone = getValorCampo(cabecalho, dados, CampoPlanilhaEnum.TELEFONE_CLIENTE).trim();
		if(StringUtils.isEmpty(telefone)) {
			cliente.setTelefone("A PREENCHER");
			observacao = adicionarObservacao(observacao, "Telefone", "VAZIO");
		} else if(telefone.length() > 15) {
			cliente.setTelefone("A PREENCHER");
			observacao = adicionarObservacao(observacao, "Telefone", telefone);
		} else {
			cliente.setTelefone(telefone);
		}

		cliente.setOrigemCliente(getValorCampo(cabecalho, dados, CampoPlanilhaEnum.ORIGEM_CLIENTE));
		cliente.setObservacao(observacao);

		Endereco endereco = getEndereco(cabecalho, dados);
		cliente.addEndereco(endereco);
		
		return cliente;
	}

	public static Endereco getEndereco(List<String> cabecalho, List<String> dados) {
		Endereco endereco = new Endereco();
		
		String enderecoCampo = getValorCampo(cabecalho, dados, CampoPlanilhaEnum.ENDERECO_CLIENTE);
		
		if(enderecoCampo.contains(",")) {
			String[] enderecoComplemento = enderecoCampo.split(",");
			endereco.setLogradouro(enderecoComplemento[0]);
			endereco.setComplemento(enderecoComplemento[1]);
		} else {
			endereco.setLogradouro(enderecoCampo);
			endereco.setComplemento("A PREENCHER");
		}
		
		endereco.setBairro(getValorCampo(cabecalho, dados, CampoPlanilhaEnum.BAIRRO_CLIENTE));
		endereco.setMunicipio("Rio de Janeiro");
		endereco.setEstado("RJ");
		endereco.setPontoDeReferencia(getValorCampo(cabecalho, dados, CampoPlanilhaEnum.PONTO_REFERENCIA_ENDERECO_CLIENTE));
		
		return endereco;
	}

	private static String adicionarObservacao(String observacao, String campo, String valor) {
		return observacao.concat(campo + ": " + valor + "#");
	}
}
