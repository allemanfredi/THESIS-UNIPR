import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import gnu.io.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;




public class Settings {//extends MainGUI{
	
	private static String[] baudRateValue = { "9600" , "14400" , "19200" , "38400", "57600" , "115200", "128000" };
		
	
	private ArrayList <String> portList;
	private Enumeration ports;
	private String[] arrayPorts;
	private JComboBox comboNomePort ,  repeatComboBox , delayComboBox; // lo dichuaro qui perchè lo devo usare
    
	
	public Settings() {
		
		final JFrame win = new JFrame ( "Settings ");		
		win.setSize(400, 400);
		win.setLayout(new GridLayout( 5 , 1));
		JPanel panelPorta = new JPanel(new GridLayout(2 , 2));
		
		comboNomePort = new JComboBox();
		this.SetPortAvaiable();
		JLabel lblNomePorta = new JLabel ( " Port :         ");
			
	    
		JLabel lblBaudRate = new JLabel ( " Baud Rate : ");
		final JComboBox comboValuePort = new JComboBox(baudRateValue);
		comboValuePort.setSelectedIndex(5);
		comboValuePort.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent e){
		    	  //...
		      }
		    });;
		
		
		JButton btnSettingOk = new JButton ("Change Value");
		btnSettingOk.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent e){
	    	  System.out.println(comboValuePort.getSelectedItem().toString());
	    	  
	    	  TelemetryData.getInstance().baudRate = Integer.parseInt(comboValuePort.getSelectedItem().toString());
	    	  
	    	  
	    	  if ( TelemetryData.getInstance().portName == "" )
	    		  JOptionPane.showMessageDialog(win,"ERRORE: Name NOT Valid","Inane error", JOptionPane.ERROR_MESSAGE);
	    	  
	    	  else
	    		  TelemetryData.getInstance().portName = comboNomePort.getSelectedItem().toString();
	    	  
	    	  switch ( delayComboBox.getSelectedItem().toString()){
	    	  	case "1 second":
	    	  	{
	    	  		TelemetryData.getInstance().delayTime = 1000;
	    	  		break;
	    	  	}
	    	  	case "2 seconds":
	    	  	{
	    	  		TelemetryData.getInstance().delayTime = 2000;
	    	  		break;
	    	  	}
	    	  	case "3 seconds":
	    	  	{
	    	  		TelemetryData.getInstance().delayTime = 3000;
	    	  		break;
	    	  	}
	    	  	case "4 seconds":
	    	  	{
	    	  		TelemetryData.getInstance().delayTime = 4000;
	    	  		break;
	    	  	}
	    	  	case "5 seconds":
	    	  	{
	    	  		TelemetryData.getInstance().delayTime = 5000;
	    	  		break;
	    	  	}
	    	  	case "6 seconds":
	    	  	{
	    	  		TelemetryData.getInstance().delayTime = 6000;
	    	  		break;
	    	  	}
	    	  	case "7 seconds":
	    	  	{
	    	  		TelemetryData.getInstance().delayTime = 7000;
	    	  		break;
	    	  	}
	    	  	case "8 seconds":
	    	  	{
	    	  		TelemetryData.getInstance().delayTime =8000;
	    	  		break;
	    	  	}
	    	  }
	    	  
	    	  
	    	  switch ( repeatComboBox.getSelectedItem().toString()){
	    	  	case "1 second":
                {
	    	  		TelemetryData.getInstance().repeatDelayTime = 1000;
	    	  		break;
	    	  	}
	    	  	case "2 seconds":
	    	  	{
	    	  		TelemetryData.getInstance().repeatDelayTime = 2000;
	    	  		break;
	    	  	}
	    	  	case "3 seconds":
	    	  	{
	    	  		TelemetryData.getInstance().repeatDelayTime = 3000;
	    	  		break;
	    	  	}
	    	  	case "4 seconds":
	    	  	{
	    	  		TelemetryData.getInstance().repeatDelayTime = 4000;
	    	  		break;
	    	  	}
	    	  	case "5 seconds":
	    	  	{
	    	  		TelemetryData.getInstance().repeatDelayTime = 5000;
	    	  		break;
	    	  	}
	    	  	case "6 seconds":
	    	  	{
	    	  		TelemetryData.getInstance().repeatDelayTime = 6000;
	    	  		break;
	    	  	}
	    	  	case "7 seconds":
	    	  	{
	    	  		TelemetryData.getInstance().repeatDelayTime = 7000;
	    	  		break;
	    	  	}
	    	  	case "8 seconds":
	    	  	{
	    	  		TelemetryData.getInstance().repeatDelayTime =8000;
	    	  		break;
	    	  	}
	    	  }
	          
	      }
	    });
		
		
		JPanel panelBtnSettingOk = new JPanel( new GridLayout ( 1 , 2));
		panelBtnSettingOk.add(btnSettingOk );
		
		JButton btnRefreshPort = new JButton ( "Refresh");
		btnRefreshPort.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent e)
	      {
	    	  SetPortAvaiable();
	          
	      }
	    });
		panelBtnSettingOk.add(btnRefreshPort);
		
		
		JPanel panelSettingsDb = new JPanel ( new GridLayout ( 2 , 2 ));
		JLabel lblDelay = new JLabel ( "Delay Time to save data on Database: ");
		String[] delay = { "1 second", "2 seconds", "3 seconds", "4 seconds", "5 seconds" , "6 seconds" , "7 seconds" , "8 seconds"};


		delayComboBox = new JComboBox(delay);
		delayComboBox.setSelectedIndex(4);
		
		panelSettingsDb.add(lblDelay);
		panelSettingsDb.add(delayComboBox);
		
		JLabel lblRepeatTimer = new JLabel( "Repeat Timer to save on Database : ");
	    repeatComboBox = new JComboBox(delay);
		repeatComboBox.setSelectedIndex(4);
		
		panelSettingsDb.add(lblRepeatTimer);
		panelSettingsDb.add(repeatComboBox);
		
		
		panelPorta.add(lblNomePorta);
		panelPorta.add(comboNomePort);
		panelPorta.add(lblBaudRate);
		panelPorta.add(comboValuePort);
		win.add(panelPorta );
		win.add( new JSeparator ( SwingConstants.HORIZONTAL));
		win.add(panelSettingsDb);
		win.add( new JSeparator ( SwingConstants.HORIZONTAL));
		win.add(panelBtnSettingOk);
		win.setVisible(true);
		win.pack();
		
	}
	
	
	public void SetPortAvaiable(){
		
		arrayPorts = null;
		portList = null;
		comboNomePort.removeAllItems();
		
		Enumeration ports = CommPortIdentifier.getPortIdentifiers();
	    portList = new ArrayList<String>();
	    String portArray[] = null;
	    while (ports.hasMoreElements()) {
	        CommPortIdentifier port = (CommPortIdentifier) ports.nextElement();
	        if (port.getPortType() == CommPortIdentifier.PORT_SERIAL) {
	            portList.add(port.getName());
	            
	        }
	    }
	  
	    arrayPorts = portList.toArray(new String[portList.size()]);
	    for ( Integer i = 0; i < portList.size(); i++)
	    	comboNomePort.addItem(arrayPorts[i]);
	    
	    return;
	}

}
