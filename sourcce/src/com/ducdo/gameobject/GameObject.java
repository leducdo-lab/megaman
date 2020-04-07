/**
 * 
 */
package com.ducdo.gameobject;

import com.ducdo.state.GameWorld;

/**
 * @author leducdo
 *
 */
public abstract class GameObject {
	
	private float posX;
	private float posY;
	private GameWorld gameWorld;
	/**
	 * @param posX
	 * @param posY
	 * @param gameWorld
	 */
	public GameObject(float posX, float posY, GameWorld gameWorld) {
		this.posX = posX;
		this.posY = posY;
		this.gameWorld = gameWorld;
	}
	/**
	 * @return the posX
	 */
	public float getPosX() {
		return posX;
	}
	/**
	 * @param posX the posX to set
	 */
	public void setPosX(float posX) {
		this.posX = posX;
	}
	/**
	 * @return the posY
	 */
	public float getPosY() {
		return posY;
	}
	/**
	 * @param posY the posY to set
	 */
	public void setPosY(float posY) {
		this.posY = posY;
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
	
	public abstract void Update();
	
}
