import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import javax.swing.DefaultListModel;

import com.digi.xbee.api.listeners.IDataReceiveListener;
import com.digi.xbee.api.models.XBeeMessage;
import com.digi.xbee.api.utils.HexUtils;

/**
 * Class to manage the XBee received data that was sent by other modules in the 
 * same network.
 * 
 * <p>Acts as a data listener by implementing the 
 * {@link IDataReceiveListener} interface, and is notified when new 
 * data for the module is received.</p>
 * 
 * @see IDataReceiveListener
 *
 */
public class MyDataReceiveListener  implements IDataReceiveListener  {
	/*
	 * (non-Javadoc)
	 * @see com.digi.xbee.api.listeners.IDataReceiveListener#dataReceived(com.digi.xbee.api.models.XBeeMessage)
	 */

	
	private Boolean stop = false;
	
	private Thread threadDecode;
	private Boolean statoRipartenzaThread = false;
	private Boolean statoThread = false;
	
	public Integer rpm , tps1 , map , lnr1l , dfarf , dmap , ae , lnr2l , vhSpeed , draxSpeed , slip_Calc , osa_slip , TEROGBase , TEROG , SABase , SA , CLC1 , CLC2 , GEAR , GearShiftTimeRemain , Poil , Pfuel , Baro , LNR3L , LNR4L , VbattKey , VbattDir , LNR5L , LNR6L;     
	
	protected DefaultListModel<String> listData;
	


