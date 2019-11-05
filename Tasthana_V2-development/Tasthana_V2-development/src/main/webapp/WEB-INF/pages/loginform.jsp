<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style>
.error {
	color: #ff0000;
}

.errorblock {
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}

</style>
<div>
<form:form action="loginform.html" commandName="loginForm" autocomplete="off">

	<div class="error">${errorMsg}</div>
	<br>
	<table cellpadding="0" cellspacing="0" width="40%">
		<tr>
			<td class="heading_text">User Name</td>
			<td><form:input path="userName" maxlength="20" autofocus /></td>
			<td><form:errors path="userName" cssClass="error" /></td>

		</tr>

		<tr>
			<td class="heading_text">Password</td>
			<td><form:password path="password" maxlength="20" /></td>
			<td><form:errors path="password" cssClass="error" /></td>

		</tr>

		<tr>

			<td><input class="btn" type="submit" value="Submit" /></td>

		</tr>


	</table>
</form:form>
</div>