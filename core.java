import java.sql.*;
import java.util.*;

import javax.management.Query;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
public class core{

    public static void main(String []args)throws SQLException{
        //Scanner sc = new Scanner(System.in);
        System.out.println("Starting Script");
        System.out.println("----------------------------------");
        System.out.println("Attempting Connection to DB");
        System.out.println("Creating user Profile");
       // System.out.print("Username : ");
        user u = new user();
        u.user = "SA";
        u.pass = "abcd.1234";
        u.dbname = "S";
        u.url = "jdbc:sqlserver://localhost:1433;" ;
        System.out.println(u.url);

        //u.user = sc.next();
        //System.out.println(u.user);
       // System.out.print("Password :");
       // u.pass = sc.next();
       // sc.close();
        connectToDb(u.url,u.user,u.pass);
        System.out.println("Suceeded !");
        System.out.println("----------------------------------");
       // System.out.println("Printing Table Metadata");
      //  printMetaData(u.url,u.user,u.pass);
        System.out.println("Querying the Database");
        querie("Select exid,conceptid,peso,course From MegExerciseConcept where course='ACDC'" , u.url, u.user, u.pass);
        System.out.println("Suceeded !");
        System.out.println("----------------------------------");
        System.out.println("Creating player class");
        System.out.println("Evaluating Database Size");
      
        System.out.println("Populating Player Class");
        player_db p = populatePlayerClass(u.url,u.user,u.pass);

        System.out.println("Suceeded !");
        System.out.println("----------------------------------");
        System.out.println("Building Answer Database for each user");
        player_answer_db(p,u.url,u.user,u.pass);
        System.out.println("Success!");
        System.out.println("----------------------------------");
        System.out.println("Building Generating User scores");
        p.printDB_score();
        System.out.println("Building Highscore");

        


    }
    
    public static void connectToDb(String url,String user, String pass)throws SQLException{
        Connection con = DriverManager.getConnection(url,user,pass);   
        try {
            
        } catch (Exception SQLException) {
            System.out.println("Erro de ligação à base de dados");
            //TODO: handle exception
        }
    
    }

    public  static void printMetaData(String url, String user, String pass)throws SQLException{//prints contentfrom database
         Connection con = DriverManager.getConnection(url,user,pass);   
        DatabaseMetaData meta = con.getMetaData();
        ResultSet rs = meta.getTables(null, null, "%", null);
        while(rs.next()){
            System.out.println(rs.getString(3));
        }
        rs.close();
    }
    public static void querie(String queries,String url,String user, String pass)throws SQLException{//queries the database and returns data 
        Connection con = DriverManager.getConnection(url,user,pass);   
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(queries);
        while(rs.next()){
            System.out.print(rs.getString("Course"));
            System.out.print(" ");
            System.out.print(rs.getString("ConceptID"));  
            System.out.print(" ");    
            System.out.print(rs.getString("exid"));
            System.out.println(" ");

        }
    }

    public static player_db populatePlayerClass(String url,String user,String pass)throws SQLException{
       int size = evaluateTableSize(url,user,pass,"UserProfiles");
        player_db p = new player_db(size);
        Connection con = DriverManager.getConnection(url,user,pass);
        Statement st = con.createStatement();

        //first sweep directly from user Profile Table
        ResultSet rs = st.executeQuery("select UserID,login,FirstName,LastName from dbo.UserProfiles where Course = 'AC'");
        while(rs.next()){
            //System.out.println("size=> "+p.size());
            //System.out.println("index=> "+p.idx());
            p.addPlayer(rs.getString("FirstName"), rs.getString("LastName"),Integer.parseInt(rs.getString("UserID")), rs.getString("login"));
        }
        //second sweep matching megua exercise answer database with userporfile database
        //for students that started using SIACUA's learning platfrom from other course

        rs.close();

        ResultSet rd = st.executeQuery("Select login from MegUserAnswer where Course='ACDC' or Course='ACAC' or Course='ACtrans'");
       
        while(rd.next()){
            String login = rd.getString("login");
           // System.out.println(login);
            //System.out.println(p.loginPos(p.idx()));

            if(!p.exists(login)){           
                break;
            }
            else{
            ResultSet rf = st.executeQuery("select UserID,login,FirstName,LastName from dbo.UserProfiles where login = '"+login+"'");
            //System.out.println("select UserID,login,FirstName,LastName from dbo.UserProfiles where login='"+login+"'");
            rf.next();
             p.addPlayer(rf.getString("FirstName"), rf.getString("LastName"),Integer.parseInt(rf.getString("UserID")), rf.getString("login"));
            }
         }
        p.printDB();




        return p;
    }



    public static void player_answer_db(player_db p,String url, String user,String pass)throws SQLException{
        Connection con = DriverManager.getConnection(url,user,pass);   
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select muaid,exid,login,correct from MegUserAnswer where Course = 'ACDC' or Course ='ACAC'");
        int num_answ = evaluateTableSize(url, user, pass,"MegUserAnswer");
        while(rs.next()){
            int tmpidx = p.findLogin(rs.getString("login"));
            //System.out.println(tmpidx);
            p.addAnswer(Integer.parseInt(rs.getString("muaid")),Integer.parseInt(rs.getString("exid")),Integer.parseInt(rs.getString("correct")),tmpidx);
          //  System.out.println(rs.getString("login"));
        }
    }
    public static int evaluateTableSize(String url, String user,String pass, String table)throws SQLException{//prints contentfrom database
         Connection con = DriverManager.getConnection(url,user,pass);   
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("Select login from" + " "+table);
        int size = 0;
        while(rs.next()){
           size++;
        }
        return size;
    }


}
class user{
String user,pass,url,dbname;
}