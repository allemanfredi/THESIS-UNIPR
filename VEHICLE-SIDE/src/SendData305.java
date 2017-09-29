import java.util.TimerTask;

import com.digi.xbee.api.exceptions.XBeeException;

public class SendData305 extends TimerTask{
	
	
	@Override
	public void run() {
		
			System.out.println("packet 305 : " + Rasp1Data.getInstance().getDato305());
			try {
				Rasp1Data.getInstance().myDevice.sendData(Rasp1Data.getInstance().remoteDevice , Rasp1Data.getInstance().getDato305().getBytes());
				
				
			} catch (XBeeException e) {
				e.printStackTrace();
			}
	}
}
