import java.awt.Dimension;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialPointer;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialScale;

public class ChartTPS extends JInternalFrame{
	
	
	private JFreeChart chart;
	
	public ChartTPS(){
		
		 	setPreferredSize(new Dimension(300, 300));
		    add(buildDialPlot(0, 100, 10));
		    pack();
		    setVisible(true);
		    setSize(200, 200);
		    setResizable(true);
		    setClosable(true);
		    setIconifiable(true);
		    setTitle("TPS");
		    
		
	}
	
	private ChartPanel buildDialPlot(int minimumValue, int maximumValue,
		      int majorTickGap) {

		    DialPlot plot = new DialPlot( TelemetryData.getInstance().datasetFarf);
		    plot.setDialFrame(new StandardDialFrame());
		    plot.addLayer(new DialValueIndicator(0));
		    plot.addLayer(new DialPointer.Pointer());
		    

		    StandardDialScale scale = new StandardDialScale(minimumValue, maximumValue,
		        -120, -300, majorTickGap, majorTickGap - 1);
		    scale.setTickRadius(0.88);
		    scale.setTickLabelOffset(0.20);
		    plot.addScale(0, scale);
		
		    return new ChartPanel(new JFreeChart(plot));
		  }
}
