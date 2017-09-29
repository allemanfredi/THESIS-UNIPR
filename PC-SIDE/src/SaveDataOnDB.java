import java.beans.Statement;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.TimerTask;
import java.util.Vector;

import com.mysql.jdbc.ResultSet;

public class SaveDataOnDB extends TimerTask {
	
	private Vector<Vector> vetDati = new Vector<Vector>( );
	
	
	public SaveDataOnDB(){
        
		if ( TelemetryData.getInstance().init == null ){
            
			TelemetryData.getInstance().init = new InitializeDatabase();
			TelemetryData.getInstance().threadInitializeDatabase = new Thread(TelemetryData.getInstance().init);
			TelemetryData.getInstance().threadInitializeDatabase.start();
		}
		
	}
	
	@Override
	public void run() {
		
		System.out.println("STARTED   " + TelemetryData.getInstance().getDatiTelemetriaID300());
		
		for ( Integer i = 0; i < TelemetryData.getInstance().vetAddressName.length; i++)
           		 this.sendData(TelemetryData.getInstance().vetAddressName[i]);	
	}
	
	private void sendData ( String address  )
	{
		
		Vector<Vector> vetDati = new Vector<Vector>();
		
		vetDati =  TelemetryData.getInstance().getDatifromAddress(address);
		
		System.out.println(" SEND DATA AT ADRESS " + address + " DIM " + vetDati.size() );
		
		for ( Integer i = 0; i < vetDati.size(); i++ ){
			for ( Integer j = 0; j <  vetDati.elementAt(i).size(); j++ ){
				PreparedStatement query = null;
				
				if ( TelemetryData.getInstance().getDatifromAddress(address).elementAt(i).elementAt(j) != null ){
					try {
						
						query = TelemetryData.getInstance().conn.prepareStatement("INSERT INTO Dato ( Nome , Indirizzo , Valore , ID_Sessione ) VALUES ( ? , ? , ?  , ?)");
					} catch (SQLException e) {
                        
						e.printStackTrace();
						TelemetryData.getInstance().listLog.addElement(e.getSQLState());
						TelemetryData.getInstance().listLog.addElement(e.getMessage());
					}
				
					try {
	
						query.setString(1, TelemetryData.getInstance().getNameOfDataFromAdress(address).elementAt(j));
						query.setString(2, address);	 
						query.setString(3, TelemetryData.getInstance().getDatifromAddress(address).elementAt(i).elementAt(j).toString());
						query.setInt(4, TelemetryData.getInstance().idSessione);
					
					} catch (SQLException e) {
						
						e.printStackTrace();
						TelemetryData.getInstance().listLog.addElement(e.getSQLState());
						TelemetryData.getInstance().listLog.addElement(e.getMessage());
					}
				
					try {
                        query.execute();
					} catch (SQLException e) {
						
						e.printStackTrace();
						TelemetryData.getInstance().listLog.addElement(e.getSQLState());
						TelemetryData.getInstance().listLog.addElement(e.getMessage());
					}
				}
			}
		}
		TelemetryData.getInstance().DeleteDatifromAdress(address);
		return;
	}
}
