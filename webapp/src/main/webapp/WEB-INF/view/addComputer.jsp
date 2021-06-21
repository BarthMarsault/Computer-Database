<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
        </div>
    </header>
	<fmt:message key="label.computerName" var="namePlaceHolder" />  
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><fmt:message key="label.addComputer"/></h1>
                    <form:form id="addComputerForm" action="addComputer" method="POST" modelAttribute="computer">     
                    	             	
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><fmt:message key="label.computerName"/></label>
                                <form:input path="name" name="computerNameValue" type="text" class="form-control" id="computerName" placeholder="${ namePlaceHolder }"/>
                            </div>
                            <div class="form-group">
                                <label for="introduced"><fmt:message key="label.introducedDate"/></label>
                                <form:input path="introduced" name="introducedValue" type="date" class="form-control" id="introduced" placeholder="Introduced date"/>
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><fmt:message key="label.discontinuedDate"/></label>
                                <form:input path="discontinued" name="discontinuedValue" type="date" class="form-control" id="discontinued" placeholder="Discontinued date"/>
                            </div>
                            <div class="form-group">
                                <label for="companyId"><fmt:message key="label.company"/></label>
                                <form:select path="idCompany" name="companyIdValue" class="form-control" id="companyId">
                                	<option value="0">--</option>
                                	<c:forEach items="${companies}" var="company">
									    <option value="${company.id}">${company.name}</option>
									</c:forEach>  
                                </form:select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input id="btn_submit" type="submit" value="<fmt:message key="label.add"/>" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default"><fmt:message key="label.cancel"/></a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>

<script src="static/js/jquery.min.js"></script>
<script src="static/js/jquery.validate.min.js"></script>
<script src="static/js/addComputer.js"></script>
</body>
</html>