/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import javax.servlet.*;  
import javax.servlet.http.*; 
import java.sql.*;
import java.util.logging.*;


@WebServlet(name="StorageServlet", urlPatterns={"/StorageServlet"})
public class StorageServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        ArrayList<String> products = (ArrayList<String>) session.getAttribute("products");
        if (products == null){
            products = new ArrayList<String>();
            session.setAttribute("products",products);
        }
        products.add(request.getParameter("product"));
        
        String users = "";
        Connection conn = DB.getInstance().getConnection();
        try{
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select * from user");
            while(rs.next()){
                users+=rs.getString("username") + " ";
            }
            rs.close();
            stm.close();
        }catch(SQLException ex){
            //Logger.getLogger(StorageServlet.class.getName().log(Level.SEVERE,null,ex));
        }
        DB.getInstance().putConnection(conn);
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StorageServlet</title>");  
            out.println("</head>");
            out.println("<body>");  
            out.println("<ul>");
            for(String s:products){
                out.println("<li>" + s + "</li>");
            }
            out.println("</ul>");
            out.println(users);
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
