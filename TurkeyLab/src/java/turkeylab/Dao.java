/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turkeylab;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author akanksha
 */
public class Dao {

    Connection con = null;

    public Dao() {
        try {
            Class.forName(Util.DRIVER);
            if (con == null) {
                con = DriverManager.getConnection(Util.DB_URL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String upload(Bird data) throws SQLException, IOException {
         String str = "/usr/local/tomcat7/webapps/TurkeyLab/Saved";
        //String str = "C:/Users/amitabh/Documents/NetBeansProjects/TurkeyLab/TurkeyLab/web/Saved";
        String var = data.getFile();
        var = var.substring(var.lastIndexOf("fakepath") + 9);
        System.out.println("Calling Upload Helper");
        uploadhelper(var);
        PreparedStatement ps = null, cs = null;
        ResultSet rs = null;
        String msg = "";
         
        if("Select Comments".equals(data.getComments()))
            data.setComments(" ");
        
        String qry = "select count(*) as rows from bird_data where collor_ID=" + data.getCollor_ID();
         System.out.println(data.getComments());
        try {
            ps = con.prepareStatement(qry);
            rs = ps.executeQuery();
            int row = 0;
            String q = "";
            while (rs.next()) {
                if ("1".equals(rs.getString("rows"))) {
                    row = 1;
                }

            }
            ps.close();
            if (row == 0) {
                if (null != data.getRorf()) 
                    switch (data.getRorf()) {
                    case "finalized":
                        q = "INSERT INTO bird_data (collor_ID,student_name,state,site,gender,age,comments,addcomm,file) values (?,?,?,?,?,?,?,?,?)";
                        break;
                    case "raw":
                         q="INSERT INTO bird_data (collor_ID,student_name,state,site,gender,age,comments,addcomm,raw_file) values (?,?,?,?,?,?,?,?,?)";
                        break;
                    case "gdf":
                        q="INSERT INTO bird_data (collor_ID,student_name,state,site,gender,age,comments,addcomm,gdf_file) values (?,?,?,?,?,?,?,?,?)";
                        break;
                    default:
                        break;
                }
                cs = con.prepareStatement(q);
                cs.setString(1, data.getCollor_ID());
                cs.setString(2, data.getStudent_name());
                cs.setString(3, data.getState());
                cs.setString(4, data.getSite());
                cs.setString(5, data.getGender());
                cs.setString(6, data.getAge());
                cs.setString(7, data.getComments());
                cs.setString(8, data.getAddcomm());
                cs.setString(9, var);
               
                cs.executeUpdate();
            } else if (row == 1) {
                if (null != data.getRorf()) 
                    switch (data.getRorf()) {
                    case "finalized":
                         q = "UPDATE turkeylab.bird_data SET student_name=? , state=? , site=? , gender=?, age=?, comments=CONCAT(comments,' ',?), addcomm=CONCAT(addcomm,' ',?), file=? WHERE collor_ID=?";
                        break;
                    case "raw":
                         q = "UPDATE turkeylab.bird_data SET student_name=? , state=? , site=? , gender=?, age=?, comments=CONCAT(comments,' ',?), addcomm=CONCAT(addcomm,' ',?), raw_file=? WHERE collor_ID=?";
                        break;
                    case "gdf":
                         q = "UPDATE turkeylab.bird_data SET student_name=? , state=? , site=? , gender=?, age=?, comments=CONCAT(comments,' ',?), addcomm=CONCAT(addcomm,' ',?), gdf_file=? WHERE collor_ID=?";
                        break;
                    default:
                        break;
                }
                cs = con.prepareStatement(q);

                cs.setString(1, data.getStudent_name());
                cs.setString(2, data.getState());
                cs.setString(3, data.getSite());
                cs.setString(4, data.getGender());
                cs.setString(5, data.getAge());
                cs.setString(6, data.getComments());
                cs.setString(7, data.getAddcomm());
                cs.setString(8, var);
                cs.setString(9, data.getCollor_ID());
                cs.executeUpdate();
            }
            cs.close();
            msg = "success";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "failed";

        }
        return msg;

    }

    public List<Bird> retrieveAllBirds() {
        Bird bird = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String qry = "select * from bird_data";

        List<Bird> list = new ArrayList();
        try {
            ps = con.prepareStatement(qry);
            rs = ps.executeQuery();
            while (rs.next()) {
                bird = new Bird();
                bird.setCollor_ID(rs.getString("collor_ID"));
                bird.setStudent_name(rs.getString("student_name"));
                bird.setState(rs.getString("state"));
                bird.setSite(rs.getString("site"));
                bird.setGender(rs.getString("gender"));
                bird.setAge(rs.getString("age"));
                bird.setComments(rs.getString("comments"));
                bird.setAddcomm(rs.getString("addcomm"));
                StringBuilder sb = new StringBuilder("");
                if (rs.getString("file") != null && !"".equals(rs.getString("file"))) {
                    sb.append("*#finalized:*#").append(rs.getString("file"));
                }
                if (rs.getString("raw_file") != null && !"".equals(rs.getString("raw_file"))) {
                    sb.append("*#raw#*").append(rs.getString("raw_file"));
                }
                if (rs.getString("gdf_file") != null && !"".equals(rs.getString("gdf_file"))) {
                    sb.append("*#gdf#*").append(rs.getString("gdf_file"));
                }
                System.out.println(sb);
                bird.setFile(sb.toString());
                list.add(bird);
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            bird = null;
        }
        System.out.println(list.size());
        return list;
    }

    public List<Bird> retrieveBird(String ID) {
        // System.out.println("the collor id is : " + ID);
        Bird bird = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String qry = "select * from bird_data where collor_ID = '" + ID + "'";

        List<Bird> list = new ArrayList();
        try {
            ps = con.prepareStatement(qry);
            // ps.setString(1, ID);
            System.out.println("ps:  " + ps);
            rs = ps.executeQuery();
            while (rs.next()) {
                bird = new Bird();
                bird.setCollor_ID(rs.getString("collor_ID"));
                bird.setStudent_name(rs.getString("student_name"));
                bird.setState(rs.getString("state"));
                bird.setSite(rs.getString("site"));
                bird.setGender(rs.getString("gender"));
                bird.setAge(rs.getString("age"));
                bird.setComments(rs.getString("comments"));
                bird.setAddcomm(rs.getString("addcomm"));
                StringBuilder sb = new StringBuilder("");
                if (rs.getString("file") != null && !"".equals(rs.getString("file"))) {
                    sb.append("*#finalized:*#").append(rs.getString("file"));
                }
                if (rs.getString("raw_file") != null && !"".equals(rs.getString("raw_file"))) {
                    sb.append("*#raw#*").append(rs.getString("raw_file"));
                }
                if (rs.getString("gdf_file") != null && !"".equals(rs.getString("gdf_file"))) {
                    sb.append("*#gdf#*").append(rs.getString("gdf_file"));
                }
                bird.setFile(sb.toString());
                list.add(bird);
            }
            ps.close();
        } catch (SQLException e) {
            bird = null;
        }
        return list;
    }

    public List<Bird> retrieveMultipleBird(String state, String site, String gender, String age, String comments, String rorf) {
        // System.out.println("the collor id is : " + ID);
        Bird bird = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String qry = "select * from bird_data where  ";
        StringBuilder sb = new StringBuilder("");
        if (!"".equals(state)) {
            sb.append("state='").append(state).append("' ");
        }

        if (!"".equals(site)) {
            if (!"".equals(state)) {
                sb.append("AND ");
            }
            sb.append(" site='").append(site).append("' ");
        }

        if (!"".equals(gender)) {
            if (!"".equals(state) || !"".equals(site)) {
                sb.append("AND ");
            }
            sb.append(" gender='").append(gender).append("' ");
        }

        if (!"".equals(age)) {
            if (!"".equals(state) || !"".equals(site) || !"".equals(gender)) {
                sb.append("AND ");
            }
            sb.append(" age='").append(age).append("' ");
        }

        if (!"".equals(comments)) {
            if (!"".equals(state) || !"".equals(site) || !"".equals(gender) || !"".equals(age)) {
                sb.append("AND ");
            }
            sb.append(" comments LIKE'%").append(comments).append("%' ");
        }

        qry += sb.toString();
        System.out.println("qry" + qry);
        List<Bird> list = new ArrayList();
        try {
            ps = con.prepareStatement(qry);
            // ps.setString(1, ID);
            System.out.println("ps:  " + ps);
            rs = ps.executeQuery();
            while (rs.next()) {
                bird = new Bird();
                bird.setCollor_ID(rs.getString("collor_ID"));
                bird.setStudent_name(rs.getString("student_name"));
                bird.setState(rs.getString("state"));
                bird.setSite(rs.getString("site"));
                bird.setGender(rs.getString("gender"));
                bird.setAge(rs.getString("age"));
                bird.setComments(rs.getString("comments"));
                bird.setAddcomm(rs.getString("addcomm"));
                StringBuilder sbc = new StringBuilder("");
                if (rs.getString("file") != null && !"".equals(rs.getString("file"))) {
                    sbc.append("*#finalized:*#").append(rs.getString("file"));
                }
                if (rs.getString("raw_file") != null && !"".equals(rs.getString("raw_file"))) {
                    sbc.append("*#raw#*").append(rs.getString("raw_file"));
                }
                if (rs.getString("gdf_file") != null && !"".equals(rs.getString("gdf_file"))) {
                    sbc.append("*#gdf#*").append(rs.getString("gdf_file"));
                }
                bird.setFile(sbc.toString());
                list.add(bird);
            }
            ps.close();
        } catch (SQLException e) {
            bird = null;
        }
        return list;
    }

    public void uploadhelper(String var) {
         String str = "/usr/local/tomcat7/webapps/TurkeyLab/Saved/";
        //String str = "C:/Users/amitabh/Documents/NetBeansProjects/TurkeyLab/TurkeyLab/web/Saved/";
        str += var;
        try {
            Files.deleteIfExists(Paths.get(str));
        } catch (NoSuchFileException e) {
            System.out.println("No such file/directory exists");
        } catch (DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty.");
        } catch (IOException e) {
            System.out.println("Invalid permissions.");
        }
        System.out.println("Deletion successful.");

         File source = new File("/usr/local/tomcat7/webapps/TurkeyLab/Buffer/" + var); //"Buffer\\"
        //File source = new File("C:/Users/amitabh/Documents/NetBeansProjects/TurkeyLab/TurkeyLab/web/Buffer/" + var);
        File dest = new File(str);

        try {
            FileUtils.copyFile(source, dest);
            System.out.println("Copy Sucessfull");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
             Files.deleteIfExists(Paths.get("/usr/local/tomcat7/webapps/TurkeyLab/Buffer/" + var));
           // Files.deleteIfExists(Paths.get("C:/Users/amitabh/Documents/NetBeansProjects/TurkeyLab/TurkeyLab/web/Buffer/" + var));
        } catch (NoSuchFileException e) {
            System.out.println("No such file/directory exists");
        } catch (DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty.");
        } catch (IOException e) {
            System.out.println("Invalid permissions.");
        }
        System.out.println("Deletion successful.");

    }
}
