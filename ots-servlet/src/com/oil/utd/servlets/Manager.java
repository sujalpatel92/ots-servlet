package com.oil.utd.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

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
 * Servlet implementation class Manager
 */
@WebServlet("/manager")
public class Manager extends HttpServlet {
	String mgr_query = "SELECT * FROM transaction WHERE (Trans_date >= ? AND Trans_date <= ?)";
	Date fromdate;
	Date todate;
	String daily_summary = "SELECT Trans_date, SUM(Cash_paid),SUM(Oil_amt), SUM(Cash_owed) FROM transaction WHERE (Trans_date >= ? AND Trans_date <= ?) GROUP BY Trans_date";
	String weekly_summary = "select week(Trans_date) ,sum(Cash_paid),sum(Oil_amt), sum(Cash_owed) from transaction where (Trans_date >= ? AND Trans_date <= ?) group by week(Trans_date)";
	String monthly_summary = "select month(Trans_date),sum(Cash_paid),sum(Oil_amt), sum(Cash_owed) from transaction where (Trans_date >= ? AND Trans_date <= ?) group by month(Trans_date)";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Manager() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int roleInp = Integer.parseInt(request.getParameter("myradio"));
		try {
			
			
			Connection con1 = (Connection) getServletContext().getAttribute("DBConnection");

			PreparedStatement ps = null;
			ResultSet rs = null;
			
			@SuppressWarnings("unused")
			HttpSession session = request.getSession();
			
			String frm = request.getParameter("fromdate").toString();
			String to = request.getParameter("todate").toString();
			
			fromdate = Date.valueOf(frm);
			todate = Date.valueOf(to);
			
//			switch(roleInp)
//			{
//				case 0:
//					ps = con1.prepareStatement(daily_summary);
//					ps.setDate(1, fromdate);
//					ps.setDate(2, todate);
//					rs = ps.executeQuery();
//					System.out.println("Daily summary"+ rs.toString());
//					break;
//				case 1:
//					ps = con1.prepareStatement(weekly_summary);
//					ps.setDate(1, fromdate);
//					ps.setDate(2, todate);
//					rs = ps.executeQuery();
//					System.out.println("weekly summary"+ rs.toString());
//					break;
//				case 2:
//					ps = con1.prepareStatement(monthly_summary);
//					ps.setDate(1, fromdate);
//					ps.setDate(2, todate);
//					rs = ps.executeQuery();
//					System.out.println("monthly summary"+ rs.toString());
//					break;
//			}
//			
//			Result res = ResultSupport.toResult(rs);
//			request.setAttribute("summary", res);
//			
			ps = con1.prepareStatement(mgr_query);
			ps.setDate(1, fromdate);
			ps.setDate(2, todate);
			rs = ps.executeQuery();
			System.out.println("Result"+ rs.toString());
			
			Result result = ResultSupport.toResult(rs);
	        request.setAttribute("result", result);
	        RequestDispatcher rd = request.getRequestDispatcher("/manager_main.jsp");
	        rd.forward(request, response);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
