
import java.sql.Connection;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.table.AbstractTableModel;

import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import demo.DialDemo2;



public class TelemetryData {
	
	private static TelemetryData instance = null; // singleton pattern

	
	
	//nome della sessione in corso utile per il salvataggio dei dati nel db
	
	public String nomeSessione;
	public Integer idSessione;
	
	// variabili per la porta seriale
	public Integer baudRate = null;
	public String  portName = null;
	
	// variabili per la gestione del db
	
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	public static final String DB_URL = "jdbc:mysql://localhost/my_telemetriaunipr";
	
	public static final String USER = "root";
	public static final String PASS = "";
	
	public Connection conn = null;
    public  Statement  stmt = null;
	
    public InitializeDatabase init = null;
	public Thread threadInitializeDatabase = null;
	
	
	public Integer delayTime = 5000;
	public Integer repeatDelayTime = 5000;
	
	//DATASET PER GRAFICO RPM
	public XYSeriesCollection datasetRpm = new XYSeriesCollection();
	public XYSeries seriesRpm = new XYSeries("RPM ( rotate per minute) ");
	private Double timeToRpmChart = 0.0;
	
	//DATASET PER GRAFICO TPS
	
	public XYSeriesCollection datasetTps = new XYSeriesCollection();
	public XYSeries seriesTps = new XYSeries("TPS");
	private Double timeToTpsChart = 0.0;
	
	//DATASET PER GRAFICO FARFALLA
	
	public DialDemo2 dialFarf = new DialDemo2("TPS ");
	public DefaultValueDataset datasetFarf = new DefaultValueDataset();
	
	
	// vettori che contengono i dati relativi ad ogni indirizzo
	
	private Vector<Vector> datiTelemetriaID300 = new Vector<Vector>( );
	private Vector<Vector> datiTelemetriaID301 = new Vector<Vector>( );
	private Vector<Vector> datiTelemetriaID302 = new Vector<Vector>( );
	private Vector<Vector> datiTelemetriaID304 = new Vector<Vector>( );
	private Vector<Vector> datiTelemetriaID305 = new Vector<Vector>( );
	private Vector<Vector> datiTelemetriaID306 = new Vector<Vector>( );
	private Vector<Vector> datiTelemetriaID307 = new Vector<Vector>( );
	private Vector<Vector> datiTelemetriaID308 = new Vector<Vector>( );
	private Vector<Vector> datiTelemetriaID309 = new Vector<Vector>( );



	public String[] vetAddressName = { "300" , "301" , "302" , "304" , "305" , "306" , "307" , "308" , "309"};
	
	private Vector<String> columnNames300 = new Vector<String>();
	private Vector<String> columnNames301 = new Vector<String>();
	private Vector<String> columnNames302 = new Vector<String>();
	private Vector<String> columnNames304 = new Vector<String>();
	private Vector<String> columnNames305 = new Vector<String>();
	private Vector<String> columnNames306 = new Vector<String>();
	private Vector<String> columnNames307 = new Vector<String>();
	private Vector<String> columnNames308 = new Vector<String>();
	private Vector<String> columnNames309 = new Vector<String>();


	public Integer indexData300 = 0;
	public Integer indexData301 = 0;
	public Integer indexData302 = 0;
	public Integer indexData304 = 0;
	public Integer indexData305 = 0;
	public Integer indexData306 = 0;
	public Integer indexData307 = 0;
	public Integer indexData308 = 0;
	public Integer indexData309 = 0;
	
	private AbstractTableModel modelID300 , modelID301 , modelID302 , modelID304 , modelID305 , modelID306 , modelID307 , modelID308 , modelID309;
	
