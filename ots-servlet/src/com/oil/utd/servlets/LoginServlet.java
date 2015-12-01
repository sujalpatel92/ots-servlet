package com.oil.utd.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oil.utd.util.Login;

/**
 * Servlet implementation class LoginServlet
 */

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	int cID;
	int tID;
	String trader_name;
	Connection con = null;
    
	public LoginServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("user");
		String password = request.getParameter("pwd");
		int roleInp = Integer.parseInt(request.getParameter("myradio"));
		int role;
		System.out.println(request.getParameter("user") + " "
				+ request.getParameter("pwd")+ request.getParameter("myradio"));

		con = (Connection) getServletContext().getAttribute(
				"DBConnection");
		PreparedStatement statement1 = null;
		PreparedStatement statement2 = null;
		ResultSet set2 = null;
		ResultSet set1 = null;

		try {
			statement1 = con.prepareStatement("select uid, Password, role from auth where uid=? and Password=?");
			statement1.setString(1, userid);
			statement1.setString(2, password);
			set1 = statement1.executeQuery();

			if (set1 != null && set1.next()) {
				Login login = new Login();
				login.setUserID(set1.getString(1));
				role = set1.getInt(3);
				if(role!=roleInp)
				{
					RequestDispatcher rd1 = getServletContext().getRequestDispatcher("/index.jsp");
					PrintWriter out = response.getWriter();
					out.println("<font color=red>Incorrect role selected.Please try again.</font>");
					rd1.include(request, response);
				}
				login.setRoleType(set1.getInt(3));
				System.out.println("Logged User: " + login.toString()
						+ "Client id : ");
				
				HttpSession session = request.getSession(true);
				session.setAttribute("User", userid);
				
				if (role == 0) {
					//client
					
					System.out.println("here"+role);
					statement2 = con.prepareStatement("select First_name from client where Cid=?");
					statement2.setString(1, userid);
					set2 = statement2.executeQuery();
					if(set2!=null && set2.next()){
						//cID = Integer.parseInt(set2.getString(1));
						session.setAttribute("Client_name", set2.getString(1));
						System.out.println(set2.getString(1));
					}
					
			session.setAttribute("ppb", (double)63.0);
					
					String getCommissionRate = "select Level from client where Cid=(?)";
					statement1 = con.prepareStatement(getCommissionRate);
					statement1.setInt(1, Integer.parseInt(userid));
					set1 = statement1.executeQuery();
					if(set1!=null && set1.next()){
						if(set1.getString(1).equals("Gold"))
							session.setAttribute("com_rate", 0.10);
						else
							session.setAttribute("com_rate", 0.20);
					}
					
					
					
					String getOil = "select Oil_owned from client where Cid=(?) limit 1";
					statement1 = con.prepareStatement(getOil);
					statement1.setInt(1, Integer.parseInt(userid));
					set1 = statement1.executeQuery();
					if(set1!=null && set1.next()){
					 
					session.setAttribute("oil_balance", set1.getString(1));
					System.out.println("Oil bal "+set1.getString(1));
					//response.sendRedirect("home.jsp");
					response.sendRedirect("client_home.jsp");
					}
					
					
					
				} else if (role == 1) {
					//trader
					
					statement2 = con.prepareStatement("select Trader_name from trader where Trader_id=?");
					statement2.setString(1, userid);
					set2 = statement2.executeQuery();
					if(set2!=null && set2.next()){
						//tID = Integer.parseInt(set2.getString(1));
						session.setAttribute("trader_id", set2.getString(1));
						System.out.println(set2.getString(1));
					}
					response.sendRedirect("trader.jsp");
					
				}
				
				else if (role == 2) {
					//manager
					response.sendRedirect("manager_main.jsp");
				}
			} else {
				RequestDispatcher rd = getServletContext()
						.getRequestDispatcher("/index.jsp");
				PrintWriter out = response.getWriter();
				out.println("<font color=red>No user found with given id, please register first.</font>");
				rd.include(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServletException("DB Connection problem.");
		} finally {
			try {
				
				set1.close();
				statement1.close();
				//con.close();
			} catch (SQLException e) {
				System.out.println("ERrror Here");
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// System.out.println(request.getParameter("user")+" "+request.getParameter("pwd"));
	}

}
