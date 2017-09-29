import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.table.TableColumn;

import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;


// java -Djava.library.path=/usr/lib/jni/ -jar RASP1.jar


public class MainGUI implements ActionListener {
	
	private JFrame win;
	
	
	private JMenuBar menuBar;
	private JMenu menuTelemetria, menuOpzioni , menuFile;
	private JMenuItem menuExit , menuSettingPort , menuAddLog;
	private JPanel panelConnection;
	protected JButton  btnOpenConnectionToolbar;
	protected JButton  btnCloseConnectionToolbar;
	protected JButton btnConnectionWithDB;
	
	
	private JButton btnSettingPort;
	
	private JInternalFrame logFrame; 
	
	private Thread threadDriver;
	
	private  XBeeDevice myDevice = null;
	private  MyDataReceiveListener listener; //new MyDataReceiveListener()
	
	private  JDesktopPane dtp;
	private  JComboBox comboTypeFrame;
	
	private SaveDataOnDB dataOnDb = null;
	
	private  static Timer timer;
	private  static TimerTask task;

	public JList logList;
	
	private Boolean statoConnessioneDB = false;
 
	public MainGUI() {
	
		win = new JFrame("TELEMETRIA UNIPR Racing Team");
	    win.setSize( 1300 , 850 );
	    win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    CreateGrapichsInterface();
	   
		win.setJMenuBar(menuBar);
		win.setVisible( true );
		
	    
	}
	
	 public void actionPerformed(ActionEvent e) {
		 
		 if (e.getSource() == menuSettingPort) {
			 Settings setting = new Settings();
			
	     }
		 if (e.getSource() == menuSettingPort) {
			 Settings setting = new Settings();
			
	     }
		
		
	 }
	 
