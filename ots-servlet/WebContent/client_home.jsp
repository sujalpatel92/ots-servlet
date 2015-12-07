<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>Client</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	
	</head>
 <br>
 
 <div class="navbar-header pull-right">
 <form action="logout" method="get">
 <input type="submit" value="logout" >
 </form>
 </div>
 <div class="container">
	<div class="row">
        <div class="col-sm-15">
            <legend>Welcome 
           <%
           		HttpSession s1 = request.getSession();
				String userName = s1.getAttribute("User").toString();
				String oil_bal = s1.getAttribute("oil_balance").toString();
				String cname = s1.getAttribute("Client_name").toString();
				String cash_owed = s1.getAttribute("cash_left").toString();
				String com_level = s1.getAttribute("com_level").toString();
				out.println(cname);
			%>


<!-- //out.println("<input type='text' value='"+cname+"'>");%> -->
            </legend> 
       
        <!-- panel preview -->
        <div class="col-sm-6">
            <h4>Summary :</h4>
            <div class="panel panel-default col-sm-10">
                <div class="panel-body form-horizontal payment-form">
						
					<div class="form-group">
                        
						<a href="transaction.jsp" role="button" class="btn btn-primary btn-large">Make a Transaction</a>
						<a href="settletransaction.jsp" role="button" class="btn btn-primary btn-large">   Settle Dues  </a>
						<a href="viewstatus.jsp" role="button" class="btn btn-primary ">View Status</a>
						
						<br>
						
						<br>
                        <!-- <h4 id=" status"> Account Type :</h4> -->
						<h4 id=" oilbal">Your Oil Balance   	 :  <% out.print(oil_bal); %> </h4>
						<h4 id=" cashbal">Your Cash Owed         :  <% out.print(cash_owed); %> </h4>
						<h4 id=" comlvl">Your Commission Level   :  <% out.print(com_level); %> </h4>
                   </div>
               </div>
            </div>            
        </div> 
        </div>
      </div>
</div>

</html>
