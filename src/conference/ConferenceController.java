package conference;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;

import networking.WeSketchClient;

import shared.WeSketchConstants;
import shared.SessionParticipant;

/**
 * This class controls the Transmission and Reception of audio, managing
 * the Targets to which transmit and the Targets to receive. 
 * @author Hamilan
 */
public class ConferenceController {
	public AVTransmitter avTransmitter;
	public AVReceiver avReceiver;

	public Vector<Target> receiverTargets = new Vector<Target>(WeSketchConstants.MAX_SESSION_PARTICIPANTS); //a quienes escuchará este Receiver 
	public Vector<Target> transmitterTargets = new Vector<Target>(WeSketchConstants.MAX_SESSION_PARTICIPANTS); //a quienes enviará este Transmitter
	public Vector<String> globalIPs = new Vector<String>(WeSketchConstants.MAX_SESSION_PARTICIPANTS);
	public Vector<String> conferenceIPs = new Vector<String>(WeSketchConstants.MAX_SESSION_PARTICIPANTS);
	private Vector<String> globalLogins = new Vector<String>(WeSketchConstants.MAX_SESSION_PARTICIPANTS);
	public Vector<Integer> globalColors = new Vector<Integer>(WeSketchConstants.MAX_SESSION_PARTICIPANTS);

	private static final int DATAPORT = 20000;
	private String defaultTransmitterTargetPort;
	private String myIP;
	private String mediaToTransmit = "javasound://8000";
	private ConferenceControlFrame myConferenceControlFrame;

	/**
	 * 
	 */
	public ConferenceController(SessionParticipant[] participants) {
		configureMyInformation();
		if(participants!=null){
			setParticipants( participants );
		}
	}