	@Override
	public void dataReceived(XBeeMessage xbeeMessage) {
		
		String message = new String(xbeeMessage.getData());
		System.out.println("packet : " + message);
		
		String[] items = message.split(",");
		
	
		for ( String item : items ){
			System.out.println("packet : " + item);
			String canName = item.substring(2 , 6);
			String canAdress = item.substring(8, 11);// lo metto intero perchè non funziona
			Integer canLenght = Character.getNumericValue(item.charAt(15));
			String canPacket = item.substring(19, 42);
		
			
			if ( canAdress.equals("300")){
				
                String strRpm = canPacket.substring(0, 2) + canPacket.substring(3, 5);
                rpm = Integer.parseInt(strRpm, 16 );
                
                
                String strTps1 = canPacket.substring(6, 8);
                tps1 = Integer.parseInt( strTps1 ,16 ) ;
                tps1 = ((tps1 * 100 )/256) + 1;
                
                String strMap =  canPacket.substring( 12 ,14) + canPacket.substring( 15,17);
                map = Integer.parseInt(strMap, 16 );
                
                String strLnr1l = canPacket.substring( 18,20) + canPacket.substring( 21 ,23);
                lnr1l = Integer.parseInt(strLnr1l, 16 );
                
                TelemetryData.getInstance().setDataID300(rpm,tps1,map, lnr1l); // SI BLOCCA QUA
                
                TelemetryData.getInstance().listDataLog.addElement("RPM : " + rpm + "     TPS1 : " + tps1 + "%     MAP : " + map + "     LNR1L : " + lnr1l);
                TelemetryData.getInstance().listDataLog.addElement("");
				
			}
			
			if ( canAdress.equals("301")){

                
                 dfarf = Integer.parseInt(canPacket.substring(0, 2), 16 );
                 dmap = Integer.parseInt(canPacket.substring(3, 5) ,16 );
                 ae = Integer.parseInt(canPacket.substring( 6,8), 16 );
                 lnr2l = Integer.parseInt(canPacket.substring(9, 11), 16 );
                
                TelemetryData.getInstance().setDataID301(dfarf,dmap,ae, lnr2l);
 
                TelemetryData.getInstance().listDataLog.addElement("DARF : " + dfarf + "     DAMPA : " + dmap + "     AE : " + ae + "     LNR2L : " + lnr2l);
                
                TelemetryData.getInstance().listDataLog.addElement("");
				
			}
			if ( canAdress.equals("302")){

                vhSpeed = Integer.parseInt(canPacket.substring(0, 2), 16 );
                draxSpeed = Integer.parseInt(canPacket.substring(3, 5) ,16 );
                slip_Calc = Integer.parseInt(canPacket.substring( 6,8), 16 );
                osa_slip = Integer.parseInt(canPacket.substring(9, 11), 16 );
				 
                TelemetryData.getInstance().setDataID302(vhSpeed,draxSpeed,slip_Calc, osa_slip);
				  
				TelemetryData.getInstance().listDataLog.addElement("\nVHSPEED : " + vhSpeed + "     DRAXSPEED : " + draxSpeed + "     SLIP_CALC : " + slip_Calc + "     OSA_SLIP : " + osa_slip);
				TelemetryData.getInstance().listDataLog.addElement("");
				
			}
			
			if ( canAdress.equals("304")){

                TEROGBase = Integer.parseInt(canPacket.substring(0, 2) + canPacket.substring(3, 5) , 16 );
                TEROG = Integer.parseInt(canPacket.substring(6, 8) + canPacket.substring( 9,11) ,16 );
                SABase = Integer.parseInt(canPacket.substring( 12,14) + canPacket.substring( 15,17), 16 );
                SA = Integer.parseInt(canPacket.substring(18, 20) + canPacket.substring(21, 23), 16 );
				
                Float SABaseApp = (float) (SABase * 0.25);
                Float SAApp = (float) (SA * 0.25);

                TelemetryData.getInstance().setDataID304(TEROGBase,TEROG,SABaseApp, SAApp);

                
                TelemetryData.getInstance().listDataLog.addElement("TEROGBase : " + TEROGBase + "     TEROG : " + TEROG + "     SABase : " + SABaseApp + "     SA : " + SAApp );
                TelemetryData.getInstance().listDataLog.addElement("");
			}
			
			if ( canAdress.equals("305")){
		
                 CLC1 = Integer.parseInt(canPacket.substring( 6,8), 16 );
                 CLC2 = Integer.parseInt(canPacket.substring(9, 11), 16 );
                
                 Float CLC1App = (float) (CLC1 * 0.05);
                 Float CLC2App = (float) (CLC2 * 0.05);
                
                 TelemetryData.getInstance().setDataID305(null,null,CLC1App, CLC2App);

                
                 TelemetryData.getInstance().listDataLog.addElement("CLC1 : " + CLC1App + "    CLC2 : " + CLC2App );
                 TelemetryData.getInstance().listDataLog.addElement("");
			}
            
			if ( canAdress.equals("306")){

				 GEAR = Integer.parseInt(canPacket.substring(0, 2), 16 );
				 GearShiftTimeRemain = Integer.parseInt(canPacket.substring(3, 5) ,16 );
				 Poil = Integer.parseInt(canPacket.substring( 6,8), 16 );
				 Pfuel = Integer.parseInt(canPacket.substring(9, 11), 16 );
								 
				 TelemetryData.getInstance().setDataID306(GEAR,GearShiftTimeRemain,Poil, Pfuel); 

				 TelemetryData.getInstance().listDataLog.addElement("GEAR : " + GEAR + "     GearShiftTimeRemain : " + GearShiftTimeRemain + "     Poil : " + Poil + "     Pfuel : " + Pfuel );
				 TelemetryData.getInstance().listDataLog.addElement("");
			}
            
			if ( canAdress.equals("307")){
                
				 Baro = Integer.parseInt(canPacket.substring(0, 2), 16 );
				 LNR3L = Integer.parseInt(canPacket.substring(3, 5) ,16 );
				 LNR4L = Integer.parseInt(canPacket.substring( 6,8), 16 );
				 
				 TelemetryData.getInstance().setDataID307(Baro,LNR3L,LNR4L, null); 

                 TelemetryData.getInstance().listDataLog.addElement( "Baro " + Baro + "   LNR3L " + LNR3L + "   LNR4L " + LNR4L );
				 TelemetryData.getInstance().listDataLog.addElement("");
			}
            
			if ( canAdress.equals("308")){
				 
				 VbattDir = Integer.parseInt(canPacket.substring(0, 2) + canPacket.substring(3, 5) , 16 );
				 VbattKey = Integer.parseInt(canPacket.substring(6, 8) + canPacket.substring(9, 11) ,16 );
				 				 
				 VbattDir = (( VbattDir * 18)/ 1024) + 1;
				 VbattKey = (( VbattKey * 18)/ 1024) + 1;
				 
				 TelemetryData.getInstance().setDataID308(VbattDir,VbattKey, null , null);
				 
				 TelemetryData.getInstance().listDataLog.addElement( "VbattDir " + VbattDir + "   VbattKey " + VbattKey );
				 TelemetryData.getInstance().listDataLog.addElement("");
			}
            
			if ( canAdress.equals("309")){
				 
				 LNR5L = Integer.parseInt(canPacket.substring( 6,8), 16 );
				 LNR6L = Integer.parseInt(canPacket.substring(9, 11), 16 );
				 				 
				 TelemetryData.getInstance().setDataID309(null,null, LNR5L , LNR6L); 

				 TelemetryData.getInstance().listDataLog.addElement( "LNR5L " + LNR5L + "   LNR6L " + LNR6L  );
				 TelemetryData.getInstance().listDataLog.addElement("");
			}
		}
	}
}
