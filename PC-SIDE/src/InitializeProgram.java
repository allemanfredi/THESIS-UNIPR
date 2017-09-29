import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class InitializeProgram {
	
	static MainGUI main = null;
	
	
	public InitializeProgram() {
		
		JFrame win = new JFrame("TELEMETRIA UNIPR Racing Team");
		win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		win.setResizable(false);
		
		
		JPanel panel = new JPanel(new GridLayout ( 2 , 1));
		final JTextArea txtNomeSessione = new JTextArea();
		JButton btnStartSessione = new JButton();
		btnStartSessione.addActionListener(new ActionListener(){
			
			 public void actionPerformed(ActionEvent e){
				 
				 if ( main == null ){
					 
					 TelemetryData.getInstance().nomeSessione = txtNomeSessione.getText();
					 main = new MainGUI();
	 
				 }
				 
			 }
		});
		
		
		Font font = new Font("Verdana", Font.BOLD, 24);
		txtNomeSessione.setFont(font);
		
		panel.add(txtNomeSessione);
		panel.add(btnStartSessione);
		
		win.add(panel);
		win.setVisible(true);
		win.setBounds(500 , 350 , 500 , 100);
		
		
	}
}
