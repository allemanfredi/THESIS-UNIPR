import java.util.TimerTask;

import com.digi.xbee.api.exceptions.XBeeException;

public class SendData301 extends TimerTask{
	
	@Override
	public void run() {
		
			System.out.println("packet 301 : " + Rasp1Data.getInstance().getDato301());
			try {
				Rasp1Data.getInstance().myDevice.sendData(Rasp1Data.getInstance().remoteDevice , Rasp1Data.getInstance().getDato301().getBytes());
				
			} catch (XBeeException e) {

                		e.printStackTrace();
			}

	}
}
