package br.com.vavive.clientes.service.planilha.factory;

import static br.com.vavive.clientes.service.planilha.util.PlanilhaUtils.getValorCampo;

import java.util.List;

import org.springframework.util.StringUtils;

import br.com.vavive.clientes.model.entity.Cliente;
import br.com.vavive.clientes.model.entity.Endereco;
import br.com.vavive.clientes.service.planilha.entity.CampoPlanilhaEnum;

public class ClienteFactory {
	private ClienteFactory() {}
	
	public static Cliente create(List<String> cabecalho, List<String> dados) {
		Cliente cliente = new Cliente();
		
		cliente.setNome(getValorCampo(cabecalho, dados, CampoPlanilhaEnum.NOME_CLIENTE));
		
		String cpf = getValorCampo(cabecalho, dados, CampoPlanilhaEnum.IDENTIFICACAO_FISCAL_CLIENTE);
		cliente.setCpf(StringUtils.isEmpty(cpf) ? null : cpf.trim());
		
		cliente.setTelefone(getValorCampo(cabecalho, dados, CampoPlanilhaEnum.TELEFONE_CLIENTE));
		cliente.setObservacao("Identificador na planilha: " + getValorCampo(cabecalho, dados, CampoPlanilhaEnum.IDENTIFICADOR));
		cliente.setOrigemCliente(getValorCampo(cabecalho, dados, CampoPlanilhaEnum.ORIGEM_CLIENTE));

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
		
		cliente.addEndereco(endereco);

		return cliente;
	}
}
