import java.util.TimerTask;

import com.digi.xbee.api.exceptions.XBeeException;

public class SendData300 extends TimerTask{

	
	@Override
	public void run() {
		
			System.out.println("packet 300 : " + Rasp1Data.getInstance().getDato300());
			try {
				Rasp1Data.getInstance().myDevice.sendData(Rasp1Data.getInstance().remoteDevice , Rasp1Data.getInstance().getDato300().getBytes());
				
			} catch (XBeeException e) {

               			 e.printStackTrace();
			}
			
	}

}