	 public void CreateGrapichsInterface(){
		 // M A I N   M E N U
		 
		 menuBar = new JMenuBar();

		 menuTelemetria = new JMenu("Telemetria");
		 menuTelemetria.setMnemonic(KeyEvent.VK_A);
		 menuBar.add(menuTelemetria);
		 menuExit = new JMenuItem("Esci");
		 menuExit.setAccelerator(KeyStroke.getKeyStroke(
				 KeyEvent.VK_E, ActionEvent.ALT_MASK));
		 menuTelemetria.add(menuExit);
	    
		 menuFile = new JMenu("File");
		 menuFile.setMnemonic(KeyEvent.VK_F);
		 menuBar.add(menuFile);
		 
		 menuAddLog = new JMenuItem("Add Log");
		 menuAddLog.setAccelerator(KeyStroke.getKeyStroke(
				 KeyEvent.VK_L, ActionEvent.ALT_MASK));
		 menuAddLog.addActionListener(new ActionListener()
		 {
			 public void actionPerformed(ActionEvent e)
			 {
				 Log log = new Log ( TelemetryData.getInstance().listLog );
				 dtp.add(log);
			 }
		 });
		 
		 menuFile.add(menuAddLog);
	    
		 menuOpzioni = new JMenu("Options");
		 menuOpzioni.setMnemonic(KeyEvent.VK_A);
		 menuBar.add(menuOpzioni);
		 
	 
		 menuSettingPort = new JMenuItem("Settings Port");
		 menuSettingPort.addActionListener(new ActionListener()
		 {
			 public void actionPerformed(ActionEvent e)
			 {
				 Settings setting = new Settings(); 
			 }
		 });
		 menuSettingPort.setAccelerator(KeyStroke.getKeyStroke(
				 KeyEvent.VK_P, ActionEvent.ALT_MASK));
		 menuOpzioni.add(menuSettingPort);	 
		 		
		 
		JPanel panelToolBarMenu = new JPanel( new GridLayout(1 , 1));
		JToolBar toolbar = new JToolBar();	
		btnOpenConnectionToolbar = new JButton(new ImageIcon ("img/wifi-connection-signal-symbol.png" ));
		
		btnOpenConnectionToolbar.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e)
			 { 
				 
				 if ( myDevice == null ){
						
					 if ( TelemetryData.getInstance().portName == null ||TelemetryData.getInstance(). baudRate == null )
					 {
						 TelemetryData.getInstance().listLog.addElement(" Invalid Parameters");
						 return;
					 }
					 else
					 	myDevice = new XBeeDevice(TelemetryData.getInstance().portName ,TelemetryData.getInstance(). baudRate);
					
						
					}
					else
					{
						System.out.println("DEVO RIPARITRE");
						System.out.println("Baud rate " + TelemetryData.getInstance().baudRate);
						
					}
				 
				 try {
					 
					myDevice.open();
					myDevice.addDataListener(new MyDataReceiveListener( ));
					 TelemetryData.getInstance().listLog.removeAllElements();
					 TelemetryData.getInstance().listLog.addElement("Receive Data...");
					btnOpenConnectionToolbar.setEnabled(false);
					btnCloseConnectionToolbar.setEnabled(true);
					StartTimer();
					statoConnessioneDB = true;
					btnConnectionWithDB.setIcon( new ImageIcon ( "img/databaseSI.png" ));
					
						
					
				} catch (XBeeException e1) {
					// TODO Auto-generated catch block
					 TelemetryData.getInstance().listLog.addElement(e1.getMessage());
					myDevice = null;
				}
				
			 }

			
		 });
		btnCloseConnectionToolbar = new JButton(new ImageIcon ("img/cancel.png" ));
		btnCloseConnectionToolbar.setEnabled(false);
		btnCloseConnectionToolbar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			 {
				

				myDevice.close();
				TelemetryData.getInstance().listLog.addElement("Stop to Receive Data");			
				btnOpenConnectionToolbar.setEnabled(true);
				btnCloseConnectionToolbar.setEnabled(false);
				
				StopTimer();
				statoConnessioneDB = false;
				btnConnectionWithDB.setIcon( new ImageIcon ( "img/databaseNO.png" ));
				
			 }
		 });
		
		btnConnectionWithDB = new JButton(new ImageIcon ("img/databaseSI.png" ));
		btnConnectionWithDB.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			 {
				
				if ( statoConnessioneDB == true )
				{
					StopTimer();
					btnConnectionWithDB.setIcon( new ImageIcon ( "img/databaseNO.png" ));
					statoConnessioneDB = false;
				}
				else
				{
					StartTimer();
					btnConnectionWithDB.setIcon( new ImageIcon ( "img/databaseSI.png" ));
					statoConnessioneDB = true;
				}
				
				
				
				
			 }
		 });
		
		btnSettingPort = new JButton(new ImageIcon ("img/settings.png" ));
		btnSettingPort.addActionListener(new ActionListener()
		{
			 public void actionPerformed(ActionEvent e)
			 {
				 Settings setting = new Settings(); 
			 }
		 });
		
		JButton btnAddFrame = new JButton(new ImageIcon ("img/add.png" ));
		btnAddFrame.addActionListener(new ActionListener()
		 {
			 public void actionPerformed(ActionEvent e)
			 {
				 
				 	
				 	
				 	switch ( comboTypeFrame.getSelectedItem().toString() )
				 	{
				 		case "Log":
				 		{
				 			Log log = new Log (  TelemetryData.getInstance().listLog );
				 			log.setLocation(0, 570);
				 		   	dtp.add(log);
				 		   	break;
				 		}
				 		case "DataLog":
				 		{
				 			DataLog dataLog = new DataLog ( );
				 		   	dtp.add(dataLog);
				 		   	break;
				 		}
				 		case "Data":
				 		{
				 			TblGeneralData data = new TblGeneralData();
				 			data.setLocation ( 1030 , 0 );
						 	dtp.add(data);
						 	break;
				 		}
				 		case "RPM Chart":
				 		{
				 			ChartRPM chartRpm = new ChartRPM();
				 		   	chartRpm.setLocation(0, 230);
				 		   	dtp.add(chartRpm);
				 		   	break;
				 		}
				 		
				 		case "FARF Chart":
				 		{
				 			ChartTPS chartTps = new ChartTPS();
				 		   	chartTps.setLocation( 820 , 230 );
				 		   	dtp.add(chartTps);
				 		   	break;
				 		}
				 		case "TPS Chart":
				 		{
				 			ChartTPSXY chartTpsXy = new ChartTPSXY();
				 		   	chartTpsXy.setLocation( 0 , 0 );
				 		   	dtp.add(chartTpsXy);
				 		   	break;
				 		}
				 				
				 	}
				 	
			 }
		 });
		
		
		String [] param = { "Log" , "DataLog" , "Data" , "RPM Chart" , "FARF Chart" , "TPS Chart" };
	    comboTypeFrame = new JComboBox(param);
	
		
		toolbar.add(btnOpenConnectionToolbar);
		toolbar.add(btnCloseConnectionToolbar);
		toolbar.add(btnSettingPort);
		toolbar.add(btnConnectionWithDB);
		toolbar.add(comboTypeFrame);
		toolbar.add(btnAddFrame);
		panelToolBarMenu.add(toolbar);
		 		 
		 dtp = new JDesktopPane();
		 win.add(dtp);
		 
		 dtp.setBackground(Color.LIGHT_GRAY);
		 
		 
	   	Log log = new Log (  TelemetryData.getInstance().listLog );
	   	dtp.add(log);
	   	log.setLocation(0, 570);
	   	
	   	DataLog dataLog = new DataLog ( );
	   	//dataLog.setLocation(0 , 170);
	   	dtp.add(dataLog);
	   	
	   	TblGeneralData data = new TblGeneralData();
	   	data.setLocation ( 1030 , 0 );
	   	dtp.add(data);
	   	
	   	ChartRPM chartRpm = new ChartRPM();
	   	chartRpm.setLocation(0, 230);
	   	dtp.add(chartRpm);
	   	
	   	ChartTPS chartTps = new ChartTPS();
	   	chartTps.setLocation( 820 , 230 );
	   	dtp.add(chartTps);
	   	
	   	ChartTPSXY chartTpsXy = new ChartTPSXY();
	   	chartTpsXy.setLocation( 0 , 0 );
	   	dtp.add(chartTpsXy);
	   	
	   	
	   	
	   	
		win.add(panelToolBarMenu, BorderLayout.NORTH);
	 }
	 
	 
	 public static void StartTimer(){
		 timer = new Timer();
		 task = new SaveDataOnDB(); 
		 timer.scheduleAtFixedRate( task, TelemetryData.getInstance().delayTime, TelemetryData.getInstance().repeatDelayTime);
		 
	 }
	 
	 public static void StopTimer(){
		timer.cancel(); //Terminates this timer,discarding any currently scheduled tasks.
	      
	 }
	 
}