	public  DefaultListModel<String> listDataLog;
	public DefaultListModel<String> listLog;
	
	
	public TelemetryData(){
		
		datiTelemetriaID300.add(new Vector());
		datiTelemetriaID301.add(new Vector());
		datiTelemetriaID302.add(new Vector());
		datiTelemetriaID304.add(new Vector());
		datiTelemetriaID305.add(new Vector());
		datiTelemetriaID306.add(new Vector());
		datiTelemetriaID307.add(new Vector());
		datiTelemetriaID308.add(new Vector());
		datiTelemetriaID309.add(new Vector());
		
		
		columnNames300.addElement("RPM");
		columnNames300.addElement("TPS1");
		columnNames300.addElement("MAP");
		columnNames300.addElement("Lnrl1");
		
		
		columnNames301.addElement("dfarf");
		columnNames301.addElement("dmap");
		columnNames301.addElement("AE");
		columnNames301.addElement("Lnrl2");
		
		columnNames302.addElement("vhSpeed");
		columnNames302.addElement("draxSpeed");
		columnNames302.addElement("slip_Calc");
		columnNames302.addElement("osa_Slip");
		
		columnNames304.addElement("TEROGBase");
		columnNames304.addElement("TEROG");
		columnNames304.addElement("SABase");
		columnNames304.addElement("SA");
		
		columnNames305.addElement("FREE");
		columnNames305.addElement("FREE");
		columnNames305.addElement("CLC1");
		columnNames305.addElement("CLC2");
		
		columnNames306.addElement("GEAR");
		columnNames306.addElement("GearShiftTimeRemain");
		columnNames306.addElement("Poil");
		columnNames306.addElement("Pfuel");
		
		columnNames307.addElement("Baro");
		columnNames307.addElement("LNR3L");
		columnNames307.addElement("LNR4L");
		columnNames307.addElement("FREE");
		
		columnNames308.addElement("VbattDir");
		columnNames308.addElement("VbattKey");
		columnNames308.addElement("FREE");
		columnNames308.addElement("FREE");
		
		columnNames309.addElement("FREE");
		columnNames309.addElement("FREE");
		columnNames309.addElement("LNR5L");
		columnNames309.addElement("LNR6L");

		
		
		datasetRpm.addSeries(seriesRpm);
		datasetTps.addSeries(seriesTps);
		
		// la lsta di log la lascio pubblica apposta 
		listDataLog = new DefaultListModel<String>();
		listLog = new DefaultListModel<String>();
		
	}
	
	//singleton
	public static TelemetryData getInstance ( ){
		if ( instance == null ){
			instance = new TelemetryData();
			
		}
		return instance;
	}
	
	
	public void setDataID300 ( Integer rpm , Integer tps , Integer map , Integer Lnrl1  ){
		Vector vetPacketApp = new Vector();
		
		vetPacketApp.add(rpm);
		vetPacketApp.add(tps);
		vetPacketApp.add(map);
		vetPacketApp.add(Lnrl1);
		
		datiTelemetriaID300.add(vetPacketApp);

		if ( this.modelID300 != null){
            
			this.modelID300.setValueAt( rpm,   0 ,  0);
			this.modelID300.setValueAt( tps,   0 ,  1);
			this.modelID300.setValueAt( map,   0 ,  2);
			this.modelID300.setValueAt( Lnrl1, 0 ,  3);
		}
		
		UptadeRpmChart(rpm);
		UptadeFarfChart(tps);
		UptadeTpsChart(tps);
	}
	
	public void setDataID301 ( Integer dfarf , Integer dmap , Integer AE, Integer Lnrl2  ){
		
        Vector vetPacketApp = new Vector();
		
		vetPacketApp.add(dfarf);
		vetPacketApp.add(dmap);
		vetPacketApp.add(AE);
		vetPacketApp.add(Lnrl2);
		
		datiTelemetriaID301.add(vetPacketApp);
		
		if ( this.modelID301 != null){
			this.modelID301.setValueAt( dfarf,   0 ,  0);
			this.modelID301.setValueAt( dmap,    0 ,  1);
			this.modelID301.setValueAt( AE,      0 ,  2);
			this.modelID301.setValueAt( Lnrl2,   0 ,  3);
        }
	}
	
