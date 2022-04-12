package controllers.employes;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EmployeDao;
import dao.EmployeDaoImpl;
import models.Employe;
import utils.EmployeExcelExporter;

@WebServlet(urlPatterns = "/employes/export")
public class ExportAll extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/octet-stream");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss"); 
		String currentDateTime = df.format(new Date());
		String fileName = "employes_" + currentDateTime + ".xlsx";
		resp.setHeader("Content-Disposition", "attachement; filename=" + fileName);
		
		EmployeDao emp = new EmployeDaoImpl();
		List<Employe> listEmps = emp.findAll(-1, -1);
		
		EmployeExcelExporter employeExcelExporter = new EmployeExcelExporter(listEmps);
		employeExcelExporter.export(resp);
	}

}
