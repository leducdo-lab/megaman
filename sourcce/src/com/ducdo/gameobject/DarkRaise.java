package com.ducdo.gameobject;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.ducdo.effect.Animation;
import com.ducdo.effect.CacheDataLoader;
import com.ducdo.state.GameWorld;

public class DarkRaise extends ParticularObject{

	private Animation forwardAnim, backAnim;
	
	private long startTimeShoot;
	private float x1, x2;
	
	public DarkRaise(float x, float y, GameWorld gameWorld) {
		super(x, y, 127, 89, 0, 100, gameWorld);
		backAnim = CacheDataLoader.getInstance().getAnimation("darkraise");
		forwardAnim = CacheDataLoader.getInstance().getAnimation("darkraise");
		forwardAnim.flipAllImage();
		startTimeShoot = 0;
		setTimeForNoBeHurt(300000000);
		x1 = x - 100;
		x2 = x + 100;
		
		setSpeedX(1);
		setDamage(10);
	}
	
	@Override
	public void attack() {
		float megamanX = getGameWorld().getMegaman().getPosX();
		float megamanY = getGameWorld().getMegaman().getPosY();
		float deltaX = megamanX - getPosX();
		float deltaY = megamanY - getPosY();
		float speed = 4;
		float a = Math.abs(deltaX/deltaY);
		float speedX = (float) Math.sqrt(speed * speed * a * a / (a * a +1));
		float speedY = (float) Math.sqrt(speed * speed / (a * a + 1));
		
		Bullet bullet = new DarkRaiseBullet(getPosX(), getPosY(), getGameWorld());
		if(deltaX < 0) {
			bullet.setSpeedX(-speedX);
		}else bullet.setSpeedX(speedX);
		bullet.setSpeedY(speedY);
		bullet.setTeamType(getTeamType());
		getGameWorld().getParticularObjectManager().addObject(bullet);
	}
	
	public void Update() {
		super.Update();
		if(getPosX() < x1)
			setSpeedX(1);
		else if(getPosX() > x2)
			setSpeedX(-1);
		setPosX(getPosX() + getSpeedX());
		if(System.nanoTime() - startTimeShoot > 1000 * 10000000 * 1.5) {
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
				forwardAnim.draw((int)(getPosX() - getGameWorld().getCamera().getPosX()),
					(int)(getPosY() - getGameWorld().getCamera().getPosY()), g2);
			}
		}
	}
	}
}
