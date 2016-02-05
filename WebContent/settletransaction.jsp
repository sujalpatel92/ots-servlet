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
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script>
		function  butfunc(b){
			var $id = b.id+"@";
			//alert("Hhey"+ $id);
			var $row = $(b).closest("tr");    // Find the row
			var $text1 = $row.find(".c1").text()+"@"; // Find the text
			var $text2 = $row.find(".c2").text()+"@";
			var $text3 = $row.find(".c3").text()+"@";
			var $text4 = $row.find(".c4").text()+"@";
			var $text5 = $row.find(".c5").text()+"@";
			var $text6 = $row.find(".c6").text()+"@";
// 			    var $text7 = $row.find(".c7").text()+"@";
// 			    var $text8 = $row.find(".c8").text()+"@";
// 			    var $text9 = $row.find(".c9").text();
			var data = $id + $text1+ $text2+ $text3+ $text4+ $text5+ $text6;
			alert(data);
			$.get("http://localhost:9090/ots-servlet/settle",{"param1": data});
		}
			
	</script>
	<script>
		function disableFunction(obj) {
			document.getElementById("1-"+obj).disabled = 'true';
			document.getElementById("2-"+obj).disabled = 'true';	           
			//document.getElementById("1-"+obj).style.backgroundColor = '#5cb85c';
		}
		function changecolgreen(obj){
			document.getElementById("1-"+obj).style.backgroundColor = '#5cb85c';
		}
		function changecolred(obj){
	    	 document.getElementById("2-"+obj).style.backgroundColor = '#ff0001';
	    }
	</script>
</head>
<body>
<%@ page import="javax.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>

<%
	HttpSession s1 = request.getSession();
	String userName = s1.getAttribute("User").toString();
	String oil_bal = s1.getAttribute("oil_balance").toString();
	String cname = s1.getAttribute("Client_name").toString();

	java.sql.Connection con;
	java.sql.Statement s;
	java.sql.ResultSet rs;
	java.sql.PreparedStatement pst;

	con=null;
	s=null;
	pst=null;
	rs=null;

	String url= "jdbc:mysql://localhost:3306/oil";
	String id= "root";
	String pass = "cool3392";

	try{
		Class.forName ("com.mysql.jdbc.Driver").newInstance ();
		con = java.sql.DriverManager.getConnection(url, id, pass);
	}catch(ClassNotFoundException cnfex){
		out.print("Ertt;kjjh");
		cnfex.printStackTrace();
	}

	String sql="SELECT Transid, Cid, Trans_date, Cash_owed, Trader_id,(CASE WHEN Trans_type like '0' THEN \"Bought\" ELSE \"Sold\" END) As Buy_Sell FROM transaction transaction where dues_settled='N' and Cid=?";

	try{
		pst = con.prepareStatement(sql);
		pst.setInt(1, Integer.valueOf(userName));
		rs = pst.executeQuery();
%>
	<div class="navbar-header pull-right">
		<form action="logout" method="get">
 			<input type="submit" value="logout" >
 		</form>
 		<a href="client_home.jsp" class="btn btn-primary btn-large" style="float: right; padding: 5px 10px 5px 10px;">Home</a>
	</div>
	<div class="container">
		<div class="row">
        	<div class="col-sm-15">
            	<legend>Welcome 
           		<%out.println(cname);%>
				</legend>
			</div>
		</div>
	</div>
	<div class="container-fluid">
	<br/>
	<br/>
	<table class="table table-bordered table-condensed table-stripped">
		<tr>
			<th>Transaction ID</th>
			<th>Client ID</th>
			<th>Date</th>
			<th>Cash Owed</th>
			<th>Trader ID</th>
			<th>Transaction Type</th>
<!-- 			<th>Client_id</th> -->
			<th>Settle/Not Settle</th>
		</tr>
<%
		while( rs!=null && rs.next() ){
%>
		<tr>
			<td class="c1"><%= rs.getInt(1) %></td>
			<td class="c2"><%= rs.getInt(2) %></td>
			<td class="c3"><%= rs.getDate(3) %></td>
			<td class="c4"><%= rs.getDouble(4) %></td>
			<td class="c5"><%= rs.getInt(5) %></td>
			<td class="c6"><%= rs.getString(6) %></td>
<%-- 			<td class="c7"><%= rs.getString(7) %></td> --%>
			<td>
				<button id='1-<%=rs.getInt(1)%>' class="use-address" name="settle" onclick="butfunc(this); disableFunction(<%=rs.getInt(1)%>); changecolgreen(<%=rs.getInt(1)%>);">Settle</button>     
				<br/>
<%-- 				<button id='2-<%=rs.getInt(1)%>' class="use-address" name="reject" onclick="butfunc(this); disableFunction(<%=rs.getInt(1)%>); changecolred(<%=rs.getInt(1)%>);">Not Settle</button> --%>
			</td>
		</tr>
<%
		}
	}
	catch(Exception e){e.printStackTrace();}
	finally{
		if(rs!=null) rs.close();
		if(s!=null) s.close();
		if(con!=null) con.close();
	}
%>
	</table>
	</div>
</body>
</html>