import javax.swing.DefaultListModel;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class DataLog extends JInternalFrame{
	
	public DataLog( )
	{
		JList<String> logList = new JList<String>( TelemetryData.getInstance().listDataLog );
	    JScrollPane scrollpane = new JScrollPane(logList);
	    
	    getContentPane().add(scrollpane);
	    
	    setBounds(0, 0, 500, 200);
	    setResizable(true);
	    setClosable(true);
	    setIconifiable(true);
	    setTitle("DataLog");
	    setVisible(true);
	}

}
