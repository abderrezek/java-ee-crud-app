<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="requestURI" value="${ requestScope['javax.servlet.forward.request_uri'] }"/>

<!doctype html>
<html lang="fr">
	<head>
		<!-- Required meta tags -->
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		
		<title>${ title != null ? title : "Crud APP" }</title>
		
		<!-- import CSS -->
		<link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet">
		<c:if test="${ fn:containsIgnoreCase(requestURI, '/employes/add') || fn:containsIgnoreCase(requestURI, '/employes/edit') }">
			<link href="<c:url value="/css/flatpickr.min.css" />" rel="stylesheet" />
		</c:if>
	</head>
	<body>