package com.oil.utd.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Accept_reject
 */
@WebServlet("/accept")
public class Approve_Trans extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int accept;
    int transaction_id;
    int client_id;
    int trader_id;
    double quantity;
    String bought_sold;
    String settled;
    double c_qty;

    String update_client_query = "update client set Oil_owned=? where Cid=?";
    String client_oil_current = "select Oil_owned from client where Cid=?";
    
    public Approve_Trans() {
        super(); 
    }
	
	@SuppressWarnings({ "resource", "unused" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("request :"+ request.getParameter("param1").toString());
		String data = request.getParameter("param1").toString();
		String[] values = data.split("@");
		System.out.println("Client Id: ID "+ values[6]);
		
		String[] str1 = values[0].split("-");
		accept = Integer.valueOf(str1[0]);
		transaction_id = Integer.valueOf(values[1]);
		quantity = Double.valueOf(values[2]);
		bought_sold = values[4];
		settled = values[5];
		client_id = Integer.valueOf(values[6]);
		double client_oilbal = 0;
		HttpSession session = request.getSession();
		trader_id = Integer.valueOf(session.getAttribute("User").toString());
		
		Connection con = (Connection) getServletContext().getAttribute("DBConnection");
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			ps = con.prepareStatement(client_oil_current);
			ps.setInt(1, client_id);
			rs = ps.executeQuery();
			if(rs!=null && rs.next()){
				client_oilbal = rs.getDouble(1);
			}	
				
			System.out.println("Logging in Approve_trans - 1");
			
		} catch (SQLException e) {
			
			System.out.println("Error: lookhere!!!");
			e.printStackTrace();
		}
		
		if(accept == 1){
			//Accept section
			System.out.println("Inside accept");
			if(bought_sold.equals("Bought")){
				//Buy
				int comm_type = 0;
				PreparedStatement ps1 = null;
				ResultSet rs1 = null;
				try {
					ps1 = con.prepareStatement("select commision_type from commision where Transid=?");
					ps1.setInt(1, transaction_id);
					rs1 = ps1.executeQuery();
					if(rs!=null && rs.next())
						comm_type=rs.getInt(1);
				}
				catch (SQLException e4) {
					System.out.println("Error: lookhere!!! 2");
					e4.printStackTrace();
				}
				
				if(comm_type==0){
					//Cash commission
					c_qty = client_oilbal + quantity;
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
						
						ps = con.prepareStatement("update transaction set Trader_id = ? where Transid =?");
						ps.setInt(1, trader_id);
						ps.setInt(2, transaction_id);
						if(ps.execute()){
							System.out.println("Could not accpect-- some error check soon");
						}
						
//						Update client set Level = 'Gold' where level='Silver' and Cid in (select cid from transaction where Status='Approved' group by cid, month(Trans_date) having sum(oil_amt)>30);
						
						ps = con.prepareStatement("Update client set Level = 'Gold' where Level='Silver' and Cid in (select Cid from transaction where Status='Approved' group by cid, month(Trans_date) having sum(oil_amt)>30)");
//						ps.setInt(1, trader_id);
//						ps.setInt(2, transaction_id);
						if(ps.execute()){
							System.out.println("Could not accpect-- some error check soon");
						}
						
						
					}
					catch (SQLException e) {
						System.out.println("Error here!!");
						e.printStackTrace();
					}
					
					System.out.println("New values that will be updated "+ c_qty);
					response.sendRedirect("trader.jsp");
				}
				else{
				//Oil Commission	
					c_qty = client_oilbal + quantity;
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
						ps = con.prepareStatement("update transaction set Trader_id = ? where Transid =?");
						ps.setInt(1, trader_id);
						ps.setInt(2, transaction_id);
						if(ps.execute()){
							System.out.println("Could not accpect-- some error check soon");
						}
						
						ps = con.prepareStatement("Update client set Level = 'Gold' where Level='Silver' and Cid in (select Cid from transaction where Status='Approved' group by cid, month(Trans_date) having sum(oil_amt)>30)");
//						ps.setInt(1, trader_id);
//						ps.setInt(2, transaction_id);
						if(ps.execute()){
							System.out.println("Could not accpect-- some error check soon");
						}
						
					}
					catch (SQLException e) {
						System.out.println("Error here!!");
						e.printStackTrace();
					}
					
					System.out.println("Here is the new values that will be updated"+ c_qty);
					response.sendRedirect("trader.jsp");
				}
			}
			else{
				//Sell
				int comm_type = 0;
				PreparedStatement ps1 = null;
				ResultSet rs1;
				try {
					ps1 = con.prepareStatement("select commision_type from commision where Transid=?");
					ps1.setInt(1, transaction_id);
					rs1=ps1.executeQuery();
					if(rs!=null && rs.next())
						comm_type=rs.getInt(1);
				}
				catch (SQLException e1) {
					e1.printStackTrace();
				}
				if(comm_type == 0){
					//cash commission
					c_qty = client_oilbal - quantity;
					System.out.println("sell cash commission");
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
						ps = con.prepareStatement("update transaction set Trader_id = ? where Transid =?");
						ps.setInt(1, trader_id);
						ps.setInt(2, transaction_id);
						if(ps.execute()){
							System.out.println("Could not accpect-- some error check soon");
						}
						
						ps = con.prepareStatement("Update client set Level = 'Gold' where Level='Silver' and Cid in (select Cid from transaction where Status='Approved' group by cid, month(Trans_date) having sum(oil_amt)>30)");
//						ps.setInt(1, trader_id);
//						ps.setInt(2, transaction_id);
						if(ps.execute()){
							System.out.println("Could not accpect-- some error check soon");
						}
						
					}
					catch (SQLException e) {
						System.out.println("Error here!!");
						e.printStackTrace();
					}
					System.out.println("New values that will be updated "+ c_qty);
					response.sendRedirect("trader.jsp");
				}
				else{
					//oil commission
					c_qty = client_oilbal - quantity;
					System.out.println("sell commision oil category here");
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
						ps = con.prepareStatement("update transaction set Trader_id = ? where Transid =?");
						ps.setInt(1, trader_id);
						ps.setInt(2, transaction_id);
						if(ps.execute()){
							System.out.println("Could not accpect-- some error check soon");
						}
						
						ps = con.prepareStatement("Update client set Level = 'Gold' where Level='Silver' and Cid in (select Cid from transaction where Status='Approved' group by cid, month(Trans_date) having sum(oil_amt)>30)");
//						ps.setInt(1, trader_id);
//						ps.setInt(2, transaction_id);
						if(ps.execute()){
							System.out.println("Could not accpect-- some error check soon");
						}
						
					}
					catch (SQLException e) {
						System.out.println("Error here!!");
						e.printStackTrace();
					}
					
					System.out.println("Here is the new values that will be updated"+ c_qty);
					response.sendRedirect("trader.jsp");
				}
			}
		}
		else{
			//Rejection section
			
			double oil_qty = 0;
			System.out.println("Rejection here");
			try {
				//Get the amount of commission  already deducted and adjust the amount of oil accordingly
				ps = con.prepareStatement("select commision_type,Commision_amount from commision where Transid=?");
				ps.setInt(1, transaction_id);
				rs = ps.executeQuery();
				if(rs!=null && rs.next())
				{
					if(rs.getInt(1) == 1)
					{
						System.out.println("Reject oil commission settlement ");
						oil_qty = quantity + rs.getDouble(2);
					}
				}
				//update the transaction table
				ps = con.prepareStatement("update transaction set Status = 'Rejected' where Transid =?");
				ps.setInt(1, transaction_id);
				if(ps.execute()){
					System.out.println("Could not accpect-- some error check soon");
				}
				//Reset the oil amount to original order
				ps = con.prepareStatement("update transaction set Oil_amt = ? where Transid =?");
				ps.setDouble(1, oil_qty);
				ps.setInt(2, transaction_id);
				if(!ps.execute())
					System.out.println("oil updated successfully");
				
				//Delete the commission table entry
				ps = con.prepareStatement("delete from commision where Transid =?");
				ps.setInt(1, transaction_id);
				if(!ps.execute())
					System.out.println("Commsion deleted");
				
				//add trader identity who performed this action
				ps = con.prepareStatement("update transaction set Trader_id = ? where Transid =?");
				ps.setInt(1, trader_id);
				ps.setInt(2, transaction_id);
				if(ps.execute()){
					System.out.println("Could not accpect-- some error check soon");
				}
				
				//mark the dues settled flag to appropriate value
				ps = con.prepareStatement("update transaction set dues_settled = 'Y' where Trader_id = ? and Transid =?");
				ps.setInt(1, trader_id);
				ps.setInt(2, transaction_id);
				if(ps.execute()){
					System.out.println("Could not accpect-- some error check soon");
				}
				
			}
			catch (SQLException e) {
				System.out.println("Inside reject exception");
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
	}

}
