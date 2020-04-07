package com.ducdo.gameobject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.ducdo.effect.Animation;
import com.ducdo.state.GameWorld;

public abstract class ParticularObject extends GameObject{
	
	public static final int LEAGUE_TEAM = 1;
	public static final int ENEMY_TEAM = 2;
	
	public static final int LEFT_DIR = 0;
	public static final int RIGHT_DIR = 1;
	
	public static final int ALIVE = 0;
	public static final int BEHURT = 1;
	public static final int FEY = 2;
	public static final int DEATH = 3;
	public static final int NOBEHURT = 4;
	private int state = ALIVE;
	
	private float width;
	private float height;
	private float mass;
	private float speedX;
	private float speedY;
	private int blood;
	
	private int damage;
	
	private int direction;
	
	protected Animation behurtForvardAnim, behurtBackAnimation;
	
	private int teamType;
	
	private long startTimeNoBeHurt;
	private long timeForNoBeHurt;
	
	
	
	
	
	/**
	 * @param posX
	 * @param posY
	 * @param gameWorld
	 * @param width
	 * @param height
	 * @param mass
	 * @param speedX
	 * @param speedY
	 * @param blood
	 */
	public ParticularObject(float posX, float posY, float width, float height, float mass,
			int blood, GameWorld gameWorld) {
		// posX, posY are the middle coordinate of the object
		super(posX, posY, gameWorld);
		this.width = width;
		this.height = height;
		this.mass = mass;
		this.blood = blood;
		
		direction = RIGHT_DIR;
	}
	
	
	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * @return the mass
	 */
	public float getMass() {
		return mass;
	}

	/**
	 * @param mass the mass to set
	 */
	public void setMass(float mass) {
		this.mass = mass;
	}

	/**
	 * @return the speedX
	 */
	public float getSpeedX() {
		return speedX;
	}

	/**
	 * @param speedX the speedX to set
	 */
	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	/**
	 * @return the speedY
	 */
	public float getSpeedY() {
		return speedY;
	}

	/**
	 * @param speedY the speedY to set
	 */
	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}

	/**
	 * @return the blood
	 */
	public int getBlood() {
		return blood;
	}

	/**
	 * @param blood the blood to set
	 */
	public void setBlood(int blood) {
		if(blood >= 0)
			this.blood = blood;
		else this.blood = 0;
	}

	/**
	 * @return the damage
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * @param damage the damage to set
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 * @return the direction
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}

	/**
	 * @return the teamType
	 */
	public int getTeamType() {
		return teamType;
	}

	/**
	 * @param teamType the teamType to set
	 */
	public void setTeamType(int teamType) {
		this.teamType = teamType;
	}

	/**
	 * @return the startTimeNoBeHurt
	 */
	public long getStartTimeNoBeHurt() {
		return startTimeNoBeHurt;
	}

	/**
	 * @param startTimeNoBeHurt the startTimeNoBeHurt to set
	 */
	public void setStartTimeNoBeHurt(long startTimeNoBeHurt) {
		this.startTimeNoBeHurt = startTimeNoBeHurt;
	}

	/**
	 * @return the timeForNoBeHurt
	 */
	public long getTimeForNoBeHurt() {
		return timeForNoBeHurt;
	}

	/**
	 * @param timeForNoBeHurt the timeForNoBeHurt to set
	 */
	public void setTimeForNoBeHurt(long timeForNoBeHurt) {
		this.timeForNoBeHurt = timeForNoBeHurt;
	}
	
	/**
	 * @return the behurtForvardAnim
	 */
	public Animation getBehurtForvardAnim() {
		return behurtForvardAnim;
	}
	
	/**
	 * @return the behurtBackAnimation
	 */
	public Animation getBehurtBackAnimation() {
		return behurtBackAnimation;
	}

	
	public abstract void attack();
	
	public boolean isObjectOutOfCameraView() {
		if(getPosX() - getGameWorld().getCamera().getPosX() > getGameWorld().getCamera().getWidthView() ||
				getPosX() - getGameWorld().getCamera().getPosX() < -50 ||
				getPosY() - getGameWorld().getCamera().getPosY() > getGameWorld().getCamera().getHeightView() ||
				getPosY() - getGameWorld().getCamera().getPosY() < -50)
			return true;
		else return false;
	}
	
	public Rectangle getBoundForCollisionWithMap() {
		Rectangle bound = new Rectangle();
		bound.x = (int) (getPosX() - (getWidth()/2));
		bound.y = (int) (getPosY() - (getHeight()/2));
		bound.width = (int) getWidth();
		bound.height = (int) getHeight();
		
		return bound;
	}
	
	// set mau lai cho nv
	public void beHurt(int damgeEat) {
		setBlood(getBlood() - damgeEat);
		state = BEHURT;
		hurtingCallback(); // goi cho cai lp ke thua 
	}
	
	@Override
	public void Update() {
		switch(state) {
			case ALIVE:
				
				// note: SET DAMAGE FOR OBJECT NO DAMAGE
				ParticularObject object = getGameWorld().getParticularObjectManager().getCollisionWithEnemyObject(this);
				if(object != null) {
					
					if(object.getDamage() > 0) {
						beHurt(object.getDamage());
					}
				}
				
				break;
				
			case BEHURT: // trung dan
				if(behurtBackAnimation == null) {
					state = NOBEHURT;
					startTimeNoBeHurt = System.nanoTime();
					if(getBlood() == 0)
						state = FEY;
				}else {
					behurtForvardAnim.Update(System.nanoTime());
					if(behurtForvardAnim.isLastFrame()) {
						behurtForvardAnim.reset();
						state = NOBEHURT;
						if(getBlood() == 0)
							state = FEY;
						startTimeNoBeHurt = System.nanoTime();
					}
				}
				break;
				
			case FEY: // gan chet
				state = DEATH; // chet
				break;
				
			case NOBEHURT: // khong trung dan
				if(System.nanoTime() - startTimeNoBeHurt > timeForNoBeHurt) {
					state = ALIVE;
				}
				break;
			
				default:
					getGameWorld().getParticularObjectManager().RemoveObject(this);
					break;
		}
	}

	public void drawBoundForCollisionWithMap(Graphics2D g2) {
		Rectangle rectangle = getBoundForCollisionWithMap();
		g2.setColor(Color.GREEN);
		
		g2.drawRect(rectangle.x - (int) getGameWorld().getCamera().getPosX(), rectangle.y - (int) getGameWorld().getCamera().getPosY(),(int) rectangle.width, rectangle.height);
		
	}
	
	public void drawBoundForCollisionWithEnemy(Graphics2D g2) {
		Rectangle rectangle = getBoundForCollisionWithEnemy();
		g2.setColor(Color.RED);
		g2.drawRect(rectangle.x - (int) getGameWorld().getCamera().getPosX(), rectangle.y - (int) getGameWorld().getCamera().getPosY(), rectangle.width, rectangle.height);
	}
	
	public abstract Rectangle getBoundForCollisionWithEnemy();
	public abstract void draw(Graphics2D g2);
	
	public void hurtingCallback() {};
	
}
