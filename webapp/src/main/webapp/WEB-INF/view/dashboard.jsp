<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
    
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
			<a class="navbar-toggler" href="?lang=en"><img src="static/flags/flag_uk.png"/></a>
			<a class="navbar-toggler" href="?lang=fr"><img src="static/flags/flag_fr.png"/></a>        	
        	<a class="pull-right" href="<c:url value="/logout" />"><span class="glyphicon glyphicon-log-in"></span> 
						<fmt:message key="label.logout" /></a>
        </div>
        
    </header>

	
	
	 
    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                <c:out value = "${nbComputer}" /> <fmt:message key="label.computerFound" />
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="<fmt:message key="label.search"/>" />
                        <input type="submit" id="searchsubmit" value="<fmt:message key="label.filter"/>"
                        class="btn btn-primary" />
                    </form>
                </div>
                <sec:authorize access="hasRole('admin')">
	                <div class="pull-right">
	                    <a class="btn btn-success" id="addComputer" href="addComputer"><fmt:message key="label.addComputer"/></a> 
	                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><fmt:message key="label.edit"/></a>
	                </div>
                </sec:authorize>
                
            </div>
        </div>

        <form id="deleteForm" action="#" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            <fmt:message key="label.computerName"/>
                            <a href="?orderBy=name" >
							<i class="fa fa-fw fa-sort"></i></a>
                        </th>
                        <th>
                            <fmt:message key="label.introducedDate"/>
                            <a href="?orderBy=introduced" >
							<i class="fa fa-fw fa-sort"></i></a>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            <fmt:message key="label.discontinuedDate"/>
                            <a href="?orderBy=discontinued" >
							<i class="fa fa-fw fa-sort"></i></a>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            <fmt:message key="label.company"/>
                            <a href="?orderBy=company" >
							<i class="fa fa-fw fa-sort"></i></a>
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
            
                <tbody id="results">
                 
                	<c:forEach items="${computers}" var="computer">
					    <tr>
                        	<td class="editMode">
                            	<input type="checkbox" name="cb" class="cb" value="${computer.id}"> 
                            </td>    
					        
					        <td><a href="<c:url value="editComputer"> <c:param name="requestId" value="${computer.id}"/></c:url>" onclick=""><c:out value = "${computer.name}" /></a></td>
					        
					        <td><c:out value = "${computer.introduced}" /></td>
					        <td><c:out value = "${computer.discontinued}" /></td>
					        <td><c:out value = "${computer.nameCompany}" /></td>
					    </tr>
					</c:forEach>  
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
        	<ul class="pagination">
           		<li>
	               	<!-- <a href="p-${pageNumber > 1 ? pageNumber - 1 : 1}" aria-label="Previous"> -->
	               	<a href="<c:url value="dashboard"> <c:param name="pageRequest" value="${pageNumber > 1 ? pageNumber - 1 : 1}"/></c:url>" aria-label="Previous">
	                	<span aria-hidden="true">&laquo;</span>
	                </a>
	              </li>
	              <!-- <li><a href="p-1"><c:out value = "1" /></a></li> -->
	              <li><a href="<c:url value="dashboard"> <c:param name="pageRequest" value="${1}"/></c:url>">${1}</a></li>
	              <li><a >...</a></li>
	              <c:forEach items="${pageProposition}" var="var">
					    <li><a href="<c:url value="dashboard"> <c:param name="pageRequest" value="${var}"/></c:url>">
					    	<c:if test="${var == pageNumber}">
					    		<strong></strong>
					    	</c:if>
					    	
					    	<c:if test="${var != pageNumber}">
					    		${var}
					    	</c:if>
					    </a></li>
				  </c:forEach>
	              <li><a >...</a></li>
	              <!-- <li><a href="p-${numberOfPage}"><c:out value = "${numberOfPage}" /></a></li> -->
	              <li><a href="<c:url value="dashboard"> <c:param name="pageRequest" value="${numberOfPage}"/></c:url>">${numberOfPage}</a></li>
	              <li>
	                <!-- <a href="p-${pageNumber < numberOfPage ? pageNumber+1 : pageNumber }" aria-label="Next"> -->
	                <a href="<c:url value="dashboard"> <c:param name="pageRequest" value="${pageNumber < numberOfPage ? pageNumber+1 : pageNumber}"/></c:url>" aria-label="Previous">
	                    <span aria-hidden="true">&raquo;</span>
	                </a>
      			</li>
        	</ul>

        <div class="btn-group btn-group-sm pull-right" role="group" >
	        <form action="" method="get">
	        	<button type="submit" class="btn btn-default" value="10" name="nbComputerByPage">10</button>
	            <button type="submit" class="btn btn-default" value="50" name="nbComputerByPage">50</button>
	            <button type="submit" class="btn btn-default" value="100" name="nbComputerByPage">100</button>
	            <button type="submit" class="btn btn-default" value="<c:out value = "${nbComputer}" />" name="nbComputerByPage"><c:out value = "${nbComputer}" /></button>  
	       		
	        </form>          
        </div>
		</div>
    </footer>
<script src="static/js/jquery.min.js"></script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/dashboard.js"></script>

</body>
</html>