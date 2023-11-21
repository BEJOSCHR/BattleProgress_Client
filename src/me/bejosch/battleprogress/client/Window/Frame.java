package me.bejosch.battleprogress.client.Window;

import java.awt.BorderLayout;
import java.awt.KeyboardFocusManager;
import java.io.File;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import me.bejosch.battleprogress.client.Data.FileData;
import me.bejosch.battleprogress.client.Data.StandardData;
import me.bejosch.battleprogress.client.Data.WindowData;
import me.bejosch.battleprogress.client.Handler.KeyHandler;
import me.bejosch.battleprogress.client.Handler.MouseHandler;
import me.bejosch.battleprogress.client.Handler.WindowHandler;
import me.bejosch.battleprogress.client.Main.ConsoleOutput;

public class Frame {
	
//==========================================================================================================
	/**
	 * Creates the Basic Frame ...
	 * @return JFrame - The Frame everything will be displayed
	 */
	public static JFrame createFrame() {
		
		JFrame Frame1 = new JFrame();
		
		Frame1.setUndecorated(true);
		
		Frame1.setVisible(true);
		Frame1.setLocationRelativeTo(null);
		Frame1.setLocation(0, 0);
		Frame1.setLayout(new BorderLayout(0, 0));
		Frame1.setTitle("BattleProgress");
//		Frame1.setTitle("BattleProgress - "+StandardData.clientVersion);
		Frame1.setResizable(false); 
		Frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame1.addKeyListener(new KeyHandler());
		Frame1.addMouseListener(new MouseHandler());
		Frame1.addMouseMotionListener(new MouseHandler());
		Frame1.addMouseWheelListener(new MouseHandler());
		Frame1.addWindowListener(new WindowHandler());
		Frame1.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.emptySet());
		
		try { //TRY TO SET ICON
			Frame1.setIconImage(ImageIO.read(new File(FileData.Ordner+"/Images/"+"Logo.png")));
		} catch (Exception e) {
			ConsoleOutput.printMessageInConsole("The Window Icon couldn't be loaded!", true);
		}
		
		//SET FULLSCREEN
		Frame1.setExtendedState(JFrame.MAXIMIZED_BOTH);
//		Frame1.setSize(1400, 800);
		Frame1.setPreferredSize(Frame1.getSize());
		Frame1.setMinimumSize(Frame1.getSize());
		Frame1.setMaximumSize(Frame1.getSize());
		
		//UPDATE SIZE
		WindowData.FrameHeight = Frame1.getHeight();
		WindowData.FrameWidth = Frame1.getWidth();
		
		//CALC FIELD SIZE
		StandardData.fieldSize = (int) ( ((double) WindowData.FrameHeight) /  ((double) StandardData.fieldsPerScreenHeight) );
		if(StandardData.fieldSize < 50) { StandardData.fieldSize = 50; } //LOWER LIMIT - 50
		ConsoleOutput.printMessageInConsole("Calculated fieldSize: "+StandardData.fieldSize, true);
		
		Frame1.requestFocus();
		
		return Frame1;
	}

}
