<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
        <div class="container-fluid">
  

<div class="approval-customer-confirm">
			<div class="col-sm-12 col-md-12 col-lg-12 header_customer" style="margin-bottom:25px;">	
			<h3><spring:message code="label.approval"/></h3>
			</div>
<form:form action="approveDepositConfrim" class="form-horizontal" commandName="fixedDepositForm" onsubmit="return val();">

			<div class="approval-customer-confirm-table">
			
				
				<div class="form-group" style="clear:both;">
					<label class="col-md-4 control-label"><spring:message code="label.id" /><span style="color: red">*</span></label>
					<div class="col-md-5">
						<form:input path="id" id="id"  readonly="true"  class="form-control" value="${model.fixedDepositForm.id}" />
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-md-4 control-label"><spring:message code="label.fdAmount" /></label>
					<fmt:formatNumber value="${fixedDepositForm.depositedAmt}" pattern="#.##" var="depAmt"/>
					<div class="col-md-5">
						<form:input path="depositedAmt" id="fdAmount" class="form-control" value="${depAmt}" readonly="true" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label"><spring:message code="label.maturityDate" /></label>
					<div class="col-md-5">
						<form:input path="maturityDate" class="form-control" readonly="true" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label"><spring:message code="label.status" /></b><span
					style="color: red">*</span></label>
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
							code="label.comment" /></b><span style="color: red">*</span></label>
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
						<a href="depositPendingList?menuId=${menuId}" class="btn btn-success"><spring:message code="label.back"/></a>
						<input type="submit" value="<spring:message code="label.confirm"/>" class="btn btn-primary">
						<a class="btn btn-success" href="viewDeposit?id=${model.fixedDepositForm.id}&page=approveFdPendingList?id=${model.fixedDepositForm.id}&menuId=${menuId}">View Deposit</a>
						
					</div>
				</div>
				
			
			</div>
								


</form:form>
	<script>
		
			
				 
				function val(){

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
</div>

