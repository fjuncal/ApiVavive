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
	
	private DataFormatter formatter = new DataFormatter();

	public void importar(MultipartFile file, TipoPlanilhaEnum tipoPlanilha) throws IOException {
		int sucesso = 0, erro = 0;

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
						List<String> dados = getLinha(row);
						boolean result = persistirEntidade(cabecalho, dados, tipoPlanilha);
						if(result) sucesso++; else erro ++;
					}
				}
			}
		}

		System.out.println("Sucesso: " + sucesso);
		System.out.println("erro: " + erro);
	}

	private boolean isLinhaCabecalho(String id) {
		return id.contentEquals("#");
	}

	private boolean persistirEntidade(List<String> cabecalho, List<String> dados, TipoPlanilhaEnum tipoPlanilha) {
		try {
			switch (tipoPlanilha) {
				case CLIENTE:
					Cliente cliente = ClienteFactory.create(cabecalho, dados);
					System.out.println(cliente);
					clienteService.salvar(cliente);
					break;
				default:
					break;
			}
			System.out.println("SUCESSO");
			return true;
		} catch(Exception e) {
			System.out.println("ERRO");
			return false;
		}

	}
}
