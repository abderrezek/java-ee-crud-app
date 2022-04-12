<%@ include file="../layout/header.jsp" %>
<%@ include file="../layout/navbar.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  

<div class="mt-4 container">
	<c:if test="${ msgEmploye != null }">
		<div class="alert alert-success alert-dismissible fade show" role="alert">
			${ msgEmploye }
			<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
		<% session.setAttribute("msgEmploye", null); %>
	</c:if>
	
	<div class="d-flex justify-content-between align-items-center">
		<div class="d-flex align-items-center">
			<!-- Add new -->
			<a href="<c:url value="/employes/add" />" class="btn btn-primary">
				<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus" viewBox="0 0 16 16">
				  <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
				</svg>
				Ajouter nouveau
			</a>
			<!-- Delete All -->
			<button class="btn btn-danger mx-1" ${ employes.isEmpty() ? 'disabled' : '' } onclick="deleteAll()">
				<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eraser" viewBox="0 0 16 16">
					<path d="M8.086 2.207a2 2 0 0 1 2.828 0l3.879 3.879a2 2 0 0 1 0 2.828l-5.5 5.5A2 2 0 0 1 7.879 15H5.12a2 2 0 0 1-1.414-.586l-2.5-2.5a2 2 0 0 1 0-2.828l6.879-6.879zm2.121.707a1 1 0 0 0-1.414 0L4.16 7.547l5.293 5.293 4.633-4.633a1 1 0 0 0 0-1.414l-3.879-3.879zM8.746 13.547 3.453 8.254 1.914 9.793a1 1 0 0 0 0 1.414l2.5 2.5a1 1 0 0 0 .707.293H7.88a1 1 0 0 0 .707-.293l.16-.16z"/>
				</svg>
				Supprimer tous
			</button>
			<!-- export excel -->
			<c:choose>
				<c:when test="${ ! employes.isEmpty() }">
					<a href="<c:url value="/employes/export" />" class="btn btn-success">				
				</c:when>
				<c:otherwise>
					<a href="#" class="btn btn-success" disabled>
				</c:otherwise>
			</c:choose>
				<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-earmark-spreadsheet-fill" viewBox="0 0 16 16">
				  <path d="M6 12v-2h3v2H6z"/>
				  <path d="M9.293 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V4.707A1 1 0 0 0 13.707 4L10 .293A1 1 0 0 0 9.293 0zM9.5 3.5v-2l3 3h-2a1 1 0 0 1-1-1zM3 9h10v1h-3v2h3v1h-3v2H9v-2H6v2H5v-2H3v-1h2v-2H3V9z"/>
				</svg>
			</a>
		</div>
		
		<!-- search -->
		<form method="get" action="<c:url value="/employes/search" />" class="d-flex justify-content-center align-items-center">
			<input class="form-control" name="q" value="${ param.q }" title="date format: yyyy-mm-dd ex: 1994-07-27" title="Tooltip on bottom" type="text" placeholder="Rechercher ici..." />
			
			<button type="submit" class="btn btn-primary btn-small">Rechercher</button>
		</form>
	</div>

	<!-- table -->
	<div class="mt-4">
		<c:choose>
			<c:when test="${ employes == null }">
				<div class="alert alert-danger" role="alert">
				  somthing wrong
				</div>
			</c:when>
			<c:otherwise>
				<table class="table table-striped">
					<thead>
						<tr>
							<th scope="col">N°</th>
							<th scope="col">Nom</th>
							<th scope="col">Prenom</th>
							<th scope="col">Date Naissance</th>
							<th scope="col">Lieu Naissance</th>
							<th scope="col">Sexe</th>
							<th scope="col">Actions</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${ employes.isEmpty() }">
								<tr>
									<td colspan="7">
										<h3 class="text-center">Il n'y a pas d'employés</h3>
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="emp" items="${ employes }" varStatus="loop">
									<tr>
										<td>${ loop.count }</td>
										<td>${ emp.getNom() }</td>
										<td>${ emp.getPrenom() }</td>
										<td>
											<fmt:formatDate type="date" dateStyle="short" value="${ emp.getDateNaissance() }"/>
										</td>
										<td>${ emp.getLieuNaissance() }</td>
										<td>${ emp.getSexe() }</td>
										<td>
											<a class="btn btn-secondary btn-sm" href="<c:url value="/employes/edit/${ emp.getId() }" />">
												<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
												  <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
												  <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
												</svg>
											</a>
											<button type="button" class="btn btn-danger btn-sm mx-1" onclick="askForDelete(${ emp.getId() })">
												<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eraser" viewBox="0 0 16 16">
												  <path d="M8.086 2.207a2 2 0 0 1 2.828 0l3.879 3.879a2 2 0 0 1 0 2.828l-5.5 5.5A2 2 0 0 1 7.879 15H5.12a2 2 0 0 1-1.414-.586l-2.5-2.5a2 2 0 0 1 0-2.828l6.879-6.879zm2.121.707a1 1 0 0 0-1.414 0L4.16 7.547l5.293 5.293 4.633-4.633a1 1 0 0 0 0-1.414l-3.879-3.879zM8.746 13.547 3.453 8.254 1.914 9.793a1 1 0 0 0 0 1.414l2.5 2.5a1 1 0 0 0 .707.293H7.88a1 1 0 0 0 .707-.293l.16-.16z"/>
												</svg>
											</button>
											<a href="<c:url value="/employes/download/${ emp.getId() }" />" class="btn btn-info btn-sm">
												<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-earmark-pdf-fill" viewBox="0 0 16 16">
									  <path d="M5.523 12.424c.14-.082.293-.162.459-.238a7.878 7.878 0 0 1-.45.606c-.28.337-.498.516-.635.572a.266.266 0 0 1-.035.012.282.282 0 0 1-.026-.044c-.056-.11-.054-.216.04-.36.106-.165.319-.354.647-.548zm2.455-1.647c-.119.025-.237.05-.356.078a21.148 21.148 0 0 0 .5-1.05 12.045 12.045 0 0 0 .51.858c-.217.032-.436.07-.654.114zm2.525.939a3.881 3.881 0 0 1-.435-.41c.228.005.434.022.612.054.317.057.466.147.518.209a.095.095 0 0 1 .026.064.436.436 0 0 1-.06.2.307.307 0 0 1-.094.124.107.107 0 0 1-.069.015c-.09-.003-.258-.066-.498-.256zM8.278 6.97c-.04.244-.108.524-.2.829a4.86 4.86 0 0 1-.089-.346c-.076-.353-.087-.63-.046-.822.038-.177.11-.248.196-.283a.517.517 0 0 1 .145-.04c.013.03.028.092.032.198.005.122-.007.277-.038.465z"/>
									  <path fill-rule="evenodd" d="M4 0h5.293A1 1 0 0 1 10 .293L13.707 4a1 1 0 0 1 .293.707V14a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2zm5.5 1.5v2a1 1 0 0 0 1 1h2l-3-3zM4.165 13.668c.09.18.23.343.438.419.207.075.412.04.58-.03.318-.13.635-.436.926-.786.333-.401.683-.927 1.021-1.51a11.651 11.651 0 0 1 1.997-.406c.3.383.61.713.91.95.28.22.603.403.934.417a.856.856 0 0 0 .51-.138c.155-.101.27-.247.354-.416.09-.181.145-.37.138-.563a.844.844 0 0 0-.2-.518c-.226-.27-.596-.4-.96-.465a5.76 5.76 0 0 0-1.335-.05 10.954 10.954 0 0 1-.98-1.686c.25-.66.437-1.284.52-1.794.036-.218.055-.426.048-.614a1.238 1.238 0 0 0-.127-.538.7.7 0 0 0-.477-.365c-.202-.043-.41 0-.601.077-.377.15-.576.47-.651.823-.073.34-.04.736.046 1.136.088.406.238.848.43 1.295a19.697 19.697 0 0 1-1.062 2.227 7.662 7.662 0 0 0-1.482.645c-.37.22-.699.48-.897.787-.21.326-.275.714-.08 1.103z"/>
									</svg>
											</a>
										</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
				
				<c:if test="${ ! employes.isEmpty() }">
					<div class="d-flex justify-content-between align-items-center">
						<!-- size -->
						<select onchange="window.location.href = this.value" class="form-select form-select-sm" style="width: 100px">
							<option ${ param.size == 5 ? 'selected' : '' } value="<c:url value="/employes?size=5" />">
								05
							</option>
							<option ${ param.size == 10 ? 'selected' : '' } value="<c:url value="/employes?size=10" />">
								10
							</option>
							<option ${ param.size == 15 ? 'selected' : '' } value="<c:url value="/employes?size=15" />">
								15
							</option>
							<option ${ param.size == 20 ? 'selected' : '' } value="<c:url value="/employes?size=20" />">
								20
							</option>
						</select>
					
						<!-- pagination -->
						  <ul class="pagination">
						    <li class="page-item">
						    	<a class="page-link" href="<c:url value="/employes?page=${ page - 1 }&size=5" />">Previous</a>
					    	</li>
						  	<c:forEach var="nb" begin="0" end="${ numberPages }">
								<li class="page-item ${ page == nb ? 'active': '' }">
									<a href="<c:url value="/employes?page=${ nb }&size=${ param.size != null ? param.size : 5 }" />" class="page-link">
										${ nb + 1 }
									</a>
								</li>
							</c:forEach>
						    <li class="page-item">
						    	<a class="page-link" href="<c:url value="/employes?page=${ page + 1 }&size=5" />">Next</a>
					    	</li>
						  </ul>
					</div>
					
					<!-- Modal delete -->
					<div class="modal fade" id="modalDelete" tabindex="-1"
						aria-labelledby="exampleModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalLabel">Supprimer</h5>
									<button type="button" class="btn-close" data-bs-dismiss="modal"
										aria-label="Close"></button>
								</div>
								<div class="modal-body">Voulez-vous le supprimer</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary"
										data-bs-dismiss="modal">Fermer</button>
									<form method="post" action="<c:url value="/employes/delete" />">
										<input id="numero" name="numero" type="hidden" />													
										<button class="btn btn-danger">Supprimer</button>
									</form>
								</div>
							</div>
						</div>
					</div>
					<!-- Modal delete all -->
					<div class="modal fade" id="modalDeleteAll" tabindex="-1"
						aria-labelledby="exampleModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalLabel">Supprimer</h5>
									<button type="button" class="btn-close" data-bs-dismiss="modal"
										aria-label="Close"></button>
								</div>
								<div class="modal-body">Voulez-vous le supprimer tous</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary"
										data-bs-dismiss="modal">Fermer</button>
									<form method="post" action="<c:url value="/employes/delete-all" />">
										<button class="btn btn-danger">Supprimer</button>
									</form>
								</div>
							</div>
						</div>
					</div>
				</c:if>
			</c:otherwise>
		</c:choose>
	</div>
</div>

<%@ include file="../layout/footer.jsp" %>