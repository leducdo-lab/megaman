package com.ducdo.gameobject;

import com.ducdo.state.GameWorld;

public class Camera extends GameObject{

	private float widthView;
	private float heightView;
	
	private boolean isLocked = false;

	
	
	public Camera(float posX, float posY,  float widthView, float heightView, GameWorld gameWorld) {
		super(posX, posY, gameWorld);
		this.widthView = widthView;
		this.heightView = heightView;
	}

	public float getWidthView() {
		return widthView;
	}

	public void setWidthView(float widthView) {
		this.widthView = widthView;
	}

	public float getHeightView() {
		return heightView;
	}

	public void setHeightView(float heightView) {
		this.heightView = heightView;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	
	public void lock() {
		setLocked(true);
	}
	
	public void unlock() {
		setLocked(false);
	}
	
	@Override
	public void Update() {
		
		if(!isLocked) {
			Megaman mainCharacter = getGameWorld().getMegaman();
			
			if(mainCharacter.getPosX() - getPosX() > 400) setPosX(mainCharacter.getPosX() - 400);
			if(mainCharacter.getPosX() - getPosX() < 200) setPosX(mainCharacter.getPosX() - 200);
			
			if(mainCharacter.getPosY() - getPosY() > 400) setPosY(mainCharacter.getPosY() - 400);
			else if(mainCharacter.getPosY() - getPosY() < 250) setPosY(mainCharacter.getPosY() - 250);
		}
	}
	
}
