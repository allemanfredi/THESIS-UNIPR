import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;

public class ChartRPM extends JInternalFrame{

	private JFreeChart chart;
	
	public ChartRPM(){
        
		TelemetryData.getInstance().RestartTimeGraphRPM();
		
		JPanel panelOptionChart = new JPanel ( new GridLayout(1 , 2));
		JButton btnResetTime = new JButton ( "Restart");
		btnResetTime.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e)
			 {
				 
				 TelemetryData.getInstance().RestartTimeGraphRPM();
				 
			 }
		});
        
        
		panelOptionChart.add(btnResetTime);
		
		JButton btnSaveAsImage = new JButton ( "Save as Image");
		btnSaveAsImage.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e){
       
				 Date date = new Date();
				 String nomeFile = date.toString() + TelemetryData.getInstance().nomeSessione;
				 try {
                     
					ChartUtilities.saveChartAsJPEG(new File( nomeFile + ".jpg"), chart, 1200, 1000);
				} catch (IOException e1) {
	
					e1.printStackTrace();
					TelemetryData.getInstance().listLog.addElement(e1.getMessage());
				}
			 }
		});
		panelOptionChart.add(btnSaveAsImage);
		
		
		setVisible(true);
		add(createChartPanel() , BorderLayout.CENTER);
		add(panelOptionChart, BorderLayout.SOUTH);
		setSize(800, 300);
		setTitle("RPM's Chart");
		setResizable(true);
	    	setClosable(true);
	    	setIconifiable(true);
		
	}
	
	private JPanel createChartPanel() {
	    String chartTitle = "RPM's chart";
	    String xAxisLabel = "Time";
	    String yAxisLabel = "Value";
	 
	 
	    chart = ChartFactory.createXYLineChart(chartTitle,
	            xAxisLabel, yAxisLabel, TelemetryData.getInstance().datasetRpm);
	 
	    chart.fireChartChanged();
	    
	    return new ChartPanel(chart);
	}

}