	public void setDataID302 ( Integer vhSpeed , Integer draxSpeed , Integer slip_Calc, Integer osa_Slip  ){
		
        Vector vetPacketApp = new Vector();
		
		vetPacketApp.add(vhSpeed);
		vetPacketApp.add(draxSpeed);
		vetPacketApp.add(slip_Calc);
		vetPacketApp.add(osa_Slip);
		
		datiTelemetriaID302.add(vetPacketApp);
		
		if ( this.modelID302 != null){
			this.modelID302.setValueAt( vhSpeed,   0 ,  0);
			this.modelID302.setValueAt( draxSpeed, 0 ,  1);
			this.modelID302.setValueAt( slip_Calc, 0 ,  2);
			this.modelID302.setValueAt( osa_Slip,  0 ,  3);
		}
	}
	
	public void setDataID304 ( Integer TEROGBase , Integer TEROG , Float SABase, Float SA  ){
		
        Vector vetPacketApp = new Vector();
		
		vetPacketApp.add(TEROGBase);
		vetPacketApp.add(TEROG);
		vetPacketApp.add(SABase);
		vetPacketApp.add(SA);
		
		datiTelemetriaID304.add(vetPacketApp);

		if ( this.modelID304 != null){
            
			this.modelID304.setValueAt( TEROGBase,   0 ,  0);
			this.modelID304.setValueAt( TEROG,       0 ,  1);
			this.modelID304.setValueAt( SABase,      0 ,  2);
			this.modelID304.setValueAt( SA,          0 ,  3);
		}
	}
	
	public void setDataID305 ( Integer FREE1 , Integer FREE2 , Float CLC1, Float CLC2  ){
		
		Vector vetPacketApp = new Vector();
		
		vetPacketApp.add(FREE1);
		vetPacketApp.add(FREE2);
		vetPacketApp.add(CLC1);
		vetPacketApp.add(CLC2);
		
		datiTelemetriaID305.add(vetPacketApp);
		
		if ( this.modelID305 != null){
            
			this.modelID305.setValueAt( FREE1,   0 ,  0);
			this.modelID305.setValueAt( FREE2,   0 ,  1);
			this.modelID305.setValueAt( CLC1,    0 ,  2);
			this.modelID305.setValueAt( CLC2,    0 ,  3);
		}
	}
	
	public void setDataID306 ( Integer GEAR , Integer GearShiftTimeRemain , Integer Poil, Integer Pfuel  ){
        
		Vector vetPacketApp = new Vector();
		
		vetPacketApp.add(GEAR);
		vetPacketApp.add(GearShiftTimeRemain);
		vetPacketApp.add(Poil);
		vetPacketApp.add(Pfuel);
		
		datiTelemetriaID306.add(vetPacketApp);
		
		if ( this.modelID306 != null){
            
			this.modelID306.setValueAt( GEAR,   			   0 ,  0);
			this.modelID306.setValueAt( GearShiftTimeRemain,   0 ,  1);
			this.modelID306.setValueAt( Poil,   			   0 ,  2);
			this.modelID306.setValueAt( Pfuel,   			   0 ,  3);
		}
    
	}
	
	public void setDataID307 ( Integer Baro , Integer LNR3L , Integer LNR4L, Integer FREE  ){
        
		Vector vetPacketApp = new Vector();
		
		vetPacketApp.add(Baro);
		vetPacketApp.add(LNR3L);
		vetPacketApp.add(LNR4L);
		vetPacketApp.add(FREE);
		
		datiTelemetriaID307.add(vetPacketApp);
	
		if ( this.modelID307 != null){
			this.modelID307.setValueAt( Baro,    0 ,  0);
			this.modelID307.setValueAt( LNR3L,   0 ,  1);
			this.modelID307.setValueAt( LNR4L,   0 ,  2);
			this.modelID307.setValueAt( FREE,    0 ,  3);
		}
	}
	
