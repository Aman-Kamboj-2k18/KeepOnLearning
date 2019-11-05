<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script>

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

function checkCategory(depositId,isSubmitted, accountAccessType, depositHolderType,obj){
	debugger;
	
	if(isSubmitted=="1"  || isSubmitted=="Yes")
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
	window.location.href=obj.attr("url");
	
	
}

</script>

<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="fd-list">
			<div class="header_customer">
				<h3>PAYMENT</h3>
			</div>

			<div class="fd-list-table">

				<span class="counter pull-right"></span>
				<div class="successMsg">
					<b><font color="error">${error}</font></b>
				</div>
				<div class="col-sm-12 col-md-12 col-lg-12">
					<input type="text" id="kwd_search" value=""
						placeholder="Search Here..." style="float: right;" />
				</div>
<!-- 				<h4> -->
<!-- 					<span style="color: red;">*</span><b>Select the deposit</b> -->
<!-- 				</h4> -->
<br>
				<table
					class="table table-bordered table-striped data jqtable example"
					id="my-table" style="margin-top: 7px; margin-left: -21px;">
					<thead>
						<tr>
							<!-- <th><spring:message code="label.fdID" /></th> -->
							<th><spring:message code="label.accountNo" /></th>
							<th><spring:message code="label.fdAmount" /></th>
							<th><spring:message code="label.maturityDate" /></th>
							<th><spring:message code="label.depositHolderStatus" /></th>
							<th><spring:message code="label.contribution" /></th>
							<th><spring:message code="label.createdDate" /></th>
							<th><spring:message code="label.status" /></th>
							<th><spring:message code="label.depositCategory" /></th>
							<th><spring:message code="label.action" /></th>
							<th><spring:message code="label.action" /></th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${! empty depositHolderList}">
							<c:forEach items="${depositHolderList}" var="depositHolder">
							 
								<tr>
									
									<td><c:out value="${depositHolder.accountNumber}"></c:out></td>

									<fmt:formatNumber value="${depositHolder.depositamount}"
										pattern="#.##" var="depositamount" />
									<td class="commaSeparated"><c:out value="${depositamount}"></c:out></td>
									<td><fmt:formatDate pattern="dd/MM/yyyy"
											value="${depositHolder.newMaturityDate}" /></td>
									<td><c:out value="${depositHolder.depositHolderStatus}"></c:out></td>
									<td><c:out value="${depositHolder.contribution}"></c:out></td>
									<td><fmt:formatDate pattern="dd/MM/yyyy"
											value="${depositHolder.createdDate}" /></td>
									<td><c:out value="${depositHolder.status}"></c:out></td>
									<td><c:if
											test="${empty depositHolder.depositType}">Regular</c:if>
										<c:out value="${depositHolder.depositType}"></c:out></td>
									
									<td><a onclick="return checkCategory('${depositHolder.depositId}','${depositHolder.deathCertificateSubmitted}','${depositHolder.accountAccessType}','${depositHolder.depositHolderStatus}',$(this)); return false;" href="#" 
										url="custDepositAmt?id=${depositHolder.depositId}&accountNumber=${depositHolder.accountNumber}&newMaurityDate=${depositHolder.newMaturityDate}&depositType=${depositHolder.depositType}"
										class="btn btn-primary"><spring:message code="label.card" /></a></td>
									<td><a  onclick="return checkCategory('${depositHolder.depositId}','${depositHolder.deathCertificateSubmitted}','${depositHolder.accountAccessType}','${depositHolder.depositHolderStatus}',$(this)); return false;" href="#" 
										url="netBankingPayment?id=${depositHolder.depositId}&accountNumber=${depositHolder.accountNumber}&newMaurityDate=${depositHolder.newMaturityDate}&depositType=${depositHolder.depositType}"
										class="btn btn-primary"><spring:message
												code="label.netBanking" /></a></td>
								</tr>
							</c:forEach>
						</c:if>

					</tbody>
				</table>
			</div>

		</div>
	</div>
</div>