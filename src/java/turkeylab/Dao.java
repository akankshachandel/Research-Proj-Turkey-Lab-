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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public String upload(Bird data) throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        String qry = "INSERT INTO bird_data (collor_ID,student_name,state,site,gender,age,comments,addcomm,rorf,file)"+ " VALUES(" + data.getCollor_ID() + ",'" + data.getStudent_name() + "','" + data.getState() + "','" + data.getSite() + "','"+ data.getGender()+"','"+data.getAge() + "','" + data.getComments() + "','"+data.getAddcomments() + "','" + data.getRorf() + "','" + data.getFile() + "')";

        String msg="";
        try {
            ps = con.prepareStatement(qry);
            ps.execute();
            ps.close();
            msg = "success";
        } catch (SQLException e) {
            msg = "fail";

        }
        return msg;
        
    }
    
    public List<Bird> retrieveAllRecords(){
        Bird usr=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String qry="select * from bird_data";
        List<Bird> usrList = new ArrayList();
        try{
            ps=con.prepareStatement(qry);
            rs=ps.executeQuery();
            while(rs.next()){
                usr=new Bird();
                usr.setCollor_ID(rs.getString("collor_ID"));
                usr.setStudent_name(rs.getString("student_name"));
                usr.setState(rs.getString("state"));
                usr.setSite(rs.getString("site"));
                usr.setGender(rs.getString("gender"));
                usr.setAge(rs.getString("age"));
                usr.setComments(rs.getString("comments"));
                usr.setAddcomments(rs.getString("addcomm"));
                usr.setRorf(rs.getString("rorf"));
                usr.setFile(rs.getString("file"));
                usrList.add(usr);
                }
            ps.close();            
        }catch(Exception e){
            e.printStackTrace();
            usr=null;
        }
     return usrList;
    }
    
    

}