	public void setDataID308 ( Integer VbattDir , Integer VbattKey , Integer FREE1, Integer FREE2  ){
		
        Vector vetPacketApp = new Vector();
		
		vetPacketApp.add(VbattDir);
		vetPacketApp.add(VbattKey);
		vetPacketApp.add(FREE1);
		vetPacketApp.add(FREE2);
		
		datiTelemetriaID308.add(vetPacketApp);
		
		if ( this.modelID308 != null){
			this.modelID308.setValueAt( VbattDir,    0 ,  0);
			this.modelID308.setValueAt( VbattKey,    0 ,  1);
			this.modelID308.setValueAt( FREE1,       0 ,  2);
			this.modelID308.setValueAt( FREE2,       0 ,  3);
		}
	}
	
	public void setDataID309 ( Integer FREE1 , Integer FREE2 , Integer LNR5L, Integer LNR6L  ){
		
        Vector vetPacketApp = new Vector();
		
		vetPacketApp.add(FREE1);
		vetPacketApp.add(FREE2);
		vetPacketApp.add(LNR5L);
		vetPacketApp.add(LNR6L);
		
		datiTelemetriaID309.add(vetPacketApp);
		
		if ( this.modelID309 != null){
            
			this.modelID309.setValueAt( FREE1,       0 ,  0);
			this.modelID309.setValueAt( FREE2,       0 ,  1);
			this.modelID309.setValueAt( LNR5L,       0 ,  2);
			this.modelID309.setValueAt( LNR6L,       0 ,  3);
		}
	}
	
    
	public Vector<Vector> getDatiTelemetriaID300(){
		return this.datiTelemetriaID300;
		
	}
    
	public Vector<Vector> getDatiTelemetriaID301(){
		return this.datiTelemetriaID301;
		
	}
    
	public Vector<Vector> getDatiTelemetriaID302(){
		return this.datiTelemetriaID302;
		
	}
    
	public Vector<Vector> getDatiTelemetriaID304(){
		return this.datiTelemetriaID304;
		
	}
    
	public Vector<Vector> getDatiTelemetriaID305(){
		return this.datiTelemetriaID305;
		
	}
    
	public Vector<Vector> getDatiTelemetriaID306(){
		return this.datiTelemetriaID306;
		
	}
    
	public Vector<Vector> getDatiTelemetriaID307(){
		return this.datiTelemetriaID307;
		
	}
    
	public Vector<Vector> getDatiTelemetriaID308(){
		return this.datiTelemetriaID308;
		
	}
    
	public Vector<Vector> getDatiTelemetriaID309(){
		return this.datiTelemetriaID309;
		
	}
	

	public void DeleteDataID300(){
        
		this.datiTelemetriaID300.removeAllElements();
		indexData300 = 0;
	}
    
	public void DeleteDataID301(){
        
		this.datiTelemetriaID301.removeAllElements();
		indexData301 = 0;
	}
    
	public void DeleteDataID302(){
        
		this.datiTelemetriaID302.removeAllElements();
		indexData302 = 0;
	}
    
	public void DeleteDataID304(){
        
		this.datiTelemetriaID304.removeAllElements();
		indexData304 = 0;
	}
	
    public void DeleteDataID305(){
        
		this.datiTelemetriaID305.removeAllElements();
		indexData305 = 0;
	}
	public void DeleteDataID306(){
        
		this.datiTelemetriaID306.removeAllElements();
		indexData306 = 0;
	}
	public void DeleteDataID307(){
      
		this.datiTelemetriaID307.removeAllElements();
		indexData307 = 0;
	}
    
	public void DeleteDataID308(){
        
		this.datiTelemetriaID308.removeAllElements();
		indexData308 = 0;
	}
	public void DeleteDataID309(){
        
		this.datiTelemetriaID309.removeAllElements();
		indexData309 = 0;
	}
	
	
	

	public Vector<String> getColoumn300(){
        
		return this.columnNames300;
	}
    
	public Vector<String> getColoumn301(){
        
		return this.columnNames301;
	}
    
	public Vector<String> getColoumn302(){
        
		return this.columnNames302;
	}
    
