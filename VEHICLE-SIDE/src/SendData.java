import java.util.ArrayList;
import java.util.Vector;

import com.digi.xbee.api.exceptions.XBeeException;
import com.digi.xbee.api.models.XBee64BitAddress;

public class SendData implements Runnable{
	
	private Boolean stop = true;
	private Boolean invio = true;
	String dato = null;
	
	private String strSend = "";// = new Vector<String>();
	private Integer contInvio = 0;
	
	

	@Override
	public void run() {

				
	}
	
	public void Stop(){
        
		stop = false;
	}
	
	public void setDato ( String par ){
        
		contInvio++;
		this.strSend += par;
		System.out.println("PAR : "  + par);
		this.strSend += ",";
		
		if ( contInvio > 5 ){
            
			contInvio = 0;
			String strSendApp = strSend;
			strSend = "";;
			System.out.println("INVIO : " + strSendApp );
			try {
				Rasp1Data.getInstance().myDevice.sendData(Rasp1Data.getInstance().remoteDevice , strSendApp.getBytes());
				
				
			} catch (XBeeException e) {
                
				e.printStackTrace();
			}
			strSendApp = "";
			
		}
	}
}
