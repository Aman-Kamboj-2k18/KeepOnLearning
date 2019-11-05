<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
        <div class="container-fluid">
			

<div class="fd-list">
			<div class="header_customer">	
				<h3><spring:message code="label.fixedDepositList"/></h3>
			</div>
			
			<div class="fd-list-table">	
			
			<span class="counter pull-right"></span>
				<div class="col-sm-12 col-md-12 col-lg-12">
					<input type="text" id="kwd_search" value="" class="form-control" placeholder="Search Here..."  style="float:right;width: 20%;margin-top: 17px;""/>
				</div>
				<span id="error" style="color:red; margin-left: 307px;"></span>
			<table class="table data jqtable example table-bordered table-striped table-hover " id="my-table">
				<thead>
					<tr>
					<th><spring:message code="label.fdID" /></th>
					<th><spring:message code="label.accno" /></th>
					<th><spring:message code="label.currentBalance" /></th>
					<th><spring:message code="label.fdAmount" /></th>
					<th><spring:message code="label.maturityDate"/></th>
					<th><spring:message code="label.depositHolderStatus"/></th>
					<th><spring:message code="label.contribution"/></th>
					<th><spring:message code="label.createdDate" /></th>
					<th>Deposit Category</th>
					<th><spring:message code="label.action"/></th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${! empty depositHolderList}">
						<c:forEach items="${depositHolderList}" var="depositHolder">
							<tr>
						
							<td><c:out value="${depositHolder.deposit.id}"></c:out></td>
							<td><c:out value="${depositHolder.deposit.accountNumber}"></c:out></td>
							
							<fmt:formatNumber value="${depositHolder.deposit.currentBalance}" pattern="#.##" var="currentBalance"/>
							<td class="commaSeparated"><c:out value="${currentBalance}"></c:out></td>
							
							<fmt:formatNumber value="${depositHolder.deposit.depositAmount}" pattern="#.##" var="depositamount"/>
							<td class="commaSeparated"><c:out value="${depositamount}"></c:out></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${depositHolder.deposit.newMaturityDate}" /></td>
							<td><c:out value="${depositHolder.depositHolder.depositHolderStatus}"></c:out></td>
							<td><c:out value="${depositHolder.depositHolder.contribution}"></c:out></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${depositHolder.deposit.createdDate}"/></td>
							<td><c:if test="${empty depositHolder.deposit.depositCategory}">Regular</c:if><c:out value="${depositHolder.deposit.depositCategory}"></c:out></td>
							<td><a id="editBtn" onclick="return checkCategory('${depositHolder.deposit.depositCategory}',${depositHolder.deposit.id},${depositHolder.depositHolder.id},'${depositHolder.depositHolder.deathCertificateSubmitted}','${depositHolder.deposit.accountAccessType}','${depositHolder.depositHolder.depositHolderStatus}'); return false;" href="#"
									class="btn btn-primary"><spring:message code="label.edit"/></a></td>
							</tr>
						
						</c:forEach>
						
					</c:if>

				</tbody>
			</table> 
			
			</div>

</div></div>
</div>
<script>

function checkCategory(depositCategory,depositId,depositHolderId,isSubmitted, accountAccessType, depositHolderType){
	debugger;
	
	if(isSubmitted=="1" || isSubmitted==1 || isSubmitted=="Yes")
		{
		alert('Account holder can not access account');
		return false;
		}
	if(accountAccessType=="EitherOrSurvivor"){
		
	}
if(accountAccessType=="AnyoneOrSurvivor"){
		
	}
if(accountAccessType=="FormerOrSurvivor"){
	if(depositHolderType=="PRIMARY"){}
	else{
		var result=getDeathCertificate(depositId);
		if(result=="0"){
			alert("Secondary Holders are not allowed to operate this account");
			return false;
		}
		
		}
}
if(accountAccessType=="LatterOrSurvivor"){
	if(depositHolderType=="SECONDARY"){}
	else{
		var result=getDeathCertificate(depositId);
if(result=="0"){
	alert("Primary Holders are not allowed to operate this account");
	return false;
		}
		
		}
}
if(accountAccessType=="Jointly"){
	alert("You are not allowed to operate this account individually");
	return false;
}
if(accountAccessType=="JointlyOrSurvivor"){
	var result=getDeathCertificate(depositId);
	if(result=="0"){
		alert("You are not allowed to operate this account individually");
		return false;
	}
	
}
	
	if(depositCategory==''){
		
		 window.location="editFD?id="+depositId+"&holderId="+depositHolderId+"&depositCategory="+depositCategory;
	}
	else if(depositCategory=='REVERSE-EMI'){
		document.getElementById("error").innerHTML='Annuity Deposit cannot be modified.';
		return false;
	}
	else{
		document.getElementById("error").innerHTML='Cannot be modified for deposit category:'+depositCategory;
		return false;
	}
	
}

function getDeathCertificate(depositId){
	if(depositId==undefined){
		alert("Deposit Id can not be null");
	return false;
	}
	var result="";
    $.ajax({  
		  async: false,
	      type: "GET",  
	      url: "<%=request.getContextPath()%>/bnkEmp/getDeathCertificate", 
	      contentType: "application/json",
	      dataType: "json",
	      data: "depositId="+depositId,
	      success: function(response){     
	      result = response;
	      },  
	      error: function(e){  
	       alert("Error occured!!");
	      }  
	    }); 
    return result;
}
</script>