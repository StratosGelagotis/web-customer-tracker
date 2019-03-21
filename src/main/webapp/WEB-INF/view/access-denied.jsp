<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<title>luv2code - Access Denied</title>
</head>

<body>

	<h2>Access Denied - You are not authorized to access this resource.</h2>

	<hr>
	
	<a href="${pageContext.request.contextPath}/">Back to Home Page</a>

	<%-- Add Logout button--%>
	<form:form action="${pageContext.request.contextPath}/logout" method="post">
		<input type="submit" value="Logout" class="add-button/>
	</form:form>

</body>

</html>