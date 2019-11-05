<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
        <div class="container-fluid">
   
<div class="fd-list">
			<div class="header_customer">	
				<h3>All Documents</h3>
			</div>
			
			<div class="fd-list-table">	
			
			<span class="counter pull-right"></span>
	
	<table class="host-list table-striped table-bordered" align="center" width="400" id="dataTable" style="margin-top:36px;">

			<thead>
				<tr>
<!-- 					<th>File</th> -->
					<th>File Name</th>
					<th>Action</th>

				</tr>
			</thead>
			<tbody>

				<c:forEach items="${fileFormList}" var="imagename"
					varStatus="status">
					<tr>
						<td style="display:none"><img class="profile-pic upload-button" width="100px"
							id="image[${status.index}]" src="${imagename.url}"></td>
							
						<td>${imagename.fileName}</td>
						<td><button type="button"
								onclick="downloadImage(${status.index},'${imagename.fileName}')"
								class="btn btn-primary">
								<i class="glyphicon glyphicon-download"></i>Download
							</button></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
			</div>

</div>



<script>

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
