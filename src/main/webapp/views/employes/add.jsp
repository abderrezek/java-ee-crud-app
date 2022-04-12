<%@ include file="../layout/header.jsp" %>
<%@ include file="../layout/navbar.jsp" %>


<div class="mt-4 container">
	<h3>Ajouter un nouveau employe</h3>
	
	<form class="mt-3 needs-validation" method="post" action="<c:url value="/employes/add" />">
		<div class="row">
			<div class="col-md-6">
				<!-- nom -->
				<div class="mb-3">
				  <label for="nom" class="form-label">Nom</label>
				  <input type="text" name="nom" class="form-control ${ errNom != null ? 'is-invalid' : '' }" value="${ oldNom }" id="nom">
				  <c:if test="${ errNom != null }">
					  <div class="invalid-feedback">
				        ${ errNom }
				      </div>
				  </c:if>
				</div>
			</div>
			<div class="col-md-6">
				<!-- prenom -->
				<div class="mb-3">
				  <label for="prenom" class="form-label">Prénom</label>
				  <input type="text" name="prenom" class="form-control ${ errPrenom != null ? 'is-invalid' : '' }" value="${ oldPrenom }" id="prenom">
				  <c:if test="${ errPrenom != null }">
					  <div class="invalid-feedback">
				        ${ errPrenom }
				      </div>
				  </c:if>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<!-- date naissance -->
				<div class="mb-3">
				  <label for="date_naissance" class="form-label">Date de naissance</label>
				  <input type="text" name="date_naissance" class="form-control ${ errDateNais != null ? 'is-invalid' : '' }" value="${ oldDateNaissance }" id="date_naissance">
				  <c:if test="${ errDateNais != null }">
					  <div class="invalid-feedback">
				        ${ errDateNais }
				      </div>
				  </c:if>
				</div>
			</div>
			<div class="col-md-6">
				<!-- lieu naissance -->
				<div class="mb-3">
				  <label for="lieu_naissance" class="form-label">Lieu de naissance</label>
				  <input type="text" name="lieu_naissance" class="form-control ${ errLieuNais != null ? 'is-invalid' : '' }" value="${ oldLieuNaissance }" id="lieu_naissance">
				  <c:if test="${ errLieuNais != null }">
					  <div class="invalid-feedback">
				        ${ errLieuNais }
				      </div>
				  </c:if>
				</div>
			</div>			
		</div>
		<div class="row">
			<div class="col-md-6">
				<!-- sexe -->
				<div class="mb-3">
				  	<label for="sexe" class="form-label">Sexe</label>
					<select id="sexe" class="form-select ${ errSexe != null ? 'is-invalid' : '' }" aria-label="Default select example" name="sexe">
					  <option
						  <c:if test="${ oldSexe != 'homme' || oldSexe != 'femme' }">selected</c:if>
						  value="no select"
					  >Ouvrir ce menu de sélection</option>
					  <option 
						  <c:if test="${ oldSexe == 'homme' }">selected</c:if>
						  value="homme"
					  >Homme</option>
					  <option 
						  <c:if test="${ oldSexe == 'femme' }">selected</c:if>
						  value="femme"
					  >Femme</option>
					</select>
					<c:if test="${ errSexe != null }">
						<div class="invalid-feedback">
						     ${ errSexe }
						</div>
					</c:if>
				</div>
			</div>
		</div>
		
		<button type="submit" class="btn btn-primary">Enregistrer</button>
	</form>
</div>

<%@ include file="../layout/footer.jsp" %>