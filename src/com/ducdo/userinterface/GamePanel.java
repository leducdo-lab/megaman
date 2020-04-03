package com.ducdo.userinterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.ducdo.gameobject.GameWorld;
import com.ducdo.gameobject.Megaman;

/**
 * @author leducdo
 *
 */
public class GamePanel extends JPanel implements Runnable, KeyListener{

	private static final long serialVersionUID = 1L;
	
	private Thread thread;
	private boolean isRunning;
	private InputManger input;
	
	private BufferedImage bufferImage;
	private Graphics2D bufferG2D;
	
	private GameWorld gameWorld;
	
	public GamePanel() {
		
		gameWorld = new GameWorld();
		input = new InputManger(gameWorld);
		bufferImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		
	}
	
	
	/**
	 * @return the isRunning
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * @param isRunning the isRunning to set
	 */
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	/**
	 * @return the bufferImage
	 */
	public BufferedImage getBufferImage() {
		return bufferImage;
	}

	/**
	 * @param bufferImage the bufferImage to set
	 */
	public void setBufferImage(BufferedImage bufferImage) {
		this.bufferImage = bufferImage;
	}

	/**
	 * @return the bufferG2D
	 */
	public Graphics2D getBufferG2D() {
		return bufferG2D;
	}

	/**
	 * @param bufferG2D the bufferG2D to set
	 */
	public void setBufferG2D(Graphics2D bufferG2D) {
		this.bufferG2D = bufferG2D;
	}

	/**
	 * @return the gameWorld
	 */
	public GameWorld getGameWorld() {
		return gameWorld;
	}

	/**
	 * @param gameWorld the gameWorld to set
	 */
	public void setGameWorld(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}


	@Override
	public void paint(Graphics g) {
		
		g.drawImage(bufferImage, 0, 0, this);
		
	}
	
	public void UpdateGame() {
		getGameWorld().Update();
	}

	public void RenderGame() {
		if(bufferImage == null) {
			bufferImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		}
		
		if(bufferImage != null) {
			bufferG2D = (Graphics2D) bufferImage.getGraphics();			
		}
		
		if(bufferG2D != null) {
			bufferG2D.setColor(Color.WHITE);
			bufferG2D.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEEN_HEIGHT);
			
			gameWorld.Render(bufferG2D);
		}
	}

	public void startGame() {
		if(thread == null) {
			isRunning = true;
			thread = new Thread(this);
			thread.start(); 
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		long FPS = 80;
		long period = 1000 * 1000000 / FPS;
		long beginTime;
		long sleepTime;
		
		beginTime = System.nanoTime();
		
		while(isRunning) {
			
			UpdateGame();
			RenderGame();
			repaint();

			long detalTime = System.nanoTime() - beginTime;
			sleepTime = period - detalTime;
			
			try {
				if(sleepTime > 0) {
					Thread.sleep(sleepTime / 1000000);
				}else {
					Thread.sleep(period / 2000000);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			beginTime = System.nanoTime();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		input.processKeyPressed(e.getKeyCode());
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		input.processKeyReleased(e.getKeyCode());
	}

}
