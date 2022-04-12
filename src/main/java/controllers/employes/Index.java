package controllers.employes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EmployeDao;
import dao.EmployeDaoImpl;
import models.Employe;

@WebServlet(urlPatterns = "/employes")
public class Index extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		EmployeDao empDao = new EmployeDaoImpl();
		List<Employe> listEmps = null;
		try {
			Long countResults = empDao.getCountResults();
			if (countResults != null) {
				int page, size, numberPages;
				// page param
				try {
					page = Integer.parseInt(req.getParameter("page"));
				} catch (Exception e) {
					page = 0;
				}
				// size param
				try {
					size = Integer.parseInt(req.getParameter("size"));
					if (size <= 0) {
						size = 5;
					}
				} catch (Exception e) {
					size = 5;
				}
				// 
				try {
					numberPages = (int) Math.ceil(countResults / size);
					System.out.println(page);
				} catch (Exception e) {
					numberPages = 1;
				}
				if (page < 0) {
					page = numberPages;
				}
				if (page > numberPages) {
					page = 0;
				}
				listEmps = empDao.findAll(page, size);
				
				req.setAttribute("numberPages", numberPages);
				req.setAttribute("page", page);
				req.setAttribute("size", size);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		

		req.setAttribute("title", "Employés");
		req.setAttribute("employes", listEmps);
		this.getServletContext().getRequestDispatcher("/views/employes/index.jsp").forward(req, resp);
	}

}
