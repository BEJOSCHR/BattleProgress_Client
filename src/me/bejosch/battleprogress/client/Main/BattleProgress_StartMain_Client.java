package me.bejosch.battleprogress.client.Main;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import me.bejosch.battleprogress.client.Data.ConnectionData;
import me.bejosch.battleprogress.client.Data.FileData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.DiscordAPI.DiscordAPI;
import me.bejosch.battleprogress.client.Game.TimeManager;
import me.bejosch.battleprogress.client.Handler.ClientPlayerHandler;
import me.bejosch.battleprogress.client.Handler.FileHandler;
import me.bejosch.battleprogress.client.Handler.HoverHandler;
import me.bejosch.battleprogress.client.Handler.MenuHandler;
import me.bejosch.battleprogress.client.Handler.OnTopWindowHandler;
import me.bejosch.battleprogress.client.Objects.Animations.Animation_BejoschGamingIntro;
import me.bejosch.battleprogress.client.ServerConnection.MinaClient;
import me.bejosch.battleprogress.client.Window.Frame;
import me.bejosch.battleprogress.client.Window.Label;
import me.bejosch.battleprogress.client.Window.Buttons.Buttons;
import me.bejosch.battleprogress.client.Window.Images.Images;
import me.bejosch.battleprogress.client.Window.ScrollPanes.ScrollPanes;
import me.bejosch.battleprogress.client.Window.TextAreas.TextAreas;
import me.bejosch.battleprogress.client.Window.TextFields.TextFields;

public class BattleProgress_StartMain_Client {

//==========================================================================================================
	/**
	 * With this methode everything starts ;D
	 */
	public static void main(String[] args) {
		
		//IDENTIFICATION SECURE
		//System.setProperty("javax.net.ssl.trustStore", "battleprogress.identification"); //TRUSTSTORE
		//PASSWORT IS ONLY ON THE SERVER!
		
		//SERVERCONNECTION:
		// https://www.youtube.com/watch?v=l4_JIIrMhIQ
		
		//PATHFINDING ALGORYTHEM:
		// https://www.youtube.com/watch?v=-L-WgKMFuhE
		
		//TODO IP: ipcwup.no-ip.biz
		
		// TODO SO NICE: https://www.youtube.com/watch?v=mZfyt03LDH4 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		ConsoleOutput.printMessageInConsole("Starting BattleProgress Client...", true);
		
		//CREATE FILES IF THEY ARE MISSING
		FileHandler.firstWrite();
		
		//USER INPUT SCANNER
		ConsoleOutput.startUserInputScanner();
		
		//CREATES WINDOW
		initialiseVisualDisplay();
		
		//LOADING IMAGES - PRE LOAD
		Images.preLoadImages();
		
		//STARTS TICK
		TimeManager.startTickTimer();
		
		//STARTS PLAYER ONLINE TIMER
		ClientPlayerHandler.startPlayerOnlineTimer();
		
		//START ANIMATION
		new Animation_BejoschGamingIntro();
		
		//CREATE BUTTONS
		Buttons.loadButtons();
		
		//CREATE TEXTFIELDS
		TextFields.loadTextFields();
		
		//CREATE TEXTAREAS
		TextAreas.loadTextAreas();
		
		//CREATE SCROLLPANES
		ScrollPanes.loadScrollPanes();
		
		//INIT OTW (WITH MAAs)
		OnTopWindowHandler.initOTWMAA();
		
		//INIT MENU MAA
		MenuHandler.initMenuMAAs();
		
		//START HOVER CLEAR TIMER
		HoverHandler.startHoverClearTimer();
		
		//CONNECTING TO SERVER
		String serverAdresse = FileHandler.readOutData(FileData.file_Settings, "ServerAdresse");
		if(serverAdresse == null) { serverAdresse = ConnectionData.DEFAULT_IP; }
		int port = ConnectionData.DEFAULT_PORT;
		try{ port = Integer.parseInt(FileHandler.readOutData(FileData.file_Settings, "Port")); }catch(NumberFormatException error) {}
		MinaClient.connectToServer(serverAdresse, port);
		
		//INIT DISCORD API
		try {
			DiscordAPI.initAPI();
			StandardData.discordAPIloaded = true;
		}catch(Exception error) {
			//error.printStackTrace();
			ConsoleOutput.printMessageInConsole("Error while loading DiscordAPI! Proceed without it...", true);
		}
		
		//LOADING IMAGES - MAIN
		Images.loadImages();
		
		ConsoleOutput.printMessageInConsole("Type '/help' for console commands!", true);
		
	}
	
//==========================================================================================================
	/**
	 * Creats all parts of the visual display...
	 */
	public static void initialiseVisualDisplay() {
		
		//FRAME
		WindowData.Frame = Frame.createFrame();
		
		//LABEL
		WindowData.Label_Main = new Label(0, 0);
		
		ConsoleOutput.printMessageInConsole("Visual displays are now running!", true);
		
		//PREVENT GREY WINDOW:
		WindowData.Label_Main.requestFocus();
		WindowData.Frame.requestFocus();
//		new Timer().schedule(new TimerTask() {
//			@Override
//			public void run() {
//				WindowData.Label_Main.requestFocus();
//				WindowData.Frame.requestFocus();
//			}
//		}, 1000);
		
	}
	
//==========================================================================================================
	/**
	 * The SSLContext with the battleprogress.identification identification key
	 * @return SSLContext - The conetxt
	 */
	public static SSLContext getSSLContext() {
		
		SSLContext sslContext = null;
		
		try {
			
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			InputStream keystoreStream = BattleProgress_StartMain_Client.class.getResourceAsStream("Identification/battleprogress.identification");
			keystore.load(keystoreStream, null);
			trustManagerFactory.init(keystore);
			TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
			sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustManagers, null);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
		
		return sslContext;
		
	}

}
