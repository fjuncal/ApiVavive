package br.com.vavive.clientes.service.planilha.entity;

public enum CampoPlanilhaEnum {
	
	IDENTIFICADOR ("#"),
	NOME_CLIENTE ("Cliente"),
	ORIGEM_CLIENTE ("Origem cliente"),
	IDENTIFICACAO_FISCAL_CLIENTE ("CPF/ CNPJ"),
	ENDERECO_CLIENTE ("Endereço"),
	BAIRRO_CLIENTE ("Bairro"),
	PONTO_REFERENCIA_ENDERECO_CLIENTE ("Ponto de Referencia"),
	TELEFONE_CLIENTE ("Contato Cliente");

	private String valor;
	
	private CampoPlanilhaEnum(String valor) {
		this.valor = valor;
	}
	
	public String getValor() {
		return valor;
	}
}
