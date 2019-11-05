<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<div class="bank-emp-list">
	<div class="header_customer">
		<h3 align="center"><spring:message code="label.approval"/></h3>
	</div>
	<form:form name="fixedDepositForm" class="form-horizontal" action="approveDepositConfrim" method="post" commandName="fixedDepositForm" onsubmit="return val();">
			
			<div class="form-group">
				<label class="col-md-4 control-label"><spring:message code="label.id" /><span style="color: red">*</span></label>
				<div class="col-md-5">
					<form:input path="id" id="id" class="form-control" value="${model.fixedDepositForm.id}" readonly="true" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-4 control-label"><spring:message code="label.fdAmount" /></label>
				<div class="col-md-5">
					<form:input path="fdAmount" id="fdAmount" class="form-control" value="${model.fixedDepositForm.fdAmount}" readonly="true" /> 
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-4 control-label"><spring:message code="label.maturityDate" /></label>
				<div class="col-md-5">
					<form:input path="maturityDate" class="form-control" value="${model.fixedDepositForm.maturityDate}" readonly="true" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-4 control-label"><spring:message code="label.status" /><span style="color: red">*</span></label>
				<div class="col-md-5">
					<form:select path="status" id="status" class="form-control"
						width="205px;!important">
						<form:option value="">
							<spring:message code="label.selectValue" />
						</form:option>
						<form:option value="Approved">
							<spring:message code="label.approve" />
						</form:option>
						<form:option value="Closed">
							<spring:message code="label.close" />
						</form:option>
					</form:select>
					<span id="statusError" class="error" style="display: none;"><font
					color="red"><spring:message code="label.selectValue" /></font></span>
					<span id="statusError" class="error" style="display: none"><spring:message
						code="label.selectValue" /></span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-4 control-label"><spring:message
							code="label.comment" /><span style="color: red">*</span></label>
				<div class="col-md-5">
					<form:textarea path="comment" id="comment" class="form-control" maxlength="255"
						placeholder="Enter Comment" style="height:120px;"></form:textarea>
						<span id="commentError" class="error" style="display: none;"><font
						color="red"><spring:message code="label.commentValidation" /></font></span>
						<span id="commentError" class="error" style="display: none"><spring:message
						code="label.commentValidation" /></span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-4 control-label"></label>
				<div class="col-md-5">
					<input type="submit"
						value="<spring:message code="label.confirm"/>"
						class="btn btn-primary">
					<a href="apprMng" class="btn btn-success"><spring:message
								code="label.back" /></a>
				</div>
			</div>
		
	</form:form>
</div>

<style>
select#select {
	width: 205px;
}
</style>
<script>
		 
				
	function val() {

		if (document.getElementById('comment').value == ''){
			document.getElementById('comment').style.borderColor="red";
			return false;
		}
		else{
			document.getElementById('comment').style.borderColor="green";
		}
		if (document.getElementById('status').value == ''){
			document.getElementById('status').style.borderColor="red";
			return false;
		}
		else{
			document.getElementById('status').style.borderColor="green";
		}
	}
</script>
