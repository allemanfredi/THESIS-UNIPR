import java.util.Timer;
import java.util.TimerTask;

public class TimerForDB implements Runnable{

	private  Timer timer  = null;
	private  TimerTask task = null;
	
	
	public TimerForDB(){
        
		 timer = new Timer();
		 task = new SaveDataOnDB(); 
	}
	
	@Override
	public void run() {
        
		 if ( timer == null )
			 timer = new Timer();
		 
		 timer.scheduleAtFixedRate( task, 5000, 5000);
	}
	
	public void StopTimer(){
		timer.cancel();
	}

}
