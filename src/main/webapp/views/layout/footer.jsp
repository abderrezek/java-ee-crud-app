		<!-- import JS -->
		<script src="<c:url value="/js/bootstrap.min.js" />"></script>
		<c:if test="${ fn:containsIgnoreCase(requestURI, '/employes/add') || fn:containsIgnoreCase(requestURI, '/employes/edit') }">
			<script src="<c:url value="/js/flatpickr.min.js" />"></script>
			<script>
				flatpickr("#date_naissance", {});
			</script>
		</c:if>
		<script src="<c:url value="/js/jquery-3.6.0.min.js" />"></script>
		<script src="<c:url value="/js/main.js" />"></script>
	</body>
</html>