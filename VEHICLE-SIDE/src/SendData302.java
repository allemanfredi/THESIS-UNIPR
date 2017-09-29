import java.util.TimerTask;

import com.digi.xbee.api.exceptions.XBeeException;

public class SendData302 extends TimerTask{
	
	
	@Override
	public void run() {
	
			System.out.println("packet 302 : " + Rasp1Data.getInstance().getDato302());
			try {
				Rasp1Data.getInstance().myDevice.sendData(Rasp1Data.getInstance().remoteDevice , Rasp1Data.getInstance().getDato302().getBytes());
				
			} catch (XBeeException e) {

                		e.printStackTrace();
			}
	}
}
