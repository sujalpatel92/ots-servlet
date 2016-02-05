package com.oil.utd.servlet.listener;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.oil.utd.util.DatabaseManager;
 
@WebListener
public class ContextListenerServlet implements ServletContextListener {
 
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext sContext = servletContextEvent.getServletContext();
         
        //initialize DB Connection
        String dbURL = sContext.getInitParameter("dbURL");
        String user = sContext.getInitParameter("dbUser");
        String pwd = sContext.getInitParameter("dbPassword");
         
        try {
            DatabaseManager connectionManager = new DatabaseManager(dbURL, user, pwd);
            sContext.setAttribute("DBConnection", connectionManager.getConnection());
            System.out.println("Database connection initialized successfully.");
        } 
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
         
       
       
    }
 
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Connection con = (Connection) servletContextEvent.getServletContext().getAttribute("DBConnection");
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     
}