/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turkeylab;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Akanksha
 */
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadFile extends HttpServlet {
        private static final long serialVersionUID = 1L;
        private final String UPLOAD_DIRECTORY = "/usr/local/tomcat7/webapps/TurkeyLab/Buffer/";
//private final String UPLOAD_DIRECTORY = "C:/Users/amitabh/Documents/NetBeansProjects/TurkeyLab/TurkeyLab/web/Buffer/";
        protected void doPost(HttpServletRequest request,
             HttpServletResponse response) throws ServletException, IOException {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        // process only if it is multipart content
        if (isMultipart) {
                // Create a factory for disk-based file items
                FileItemFactory factory = new DiskFileItemFactory();

                // Create a new file upload handler
                ServletFileUpload upload = new ServletFileUpload(factory);
                try {
                // Parse the request
                List<FileItem> multiparts = upload.parseRequest(request);

                for (FileItem item : multiparts) {
                if (!item.isFormField()) {
                String name = new File(item.getName()).getName();
                System.out.println(UPLOAD_DIRECTORY + name);
                item.write(new File(UPLOAD_DIRECTORY + name));
                System.out.println(UPLOAD_DIRECTORY+name);
                }
                }
                } 
                catch (Exception e) 
                {
                  e.printStackTrace();
                }
        }
}
}