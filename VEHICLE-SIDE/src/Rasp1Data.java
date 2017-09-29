import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TimerTask;
import java.util.Vector;

import com.digi.xbee.api.RemoteXBeeDevice;
import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.models.XBee64BitAddress;

public class Rasp1Data {
	
	private static Rasp1Data instance = null;
	
	public BufferedReader stdInput;
	
	public String dato300 = "";
	public String dato301 = "";
	public String dato302 = "";
	public String dato304 = "";
	public String dato305 = "";
	public String dato306 = "";
	public String dato307 = "";
	public String dato308 = "";
	
	public Runtime rt = Runtime.getRuntime();
	public Process proc = null;
	public XBeeDevice myDevice;
	public RemoteXBeeDevice remoteDevice;
	
	private static final String PORT = "/dev/ttyUSB0";
	private static final int BAUD_RATE = 115200;
	
	
	public Integer baudRate;
	public String port;
	public String idDest;
	public Integer bitRate;
	
	public InputStreamReader input;
	
	public Boolean arrivato = false;

    
	public static Rasp1Data getInstance ( ){
		
		if ( instance == null ){
            
			instance = new Rasp1Data();
		}
		
		return instance;
	}
    

	public Rasp1Data() {
        //....
	}
	
	public void startSetting(){
		input = new InputStreamReader(proc.getInputStream());
		
		stdInput = new BufferedReader(input);
	    myDevice = new XBeeDevice(PORT, BAUD_RATE);
				
	    remoteDevice = new RemoteXBeeDevice(Rasp1Data.getInstance().myDevice , new XBee64BitAddress(idDest) );
	}
    
	
	public void setDato300( String dato){
		this.dato300 = dato;
	}
    
    public void setDato301( String dato){
        this.dato301 = dato;
    }
    
    public void setDato302( String dato){
        this.dato302 = dato;
    }
    
    public void setDato304( String dato){
        this.dato304 = dato;
    }
    
    public void setDato305( String dato){
        this.dato305 = dato;
    }
    
    public void setDato306( String dato){
        this.dato306 = dato;
    }
    
    public void setDato307( String dato){
        this.dato307 = dato;
    }
    
    public void setDato308( String dato){
        this.dato308 = dato;
    }
    
    
    
    
	public String getDato300 (){
		return this.dato300;
	}
    
	public String getDato301 (){
		return this.dato301;
	}
	public String getDato302 (){
		return this.dato302;
	}
	
	public String getDato304 (){
		return this.dato304;
	}
	
	public String getDato305 (){
		return this.dato305;
	}
	
	public String getDato306 (){
		return this.dato306;
	}

	public String getDato307 (){
		return this.dato307;
	}
	
	public String getDato308 (){
		return this.dato308;
	}

}
