package br.com.vavive.clientes.service.planilha;

import static br.com.vavive.clientes.service.planilha.util.PlanilhaUtils.getLinha;

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
import org.springframework.web.multipart.MultipartFile;

import br.com.vavive.clientes.model.entity.Cliente;
import br.com.vavive.clientes.service.ClienteService;
import br.com.vavive.clientes.service.planilha.entity.TipoPlanilhaEnum;
import br.com.vavive.clientes.service.planilha.factory.ClienteFactory;

@Service
public class PlanilhaService {
	
	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ClienteFactory clienteFactory;

	private DataFormatter formatter = new DataFormatter();
	
	List<String> sucessos;
	List<String> erros;

	public void importar(MultipartFile file, TipoPlanilhaEnum tipoPlanilha) throws IOException {
		sucessos = new ArrayList<String>();
		erros = new ArrayList<String>();

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
						if((sucessos.size() + erros.size()) % 200 == 0)
							System.out.println("################# " + (sucessos.size() + erros.size()) + " #################");

						List<String> dados = getLinha(row);
						persistirEntidade(cabecalho, dados, tipoPlanilha);
					}
				}
			}
		}

		System.out.println("Sucesso: " + sucessos.size());
		System.out.println("erro: " + erros.size());
		for (String erro : erros) {
			System.err.println(erro);
		}
	}

	private boolean isLinhaCabecalho(String id) {
		return id.contentEquals("#");
	}

	private boolean persistirEntidade(List<String> cabecalho, List<String> dados, TipoPlanilhaEnum tipoPlanilha) {
		String entidade = "";
		try {
			switch (tipoPlanilha) {
				case CLIENTE:
					Cliente cliente = clienteFactory.create(cabecalho, dados);
					entidade = cliente.toString();
					clienteService.salvar(cliente);
					break;
				default:
					break;
			}
			sucessos.add(entidade);
			return true;
		} catch(Exception e) {
			erros.add(entidade + "\n" + e.getMessage());
			return false;
		}

	}
}
