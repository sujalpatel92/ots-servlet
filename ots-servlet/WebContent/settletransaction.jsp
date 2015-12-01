<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ page import="javax.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Settle Transaction</title>
</head>
<body>
<%@ page import="javax.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>

<%
String name1 = session.getAttribute("User").toString();
int client_id1 = Integer.valueOf(name1);
out.println("<p>Client id here is:"+client_id1+"</p>");

%>
<%
    String name = session.getAttribute("User").toString();
	int client_id = Integer.valueOf(name);
	String query = "select SUM(Cash_owed), SUM(Cash_paid) from transaction where Cid=";
   
    
%>


<sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
     url="jdbc:mysql://localhost:3306/oil"
     user="root"  password="cool3392"/>
     
     
     <sql:query dataSource="${snapshot}" var="result">
		query		
	</sql:query>
	
	<table border="1" width="100%">
<tr>
   <th>Client Id</th>
   <th>Trader ID</th>
   <th>Transaction ID</th>
   <th>Date</th>
   <th>cost of transaction</th>
   <th>commission</th>
</tr>
<c:forEach var="row" items="${result.rows}">
<tr>
   <td><c:out value="${row.client_id}"/></td>
   <td><c:out value="${row.trader_id}"/></td>
   <td><c:out value="${row.transaction_id}"/></td>
   <td><c:out value="${row.date}"/></td>
   <td><c:out value="${row.cost_of_transaction}"/></td>
   <td><c:out value="${row.com_charge}"/></td>
</tr>
</c:forEach>
</table>
</body>
</html>