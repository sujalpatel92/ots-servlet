<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%><%@ page import="javax.sql.*" %>
<html>
<head><title>Enter to database</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	
</head>
<body>
<div class="navbar-header pull-right">
	<form action="logout" method="get">
 		<input type="submit" value="logout" >
 	</form>
	<a href="client_home.jsp" role="button" class="btn btn-primary btn-large" style="float: right; padding: 5px 10px 5px 10px;">Home</a>
</div>
<%@ page import="java.util.*" %>
<%@ page import="javax.sql.*" %>
<%

java.sql.Connection con;
java.sql.Statement s;
java.sql.ResultSet rs;
java.sql.PreparedStatement pst;

con=null;
s=null;
pst=null;
rs=null;

HttpSession s1 = request.getSession();
String client_id= s1.getAttribute("User").toString();

// Remember to change the next line with your own environment 
String url= 
"jdbc:mysql://localhost:3306/oil";
String id= "root";
String pass = "cool3392";
try{

Class.forName ("com.mysql.jdbc.Driver").newInstance ();
con = java.sql.DriverManager.getConnection(url, id, pass);

}catch(ClassNotFoundException cnf){
out.print("Error on mysql - ask admin 1800-(oil)-(tran)");
cnf.printStackTrace();
}


String sql = "SELECT t.Trans_date, t.Transid, t.Oil_amt,(CASE WHEN t.Trans_type like '0' THEN \"Bought\" ELSE \"Sold\" END), h.Commision_amount, (CASE WHEN h.commision_type <> 1 THEN 'Cash' ELSE 'Oil' END), t.Status FROM transaction t, commision h where t.Transid=h.Transid and Cid=?";
try{
pst = con.prepareStatement(sql);
pst.setInt(1, Integer.valueOf(request.getSession().getAttribute("User").toString()));
rs = pst.executeQuery();
%>

<div class="container-fluid">
<br>
<br>
<table class="table table-bordered table-condensed table-stripped">
<tr>
   
   <th>Date</th>
   <th>Transaction ID</th>
   <th>Quantity</th>
   <th>Bought_Or_Sold</th>
<!--    <th>Cost</th> -->
   <th>Commission</th>
   <th>Comission_Mode</th>
   <th>Accepted/Rejected</th>
</tr>

<%
while( rs!=null && rs.next() ){
%>
<tr>

	<td><%= rs.getDate(1) %></td>
	<td><%= rs.getInt(2) %></td>
	<td><%= rs.getDouble(3) %></td>
	<td><%= rs.getString(4) %></td>
<%-- 	<td><%= rs.getDouble(5) %></td> --%>
	<td><%=(float)rs.getDouble(5) %></td>
	<td><%= rs.getString(6) %></td>
	<td><%= rs.getString(7) %></td>

</tr>
<%
}
%>
<%

}
catch(Exception e){
	
}

finally{
if(rs!=null) rs.close();
if(s!=null) s.close();
if(con!=null) con.close();
}
%>
</body>
</html>