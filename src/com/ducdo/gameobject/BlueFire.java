package com.ducdo.gameobject;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.ducdo.effect.Animation;
import com.ducdo.effect.CacheDataLoader;

public class BlueFire extends Bullet{

	private Animation forwardBulletAnim, backBulletAnim;
	
	public BlueFire(float x, float y, GameWorld gameWorld) {
		super(x, y, 60, 30, 1.0f, 10, gameWorld);
		forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("bluefire");
		backBulletAnim = CacheDataLoader.getInstance().getAnimation("bluefire");
		backBulletAnim.flipAllImage();
	}
	
	@Override
	public Rectangle getBoundForCollisionWithEnemy() {
		return getBoundForCollisionWithMap();
	}
	
	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		System.out.println("speed x: "+getSpeedX());
		if (getSpeedX() > 0) {
			if(!forwardBulletAnim.isIgmoreFrame(0) && forwardBulletAnim.getCurrentFrame() == 3) {
				forwardBulletAnim.setIgnoreFrame(0);
				forwardBulletAnim.setIgnoreFrame(1);
				forwardBulletAnim.setIgnoreFrame(2);
			}
			forwardBulletAnim.Update(System.nanoTime());
			System.out.println("getPosx blue: "+getPosX());
			forwardBulletAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
		}else {
			if(!backBulletAnim.isIgmoreFrame(0) && backBulletAnim.getCurrentFrame() == 3) {
				backBulletAnim.setIgnoreFrame(0);
				backBulletAnim.setIgnoreFrame(1);
				backBulletAnim.setIgnoreFrame(2);
			}
			backBulletAnim.Update(System.nanoTime());
			backBulletAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
		}
	}
	
	@Override
	public void Update() {
		
		if(forwardBulletAnim.isIgmoreFrame(0) || backBulletAnim.isIgmoreFrame(0))
			setPosX(getPosX() + getSpeedX());
		
//		ParticularObject object = getGameWorld().particularObjectManager.getCollisionWithEnemyObject(this);
//		if(object != null && object.getState() == ALIVE) {
//			setBlood(0);
//			object.setBlood(object.getBlood() - getDamage());
//			object.setState(BEHURT);
//			
//		}
	}
	
	@Override
	public void attack() {}
	
}
