package com.oil.utd.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
/**
 * Servlet implementation class Settle
 */
@WebServlet("/settle")
public class Settle extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	int accept;
    int transaction_id;
    int client_id;
    int trans_id;
    int trader_id;
    //double quantity;
    //String bought_sold;
    String settled;
    //double c_qty;
    double cash;
    
    String acc_query = "";
    String rej_query = "";
    String settle_Xact = "update transaction set Cash_paid =? where Transid =?";
    String update_dues = "update transaction set dues_settled = 'Y' where Transid =?";
    
    //String get_cash_owed = "select Oil_owned from client where Cid=?";
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Settle() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("request :"+ request.getParameter("param1").toString());
		String data = request.getParameter("param1").toString();
		String[] values = data.split("@");
		System.out.println("Client Id: ID "+ values[2]);
		
		String[] str1 = values[0].split("-");
		accept = Integer.valueOf(str1[0]);
		transaction_id = Integer.valueOf(values[1]);
		client_id = Integer.valueOf(values[2]);
		trader_id = Integer.valueOf(values[5]);
		cash = Double.valueOf(values[4]);
		
		Connection con = (Connection) getServletContext().getAttribute("DBConnection");
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(settle_Xact);
			ps.setDouble(1, cash);
			//ps.setInt(2, client_id);
			ps.setInt(2, transaction_id);
			System.out.println(cash+" "+transaction_id+" to be updated");
			if(!ps.execute())
				System.out.println("Updated Successfully");
			
			ps = con.prepareStatement(update_dues);
			//ps.setDouble(1, cash);
			//ps.setInt(2, client_id);
			ps.setInt(1, transaction_id);
			System.out.println(cash+" dues  "+transaction_id+" to be updated");
			if(!ps.execute())
				System.out.println("Updated Successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Failed to settle dues, check error here-------");
			e.printStackTrace();
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
	}

}
