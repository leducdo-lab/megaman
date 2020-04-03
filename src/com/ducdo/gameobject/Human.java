package com.ducdo.gameobject;

import java.awt.Rectangle;

public abstract class Human extends ParticularObject{

	private boolean isJumping;
	private boolean isDicking;
	
	private boolean isLanding;

	/**
	 * @param posX
	 * @param posY
	 * @param width
	 * @param height
	 * @param mass
	 * @param blood
	 * @param gameWorld
	 */
	public Human(float posX, float posY, float width, float height, float mass, int blood, GameWorld gameWorld) {
		super(posX, posY, width, height, mass, blood, gameWorld);
		setState(ALIVE);
	}
	
	public abstract void run();
	
	public abstract void jump();
	
	public abstract void dick();
	
	public abstract void standUp();
	
	public abstract void stopRun();

	
	public boolean isJumping() {
		return isJumping;
	}

	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}

	public boolean isDicking() {
		return isDicking;
	}

	public void setDicking(boolean isDicking) {
		this.isDicking = isDicking;
	}

	public boolean isLanding() {
		return isLanding;
	}

	public void setLanding(boolean isLanding) {
		this.isLanding = isLanding;
	}
	
	
	@Override
	public void Update() {
		super.Update();
		
		if(getState() == ALIVE || getState() == NOBEHURT){
			
			if(!isLanding) {
				
				setPosX(getPosX() + getSpeedX());
				
				
				if(getDirection() == LEFT_DIR &&
						getGameWorld().getPhysicalMap().haveCollisionWithLeftWall(getBoundForCollisionWithMap()) != null) {
					
					Rectangle rectLeftWall = getGameWorld().getPhysicalMap().haveCollisionWithLeftWall(getBoundForCollisionWithMap());
					setPosX(rectLeftWall.x + rectLeftWall.width + getWidth()/2);
				}
				
				
				if(getDirection() == RIGHT_DIR &&
						getGameWorld().getPhysicalMap().haveCollisionWithRightWall(getBoundForCollisionWithMap()) != null) {
					Rectangle rectRightWall = getGameWorld().getPhysicalMap().haveCollisionWithRightWall(getBoundForCollisionWithMap());
					setPosX(rectRightWall.x - getWidth()/2);
				}
				
				
				Rectangle boundForCollisionWithMapFuture = getBoundForCollisionWithMap();
				boundForCollisionWithMapFuture.y += (getSpeedY() != 0 ? getSpeedY() : 2);
				Rectangle rectLand = getGameWorld().getPhysicalMap().haveCollisionWithLand(boundForCollisionWithMapFuture);
				
				Rectangle rectTop = getGameWorld().getPhysicalMap().haveCollisionWithTop(boundForCollisionWithMapFuture);
				
				if(rectTop != null) {
					
					setSpeedY(0);
					setPosY(rectTop.y + getGameWorld().getPhysicalMap().getTitleSize() + getHeight()/2);
				}else if(rectLand != null){
					setJumping(false);
					if(getSpeedY() > 0) setLanding(true);
					setSpeedY(0);
					setPosY(rectLand.y - getHeight()/2 - 1);
				}else {
					setJumping(true);
					setSpeedY(getSpeedY() + getMass());
					setPosY(getPosY() + getSpeedY());
				}
			}
		}
	}
	
	
}
