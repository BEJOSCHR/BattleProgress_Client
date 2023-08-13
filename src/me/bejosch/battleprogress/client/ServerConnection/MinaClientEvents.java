package me.bejosch.battleprogress.client.ServerConnection;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import me.bejosch.battleprogress.client.Data.ConnectionData;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;

public class MinaClientEvents extends IoHandlerAdapter  {

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		cause.printStackTrace();
	}
	
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		
		ConnectionData.serverConnectionEstablished = true;
		ConsoleOutput.printMessageInConsole("Connection established", true);
		
	}
	
	@Override
	public void sessionClosed(IoSession session) {
		
		ConnectionData.serverConnectionEstablished = false;
		if(ConsoleOutput.closingTheGame == true) {
			ConsoleOutput.printMessageInConsole("Connection closed", true);
		}else {
			ConsoleOutput.printMessageInConsole("Lost connection to server!", true);
			MinaClient.handleConnectionlos();
		}
		
	}
	
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		
		String data = (String) message;
		int signal = Integer.parseInt(data.substring(0, 3).trim());
		int id = Integer.parseInt(data.substring(4, ConnectionData.PACKAGEIDLENGTH+4).trim());
		String answer = null;
		if(data.length() > ConnectionData.PACKAGEIDLENGTH+1+4) {
			answer = data.substring(ConnectionData.PACKAGEIDLENGTH+1+4, data.length()).trim();
		}
		
		MinaClient.handlePackage(signal, id, answer);
	    
	}
	
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {}
	
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {}
	
}
