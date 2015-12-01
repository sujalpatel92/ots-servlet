package com.oil.utd.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Accept_reject
 */
@WebServlet("/accept")
public class Approve_Trans extends HttpServlet {

    int accept;
    int transaction_id;
    int client_id;

    
    int trans_id;
    
    double quantity;

    String bought_sold;

    String settled;
    double c_qty;
    double c_cash;
    
    String acc_query = "";
    String rej_query = "";
    String update_client_query = "update client set Oil_owned=? where Cid=?";
    String client_oil_current = "select Oil_owned from client where Cid=?";
    public Approve_Trans() {
        super();
       
    }

	
	@SuppressWarnings("resource")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("request :"+ request.getParameter("param1").toString());
		String data = request.getParameter("param1").toString();
		String[] values = data.split("@");
		System.out.println("Client Id: ID "+ values[7]);
		
		String[] accs = values[0].split("-");
		accept = Integer.valueOf(accs[0]);
		//ts = Timestamp.valueOf(values[1]);
		transaction_id = Integer.valueOf(values[1]);
		quantity = Double.valueOf(values[2]);
		bought_sold = values[5];
		settled = values[6];
		client_id = Integer.valueOf(values[7]);
		double client_oilbal = 0;
		Connection con = (Connection) getServletContext().getAttribute("DBConnection");
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(client_oil_current);
			ps.setInt(1, client_id);
			rs = ps.executeQuery();
			if(rs!=null && rs.next()){
				client_oilbal = rs.getDouble(1);
				//client_cashbal = rs.getDouble(2);
				
			}	
				
			System.out.println("Woo hoo hurray!!!!!! 7878");
			
		} catch (SQLException e) {
			System.out.println("error: lookhere!!!");
			e.printStackTrace();
		}
		
		
		if(accept == 1){
			//accept
			System.out.println("Inside accept");
			if(bought_sold.equals("Bought")){
				//Buy
				int comm_type = 0;
				PreparedStatement ps1 = null;
				try {
					ps1 = con.prepareStatement("select commision_type from commision where Transid=?");
				} catch (SQLException e4) {
					// TODO Auto-generated catch block
					e4.printStackTrace();
				}
				try {
					ps1.setInt(1, transaction_id);
				} catch (SQLException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				ResultSet rs1;
				try {
					rs1=ps1.executeQuery();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					if(rs!=null && rs.next())
						try {
							comm_type=rs.getInt(1);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(comm_type==0){
					c_qty = client_oilbal + quantity;
					//c_cash = client_cashbal - (transactioncost+commissioncharge);
					
					
					try {
						ps = con.prepareStatement(update_client_query);
						ps.setDouble(1, c_qty);
						ps.setInt(2, client_id);
						if(!ps.execute()){
							System.out.println("Updated successfully");
							
						}
						//accepted status//
						ps = con.prepareStatement("update transaction set Status = 'Approved' where Transid =?");
						ps.setInt(1, transaction_id);
						if(ps.execute()){
							System.out.println("Could not accpect-- some error check soon");
						}
						
						
					} catch (SQLException e) {
						System.out.println("Error here!!");
						e.printStackTrace();
					}
					
					System.out.println("New values that will be updated "+ c_qty+ " cash"+c_cash);
					response.sendRedirect("trader.jsp");
					
				}
				else{
					
					c_qty = client_oilbal + quantity;
					
					//c_cash = client_cashbal - transactioncost;
					System.out.println("buy commision oil category here");

					try {
						ps = con.prepareStatement(update_client_query);
						ps.setDouble(1, c_qty);
						ps.setInt(2, client_id);
						if(!ps.execute()){
							System.out.println("Updated successfully");
						}
						
						ps = con.prepareStatement("update transaction set Status = 'Approved' where Transid =?");
						ps.setInt(1, transaction_id);
						if(ps.execute()){
							System.out.println("Could not accpect-- some error check soon");
						}
						
					} catch (SQLException e) {
						System.out.println("Error here!!");
						e.printStackTrace();
					}
					
					System.out.println("Here is the new values that will be updated"+ c_qty+ "cash"+c_cash);
					response.sendRedirect("trader.jsp");
					
				}
				
			}
			else{
				//Sell
				int comm_type = 0;
				PreparedStatement ps1 = null;
				try {
					ps1 = con.prepareStatement("select commision_type from commision where Transid=?");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					ps1.setInt(1, transaction_id);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ResultSet rs1;
				try {
					rs1=ps1.executeQuery();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					if(rs!=null && rs.next())
						comm_type=rs.getInt(1);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(comm_type == 0){
					c_qty = client_oilbal - quantity;
					//c_cash = client_cashbal + (transactioncost - commissioncharge);
					
					System.out.println("sell cash commission");

					try {
						ps = con.prepareStatement(update_client_query);
						ps.setDouble(1, c_qty);
						ps.setInt(2, client_id);
						if(!ps.execute()){
							System.out.println("Updated successfully");
							
						}
						//accepted status//
						ps = con.prepareStatement("update transaction set Status = 'Approved' where Transid =?");
						ps.setInt(1, transaction_id);
						if(ps.execute()){
							System.out.println("Could not accpect-- some error check soon");
						}
						
						
					} catch (SQLException e) {
						System.out.println("Error here!!");
						e.printStackTrace();
					}
					
					System.out.println("New values that will be updated "+ c_qty+ " cash"+c_cash);
					response.sendRedirect("trader.jsp");
				}
				else{
					c_qty = client_oilbal + quantity;
					
					//c_cash = client_cashbal - transactioncost;
					System.out.println("buy commision oil category here");

					try {
						ps = con.prepareStatement(update_client_query);
						ps.setDouble(1, c_qty);
						ps.setInt(2, client_id);
						if(!ps.execute()){
							System.out.println("Updated successfully");
						}
						
						ps = con.prepareStatement("update transaction set Status = 'Approved' where Transid =?");
						ps.setInt(1, transaction_id);
						if(ps.execute()){
							System.out.println("Could not accpect-- some error check soon");
						}
						
					} catch (SQLException e) {
						System.out.println("Error here!!");
						e.printStackTrace();
					}
					
					System.out.println("Here is the new values that will be updated"+ c_qty+ "cash"+c_cash);
					response.sendRedirect("trader.jsp");
					
				}
				
			}
			
		}
		else{
			
			//reject
			System.out.println("Rejection here");
			try {
				ps = con.prepareStatement("update transaction set Status = 'Rejected' where Transid =?");
				ps.setInt(1, transaction_id);
				if(ps.execute()){
					System.out.println("Could not accpect-- some error check soon");
				}
			} catch (SQLException e) {
				System.out.println("Inside reject exception");
				e.printStackTrace();
			}
			
			
		}
		
		
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
