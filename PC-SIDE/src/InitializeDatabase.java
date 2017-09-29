
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



public class InitializeDatabase implements Runnable{

	@Override
	public void run() {
		
		try{
			  Class.forName("com.mysql.jdbc.Driver");

		      System.out.println("Connecting to database...");
		      TelemetryData.getInstance().listLog.addElement("Connecting to database...");
		      
		      TelemetryData.getInstance().conn = DriverManager.getConnection(TelemetryData.getInstance().DB_URL,TelemetryData.getInstance().USER,TelemetryData.getInstance().PASS);

		      System.out.println("Creating statement...");
		      TelemetryData.getInstance().listLog.addElement("Creating statement...");
		      
		      TelemetryData.getInstance().stmt = TelemetryData.getInstance().conn.createStatement();
		      
		      
		      System.out.println("Database created successfully...");
		      TelemetryData.getInstance().listLog.addElement("Database created successfully...");
		      
		      
		      this.InitQuery();
		      
		      
		   }catch(SQLException se){
		      //Handle errors for JDBC
			   
			   TelemetryData.getInstance().listLog.addElement(se.toString());
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
			   
			   TelemetryData.getInstance().listLog.addElement(e.getMessage());
		      e.printStackTrace();
		   }finally{
			   
		      //finally block used to close resources
		      try{
		         if(TelemetryData.getInstance().stmt!=null)
		            ((Connection) TelemetryData.getInstance().stmt).close();
		      }catch(SQLException se2){
		    	  TelemetryData.getInstance().listLog.addElement(se2.getMessage());
		      }// nothing we can do
		      try{
		         if(TelemetryData.getInstance().conn!=null)
		        	 TelemetryData.getInstance().conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		         TelemetryData.getInstance().listLog.addElement(se.getMessage());
		      }//end finally try
		   }//end try
		
	}
	
	private void InitQuery() throws SQLException{
		
		PreparedStatement query = TelemetryData.getInstance().conn .prepareStatement("INSERT INTO Sessione ( Nome , Data  ) VALUES ( ? , ?)",PreparedStatement.RETURN_GENERATED_KEYS);
	    query.setString(1, TelemetryData.getInstance().nomeSessione);
	    query.setDate(2, new java.sql.Date(date.getTime()));
	    query.execute();
	    
	    ResultSet rs = query.getGeneratedKeys();
	    if(rs.next())
        {
	    	TelemetryData.getInstance().idSessione = rs.getInt(1);
        }
        
        
        System.out.println("IDDDDD " + TelemetryData.getInstance().idSessione);

	}
	
	

}
