package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import models.Employe;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class EmployeReport {

	private String pathDest;
	private JasperReport jasperReport;
	private Map<String, Object> params;
	private Employe employe;

	private EmployeReport() {
		params = new HashMap<>();
		pathDest = getTempFolderPath("reportExported");
	}
	
	public EmployeReport(Employe employe) {
		this();
		this.employe = employe;
	}
	
	private String getTempFolderPath(String folder) {
		String tempFolderPath = System.getProperty("java.io.tmpdir") + File.separator + folder;
	    File tempFolder = new File(tempFolderPath);
	    if (!tempFolder.exists()) {
	        tempFolder.mkdirs();
	    }
	    return tempFolderPath;
	}
	
	private void compileFile(HttpServletRequest request) throws JRException, IOException {
	    String tempFolderPath = getTempFolderPath("jasperReport");
	    String jasperFilePath = tempFolderPath + File.separator + "Employe.jasper";
	    File reportFile = new File(jasperFilePath);
	    // If compiled file is not found, then compile XML template
	    if (!reportFile.exists()) {
	        InputStream jRXmlStream = request.getSession()
	        		.getServletContext()
	        		.getResourceAsStream("/reports/Employe.jrxml");
	        JasperDesign jasperDesign = JRXmlLoader.load(jRXmlStream);
	        JasperCompileManager.compileReportToFile(jasperDesign, jasperFilePath);
	    }
	    jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportFile.getPath());
	}
	
	private void fillData() {
		params.put("nom", employe.getNom());
		params.put("prenom", employe.getPrenom());
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		params.put("dateNaissance", df.format(employe.getDateNaissance()));
		params.put("lieuNaissance", employe.getLieuNaissance());
		params.put("sexe", employe.getSexe().toString());
	}
	
	public String generate(HttpServletRequest request) {
		fillData();
		
		try {
			compileFile(request);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource(1));
			String pathFileDest = pathDest + File.separator + "report.pdf";
			JasperExportManager.exportReportToPdfFile(jasperPrint, pathFileDest);
			
			return pathFileDest;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
