package br.com.vavive.clientes.service.planilha.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

import br.com.vavive.clientes.service.planilha.entity.CampoPlanilhaEnum;

public class PlanilhaUtils {
	
	public static String getValorCampo(List<String> cabecalho, List<String> dados, CampoPlanilhaEnum campo) {
		try {
			return dados.get(cabecalho.indexOf(campo.getValor()));
		} catch(Exception e) {
			return "";
		}
	}
	
	public static List<String> getLinha(Row linha) {
		List<String> celulas = new ArrayList<>();
		Iterator<Cell> cells = linha.cellIterator();
		
		while (cells.hasNext()) {
			Cell cell = cells.next();
			celulas.add(PlanilhaUtils.getValorCelula(cell));
		}
		
		return celulas;
	}
	
	private static String getValorCelula(Cell cell) {
		DataFormatter formatValue = new DataFormatter();
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd"); 
		
		if(cell == null) {
			return formatValue.formatCellValue(cell);
		} else if(cell.getCellType().equals(CellType.FORMULA)) {
			return getValorFormula(cell, formatValue, formatDate); 
		} else if(cell.getCellType().equals(CellType.NUMERIC)) {
			if(DateUtil.isCellDateFormatted(cell)) {
				return formatDate.format(cell.getDateCellValue());
			} else {
				return formatValue.formatCellValue(cell);
			}
		} else {
			return formatValue.formatCellValue(cell);
		}
	}

	private static String getValorFormula(Cell cell, DataFormatter formatValue, SimpleDateFormat formatDate) {
		switch (cell.getCachedFormulaResultType()) {
			case STRING:
				return cell.getRichStringCellValue().getString();
			case NUMERIC:
				if(DateUtil.isCellDateFormatted(cell)) {
					return formatDate.format(cell.getDateCellValue());
				} else {
					return tratarInteiro(String.valueOf(cell.getNumericCellValue()));
				}
			default:
				return "";
		}
	}

	private static String tratarInteiro(String valorNumero) {
		return new BigDecimal(valorNumero).setScale(0, RoundingMode.FLOOR).toString();
	}
}
