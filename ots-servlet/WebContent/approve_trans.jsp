<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ page import="javax.sql.*" %>
<html>
<head>
	<title>Approve Transaction</title>
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
			    
			    $.get("http://localhost:9090/ots-servlet/accept",{"param1": data});
     
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

// Remember to change the next line with your own environment 
String url= 
"jdbc:mysql://localhost:3306/oil";
String id= "root";
String pass = "cool3392";
try{

Class.forName ("com.mysql.jdbc.Driver").newInstance ();
con = java.sql.DriverManager.getConnection(url, id, pass);

}catch(ClassNotFoundException cnfex){
out.print("Ertt;kjjh");
cnfex.printStackTrace();

}

//String sql = "SELECT date,t.id transaction_id,t.quantity,(CASE WHEN buy_sell like 'b%' THEN \"Bought\" ELSE \"Sold\" END) As Buy_Sell ,\r\nt.cost_of_transaction,h.com_charge, (CASE WHEN comtype <> 0 THEN 'Cash' ELSE 'Oil' END) As Comission_Mode,\r\n(CASE WHEN h.settled_flag = 1 THEN 'Applied for settlement' ELSE 'Client_has_Not_Yet_Applied_for_settlement' END) As Settlement\r\n ,h.client_id client_id FROM transaction t, client_trader_transaction_history h \r\nwhere t.id=h.transaction_id ";
String sql = "SELECT Transid,Oil_amt, Trans_date,(CASE WHEN Trans_type like '0' THEN \"Bought\" ELSE \"Sold\" END) As Buy_Sell,\r\n(CASE WHEN dues_settled like 'Y' THEN 'Applied for settlement' ELSE 'Not_settled' END) As Settlement\r\n ,Cid FROM transaction where Status='Pending'";
		
try{
pst = con.prepareStatement(sql);
//pst.setInt(1, Integer.valueOf(request.getSession().getAttribute("Client_name").toString()));
rs = pst.executeQuery();
%>
<div class="navbar-header pull-right">
	<form action="logout" method="get">
 		<input type="submit" value="logout" >
 	</form>
	<a href="trader.jsp" role="button" class="btn btn-primary btn-large" style="float: right; padding: 5px 10px 5px 10px;">Home</a>
</div>

<div class="container-fluid">
<br>
<br>
<table class="table table-bordered table-condensed table-stripped">
<tr>
   
   <th>Transaction ID</th>
   <th>Quantity</th>
   <th>Date</th>
<!--    <th>Commission</th> -->
   <th>Bought_Or_Sold</th>
   <th>Settled_Or_NotSettled</th>
   <th>Client_id</th>
   <th>Accept/Reject</th>
</tr>

<%
while( rs!=null && rs.next() ){
%>
<tr>

	<td class="c1"><%= rs.getInt(1) %></td>
	<td class="c2"><%= rs.getDouble(2) %></td>
	<td class="c3"><%= rs.getDate(3) %></td>
<%-- 	<td class="c4"><%= rs.getDouble(4) %></td> --%>
	<td class="c4"><%= rs.getString(4) %></td>
	<td class="c5"><%= rs.getString(5) %></td>
	<td class="c6"><%= rs.getInt(6) %></td>
	
	<td>
	<button id='1-<%=rs.getInt(1)%>' class="use-address" name="accept" onclick="butfunc(this); disableFunction(<%=rs.getInt(1)%>); changecolgreen(<%=rs.getInt(1)%>);">Accept</button>     
	<br/>
	<button id='2-<%=rs.getInt(1)%>' class="use-address" name="reject" onclick="butfunc(this); disableFunction(<%=rs.getInt(1)%>); changecolred(<%=rs.getInt(1)%>);">Reject</button>
	</td>

</tr>
<%
}
%>
<%
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