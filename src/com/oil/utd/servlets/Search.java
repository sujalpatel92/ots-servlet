package com.oil.utd.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

/**
 * Servlet implementation class Search
 */
@WebServlet("/search")
public class Search extends HttpServlet {
	
	int cid;
	String cadd;
	String czip;
	int choiceid;


	String searchByID = "SELECT * FROM client  WHERE Cid=?";
	String searchByZip = "SELECT * FROM client WHERE Zip=?";
	String searchByAddress = "SELECT * FROM client WHERE Street_addr LIKE ?";
	
	public Search() {
		super();
	
	}

	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Hi");
	
		System.out.println("Data received frm search JSP: Choice: "
				+ request.getParameter("searchby") + "\tClientID: "
				+ request.getParameter("cid") + "\tAddress: "
				+ request.getParameter("caddress").toString() + "\tZipcode: "
				+ request.getParameter("czip"));
			
			choiceid = Integer.parseInt(request.getParameter("searchby").toString());
			
			System.out.println("Choice: "+ choiceid);
			
			
		  try {
		  
		  Connection con1 = (Connection) getServletContext().getAttribute("DBConnection");
		  
		  PreparedStatement ps = null; ResultSet rs = null;
		  
		  @SuppressWarnings("unused")
		  HttpSession session = request.getSession();
		  
			System.out.println("Choice ID:"+choiceid);	
			
		  if ( choiceid == 1) { 
			  
			  cid = Integer.valueOf(request.getParameter("cid").toString());
			  System.out.println("Entered 1");
			  ps = con1.prepareStatement(searchByID); 
			  ps.setInt(1, cid);
			  rs = ps.executeQuery();
			  
		  System.out.println("Result: " + rs.toString());
		  
		  Result result = ResultSupport.toResult(rs);
		  request.setAttribute("result", result); 
		  RequestDispatcher rd = request .getRequestDispatcher("/search.jsp"); 
		  rd.forward(request, response);
		  
		  } else if ( choiceid == 2) { 
			  cadd = request.getParameter("caddress").toString();
			  System.out.println("entering 2");
			  ps = con1.prepareStatement(searchByAddress);
			  ps.setString(1, "%" +cadd +"%");
			  System.out.println("%" +cadd +"%");
			  
			  rs = ps.executeQuery();
			  System.out.println(cadd);

		  System.out.println("Result" + rs.toString());
		  
		  Result result = ResultSupport.toResult(rs);
		  request.setAttribute("result", result); 
		  
	
		  System.out.println("Result is null, do something");
		  
		  if(rs!=null && rs.next())
		  {
			  System.out.println(rs.getString(2));
		  }
		  RequestDispatcher rd = request.getRequestDispatcher("/search.jsp");
		  rd.forward(request,response);
		  
		  } else if ( choiceid == 3) { 
			  
				czip = request.getParameter("czip").toString();
			  System.out.println("entering 3");
			  ps = con1.prepareStatement(searchByZip); 
			  ps.setInt(1,Integer.valueOf(czip));
			  rs = ps.executeQuery();
		  
		  System.out.println("Result" + rs.toString());
		  
		  Result result = ResultSupport.toResult(rs);
		  request.setAttribute("result", result); RequestDispatcher rd =
		  request .getRequestDispatcher("/search.jsp"); rd.forward(request,
		  response);
		  
		  }
		  
		  
		  } catch (Exception e) {
		  
		  e.printStackTrace(); }
		 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
