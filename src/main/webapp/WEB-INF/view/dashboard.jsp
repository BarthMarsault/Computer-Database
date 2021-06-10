<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


    
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
        </div>
    </header>

	
	
	 
    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                <c:out value = "${nbComputer}" /> Computers found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
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
                            Computer name
                            <a href="?orderBy=name" >
							<i class="fa fa-fw fa-sort"></i></a>
                        </th>
                        <th>
                            Introduced date
                            <a href="?orderBy=introduced" >
							<i class="fa fa-fw fa-sort"></i></a>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                            <a href="?orderBy=discontinued" >
							<i class="fa fa-fw fa-sort"></i></a>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
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
					    <!--<li><a href="p-${ var }">${ var }</a></li> -->
					    <li><a href="<c:url value="dashboard"> <c:param name="pageRequest" value="${var}"/></c:url>">
					    	<c:if test="${var == pageNumber}">
					    		<strong>
					    	</c:if>
					    	${var}
					    	<c:if test="${var == pageNumber}">
					    		</strong>
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