	private void configureMyInformation(){
		try {
			myIP=InetAddress.getLocalHost().getHostAddress();
			String lastPartOfMyIP =myIP.substring( myIP.lastIndexOf(".")+1 );
			defaultTransmitterTargetPort = (DATAPORT+Integer.parseInt(lastPartOfMyIP)*2)+"";
			System.out.println(myIP+"-"+defaultTransmitterTargetPort );
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	public void setParticipants(SessionParticipant[] participants) {
		stopSound();
		transmitterTargets = new Vector<Target>(WeSketchConstants.MAX_SESSION_PARTICIPANTS);
		receiverTargets = new Vector<Target>(WeSketchConstants.MAX_SESSION_PARTICIPANTS);
		globalIPs = new Vector<String>(WeSketchConstants.MAX_SESSION_PARTICIPANTS);
		conferenceIPs = new Vector<String>(WeSketchConstants.MAX_SESSION_PARTICIPANTS);
		globalColors = new Vector<Integer>(WeSketchConstants.MAX_SESSION_PARTICIPANTS);
		globalLogins = new Vector<String>(WeSketchConstants.MAX_SESSION_PARTICIPANTS);

		for (SessionParticipant p: participants) {
			if(p!=null && p.id!=WeSketchClient.myParticipantInfo.id){
				globalIPs.add(p.ip);
				conferenceIPs.add(p.ip);
				globalColors.add(p.chairIndexOrColor);
				globalLogins.add(p.login);
			}
		}
	}
	/**
	 * Creates targets for Transmission and Reception based on an
	 *  IP addresses Vector
	 * @param ips
	 */
	private void setTargets(Vector<String> ips) {
		createTransmitterTargets(ips);
		createReceiverTargets(ips);
		imprimirTargets();
	}

	void removeAllTargets() {
		stopSound();
		transmitterTargets = new Vector<Target>(WeSketchConstants.MAX_SESSION_PARTICIPANTS);
		receiverTargets = new Vector<Target>(WeSketchConstants.MAX_SESSION_PARTICIPANTS);
	}
	/**
	 * Creates targets for the Transmitter based on an IP addresses Vector
	 * @param ips the Vector with IPs
	 */
	private void createTransmitterTargets(Vector<String> ips) {
		transmitterTargets= new Vector<Target>(WeSketchConstants.MAX_SESSION_PARTICIPANTS);
		for (int i = 0; i < ips.size(); i++){
			if( !ips.get(i).equals(myIP) ){
				Target target= new Target(""+DATAPORT, ips.get(i), defaultTransmitterTargetPort);
				transmitterTargets.add(target);
			}
		}
	}
	/**
	 * Creates targets for the Receiver based on an IP addresses Vector
	 * @param ips the Vector with IPs
	 */
	private void createReceiverTargets(Vector<String> ips) {
		receiverTargets= new Vector<Target>(WeSketchConstants.MAX_SESSION_PARTICIPANTS);
		Target target;
		for (int i = 0; i < ips.size(); i++) {
			if( !ips.get(i).equals(myIP) ) {
				String num=ips.get(i).substring( ips.get(i).lastIndexOf(".")+1 );
				int numFinal= DATAPORT+(Integer.parseInt(num)*2);
				num=numFinal+"";
				target= new Target(num, ips.get(i), DATAPORT+"");
				receiverTargets.add( target );
			}
		}
	}

	private void imprimirTargets() 
	{	
		System.out.println("  ");
		System.out.println("Targets Transmitter :");
		for (int i = 0; i < transmitterTargets.size(); i++) {
			System.out.println(transmitterTargets.get(i).localPort+"<---"+transmitterTargets.get(i).ip+":"+transmitterTargets.get(i).port);
		}

		System.out.println("  ");
		System.out.println("Targets Receiver :");
		for (int i = 0; i < receiverTargets.size(); i++) {
			System.out.println(receiverTargets.get(i).localPort+"<---"+receiverTargets.get(i).ip+":"+receiverTargets.get(i).port);
		}

	}

	/**
	 * Enables the participant in position numparticipant 
	 * 
	 */
	public void addIp(int numParticipant) {
		String ip= globalIPs.get(numParticipant);
		conferenceIPs.add(ip);

	}
	public void addTarget(SessionParticipant p){
		globalIPs.add(p.ip);
		conferenceIPs.add(p.ip);
		globalColors.add(p.chairIndexOrColor);
		globalLogins.add(p.login);

		myConferenceControlFrame.update(globalLogins,globalColors);
		
		Target target= new Target(""+DATAPORT, p.ip, defaultTransmitterTargetPort);
		transmitterTargets.add(target);

		String num=p.ip.substring( p.ip.lastIndexOf(".")+1 );
		int numFinal= DATAPORT+(Integer.parseInt(num)*2);
		num=numFinal+"";
		Target target2= new Target(num, p.ip, DATAPORT+"");
		receiverTargets.add( target2 );

		if(avTransmitter!=null && avReceiver!=null){
			avTransmitter.addTarget(p.ip, defaultTransmitterTargetPort);
			avReceiver.addTarget(target2.ip, target2.port, target2.localPort);
		}
	}

	/**
	 * Removes a Target from the Receiver and the Transmitter Targets
	 */
	public void removeTarget(int index) {
		if( index != -1) {
			System.out.print("RemovingTarget "+index+" ");
			int i=0;
			for (Integer pos : globalColors) {
				if(pos==index){
					index = i;
					break;
				}
				i++;
			}
			System.out.println("found in "+i);
			globalColors.remove(i);
			globalIPs.remove(i);
			globalLogins.remove(i);
			myConferenceControlFrame.update(globalLogins, globalColors);
			
			//remove from transmitter targets
			if(transmitterTargets!=null && transmitterTargets.size()>i){
				Target target= (Target) transmitterTargets.elementAt( i );
				if( avTransmitter != null) {
					avTransmitter.removeTarget( target.ip, target.port);
				}
				transmitterTargets.removeElement( target );

				//remove from receiver targets
				Target targetR= (Target) receiverTargets.elementAt( i );
				if( avReceiver != null) {
					avReceiver.removeTarget( targetR.ip, targetR.port );
				}
				receiverTargets.removeElement( targetR );
			}
		}
	}

	/**
	 * Starts transmission and reception of the media
	 *  to the targets and from the targets 
	 */
	public void startSound( ) {
		setTargets(conferenceIPs);

		//Iniciar el transmisor
		avTransmitter= new AVTransmitter( 20000 );
		avTransmitter.start( mediaToTransmit , transmitterTargets );		
		//Iniciar el receptor
		avReceiver= new AVReceiver( receiverTargets );
	}

	public void updateConferenceIPs(java.util.ArrayList<Integer> selectedIPs) {
		conferenceIPs = new Vector<String>(WeSketchConstants.MAX_SESSION_PARTICIPANTS);
		for (Integer i : selectedIPs) {
			conferenceIPs.add( globalIPs.elementAt(i) );
		}
	}

	/**
	 * Stops transmission and reception of the media
	 *  to the targets and from the targets 
	 */
	public void stopSound( ) {
		//Detener el transmisor
		if(avTransmitter!=null){
			avTransmitter.stop();
			avTransmitter= null;
			removeNonBaseTargets();
			//Detener el receptor
			avReceiver.close();
			avReceiver= null;
		}
	}

	private void removeNonBaseTargets() {
		String localPort= DATAPORT+"";
		for( int i= transmitterTargets.size(); i > 0;) {
			Target target= (Target) transmitterTargets.elementAt( i - 1 );
			if( !target.localPort.equals( localPort)) {
				transmitterTargets.removeElement( target);
			}
			i--;
		}
	}
	public void addAllTargets() {
		conferenceIPs=globalIPs;
	}
	public void setMyConferenceControlFrame( ConferenceControlFrame ccf) {
		this.myConferenceControlFrame = ccf;
		myConferenceControlFrame.update(globalLogins, globalColors);
	}
}
