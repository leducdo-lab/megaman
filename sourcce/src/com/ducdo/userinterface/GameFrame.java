/**
 * 
 */
package com.ducdo.userinterface;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;

import com.ducdo.effect.CacheDataLoader;

/**
 * @author leducdo
 *
 */
public class GameFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public static final int SCREEN_WIDTH = 1000;
	public static final int SCREEEN_HEIGHT = 600;
	
	GamePanel gamePanel;
	
	public GameFrame() {
		super("Mega Man");
		Toolkit tookit = this.getToolkit();
		Dimension dimension = tookit.getScreenSize();
		this.setBounds((dimension.width - SCREEN_WIDTH)/2, (dimension.height - SCREEEN_HEIGHT)/2, SCREEN_WIDTH, SCREEEN_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			CacheDataLoader.getInstance().LoadData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		gamePanel = new GamePanel();
		this.add(gamePanel);
		this.addKeyListener(gamePanel);
	}
	
	public void startGame() {
		gamePanel.startGame();
	}
	
	public static void main(String[] args) {
		GameFrame gameFrame = new GameFrame();
		gameFrame.setVisible(true);
		gameFrame.startGame();
	}
}
