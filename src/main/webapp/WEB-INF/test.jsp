
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.excilys.cdb.model.Computer"%>
<%@page import="com.excilys.cdb.persistence.ComputerDAO"%>





<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Super T</title>
</head>
<body>
	

    
    <p>~~~~~~~~~~~~~~~~~~~~~~~~</p>
    
    <table>
    	<c:forEach items="${computers}" var="co">
		    <tr>      
		        <td><c:out value = "${co.id}" /></td>
		        <td><c:out value = "${co.name}" /></td>
		    </tr>
		</c:forEach>

    
    </table>
</body>
</html>

