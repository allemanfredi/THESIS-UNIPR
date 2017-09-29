import java.util.TimerTask;

import com.digi.xbee.api.exceptions.XBeeException;

public class SendData307 extends TimerTask{
	
	@Override
	public void run() {

			System.out.println("packet 307 : " + Rasp1Data.getInstance().getDato307());
			try {
				Rasp1Data.getInstance().myDevice.sendData(Rasp1Data.getInstance().remoteDevice , Rasp1Data.getInstance().getDato307().getBytes());
				
			} catch (XBeeException e) {
				e.printStackTrace();
			}
	}
}
