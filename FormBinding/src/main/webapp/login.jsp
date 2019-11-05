<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h3>Please Enter the employee details</h3>
	<form:form method="POST" action="/addEmployee"
		modelAttribute="employee">
		<table>
			<tr>
				<%-- 	<td><form:label path="name">Name</form:label></td> --%>
				<td>Name</td>
				<td><form:input path="name" /></td>
			</tr>
			<tr>
				<%-- 	<td><form:label path="id">ID</form:label></td> --%>
				<td>ID</td>
				<td><form:input path="id" value="" /></td>
			</tr>
			<tr>
				<%-- <td><form:label path="contactNumber">Contact Number</form:label></td> --%>
				<td>Contact Number</td>
				<td><form:input path="contactNumber" /></td>
			</tr>
			<tr>
				<td>Employer</td>
				<td><input name="employer" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Submit" /></td>
			</tr>
		</table>
	</form:form>
</body>
</html>