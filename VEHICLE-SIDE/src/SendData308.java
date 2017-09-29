import java.util.TimerTask;

import com.digi.xbee.api.exceptions.XBeeException;

public class SendData308 extends TimerTask{
	
	@Override
	public void run() {
	
			System.out.println("packet 308 : " + Rasp1Data.getInstance().getDato308());
			try {
				Rasp1Data.getInstance().myDevice.sendData(Rasp1Data.getInstance().remoteDevice , Rasp1Data.getInstance().getDato308().getBytes());
				
			} catch (XBeeException e) {

                e.printStackTrace();
			}
	}
}
