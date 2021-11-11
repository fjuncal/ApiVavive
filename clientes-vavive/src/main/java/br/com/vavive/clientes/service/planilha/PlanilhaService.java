package br.com.vavive.clientes.service.planilha;

import static br.com.vavive.clientes.service.planilha.util.PlanilhaUtils.getLinha;
import static br.com.vavive.clientes.service.planilha.util.PlanilhaUtils.getValorCampo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.vavive.clientes.model.entity.Cliente;
import br.com.vavive.clientes.model.entity.Endereco;
import br.com.vavive.clientes.rest.dto.ResultadoImportacaoDTO;
import br.com.vavive.clientes.service.ClienteService;
import br.com.vavive.clientes.service.planilha.entity.CampoPlanilhaEnum;
import br.com.vavive.clientes.service.planilha.entity.TipoPlanilhaEnum;
import br.com.vavive.clientes.service.planilha.factory.ClienteFactory;

@Service
public class PlanilhaService {
	
	@Autowired
	private ClienteService clienteService;

	private DataFormatter formatter = new DataFormatter();
	
	ResultadoImportacaoDTO resultado;
	
	public ResultadoImportacaoDTO importar(MultipartFile file, TipoPlanilhaEnum tipoPlanilha) throws IOException {
		resultado = new ResultadoImportacaoDTO(tipoPlanilha.name());

		try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
			Iterator<Sheet> sheets = workbook.sheetIterator();
			
			while(sheets.hasNext()) {
				Sheet sheet = sheets.next();
				
				Iterator<Row> rows = sheet.iterator();
				List<String> cabecalho = new ArrayList<>();
				
				while(rows.hasNext()) {
					Row row = rows.next();
					
					String id = formatter.formatCellValue(row.getCell(0));

					if(isLinhaCabecalho(id)) {
						cabecalho = getLinha(row);
					} else {
						if(resultado.getQuantidadeLinhasLidas() % 500 == 0)
							System.out.println("Linhas lidas da planilha: " + resultado.getQuantidadeLinhasLidas());

						List<String> dados = getLinha(row);
						persistirEntidade(cabecalho, dados, tipoPlanilha);
					}
				}
			}
		}

		System.out.println("\n##### RESUMO #####\n");
		System.out.println("Linhas lidas sem erro: " + resultado.getQuantidadeLinhasLidasSemErro());
		System.out.println("Linhas lidas com erro: " + resultado.getQuantidadeLinhasLidasComErro());
		System.out.println("Linhas com novos clientes: " + resultado.getQuantidadeRegistrosNovos());
		System.out.println("Linhas com novos endereços de clientes já cadastrados: " + resultado.getQuantidadeRegistrosAtualizados());
		System.out.println("Linhas com cliente e endereço já cadastrados: " + resultado.getQuantidadeRegistrosRepetidos());
		System.out.println("\n##### LISTA DE ERROS #####\n");
		if(resultado.getErros().isEmpty()) {
			System.out.println("SEM ERROS");
		} else {
			for (String erro : resultado.getErros()) {
				System.err.println(erro);
			}
		}
		
		return resultado;
	}

	private boolean isLinhaCabecalho(String id) {
		return id.contentEquals("#");
	}

	private void persistirEntidade(List<String> cabecalho, List<String> dados, TipoPlanilhaEnum tipoPlanilha) {
		Cliente cliente = null;
		try {
			switch (tipoPlanilha) {
				case CLIENTE:
					String nome = getValorCampo(cabecalho, dados, CampoPlanilhaEnum.NOME_CLIENTE);
					if(StringUtils.isEmpty(nome)) {
						return;
					}
					
					cliente = clienteService.consultarPorNome(nome);

					if(cliente != null) {
						Endereco endereco = ClienteFactory.getEndereco(cabecalho, dados);
						if(!cliente.possuiEndereco(endereco)) {
							cliente.addEndereco(endereco);
							clienteService.salvar(cliente);
							resultado.incrementaQuantidadeRegistrosAtualizados();
						} else {
							resultado.incrementaQuantidadeRegistrosRepetidos();
						}
					} else {
						cliente = ClienteFactory.criar(cabecalho, dados);
						clienteService.salvar(cliente);
						resultado.incrementaQuantidadeRegistrosNovos();
					}
					break;
				default:
					break;
			}
		} catch(Exception e) {
			String entidade = cliente == null ? "" : cliente.toString();
			resultado.addErro(entidade + "\n" + e.getMessage());
			e.printStackTrace();
		}

	}
}
