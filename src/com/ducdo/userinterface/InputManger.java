package com.ducdo.userinterface;

import java.awt.event.KeyEvent;

import com.ducdo.gameobject.GameWorld;
import com.ducdo.gameobject.Megaman;

public class InputManger {
	
	private GameWorld gameWorld;
	
	
	public InputManger(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
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

	
	public void processKeyPressed(int keyCode) {
		
		switch (keyCode) {
		case KeyEvent.VK_UP	: case KeyEvent.VK_W: 
			getGameWorld().getMegaman().jump();
			break;

		case KeyEvent.VK_DOWN: case KeyEvent.VK_S:
			getGameWorld().getMegaman().dick();
			break;
			
		case KeyEvent.VK_LEFT: case KeyEvent.VK_D:
			 getGameWorld().getMegaman().setDirection(getGameWorld().getMegaman().LEFT_DIR);
			 getGameWorld().getMegaman().run();
			break;
			
		case KeyEvent.VK_RIGHT: case KeyEvent.VK_A:
			getGameWorld().getMegaman().setDirection(getGameWorld().getMegaman().RIGHT_DIR);
			getGameWorld().getMegaman().run();
			break;
			
		case KeyEvent.VK_ENTER:
			
			break;
			
		case KeyEvent.VK_SPACE:
			getGameWorld().getMegaman().attack();
			break;
			
		default:
			break;
		}
	}
	
public void processKeyReleased(int keyCode) {
		
		switch (keyCode) {
		case KeyEvent.VK_UP	: case KeyEvent.VK_W: 
			
			break;

		case KeyEvent.VK_DOWN: case KeyEvent.VK_S:
			getGameWorld().getMegaman().standUp();
			break;
			
		case KeyEvent.VK_LEFT: case KeyEvent.VK_D:
			if(getGameWorld().getMegaman().getSpeedX() < 0)
				getGameWorld().getMegaman().stopRun();
			break;
			
		case KeyEvent.VK_RIGHT: case KeyEvent.VK_A:
			if(getGameWorld().getMegaman().getSpeedX() > 0)
				getGameWorld().getMegaman().stopRun();
			break;
			
		case KeyEvent.VK_ENTER:
			
			break;
			
		case KeyEvent.VK_SPACE:
			
			break;
			
		default:
			break;
		}
	}
	
}
