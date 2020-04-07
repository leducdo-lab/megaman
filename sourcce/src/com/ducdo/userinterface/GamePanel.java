package com.ducdo.userinterface;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import com.ducdo.state.MenuStates;
import com.ducdo.state.State;

/**
 * @author leducdo
 *
 */
public class GamePanel extends JPanel implements Runnable, KeyListener{

	private static final long serialVersionUID = 1L;
	
	public State gameState;
	public InputManager inputManger;
	
	private Thread gameThread;
	private boolean isRunning = true;
	
	
	public GamePanel() {
		
		gameState = new MenuStates(this);
		inputManger = new InputManager(gameState);
		
	}
	
	public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        long previousTime = System.nanoTime();
        long currentTime;
        long sleepTime;
        long period = 1000000000 / 80;

        while (isRunning) {
            gameState.Update();
            gameState.Render();

            repaint();
            currentTime = System.nanoTime();
            sleepTime = period - (currentTime - previousTime);
            try {
                if (sleepTime > 0)
                    Thread.sleep(sleepTime / 1000000);
                else Thread.sleep(period / 2000000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            previousTime = System.nanoTime();
        }

    }

    public void paint(Graphics g) {
        g.drawImage(gameState.getBufferedImage(), 0, 0, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        inputManger.setPressedButton(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
    	inputManger.setReleasedButton(e.getKeyCode());
    }

    public void setState(State state) {
        gameState = state;
        inputManger.setState(state);
    }
}
