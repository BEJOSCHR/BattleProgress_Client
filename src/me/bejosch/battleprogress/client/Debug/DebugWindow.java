package me.bejosch.battleprogress.client.Debug;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import me.bejosch.battleprogress.client.Data.FileData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;

public class DebugWindow {

	public static JFrame DebugFrame = null;
	public static JLabel DebugLabel = null;
	public static JTextArea DebugTextArea = null;
	public static JScrollPane DebugScrollPane = null;
	
	public static boolean coordinatesDebug = false;
	
//==========================================================================================================
	/**
	 * Start a new debug instance if there isn't an other
	 */
	public static void startDebug() {
		
		if(DebugFrame == null) {
			DebugFrame = createDebugFrame();
			DebugLabel = new DebugLabel();
			DebugTextArea = createTextArea();
			DebugScrollPane = createScrollPane();
			DebugTextArea.requestFocus();
			DebugScrollPane.requestFocus();
			
			ConsoleOutput.printMessageInConsole("Debug started", true);
		}
		
	}
	
//==========================================================================================================
	/**
	 * Activate the coordinates display on the main screen
	 */
	public static void startCoordinatesDebug() {
		
		if(coordinatesDebug == false) {
			coordinatesDebug = true;
			ConsoleOutput.printMessageInConsole("CoordinatesDebug started", true);
		}else{
			coordinatesDebug = false;
			ConsoleOutput.printMessageInConsole("CoordinatesDebug ended", true);
		}
		
	}
	
//==========================================================================================================
	/**
	 * Create a new JTextArea
	 * @return {@link JTextArea} - The created textArea
	 */
	public static JTextArea createTextArea() {
		
		final JTextArea textArea = new JTextArea();
		textArea.setVisible(true);
		textArea.setEditable(false);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(false);
		textArea.setBackground(Color.DARK_GRAY);
		textArea.setForeground(Color.WHITE);
		
		DebugLabel.add(textArea);
		
		return textArea;
	}
	
//==========================================================================================================
	/**
	 * Create a new JScrollPane
	 * @return {@link JScrollPane} - The created scrollPane
	 */
	public static JScrollPane createScrollPane() {
		
		int UPDOWN = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS;
		int LEFTRIGHT = JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS;
		
		final JScrollPane scrollPane = new JScrollPane(DebugTextArea, UPDOWN, LEFTRIGHT);
		scrollPane.setVisible(true);
		
		DebugLabel.add(scrollPane);
		
		return scrollPane;
	}
	
//==========================================================================================================
	/**
	 * Creates the Debug Frame ...
	 * @return JFrame - On the Frame everything will be displayed
	 */
	public static JFrame createDebugFrame() {
		
		JFrame Frame1 = new JFrame();
		Frame1.setVisible(true);
		Frame1.setLocationRelativeTo(null);
		Frame1.setLocation(WindowData.Frame.getX(), WindowData.Frame.getY());
		Frame1.setLayout(null);
		Frame1.setTitle("BattleProgress - Debug");
		Frame1.setResizable(true); //FALSE
		Frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		Frame1.addKeyListener(new KeyHandler());
//		Frame1.addMouseListener(new MouseHandler());
//		Frame1.addMouseMotionListener(new MouseHandler());
//		Frame1.addMouseWheelListener(new MouseHandler());
		Frame1.addWindowListener(new WindowListener() { 
			
			@Override
			public void windowOpened(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
			@Override
			public void windowActivated(WindowEvent e) {}
			@Override
			public void windowClosing(WindowEvent e) {}
			
			@Override
			public void windowClosed(WindowEvent e) {
				
				DebugFrame = null;
				DebugLabel = null;
				DebugTextArea = null;
				DebugScrollPane = null;
				
			}
			
		});
		try { //TRY TO SET ICON
			Frame1.setIconImage(ImageIO.read(new File(FileData.Ordner+"/Images/"+"Logo.png")));
		} catch (Exception e) {
			ConsoleOutput.printMessageInConsole("The DEBUG Window Icon couldn't be loaded!", true);
		}
		Frame1.setSize(400, 600);
		Frame1.requestFocus();
		
		return Frame1;
	}
	
}
