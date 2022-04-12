package controllers.employes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EmployeDao;
import dao.EmployeDaoImpl;
import models.Employe;
import models.Sexe;

@WebServlet(urlPatterns = "/employes/edit/*")
public class Edit extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Employe emp = null;
		Long id = getId(req.getPathInfo());
		if (id != -1) {
			try {
				EmployeDao employeDao = new EmployeDaoImpl();
				emp = employeDao.findById(id);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		req.setAttribute("title", "Modifier un employés");
		req.setAttribute("employe", emp);
		this.getServletContext().getRequestDispatcher("/views/employes/edit.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boolean validate = (boolean) req.getAttribute("validate");
		Long id = getId(req.getPathInfo());
		if (validate && id != -1) {
			String nom = req.getParameter("nom");
			String prenom = req.getParameter("prenom");
			String dateNaissance = req.getParameter("date_naissance");
			String lieuNaissance = req.getParameter("lieu_naissance");
			String sexe = req.getParameter("sexe");
			
			try {
				Employe emp = new Employe();
				emp.setId(id);
				emp.setNom(nom);
				emp.setPrenom(prenom);
				Date dn = new SimpleDateFormat("yyyy-MM-dd").parse(dateNaissance); 
				emp.setDateNaissance(dn);
				emp.setLieuNaissance(lieuNaissance);
				Sexe s = Sexe.valueOf(sexe); 
				emp.setSexe(s);
				
				EmployeDao empDao = new EmployeDaoImpl();
				empDao.update(emp);
				
				req.getSession().setAttribute("msgEmploye", "Modifier réussie");
				
				resp.sendRedirect(req.getContextPath() + "/employes");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			doGet(req, resp);
		}
	}
	
	private Long getId(String pathInfo) {
		if (pathInfo != null) {
			String[] params = pathInfo.split("/");
			int pl = params.length;
			if (pl > 0 && pl < 3) {
				try {
					return Long.parseLong(params[1]);
				} catch (Exception ex) {}
			}
		}
		return -1L;
	}
	
}
