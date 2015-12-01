package com.oil.utd.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Statement;



@WebServlet("/client")
public class ClientServlet extends HttpServlet {
    
	String query1 = "select id,name from trader";
	String query2 = "select * from  inventory";
	String query3 = "select oilbalance,cashbalance from client where id=(?)";
	String query4 = "select category from belongsto where clientid=(?)";
	int tid;
	int commission_type;
   
    public ClientServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		try {
			
			PreparedStatement statement = null;
			ResultSet set = null;
			
			HttpSession session = request.getSession();
			
			
			int client_id = Integer.valueOf(session.getAttribute("User").toString());
			double client_oil_bal = Double.valueOf(session.getAttribute("oil_balance").toString());
			double ppb = Double.valueOf(session.getAttribute("ppb").toString());
			String qty = request.getParameter("quantity");
			
			String req = request.toString();
			System.out.println("Oil quanti "+ qty);
			
			int buyORsell;
			if(request.getParameter("buysell").equals("Buy")){
				buyORsell = 0;
			}
			else{ buyORsell = 1;}
			
			int quantity=Integer.valueOf(request.getParameter("quantity"));
			int commissionType;
			String c_type = request.getParameter("comissionstatus");
			if(c_type.equals("Cash")){
				commissionType = 0;
			}
			else{
				commissionType = 1;
			}
			//System.out.println(commission_type);
			
			
			
			
			String transactiontype= request.getParameter("transactiontype");
			
			//String tradername = request.getParameter("tradername");
			
			Transaction t = new Transaction();
			t.setBuyORSell(buyORsell);
			t.setQuantity(quantity);
			t.setCommissionType(commissionType);
			double cost_of_trans = (quantity*ppb);
			t.setTransCost(cost_of_trans);
			
			String updateCommissionQuery = "INSERT INTO commision (Transid, Commision_amount, Curr_dt_oil_price, commision_type) VALUES (?,?,?,?)";
			String updateTransQuery = "INSERT INTO transaction(Cid, Oil_amt, Trans_date, Cash_owed, Status, Trans_type, dues_settled) VALUES (?,?,?,?,?,?,?)";
			Connection con1 = (Connection) getServletContext().getAttribute("DBConnection");
			//System.out.println("am here!!");
			DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
		    Date dateobj = new Date();
		    System.out.println(df.format(dateobj));
		    if(buyORsell == 0)	{
			//check cash balance
			
		    	
		    	statement = con1.prepareStatement(updateTransQuery);
				statement.setInt(1, client_id);//client id
				System.out.println("Client is id "+client_id);
				
				statement.setInt(2, quantity);// oil_amt
				System.out.println("oil qty is: "+quantity);
				
				statement.setString(3, df.format(dateobj));//date
				
				//statement.setDouble(5, 0);//trader_id
				statement.setString(5, "Pending");
				statement.setDouble(6, 0);
				System.out.println("cost of trans is id "+cost_of_trans);
				
				if(c_type.equals("Cash")){
					statement.setDouble(4, cost_of_trans*.2);//com_charge
				System.out.println("comissin is id "+ cost_of_trans*.2);
				statement.setString(7, "N");
				}
				else{
					double rem_qty = quantity - quantity*0.2;
					statement.setDouble(2, rem_qty);//com_charge
					statement.setDouble(4, 0);
					System.out.println("comissin in liter"+ quantity*.2);
					statement.setString(7, "Y");
				}
				
				//insert into history
				if(statement.execute()){}
				else{
					System.out.println("Inserted into history");
				}
				PreparedStatement st = null;
				ResultSet rs;
				st = con1.prepareStatement("select MAX(Transid) as last_id from transaction");
				rs = st.executeQuery();
				String lastid = "9";
				if(rs.next())
					lastid = rs.getString("last_id");
				statement = con1.prepareStatement(updateCommissionQuery , Statement.RETURN_GENERATED_KEYS);
				statement.setInt(1, Integer.parseInt(lastid));
				if(c_type.equals("Cash")){
					statement.setDouble(2, cost_of_trans*.2);//com_charge
				}
				else{
					double rem_qty = quantity - quantity*0.2;
					statement.setDouble(2, rem_qty);//com_charge
				}
				statement.setDouble(3, ppb);
				statement.setInt(4, commissionType);
			
				if(statement.execute()){
										
					set = statement.getGeneratedKeys();
					if(set.next()){
					tid = set.getInt(1);
					System.out.println("aaa"+ tid);
					session.setAttribute("transaction_id", tid);
//					RequestDispatcher dispatcher = request.getRequestDispatcher("transaction.jsp");
//					request.setAttribute("total", String.valueOf(cost_of_trans)+"$");
//					dispatcher.forward( request, response );
					response.sendRedirect("client_home.jsp");
					
					}
			  	}
				response.sendRedirect("client_home.jsp");
				
					
					
				
				
		    }
		    else if(buyORsell==1){
			//check oil balance
			System.out.println("Woo Am selling oil!!");
			if(client_oil_bal >= quantity){
				
				statement = con1.prepareStatement(updateTransQuery);
				statement.setInt(1, client_id);//client id
				System.out.println("Client is id "+client_id);
				
				statement.setInt(2, quantity);// oil_amt
				System.out.println("oil qty is: "+quantity);
				
				statement.setString(3, df.format(dateobj));//date
				
				//statement.setDouble(5, 0);//trader_id
				statement.setString(5, "Pending");
				statement.setDouble(6, 1);
				System.out.println("cost of trans is id "+cost_of_trans);
				
				if(c_type.equals("Cash")){
					statement.setDouble(4, cost_of_trans*.2);//com_charge
				System.out.println("comission is id "+ cost_of_trans*.2);
				statement.setString(7, "N");
				}
				else{
					double rem_qty = quantity - quantity*0.2;
					statement.setDouble(2, rem_qty);//com_charge
					statement.setDouble(4, 0);
					System.out.println("comissin in liter"+ quantity*.2);
					statement.setString(7, "Y");
				}
				
				//insert into history
				if(statement.execute()){}
				else{
					System.out.println("Inserted into history");
				}
				
				PreparedStatement st1 = null;
				ResultSet rs1;
				st1 = con1.prepareStatement("select MAX(Transid) as last_id from transaction");
				rs1 = st1.executeQuery();
				String lastid1 = "9";
				if(rs1.next())
					lastid1 = rs1.getString("last_id");
				statement = con1.prepareStatement(updateCommissionQuery , Statement.RETURN_GENERATED_KEYS);
				statement.setInt(1, Integer.parseInt(lastid1));
				if(c_type.equals("Cash")){
					statement.setDouble(2, cost_of_trans*.2);//com_charge
				}
				else{
					double rem_qty = quantity - quantity*0.2;
					statement.setDouble(2, quantity*0.2);//com_charge
				}
				statement.setDouble(3, ppb);
				statement.setInt(4, commissionType);
			
				if(statement.execute()){
										
					set = statement.getGeneratedKeys();
					if(set.next()){
					tid = set.getInt(1);
					System.out.println("aaa"+ tid);
					session.setAttribute("transaction_id", tid);
//					RequestDispatcher dispatcher = request.getRequestDispatcher("transaction.jsp");
//					request.setAttribute("total", String.valueOf(cost_of_trans)+"$");
//					dispatcher.forward( request, response );
					response.sendRedirect("client_home.jsp");
					
					
					
					}
			  	}
				response.sendRedirect("client_home.jsp");
			}
			else{
				//not enough oil to sell
				System.out.println("Not enough Oil Balance to sell");
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/transaction.jsp");
				PrintWriter out = response.getWriter();
				out.println("<font size=\"6\" color=red>Not enough Oil Balance</font>");
				rd.include(request, response);
				
			}
			
			
		 }
		
		
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("Exception "+e);
		}
		
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		/*try {
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			HttpSession session = request.getSession();
			
			
			int client_id = Integer.valueOf(session.getAttribute("Client_name").toString());
			double client_cash_bal = Double.valueOf(session.getAttribute("cash_balance").toString());
			double client_oil_bal = Double.valueOf(session.getAttribute("oil_balance").toString());
			double ppb = Double.valueOf(session.getAttribute("ppb").toString());
			String quan = request.getParameter("quantity");
			String req = request.toString();
			System.out.println("Oil quanti "+ quan);
			
			String buysell;
			if(request.getParameter("buysell").equals("Buy")){
				buysell = "b";
			}
			else{ buysell = "s";}
			
			int quantity=Integer.valueOf(request.getParameter("quantity"));
			
			String c_type = request.getParameter("comissionstatus");
			if(c_type.equals("Cash")){
				commission_type = 1;
			}
			else{
				commission_type = 0;
			}
			//System.out.println(commission_type);
			
			
			
			String transactiontype= request.getParameter("transactiontype");
			String tradername = request.getParameter("tradername");
			
			Transaction t = new Transaction();
			t.setBuy_sell(buysell);
			t.setQuantity(quantity);
			t.setComtype(commission_type);
			double cost_of_trans = (quantity*ppb);
			t.setCost_transation(cost_of_trans);
			
			String ins_query = "INSERT INTO transaction (quantity, buy_sell, cost_of_transaction, commission_type) VALUES (?,?,?,?)";
			String ins_hist = "INSERT INTO client_trader_transaction_history(client_id, trader_id, transaction_id, date, cost_of_transaction, com_charge) VALUES (?,?,?,?,?,?)";
			Connection con1 = (Connection) getServletContext().getAttribute("DBConnection");
			//System.out.println("am here!!");
			
		if(buysell.equals("b"))	{
			//check cash balance
			if(client_cash_bal >= cost_of_trans){
			
			ps = con1.prepareStatement(ins_query , Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, quantity);
			ps.setString(2, buysell);
			ps.setDouble(3, cost_of_trans);
			ps.setInt(4, commission_type);
			
			
				if(ps.execute()){
					request.setAttribute("total", String.valueOf(cost_of_trans)+"additional commission charges as applicable");
					session.setAttribute("total", cost_of_trans);
					rs = ps.getGeneratedKeys();
					if(rs.next()){
						int tid = rs.getInt(1);
					}
			}else{
				
					rs = ps.getGeneratedKeys();
					if(rs.next()){
					tid = rs.getInt(1);
					System.out.println("aEminem!"+ tid);
					session.setAttribute("transaction_id", tid);
					RequestDispatcher dispatcher = request.getRequestDispatcher("transaction.jsp");
					request.setAttribute("total", String.valueOf(cost_of_trans)+" $ + Additional commission charges as applicable");
					dispatcher.forward( request, response );
					//if cah
					if(commission_type == 1){
						ps = con1.prepareStatement(ins_hist);
						ps.setInt(1, client_id);//cliendt id
						ps.setInt(2, tid);// trader id
						ps.setInt(3, x);//transaction_id
						ps.setDate(4, x);//date
						ps.setDouble(5, x);//cost_of transaction
						ps.setDouble(6, x);//com_charge
						
					}
					//oil
					else{
						
						
					}
					
					
					}
			  	}
			}
			
			
			else{
				
				System.out.println("Not enough cash balance- contact helpdesk");
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/transaction.jsp");
				PrintWriter out = response.getWriter();
				out.println("<font size=\"6\" color=red>Not enough cash balance- contact helpdesk : 1(800)-(oil)-(tran)</font>");
				rd.include(request, response);
				
			}
			
			
			
		}
		else if(buysell.equals("s")){
			//check oil balance
			if(client_oil_bal >= quantity){
				
				ps = con1.prepareStatement(ins_query , Statement.RETURN_GENERATED_KEYS);
				ps.setDouble(1, quantity);
				ps.setString(2, buysell);
				ps.setDouble(3, cost_of_trans);
				ps.setInt(4, commission_type);
				
				
					if(ps.execute()){
						
						rs = ps.getGeneratedKeys();
						if(rs.next()){
							int tid = rs.getInt(1);
							session.setAttribute("transaction_id", tid);
						}
				}else{
						
						rs = ps.getGeneratedKeys();
						if(rs.next()){
						tid = rs.getInt(1);
						session.setAttribute("transaction_id", tid);
						RequestDispatcher dispatcher = request.getRequestDispatcher("transaction.jsp");
						request.setAttribute("total", String.valueOf(cost_of_trans)+" $ additional commission charges as applicable");
						dispatcher.forward( request, response );
						
						}
				  	}
				
			}
			else{
				//not enough oil to sell
				System.out.println("Not enough Oil Balance to sell- contact helpdesk");
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/transaction.jsp");
				PrintWriter out = response.getWriter();
				out.println("<font size=\"6\" color=red>Not enough Oil Balance in your account - contact helpdesk : 1(800)-(oil)-(tran)</font>");
				rd.include(request, response);
				
			}
			
			
		 }
		
		
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("Exception "+e);
		}
		
		*/
		
	}


}
