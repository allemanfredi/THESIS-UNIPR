import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

public class ChartTPSXY extends JInternalFrame{

	private JFreeChart chart;
	
	public ChartTPSXY()
	{
		TelemetryData.getInstance().RestartTimeGraphTPS();
		
		JPanel panelOptionChart = new JPanel ( new GridLayout(1 , 2));
		JButton btnResetTime = new JButton ( "Restart");
		btnResetTime.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e)
			 {
				 
				 TelemetryData.getInstance().RestartTimeGraphTPS();
				 
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
					// TODO Auto-generated catch block
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
		setTitle("TPS's Chart");
		setResizable(true);
	    	setClosable(true);
	    	setIconifiable(true);
		
	}
	
	private JPanel createChartPanel() {
		
	    String chartTitle = "TPS's chart";
	    String xAxisLabel = "Time";
	    String yAxisLabel = "Value";
 
	 
	    chart = ChartFactory.createXYLineChart(chartTitle,
	            xAxisLabel, yAxisLabel, TelemetryData.getInstance().datasetTps);
	 
	    chart.fireChartChanged();
	    
	    return new ChartPanel(chart);
	}

}

