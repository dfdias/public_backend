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
        System.out.println("Printing Table Metadata");
        printMetaData(u.url,u.user,u.pass);
        System.out.println("Querying the Database");
        querie("Select exid,conceptid,peso,course From MegExerciseConcept" , u.url, u.user, u.pass);
        System.out.println("Suceeded !");
        System.out.println("----------------------------------");
        System.out.println("Creating player class");
        System.out.println("Evaluating Database Size");
      
        System.out.println("Populating Player Class");
        player p[] = populatePlayerClass(u.url,u.user,u.pass);

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

    public static player[] populatePlayerClass(String url,String user,String pass)throws SQLException{
       int size = evaluateTableSize(url,user,pass,"UserProfiles");
        player p[] = new player[size];
        Connection con = DriverManager.getConnection(url,user,pass);
        Statement st = con.createStatement();

        //first sweep directly from user Profile Table
        ResultSet rs = st.executeQuery("select UserID,login,FirstName,LastName from dbo.UserProfiles where Course = 'AC'");
        int i = 0;
        while(rs.next()){
            player tmp = new player(); 
            tmp.userID = Integer.parseInt(rs.getString("UserID"));
            tmp.nick = rs.getString("login");
            tmp.stname = rs.getString("FirstName");
            tmp.ndname = rs.getString("LastName");
            p[i] = tmp;           
        }
        //second sweep matching megua exercise answer database with userporfile database
        //for students that started using SIACUA's learning platfrom from other course

        rs.close();

        ResultSet rd = st.executeQuery("Select login from MegUserAnswer where Course='ACDC' or Course='ACAC' or Course='ACtrans'");
       
        while(rd.next()){
         for(int j = 0; j<=p.length;j++){
             String login = rd.getString("login");
             System.out.println(login);
             System.out.println(p[j].nick);
            if(login.equals(p[j].nick)){           
                break;
            }
            else{
                Arrays.copyOf(p,p.length+1);
                ResultSet pm = st.executeQuery("select UserID,login,FirstName,LastName from dbo.UserProfiles where login='"+login+"'");
                player tmp2 = new player();
                tmp2.userID = Integer.parseInt(pm.getString("UserID"));
                tmp2.nick = pm.getString("login");
                tmp2.stname = pm.getString("FirstName");
                tmp2.ndname = pm.getString("LastName");     
                p[p.length-1] = tmp2;     
            }
         }
        }




        return p;
    }
    public static int evaluateTableSize(String url, String user,String pass, String table)throws SQLException{//prints contentfrom database
         Connection con = DriverManager.getConnection(url,user,pass);   
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("Select UserID from" + " "+table);
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