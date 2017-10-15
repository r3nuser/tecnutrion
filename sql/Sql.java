package sql;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Sql {

    public static Connection getConnection(String username, String password) {
        try {
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/visualnutrion";
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println(username + " Connected !");
            return con;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    public static void closeConnection(Connection con){
	try{    	
		if(con!=null){
		    con.close();
		}
	}catch(Exception e){
		System.out.println(e);	
	}
    }
    
    public static void closeConnection(Connection con, PreparedStatement stmt){
	closeConnection(con);
	try{    	
		if(stmt != null){
			stmt.close();
		}
	}catch(Exception e){
		System.out.println(e);	
	}
    }
	
    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs){
	closeConnection(con, stmt);
	try{    	
		if(rs != null){
			rs.close();
		}
	}catch(Exception e){
		System.out.println(e);	
	}
    }
	
}
