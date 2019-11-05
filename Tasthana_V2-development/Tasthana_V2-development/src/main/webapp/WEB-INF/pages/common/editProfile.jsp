<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>

<div class="right-container" id="right-container">
        <div class="container-fluid">
		
<div class="edit-admin-profile">
<div class="col-md-12">
			<div class="header_customer">
				<h3>Profile</h3>
			</div></div>
	<div class="header_customer">
		<div class="p_content">
			<form:form action="confirmEditProfile" name="editProfile"
				autocomplete="off" commandName="endUserForm"
				onsubmit="return val();">
				<div class="Pro_img1 col-sm-4" style="display: inline-block; float: left; padding: 4px 18px;">
					 <c:if test="${! empty endUserForm.imageName}">
        <img src="${endUserForm.imageName}" class="img_pro">
        </c:if>
       <c:if test="${empty endUserForm.imageName}">
        <img class="img_pro">
        </c:if>
					<div id="drop" style="margin-top: 15px;    text-align: center;">

						<a href="#" id='modal-launcher' data-toggle="modal"
							data-target="#login-modal" data-id="${endUserForm.id}"
							class="open-dialog btn btn-primary" id="cancel"><spring:message
								code="label.uploadPhoto" /></a>


					</div>
				</div>
				<div class="form_page col-sm-8">
					<form:hidden path="id" />
					<form:hidden path="transactionId" />
					<table style="margin:0px auto;" width="600">


						<tr>
							<td><label for=""><spring:message
										code="label.userName" />:</label></td>
							<td><form:input path="userName"
									class="myform-control" id="userName" autocomplete="off"
									readonly="true"></form:input></td>
						</tr>
						<tr>
							<td><label for=""><spring:message
										code="label.displayName" />:</label></td>
							<td><form:input path="displayName"
									placeholder="Enter  Display Name" class="myform-control"
									id="displayName" autocomplete="off"></form:input></td>
						</tr>
						<tr>
							<td><label for=""><spring:message
										code="label.email" />:</label></td>
							<td><form:input path="email"
									class="myform-control" placeholder="Enter  Email" id="email"
									autocomplete="off"></form:input>
								</td>
						</tr>
						<tr>
							<td></td>
							<td id="EmailError" class="error col-sm-12"
									style="display: none;">
									<font color="red"><spring:message
											code="label.validation" /></font>
							</td>
						</tr>
						<tr>
							<td><label for=""><spring:message
										code="label.altEmail" />:</label></td>
							<td><form:input path="altEmail"
									class="myform-control" id="altEmail"
									placeholder="Enter Alternate Email" autocomplete="off"></form:input>
								</td>
						</tr>
						<tr>
							<td></td>
							<td id="altEmailError" class="error col-sm-12"
									style="display: none;">
									<font color="red"><spring:message
											code="label.validation" /></font>
								</td>
						</tr>
						<tr>
							<td><label for=""><spring:message
										code="label.contactNumber" />:</label></td>
							<td><form:input path="contactNo"
									class="myform-control" id="contactNo"
									placeholder="Enter Contact Number" autocomplete="off"></form:input>
							</td>
						</tr>
						<tr>
							<td></td>
							<td id="contactNoError" class="error col-sm-12"
									style="display: none;">
									<font color="red"><spring:message
											code="label.validation" /></font>
								</td>
						</tr>
						<tr>
							<td><label for=""><spring:message
										code="label.altContactNo" />:</label></td>
							<td><form:input path="altContactNo"
									class="myform-control" id="altContactNo"
									placeholder="Enter Alternate ContactNo" autocomplete="off"></form:input>
							</td>
							
						</tr>
						<tr>
							<td></td>
							<td id="altContactNoError" class="error col-sm-12"
									style="display: none;">
									<font color="red"><spring:message
											code="label.validation" /></font>
								</td>
						</tr>
						<tr><td>&nbsp;</td></tr>
						<tr>
							<td><a
								href="editPWD?id=${endUserForm.id}&menuId=${menus[0].id}" style="color: #f89406;"><spring:message
										code="label.changePassword" /></a></td>
							<td><a href="homePage?menuId=${menus[0].id}"
								class="btn btn-success"><spring:message code="label.back" /></a> <input type="submit"
								value="<spring:message code="label.confirm"/>"
								class="btn btn-primary"> </td>
						</tr>
					</table>

				</div>
			</form:form>
		</div>
	</div>
</div></div>
<section>
	<div class="container">
		<div class="modal fade" id="login-modal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header login_modal_header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h3 class="modal-title" id="myModalLabel">
							<spring:message code="label.uploadImage" />
						</h3>
					</div>
					<div class="modal-body login-modal">
						<div class="clearfix"></div>
						<div id='social-icons-conatainer'>
							<div class='modal-body-left'>
								<form:form action="updateImageForProfile"
									commandName="endUserForm" enctype="multipart/form-data">
									<div class="form-group">
										<form:hidden path="id" value="" id="id" />

										<form:input path="file" type="file"
											onchange="checkfile(this);" id="imageFile" />
									</div>

									<input type="submit" class="btn btn-primary" value="Upload">
									<button class="btn" data-dismiss="modal" aria-hidden="true">
										<spring:message code="label.close" />
									</button>
								</form:form>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>

				</div>
			</div>
		</div>
	</div>
</section>

<script>
	function checkfile(sender) {
		var validExts = new Array(".bmp", ".dib", ".JPG", ".jpg", ".jpeg",
				".jpe", ".jfif", ".gif", ".tif", ".tiff", ".png");
		var fileExt = sender.value;
		fileExt = fileExt.substring(fileExt.lastIndexOf('.'));
		if (validExts.indexOf(fileExt) < 0) {
			alert("Invalid file selected, valid files are of "
					+ validExts.toString() + " types.");
			var fld = document.getElementById("imageFile");

			fld.form.reset();
			fld.focus();
		}
	}

	$(document).on("click", ".open-dialog", function() {
		$(".modal-body #id").val($(this).data('id'));

	});
</script>
<script>
	function val() {

		var phoneNum = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
		var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

		var altContactNo = document.getElementById('altContactNo').value;
		var contactNo = document.getElementById('contactNo').value;
		var canSubmit = true;

		if (altContactNo
				.match(/^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/)
				|| (altContactNo == '')) {

			document.getElementById('altContactNoError').style.display = 'none';

		} else {
			document.getElementById('altContactNoError').style.display = 'block';
			canSubmit = false;
		}

		if (altEmail.value
				.match(/^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/)
				|| (document.getElementById('altEmail').value == '')) {

			document.getElementById('altEmailError').style.display = 'none';

		} else {
			document.getElementById('altEmailError').style.display = 'block';
			canSubmit = false;
		}

		if (email.value
				.match(/^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/)
				&& !(document.getElementById('email').value == '')) {

			document.getElementById('EmailError').style.display = 'none';

		} else {
			document.getElementById('EmailError').style.display = 'block';
			canSubmit = false;
		}

		if (contactNo
				.match(/^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/)
				&& !(contactNo == '')) {

			document.getElementById('contactNoError').style.display = 'none';

		} else {
			document.getElementById('contactNoError').style.display = 'block';
			canSubmit = false;
		}

		if (canSubmit == false) {
			return false;
		}
	}
</script>