	public Vector<String> getColoumn304(){
        
		return this.columnNames304;
	}
    
	public Vector<String> getColoumn305(){
        
		return this.columnNames305;
	}
    
	public Vector<String> getColoumn306(){
        
		return this.columnNames306;
	}
    
	public Vector<String> getColoumn307(){
        
		return this.columnNames307;
	}
    
	public Vector<String> getColoumn308(){
        
		return this.columnNames308;
	}
    
	public Vector<String> getColoumn309(){
        
		return this.columnNames309;
	}
	

	public void setTableModel ( AbstractTableModel model , String modelID){
		
		switch ( modelID ){
			case "300":
			{
				this.modelID300 = model;
				break;
			}
			case "301":
			{
				this.modelID301 = model;
				break;
			}
			case "302":
			{
				this.modelID302 = model;
				break;
			}
			case "304":
			{
				this.modelID304 = model;
				break;
			}
			case "305":
			{
				this.modelID305 = model;
				break;
			}
			case "306":
			{
				this.modelID306 = model;
				break;
			}
			case "307":
			{
				this.modelID307 = model;
				break;
			}
			case "308":
			{
				this.modelID308 = model;
				break;
			}
			case "309":
			{
				this.modelID309 = model;
				break;
			}
				
		}
	}
	
	public Vector<String> getNameOfDataFromAdress (String address ){
        
		switch (address){
			case "300":
				return this.getColoumn300();

			case "301":
				return this.getColoumn301();

			case "302":
				return this.getColoumn302();

			case "304":
				return this.getColoumn304();

			case "305":
				return this.getColoumn305();
				
			case "306":
				return this.getColoumn306();

			case "307":
				return this.getColoumn307();
				
			case "308":
				return this.getColoumn308();
				
			case "309":
				return this.getColoumn309();
		
			
		}
		return null;
	}
	
	public Vector<Vector> getDatifromAddress ( String address ){
		
		switch (address){
			case "300":
				return this.getDatiTelemetriaID300();

			case "301":
				return this.getDatiTelemetriaID301();

			case "302":
				return this.getDatiTelemetriaID302();

			case "304":
				return this.getDatiTelemetriaID304();

			case "305":
				return this.getDatiTelemetriaID305();
				
			case "306":
				return this.getDatiTelemetriaID306();

			case "307":
				return this.getDatiTelemetriaID307();
				
			case "308":
				return this.getDatiTelemetriaID308();
				
			case "309":
				return this.getDatiTelemetriaID309();
			
		}
		return null;
		
	}
	
	public void DeleteDatifromAdress ( String address ){
        
		switch (address){
			case "300":
			{
				this.DeleteDataID300();
				break;
			}

			case "301":
			{
				this.DeleteDataID301();
				break;
			}

			case "302":
			{
				this.DeleteDataID302();
				break;
			}

			case "304":
			{
				this.DeleteDataID304();
				break;
			}

			case "305":
			{
				this.DeleteDataID305();
				break;
			}
				
			case "306":
			{
				this.DeleteDataID306();
				break;
			}

			case "307":
			{
				this.DeleteDataID307();
				break;
			}
				
			case "308":
			{
				this.DeleteDataID308();
				break;
			}
				
			case "309":
			{
				this.DeleteDataID309();
				break;
			}

		}
	
	
	}
	
	public void UptadeRpmChart( Integer rpm ){
        
		seriesRpm.add( timeToRpmChart, rpm);
		timeToRpmChart++;
		
	}
	public void UptadeTpsChart( Integer tps ){
        
		seriesTps.add( timeToTpsChart, tps);
		timeToTpsChart++;

	}
	
	public void UptadeFarfChart ( Integer farf ){
        
		this.datasetFarf.setValue(farf);
	}
	
	public void RestartTimeGraphRPM(){
        
		timeToRpmChart = 0.0;
		seriesRpm.clear();
	}
	
	public void RestartTimeGraphTPS(){
        
		timeToTpsChart = 0.0;
		seriesTps.clear();
	}
}
