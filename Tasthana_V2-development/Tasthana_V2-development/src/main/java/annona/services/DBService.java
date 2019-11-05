package annona.services;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DBService {

    public void createDB(String dbName) {
        // Defines the JDBC URL. As you can see, we are not specifying
        // the database name in the URL.
        String url = "jdbc:postgresql://localhost:5432/";

        // Defines username and password to connect to database server.
        String username = "postgres";
        String password = "1234";

        // SQL command to create a database in MySQL.
        String sql = "CREATE DATABASE " +  dbName;
        //String sql = "CREATE SCHEMA IF NOT EXISTS " +  dbName;

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void restoreDB(String dbName) throws IOException, InterruptedException
    {
    	Runtime r = Runtime.getRuntime();
    	Process p;
    	ProcessBuilder pb;
    	r = Runtime.getRuntime();
//    	pb = new ProcessBuilder( 
//    	    "C:\\Program Files\\PostgreSQL\\9.4\\bin\\pg_restore.exe",
//    	    "--host", "localhost",
//    	    "--port", "5432",
//    	    "--username", "postgres",
//    	    "--dbname", dbName,
//    	    "--role", "postgres",
//    	    "--no-password",
//    	    "--verbose",
//    	   "C:\\Users\\Annona\\Desktop\\DBBackup\\TasthanaNew.backup");
    	
    	pb = new ProcessBuilder( 
    			    "C:\\Program Files\\PostgreSQL\\9.4\\bin\\pg_restore.exe",
    			    "--host", "localhost",
    			    "--port", "5432",
    			    "--username", "postgres",
    			    "--dbname", dbName,
    			    "--password","1234",
    			    "C://Users//Annona//Desktop//DBBackup//asthanaNew.backup");
    	
 
    			    
    	pb.redirectErrorStream(true);
    	p = pb.start();
    	p.waitFor();
    	InputStream is = p.getInputStream();
    	InputStreamReader isr = new InputStreamReader(is);
    	BufferedReader br = new BufferedReader(isr);
    	String ll;
    	while ((ll = br.readLine()) != null) {
    	 System.out.println(ll);
    	}      
    	
    	 System.out.println("Restoration done");
    }
    
}
