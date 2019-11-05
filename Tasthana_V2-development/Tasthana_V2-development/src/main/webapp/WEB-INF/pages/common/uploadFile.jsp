<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="fd-list">
			<div class="header_customer">
				<h3>
					<spring:message code="label.upload" />
				</h3>
			</div>
			<h5>
				<div style="text-align: center; color: red; font-size: 1.1em;">
					${error}</div>
			</h5>

			<div class="fd-list-table">

				<span class="counter pull-right"></span>
				<form:form action="uploadedFile" method="post"
					commandName="uploadedFile" enctype="multipart/form-data">
					<table class="table data jqtable example" id="my-table">
						<thead>
							<tr>
								<td><input name="files" type="file"
									id="fileValue${status.index}" multiple="multiple"
									onchange="myFunction(${status.index})" /></td>
							</tr>
							<tr>
								<td><input type="submit" class="btn btn-primary"
									value="Upload File" /></td>
							</tr>
						</thead>

					</table>
				</form:form>
			</div>

			<div class="header_customer">
				<h3>All Documents</h3>
			</div>

			<div class="fd-list-table">

				<span class="counter pull-right"></span>

				<table class="host-list table-striped table-bordered" align="center"
					width="400" id="dataTable" style="margin-top: 36px;">

					<thead>
						<tr>
							<th style="display: none">File URL
							<th>File Name</th>
							<th>Action</th>

						</tr>
					</thead>
					<tbody>

						<c:forEach items="${fileFormList}" var="imagename"
							varStatus="status">
							<tr>
								<td style="display: none"><img
									class="profile-pic upload-button" width="100px"
									id="image[${status.index}]" src="${imagename.url}"></td>
								<td>${imagename.fileName}</td>
								<td><button type="button"
										onclick="downloadImage(${status.index},'${imagename.fileName}')"
										class="btn btn-primary">
										<i class="glyphicon glyphicon-download"></i>Download
									</button></td>
								<%-- <td style="display:none"><img class="profile-pic upload-button" width="100px"
							id="image[${status.index}]" src="${imagename.url}"></td>
						<td>${imagename.fileName}</td>
						<td><button type="button"
								onclick="downloadImage(${status.index},'${imagename.fileName}')"
								class="btn btn-primary">
								<i class="glyphicon glyphicon-download"></i>Download
							</button></td> --%>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

		</div>
		<script>
function myFunction(a)
{
 
 var files = document.getElementById("fileValue"+a).files; 
 document.getElementById("fileName"+a).value="";
 var validExts = new Array("jpg", "jpeg", "png", "gif");

    for (var i = 0; i < files.length; i++){     
     var fileExt= /[^.]+$/.exec( files[i].name);     
     
     if (validExts.indexOf(fileExt.toString()) < 0) {
           alert("Invalid file selected, valid files are of " +
                    validExts.toString() + " types."); 
           document.getElementById("fileValue"+a).value="";
         }
     else {
      document.getElementById("fileName"+a).value += files[i].name+","; 
     }
     
    }
}

/* function downloadImage(idx,fileName) {

	   
    var id="image["+idx+"]";
	var canvasElement = document.getElementById(id);
	var MIME_TYPE = "image/jpg";
    var imgURL = canvasElement.src;
    var dlLink = document.createElement('a');
    
    dlLink.download = fileName;
    dlLink.href = imgURL;
    dlLink.dataset.downloadurl = [MIME_TYPE, dlLink.download, dlLink.href].join(':');

    document.body.appendChild(dlLink);
    dlLink.click();
    document.body.removeChild(dlLink);
} */

function downloadImage(idx,fileName) {

	   
    var id="image["+idx+"]";
	var canvasElement = document.getElementById(id);
	var MIME_TYPE = "image/jpg";
    var imgURL = canvasElement.src;
    var dlLink = document.createElement('a');
    
    dlLink.download = fileName;
    dlLink.href = imgURL;
    dlLink.dataset.downloadurl = [MIME_TYPE, dlLink.download, dlLink.href].join(':');

    document.body.appendChild(dlLink);
    dlLink.click();
    document.body.removeChild(dlLink);
}


</script>