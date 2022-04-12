package utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import models.Employe;

public class EmployeExcelExporter {
	
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	private List<Employe> listEmployes;
	
	private EmployeExcelExporter() {
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Employés");
	}
	
	public EmployeExcelExporter(List<Employe> listEmps) {
		this();
		listEmployes = listEmps;
	}
	
	private void writeHeaderRow() {
		Row row = sheet.createRow(0);
		
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(14);
		style.setFont(font);
		
		Cell cell = row.createCell(0);
		cell.setCellValue("N°");
		cell.setCellStyle(style);

		cell = row.createCell(1);
		cell.setCellValue("Nom");
		cell.setCellStyle(style);
		
		cell = row.createCell(2);
		cell.setCellValue("Prénom");
		cell.setCellStyle(style);
		
		cell = row.createCell(3);
		cell.setCellValue("Date de naissance");
		cell.setCellStyle(style);
		
		cell = row.createCell(4);
		cell.setCellValue("lieu de naissance");
		cell.setCellStyle(style);
		
		cell = row.createCell(5);
		cell.setCellValue("Sexe");
		cell.setCellStyle(style);
	}
	
	private void writeDataRows() {
		int rowCount = 1, n = 1;
		Row row;
		Cell cell;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(12);
		style.setFont(font);
		
		for (Employe emp : listEmployes) {
			row = sheet.createRow(rowCount++);
			
			cell = row.createCell(0);
			cell.setCellValue(n++);
			cell.setCellStyle(style);
			sheet.autoSizeColumn(0);
			
			cell = row.createCell(1);
			cell.setCellValue(emp.getNom());
			cell.setCellStyle(style);
			sheet.autoSizeColumn(1);
			
			cell = row.createCell(2);
			cell.setCellValue(emp.getPrenom());
			cell.setCellStyle(style);
			sheet.autoSizeColumn(2);
			
			cell = row.createCell(3);
			cell.setCellValue(df.format(emp.getDateNaissance()));
			cell.setCellStyle(style);
			sheet.autoSizeColumn(3);
			
			cell = row.createCell(4);
			cell.setCellValue(emp.getLieuNaissance());
			cell.setCellStyle(style);
			sheet.autoSizeColumn(4);
			
			cell = row.createCell(5);
			cell.setCellValue(emp.getSexe().toString());
			cell.setCellStyle(style);
			sheet.autoSizeColumn(5);
		}
	}
	
	public void export(HttpServletResponse response) throws IOException {
		writeHeaderRow();
		writeDataRows();
		
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}

}
