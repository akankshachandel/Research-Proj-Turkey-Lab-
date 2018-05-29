/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turkeylab;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author amitabh
 */
public class UploadService extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UploadService</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UploadService at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       PrintWriter out = response.getWriter();
       
       Bird bird = new Bird();
       bird.setCollor_ID(request.getParameter("collor_id"));
       bird.setStudent_name(request.getParameter("student_name"));
       bird.setState(request.getParameter("state"));
       bird.setSite(request.getParameter("site"));
       bird.setGender(request.getParameter("gender"));
       bird.setAge(request.getParameter("age"));
       bird.setComments(request.getParameter("comments"));
       bird.setAddcomments(request.getParameter("other_comments"));
       bird.setRorf(request.getParameter("rorf"));
       bird.setFile(request.getParameter("file"));
       
       
       Dao dao=new Dao();
       Message msg= new Message();
       msg.setMsg("fail");
        try {
            String m = dao.upload(bird);
            if(m.equals("success")){
              msg.setMsg("success");   
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(UploadService.class.getName()).log(Level.SEVERE, null, ex);
        }
       String res = toJSONString(msg);
       out.println(res);
       out.close();
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


public String toJSONString(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyy-MM-dd'T'HH:mm:ss.SSS'Z'"); 								// UTC
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
    
    public Bird fromJSON(String string) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyy-MM-dd'T'HH:mm:ss.SSS'Z'"); 								// UTC
        Gson gson = gsonBuilder.create();
        
        return gson.fromJson(string,Bird.class);
    }

}
