package com.ducdo.gameobject;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.ducdo.effect.Animation;
import com.ducdo.effect.CacheDataLoader;
import com.ducdo.state.GameWorld;

public class DarkRaiseBullet extends Bullet{

	private Animation forwardAnim, backAnim;
	
	public DarkRaiseBullet(float x, float y, GameWorld gameWorld) {
		super(x, y, 30, 30, 1.1f, 10, gameWorld);
		forwardAnim = CacheDataLoader.getInstance().getAnimation("darkraisebullet");
		backAnim = CacheDataLoader.getInstance().getAnimation("darkraisebullet");
		backAnim.flipAllImage();
	}
	
	@Override
	public Rectangle getBoundForCollisionWithEnemy() {
		return getBoundForCollisionWithMap();
	}
	
	@Override
	public void draw(Graphics2D g2) {
		if(getSpeedX() > 0) {
			forwardAnim.Update(System.nanoTime());
			forwardAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
			 (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
		}else {
			backAnim.Update(System.nanoTime());
			backAnim.draw((int)(getPosX() - getGameWorld().getCamera().getPosX()),
				(int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
		}
	}
	
	@Override
	public void Update() {
		super.Update();
	}
	@Override
	public void attack() {}
	
}
