import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class TblGeneralData extends JInternalFrame   {
	
	
	public TblGeneralData( ){
		JPanel panel = new JPanel(new GridLayout(9 , 1));
		
		JTable tblData300 = new JTable ( TelemetryData.getInstance().getDatiTelemetriaID300() , TelemetryData.getInstance().getColoumn300() );
		JScrollPane scrollPane300 = new JScrollPane(tblData300);
			
		TelemetryData.getInstance().setTableModel( (( AbstractTableModel ) tblData300.getModel() ) , "300");
		
		
		JTable tblData301 = new JTable ( TelemetryData.getInstance().getDatiTelemetriaID301() , TelemetryData.getInstance().getColoumn301() );
		JScrollPane scrollPane301 = new JScrollPane(tblData301);
		
		TelemetryData.getInstance().setTableModel( (( AbstractTableModel ) tblData301.getModel() ) , "301");
		
		
		
		JTable tblData302 = new JTable ( TelemetryData.getInstance().getDatiTelemetriaID302() , TelemetryData.getInstance().getColoumn302() );
		JScrollPane scrollPane302 = new JScrollPane(tblData302);
		
		TelemetryData.getInstance().setTableModel( (( AbstractTableModel ) tblData302.getModel() ) , "302");
		
		
		JTable tblData304 = new JTable ( TelemetryData.getInstance().getDatiTelemetriaID304() , TelemetryData.getInstance().getColoumn304() );
		JScrollPane scrollPane304 = new JScrollPane(tblData304);
		
		TelemetryData.getInstance().setTableModel( (( AbstractTableModel ) tblData304.getModel() ) , "304");
		
		
		JTable tblData305 = new JTable ( TelemetryData.getInstance().getDatiTelemetriaID305() , TelemetryData.getInstance().getColoumn305() );
		JScrollPane scrollPane305 = new JScrollPane(tblData305);
		
		TelemetryData.getInstance().setTableModel( (( AbstractTableModel ) tblData305.getModel() ) , "305");
		
		
		JTable tblData306 = new JTable ( TelemetryData.getInstance().getDatiTelemetriaID306() , TelemetryData.getInstance().getColoumn306() );
		JScrollPane scrollPane306 = new JScrollPane(tblData306);
		
		TelemetryData.getInstance().setTableModel( (( AbstractTableModel ) tblData306.getModel() ) , "306");
		
		JTable tblData307 = new JTable ( TelemetryData.getInstance().getDatiTelemetriaID307() , TelemetryData.getInstance().getColoumn307() );
		JScrollPane scrollPane307 = new JScrollPane(tblData307);
		
		TelemetryData.getInstance().setTableModel( (( AbstractTableModel ) tblData307.getModel() ) , "307");
		
		JTable tblData308 = new JTable ( TelemetryData.getInstance().getDatiTelemetriaID308() , TelemetryData.getInstance().getColoumn308() );
		JScrollPane scrollPane308 = new JScrollPane(tblData308);
		
		TelemetryData.getInstance().setTableModel( (( AbstractTableModel ) tblData308.getModel() ) , "308");
		
		JTable tblData309 = new JTable ( TelemetryData.getInstance().getDatiTelemetriaID309() , TelemetryData.getInstance().getColoumn309() );
		JScrollPane scrollPane309 = new JScrollPane(tblData309);
		
		TelemetryData.getInstance().setTableModel( (( AbstractTableModel ) tblData309.getModel() ) , "309");
		
		
		panel.add(scrollPane300);
		panel.add(scrollPane301);
		panel.add(scrollPane302);
		panel.add(scrollPane304);
		panel.add(scrollPane305);
		panel.add(scrollPane306);
		panel.add(scrollPane307);
		panel.add(scrollPane308);
		panel.add(scrollPane309);

		
		this.add(panel);
	    
	    setBounds(0, 0, 270, 600);
	    setResizable(true);
	    setClosable(true);
	    setIconifiable(true);
	    setTitle("Data");
	    setVisible(true);
		
	}

}
