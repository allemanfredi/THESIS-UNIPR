import java.util.TimerTask;

import com.digi.xbee.api.exceptions.XBeeException;

public class SendData304 extends TimerTask{
	
	@Override
	public void run() {
		
			System.out.println("packet 304 : " + Rasp1Data.getInstance().getDato304());
			try {
				Rasp1Data.getInstance().myDevice.sendData(Rasp1Data.getInstance().remoteDevice , Rasp1Data.getInstance().getDato304().getBytes());
				
			} catch (XBeeException e) {
				e.printStackTrace();
			}
	}
}
