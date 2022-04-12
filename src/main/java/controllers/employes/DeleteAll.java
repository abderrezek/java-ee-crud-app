package controllers.employes;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EmployeDao;
import dao.EmployeDaoImpl;

@WebServlet(urlPatterns = "/employes/delete-all")
public class DeleteAll extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String msgEmploye = "Supprimer n'est pas réussie";

		EmployeDao employeDao = new EmployeDaoImpl();
		employeDao.deleteAll();
		msgEmploye = "Supprimer à réussie";

		
		req.getSession().setAttribute("msgEmploye", msgEmploye);
		
		resp.sendRedirect(req.getContextPath() + "/employes");
	}
}
