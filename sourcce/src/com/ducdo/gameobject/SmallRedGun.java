package com.ducdo.gameobject;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.ducdo.effect.Animation;
import com.ducdo.effect.CacheDataLoader;
import com.ducdo.state.GameWorld;

public class SmallRedGun extends ParticularObject{

	private Animation forwardAnim, backAnim;
	private long startTimeShoot;
	
	public SmallRedGun(float x, float y, GameWorld gameWorld) {
		super(x, y, 127, 89, 0, 100, gameWorld);
		backAnim = CacheDataLoader.getInstance().getAnimation("smallredgun");
		forwardAnim = CacheDataLoader.getInstance().getAnimation("smallredgun");
		forwardAnim.flipAllImage();
		startTimeShoot = 0;
		setTimeForNoBeHurt(300 * 1000000);
	}
	
	@Override
	public void attack() {
		Bullet bullet = new YellowFlowerBullet(getPosX(), getPosY(), getGameWorld());
		bullet.setSpeedX(-3);
		bullet.setSpeedY(-3);
		bullet.setTeamType(getTeamType());
		getGameWorld().getBulletManager().addObject(bullet);
		bullet = new YellowFlowerBullet(getPosX(), getPosY(), getGameWorld());
		bullet.setSpeedX(3);
		bullet.setSpeedY(3);
		bullet.setTeamType(getTeamType());
		getGameWorld().getBulletManager().addObject(bullet);
	}
	
	public void Update() {
		super.Update();
		if(System.nanoTime() - startTimeShoot > 1000 * 10000000 * 2.1) {
			attack();
			startTimeShoot = System.nanoTime();
		}
	}
	
	@Override
	public Rectangle getBoundForCollisionWithEnemy() {
		Rectangle rect = getBoundForCollisionWithMap();
		rect.x += 20;
		rect.width -= 40;
		return rect;
	}
	
	@Override
	public void draw(Graphics2D g2) {
		if(!isObjectOutOfCameraView()) {
			if(getState() == NOBEHURT && (System.nanoTime() / 10000000)%2 != 1) {
				
			}else {
				if(getDirection() == LEFT_DIR) {
					backAnim.Update(System.nanoTime());
					backAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
                            (int) (getPosY() - getGameWorld().getCamera().getPosY()), g2);
				}else {
					forwardAnim.Update(System.nanoTime());
					forwardAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
                            (int) (getPosY() - getGameWorld().getCamera().getPosY()), g2);
				}
			}
		}
	}
	
}
