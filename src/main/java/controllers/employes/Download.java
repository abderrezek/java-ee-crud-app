package controllers.employes;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EmployeDao;
import dao.EmployeDaoImpl;
import models.Employe;
import utils.EmployeReport;

@WebServlet(urlPatterns = "/employes/download/*")
public class Download extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		String msg = "Impossible de l'imprimer cette employé";
		if (pathInfo != null) {
			String[] params = pathInfo.split("/");
			if (params.length > 0 && params.length < 3) {
				Long id;
				try {
					id = Long.parseLong(params[1]);
				} catch (Exception ex) {
					id = -1L;
				}
				if (id != -1) {
					EmployeDao empDao = new EmployeDaoImpl();
					Employe emp = empDao.findById(id);
					if (emp != null) {
						EmployeReport empReport = new EmployeReport(emp);
						String pathFile = empReport.generate(req);
						if (pathFile != null) {
							showFile(pathFile, resp);
							
//							// you can use : Guava (Files.toString(...)), IOUtils.toString(...)
//							String content = Helper.getContentFile(pathFile);
//							if (! content.isEmpty()) {
//								resp.setContentType("text/html; charset=UTF-8");
//								PrintWriter out = resp.getWriter();
//								out.print(content);
//							}
					        return;
						}
						msg = "Impossible de l'imprimer";
					} else {
						msg = "Impossible de l'imprimer car l'employé n'exist pas";
					}
				}
			}
		}
		req.getSession().setAttribute("msgEmploye", msg);
		resp.sendRedirect(req.getContextPath() + "/employes");
	}
	
	private void showFile(String pathFile, HttpServletResponse res) {
		res.setContentType("application/pdf");
		
		byte[] buffer = null;
		try {
			File f = new File(pathFile);
			res.setHeader("Content-Disposition", "attachement; filename=" + f.getName());
			BufferedInputStream is = new BufferedInputStream(new FileInputStream(f));
			ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
			int ch;
			long actual = 0;
			while ((ch = is.read()) != -1) {
				bos.write(ch);
				actual++;
			}
			bos.flush();
			bos.close();
			buffer = bos.toByteArray();
			res.getOutputStream().write(buffer, 0, buffer.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
