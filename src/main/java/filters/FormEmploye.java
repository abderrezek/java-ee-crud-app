package filters;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter(urlPatterns = { "/employes/add", "/employes/edit/*" })
public class FormEmploye implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (((HttpServletRequest) request).getMethod().equalsIgnoreCase("post")) {
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String dateNaissance = request.getParameter("date_naissance");
			String lieuNaissance = request.getParameter("lieu_naissance");
			String sexe = request.getParameter("sexe");
			boolean validate = true;
			
			// nom
			if (nom.isEmpty()) {
				request.setAttribute("errNom", "Nom est vide");
				validate = false;
			} else if (!nom.isEmpty()) {
				request.setAttribute("oldNom", nom);
			}
			// prenom
			if (prenom.isEmpty()) {
				request.setAttribute("errPrenom", "Prénom est vide");
				validate = false;
			} else if (!prenom.isEmpty()) {
				request.setAttribute("oldPrenom", prenom);
			}
			// date naissance
			if (dateNaissance.isEmpty()) {
				request.setAttribute("errDateNais", "Date de naissance est vide");
				validate = false;
			} else if (!dateNaissance.isEmpty()) {
				try {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					int age = Period.between(LocalDate.parse(dateNaissance, formatter), LocalDate.now()).getYears();
					if (age <= 18 || age >= 70) {
						request.setAttribute("errDateNais", "Date de naissance n'est pas valide");
						validate = false;
					} else {
						request.setAttribute("oldDateNaissance", dateNaissance);
					}
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("errDateNais", "Date de naissance n'est pas valide");
					validate = false;
				}
			}
			// lieu naissance
			if (lieuNaissance.isEmpty()) {
				request.setAttribute("errLieuNais", "Lieu de naissance est vide");
				validate = false;
			} else if (!lieuNaissance.isEmpty()) {
				request.setAttribute("oldLieuNaissance", lieuNaissance);
			}
			// sexe
			if (sexe.isEmpty() || sexe.equals("no select")) {
				request.setAttribute("errSexe", "Sexe est vide");
				validate = false;
			} else if (!sexe.isEmpty() && !sexe.equals("no select")) {
				if (!sexe.equals("homme") && !sexe.equals("femme")) {
					request.setAttribute("errSexe", "Sexe n'est pas valide");
					validate = false;
				} else {
					request.setAttribute("oldSexe", sexe);
				}
			}
			
			request.setAttribute("validate", validate);
		}
		
		chain.doFilter(request, response);
	}

}
