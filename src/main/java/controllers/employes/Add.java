package controllers.employes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.EmployeDao;
import dao.EmployeDaoImpl;
import models.Employe;
import models.Sexe;

@WebServlet(urlPatterns = "/employes/add")
public class Add extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("title", "Ajouter un employés");
		
		this.getServletContext().getRequestDispatcher("/views/employes/add.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean validate = (boolean) request.getAttribute("validate");		
		if (validate) {
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String dateNaissance = request.getParameter("date_naissance");
			String lieuNaissance = request.getParameter("lieu_naissance");
			String sexe = request.getParameter("sexe");
			try {
				Employe emp = new Employe();
				emp.setNom(nom);
				emp.setPrenom(prenom);
				Date dn = new SimpleDateFormat("yyyy-MM-dd").parse(dateNaissance); 
				emp.setDateNaissance(dn);
				emp.setLieuNaissance(lieuNaissance);
				Sexe s = Sexe.valueOf(sexe); 
				emp.setSexe(s);
				
				EmployeDao empDao = new EmployeDaoImpl();
				empDao.insert(emp);
				
				request.getSession().setAttribute("msgEmploye", "Sauvegarde réussie");
				
				response.sendRedirect(request.getContextPath() + "/employes");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			doGet(request, response);
		}
	}

}
