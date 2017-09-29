import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import com.digi.xbee.api.RemoteXBeeDevice;
import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.exceptions.TimeoutException;
import com.digi.xbee.api.exceptions.XBeeException;
import com.digi.xbee.api.models.XBee64BitAddress;
import com.digi.xbee.api.utils.HexUtils;

// /usr/lib/jni/
//java -Djava.library.path=/usr/lib/jni/ -jar RASP1.jar


public class Main {

	static int numberOfParameters = 4;
	
	
	private  static Timer timer300 , timer301 , timer302 , timer304 , timer305 , timer306 , timer307 , timer308;
	private  static TimerTask task300 , task301, task302 , task304 , task305 , task306 , task307 , task308;
	

	public static void main ( String[] args ) throws IOException, XBeeException {
    
		if ( args.length != numberOfParameters  && args.length != 0 )
		{
			System.err.println("ERROR :Number of parameters is incorrect   (    baudRate  port  idDest  bitRate  )");
			return;
		}
		if ( args.length == 0){
            
			Rasp1Data.getInstance().baudRate = 115200;
			Rasp1Data.getInstance().port = "can0";
			Rasp1Data.getInstance().idDest = "0013A20040E55A96";
			Rasp1Data.getInstance().bitRate = 1000000;
			
		}
		else{
            
			Rasp1Data.getInstance().baudRate = Integer.parseInt(args[0]);
			Rasp1Data.getInstance().port = args[1];
			Rasp1Data.getInstance().idDest = args[2];
			Rasp1Data.getInstance().bitRate = Integer.parseInt(args[3]);
		}
		

		try{
			String cmdSetPorta = "sudo /sbin/ip link set "+ Rasp1Data.getInstance().port + " up type can bitrate "+ Rasp1Data.getInstance().bitRate.toString();
			String cmdDownPorta = "sudo /sbin/ip link set " + Rasp1Data.getInstance().port + " down";
			Rasp1Data.getInstance().proc = Rasp1Data.getInstance().rt.exec(cmdDownPorta);
			Rasp1Data.getInstance().proc = Rasp1Data.getInstance().rt.exec(cmdSetPorta);
			Rasp1Data.getInstance().proc.waitFor();

		 }catch (Throwable t){
			    t.printStackTrace();
		  }
				
		try{
            
			String cmdCanDump = "sudo candump "+ Rasp1Data.getInstance().port;
			Rasp1Data.getInstance().proc = Rasp1Data.getInstance().rt.exec(cmdCanDump);

		    } catch (Throwable t){
			    t.printStackTrace();
			}
	
		
		Rasp1Data.getInstance().startSetting();
		
		
		try {
            
			Rasp1Data.getInstance().myDevice.open();
		} catch (XBeeException e1) {

            		e1.printStackTrace();
		}

		
		FileOutputStream prova = new FileOutputStream("dati.txt");
        	PrintStream scrivi = new PrintStream(prova);
        
		
		timer300 = new Timer();
		task300 = new SendData300(); 
		timer300.scheduleAtFixedRate( task300, 5, 5); // Data at 200 mhz
		
		timer301 = new Timer();
		task301 = new SendData301(); 
		timer301.scheduleAtFixedRate( task301, 6 , 6);
		
		timer302 = new Timer();
		task302 = new SendData302(); 
		timer302.scheduleAtFixedRate( task302, 7 , 7);
		
		timer304 = new Timer();
		task304 = new SendData304(); 
		timer304.scheduleAtFixedRate( task304, 9 , 9);
		
		timer305 = new Timer();
		task305 = new SendData305(); 
		timer305.scheduleAtFixedRate( task305, 8 , 8);
		
		timer306 = new Timer();
		task306 = new SendData306(); 
		timer306.scheduleAtFixedRate( task306, 10 , 10);
		
		timer307 = new Timer();
		task307 = new SendData307(); 
		timer307.scheduleAtFixedRate( task307, 11 , 11);
		
		timer308 = new Timer();
		task308 = new SendData308(); 
		timer308.scheduleAtFixedRate( task308, 12 , 12);
		
		
		String s1 = "";
		while ( ( s1 = Rasp1Data.getInstance().stdInput.readLine()) != null) {
            
            String canName = s1.substring(2 , 6);
            String canAdress = s1.substring(8, 11);// lo metto intero perchè non funziona
            Integer canLenght = Character.getNumericValue(s1.charAt(15));
            String canPacket = s1.substring(19, 42);
            
            
            if ( canAdress.equals("300") )
                Rasp1Data.getInstance().setDato300(s1);
            
            if ( canAdress.equals("301") )
                Rasp1Data.getInstance().setDato301(s1);
            
            if ( canAdress.equals("302") )
                Rasp1Data.getInstance().setDato302(s1);
        
            if ( canAdress.equals("304") )
                Rasp1Data.getInstance().setDato304(s1);
            
            if ( canAdress.equals("305") )
                Rasp1Data.getInstance().setDato305(s1);
            
            if ( canAdress.equals("306") )
                Rasp1Data.getInstance().setDato306(s1);
            
            if ( canAdress.equals("307") )
                Rasp1Data.getInstance().setDato307(s1);
            
            if ( canAdress.equals("308") )
                Rasp1Data.getInstance().setDato308(s1);
            
            
            /* ................. */
            
			
		}
	}
}
