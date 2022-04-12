package controllers.employes;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EmployeDao;
import dao.EmployeDaoImpl;
import models.Employe;

@WebServlet(urlPatterns = "/employes/search")
public class Search extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String q = req.getParameter("q");

		EmployeDao emp = new EmployeDaoImpl();	
		List<Employe> listEmps = emp.search(q);

		req.setAttribute("title", "Employés");
		req.setAttribute("employes", listEmps);
		this.getServletContext().getRequestDispatcher("/views/employes/index.jsp").forward(req, resp);
	}
	
}
