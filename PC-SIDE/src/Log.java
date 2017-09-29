import javax.swing.DefaultListModel;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class Log extends JInternalFrame{
	

	public Log( DefaultListModel<String> list ){
		JList<String> logList = new JList<String>(list);
	    JScrollPane scrollpane = new JScrollPane(logList);
	    
	    getContentPane().add(scrollpane);
	    
	    setBounds(0, 0, 500, 170);
	    setResizable(true);
	    setClosable(true);
	    setIconifiable(true);
	    setTitle("Log");
	    setVisible(true);
	}
}
