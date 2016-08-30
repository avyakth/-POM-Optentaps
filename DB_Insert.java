package db;

// import from sql
import java.sql.*;

public class DB_Insert {

	// JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost:3306/bugs";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "root";
   
   public static void main(String[] args) {
	   
	   Connection conn = null;
	   Statement stmt = null;
	   
	   try{
	   
		   //STEP 2: Register JDBC driver
	      Class.forName(JDBC_DRIVER);
	     
	      //STEP 3: Open a connection
	      conn = DriverManager.getConnection(DB_URL,USER,PASS);
	
	      //STEP 4: Execute a query
	      stmt = conn.createStatement();
	      // Insert, Delete and Update
	      String sql = "INSERT INTO Bugs.Priority(id,value,sortkey,isActive) VALUES(11,'checkNEW',1001,1)";
	      stmt.executeUpdate(sql);
	
     	      //STEP 5: Clean-up environment
	      stmt.close();
	      conn.close();
	      
	   }catch(SQLException se){
	      
		   //Handle errors for JDBC
	      se.printStackTrace();
	      
	   }catch(Exception e){
	      
		   //Handle errors for Class.forName
	      e.printStackTrace();
	      
	   }finally{
	     
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se){
	    	  se.printStackTrace();
	      }
	      
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }
	   }
	   
   
   }
}