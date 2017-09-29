import java.util.TimerTask;

import com.digi.xbee.api.exceptions.XBeeException;

public class SendData306 extends TimerTask{

	
	@Override
	public void run() {
		
			System.out.println("packet 306 : " + Rasp1Data.getInstance().getDato306());
			try {
				Rasp1Data.getInstance().myDevice.sendData(Rasp1Data.getInstance().remoteDevice , Rasp1Data.getInstance().getDato306().getBytes());
				
			} catch (XBeeException e) {
				e.printStackTrace();
			}
	}
}
