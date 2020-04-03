package com.ducdo.gameobject;

import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.ducdo.effect.Animation;
import com.ducdo.effect.CacheDataLoader;


@SuppressWarnings("deprecation")
public class Megaman extends Human{
	
	public static final int RUNSPEED = 3;
	
	private Animation runForwardAnimation, runBackAnimation, runShootingForwarAnimation, runShootingBackAnimation;
	private Animation idleForwardAnimation, idleBackAnimation, idleShootingForwardAnimation, idleShootingBackAnimation;
	private Animation dickForwardAnimation, dickBackAnimation;
	private Animation flyForwardAnimation, flyBackAnimation, flyShootingForwardAnimation, flyShootingBackAnimation;
	private Animation landingForwardAnimation, landingBackAnimation;
	
	private Animation climWallForward, climWallBack;
	
	private long lastShootingTime;
	private boolean isShooting = false;
	
	private AudioClip hurtingSound;
	private AudioClip shooting1;
	
	public Megaman(float x, float y, GameWorld gameWorld) {
		super(x, y, 70, 90, 0.1f, 1000, gameWorld);
		
//		shooting1 = CacheDataLoader.getInstance().getSounds("bluefireshooting");
//		hurtingSound = CacheDataLoader.getInstance().getSounds("megamanhurt");
		
		setTeamType(LEAGUE_TEAM);
		
		setTimeForNoBeHurt(2000 * 1000000);
		
		runForwardAnimation = CacheDataLoader.getInstance().getAnimation("run");
		runBackAnimation = CacheDataLoader.getInstance().getAnimation("run");
		runBackAnimation.flipAllImage();
		
		idleForwardAnimation = CacheDataLoader.getInstance().getAnimation("idle");
		idleBackAnimation = CacheDataLoader.getInstance().getAnimation("idle");
		idleBackAnimation.flipAllImage();
		
		dickForwardAnimation = CacheDataLoader.getInstance().getAnimation("dick");
		dickBackAnimation = CacheDataLoader.getInstance().getAnimation("dick");
		dickBackAnimation.flipAllImage();
		
		flyForwardAnimation = CacheDataLoader.getInstance().getAnimation("flyingup");
		flyForwardAnimation.setRepeated(false);
		flyBackAnimation = CacheDataLoader.getInstance().getAnimation("flyingup");
		flyBackAnimation.setRepeated(false);
		flyBackAnimation.flipAllImage();
		
		landingForwardAnimation = CacheDataLoader.getInstance().getAnimation("landing");
		landingBackAnimation = CacheDataLoader.getInstance().getAnimation("landing");
		landingBackAnimation.flipAllImage();
		
		climWallBack = CacheDataLoader.getInstance().getAnimation("clim_wall");
		climWallForward = CacheDataLoader.getInstance().getAnimation("clim_wall");
		climWallForward.flipAllImage();
		
		behurtForvardAnim = CacheDataLoader.getInstance().getAnimation("hurting");
		behurtBackAnimation = CacheDataLoader.getInstance().getAnimation("hurting");
		behurtBackAnimation.flipAllImage();
		
		idleShootingForwardAnimation = CacheDataLoader.getInstance().getAnimation("idleshoot");
		idleShootingBackAnimation = CacheDataLoader.getInstance().getAnimation("idleshoot");
		idleShootingBackAnimation.flipAllImage();
		
		runShootingForwarAnimation = CacheDataLoader.getInstance().getAnimation("runshoot");
		runShootingBackAnimation = CacheDataLoader.getInstance().getAnimation("runshoot");
		runShootingBackAnimation.flipAllImage();
		
		flyShootingForwardAnimation = CacheDataLoader.getInstance().getAnimation("flyingupshoot");
		flyShootingBackAnimation = CacheDataLoader.getInstance().getAnimation("flyingupshoot");
		flyShootingBackAnimation.flipAllImage();
		
	}
	
	
	
	@Override
	public void Update() {
		
		super.Update();
		
		if(isShooting) {
			if(System.nanoTime() - lastShootingTime > 500 * 1000000) {
				isShooting = false;
			}
		}
		
		if(isLanding()) {
			landingBackAnimation.Update(System.nanoTime());
			if(landingBackAnimation.isLastFrame()) {
				setLanding(false);
				landingBackAnimation.reset();
				runForwardAnimation.reset();
				runBackAnimation.reset();
			}
		}
	}
	
	@Override
	public Rectangle getBoundForCollisionWithEnemy() {
		
		Rectangle rect = getBoundForCollisionWithMap();
		
		if(isDicking()) {
			rect.x = (int) getPosX() - 22;
			rect.y = (int) getPosY() - 20;
			rect.width = 44;
			rect.height = 65;
		}else {
			rect.x = (int) getPosX() - 22;
			rect.y = (int) getPosY() - 40;
			rect.width = 44;
			rect.height = 80;
		}
		
		return rect;
	}
	
	@Override
	public void draw(Graphics2D g2) {
		
		
		switch(getState()) {
		
		case ALIVE:
		case NOBEHURT:
			if(getState() == NOBEHURT && (System.nanoTime()/10000000)%2 != 1) {
				System.out.println("Plash...");
			}else {
				if(isLanding()) {
					
					if(getDirection() == RIGHT_DIR) {
						landingForwardAnimation.setCurrentFrame(landingBackAnimation.getCurrentFrame());
						landingForwardAnimation.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), 
								(int) getPosY() - (int) getGameWorld().getCamera().getPosY() + (getBoundForCollisionWithMap().height/2
										- landingForwardAnimation.getCurrentImage().getHeight()/2),g2);
					}else {
						landingBackAnimation.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
								(int) getPosY() - (int) getGameWorld().getCamera().getPosY() + (getBoundForCollisionWithMap().height/ 2 
										- landingBackAnimation.getCurrentImage().getHeight()/2 ), g2);
					}
				}else if(isJumping()) {
					
					if(getDirection() == RIGHT_DIR) {
						flyForwardAnimation.Update(System.nanoTime());
						if(isShooting) {
							flyShootingForwardAnimation.setCurrentFrame(flyForwardAnimation.getCurrentFrame());
							flyShootingForwardAnimation.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()) + 10, 
									(int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
						}else {
							flyForwardAnimation.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), 
									(int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
						}
					}else {
						flyBackAnimation.Update(System.nanoTime());
						if(isShooting) {
							flyShootingBackAnimation.setCurrentFrame(flyBackAnimation.getCurrentFrame());
							flyShootingBackAnimation.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()) - 10,
									(int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
						}else
							flyBackAnimation.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
					}
				}else if(isDicking()) {
					if(getDirection() == RIGHT_DIR) {
						dickForwardAnimation.Update(System.nanoTime());
						dickForwardAnimation.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
								(int) getPosY() - (int) getGameWorld().getCamera().getPosY() + (getBoundForCollisionWithMap().height/2 - dickForwardAnimation.getCurrentImage().getHeight()/2)
								, g2);
						
					}else {
						dickBackAnimation.Update(System.nanoTime());
						dickBackAnimation.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
						 (int) getPosY() - (int) getGameWorld().getCamera().getPosY() +(getBoundForCollisionWithMap().height/2 - dickBackAnimation.getCurrentImage().getHeight()/2),
						 g2);
					}
				}else {
					if(getSpeedX() > 0) {
						runForwardAnimation.Update(System.nanoTime());
						if(isShooting) {
							runShootingForwarAnimation.setCurrentFrame(runForwardAnimation.getCurrentFrame() - 1);
							runShootingForwarAnimation.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
									(int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
						}else
							runForwardAnimation.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), 
									(int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
						if(runForwardAnimation.getCurrentFrame() == 1) runForwardAnimation.setIgnoreFrame(0);
					}else if(getSpeedX() < 0) {
						runBackAnimation.Update(System.nanoTime());
						if(isShooting) {
							runShootingBackAnimation.setCurrentFrame(runBackAnimation.getCurrentFrame() - 1);
							runShootingBackAnimation.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
								(int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
						}else 
							runBackAnimation.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), 
							(int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
						if(runBackAnimation.getCurrentFrame() == 1) runBackAnimation.setIgnoreFrame(0);
						
					}else {
						if(getDirection() == RIGHT_DIR) {
							if(isShooting) {
								idleShootingForwardAnimation.Update(System.nanoTime());
								idleShootingForwardAnimation.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), 
								(int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
							}else {
								idleForwardAnimation.Update(System.nanoTime());
								idleForwardAnimation.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), 
								(int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
							}
						}else {
							if(isShooting) {
								idleShootingBackAnimation.Update(System.nanoTime());
								idleShootingBackAnimation.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
								  (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
							}else {
								idleBackAnimation.Update(System.nanoTime());
								idleBackAnimation.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
								  (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
							}
						}
					}
				}
			}
			break;
			
		case BEHURT:
			if(getDirection() == RIGHT_DIR) {
				behurtForvardAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), 
				 (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
			}else {
				behurtBackAnimation.setCurrentFrame(behurtForvardAnim.getCurrentFrame());
				behurtBackAnimation.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
				 (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
			}
			break;
			
		case FEY:
			break;
			
		default:
			break;
			
			
		}
//		
//
//		drawBoundForCollisionWithMap(g2);
//		drawBoundForCollisionWithEnemy(g2);
	}
	
	@Override
	public void run() {
		if(getDirection() == LEFT_DIR)
			setSpeedX(-3);
		else setSpeedX(3);
	}
	
	@Override
	public void jump() {
		
		if(!isJumping()) {
			setJumping(true);
			setSpeedY(-5.0f);
			flyBackAnimation.reset();
			flyForwardAnimation.reset();
		}
		
		// for clim wall
		else {
			Rectangle rectRightWall = getBoundForCollisionWithMap();
			rectRightWall.x += 1;
			Rectangle rectLeftWall = getBoundForCollisionWithMap();
			rectLeftWall.x -= 1;
			
			if(getGameWorld().getPhysicalMap().haveCollisionWithRightWall(rectRightWall) != null && getSpeedX() > 0) {
				setSpeedY(-5.0f);
				
				flyBackAnimation.reset();
				flyForwardAnimation.reset();
				
			}else if(getGameWorld().getPhysicalMap().haveCollisionWithLeftWall(rectLeftWall) != null && getSpeedX() < 0) {
				setSpeedY(-5.0f);
				
				flyBackAnimation.reset();
				flyForwardAnimation.reset();
			}
		}
	}
	
	@Override
	public void dick() {
		if(!isJumping())
			setDicking(true);
	}
	
	@Override
	public void standUp() {
		setDicking(false);
		idleForwardAnimation.reset();
		idleBackAnimation.reset();
		dickForwardAnimation.reset();
		dickBackAnimation.reset();
	}
	
	@Override
	public void stopRun() {
		setSpeedX(0);
		runForwardAnimation.reset();
		runBackAnimation.reset();
		runForwardAnimation.unIgnoreFrame(0);
		runBackAnimation.unIgnoreFrame(0);
	}
	
	@Override
	public void attack() {
		
		if(!isShooting && !isDicking()) {
			
			//shooting1.play();
			System.out.println("x: "+getPosX()+"\ny:"+getPosY());
			Bullet bullet = new BlueFire(getPosX(), getPosY(), getGameWorld());
			if(getDirection() == LEFT_DIR) {
				bullet.setSpeedX(-10);
				System.out.println("bullet.speed: "+bullet.getSpeedX());
				bullet.setPosX(bullet.getPosX() - 40);
				System.out.println("bullet.x: "+bullet.getPosX());
				if(getSpeedX() != 0 && getSpeedY() == 0) {
					bullet.setPosX(bullet.getPosX() - 10);
					bullet.setPosY(bullet.getPosY() - 5);
					System.out.println("bullet.x(naka): "+bullet.getPosX()+"\nbullet.y(naka): "+bullet.getPosY());
				}
			}else {
				bullet.setSpeedX(10);
				bullet.setPosX(bullet.getPosX() + 40);
				System.out.println("bullet.speed: "+bullet.getSpeedX());
				System.out.println("bullet.x: "+bullet.getPosX()+"\nspeedY: "+getSpeedY()+"\nspeedX: "+getSpeedX());
				if(getSpeedX() != 0 && getSpeedY() == 0) {
					bullet.setPosX(bullet.getPosX() + 10);
					bullet.setPosY(bullet.getPosY() - 5);
					System.out.println("bullet.x(naka): "+bullet.getPosX()+"\nbullet.y(naka): "+bullet.getPosY());
				}
			}
			if(isJumping()){
				bullet.setPosY(bullet.getPosY() - 20);
				System.out.println("bullet.y: "+bullet.getPosY());
			}
			bullet.setTeamType(getTeamType());
			getGameWorld().getBulletManager().addObject(bullet);
			
			lastShootingTime = System.nanoTime();
			isShooting = true;
		}
		
	}
	
	/*
	@Override
    public void attack() {
        if (!isShooting && !isDicking()) {
            //shooting1.play();
            Bullet bullet = new BlueFire(getPosX(), getPosY(), getGameWorld());
            if (getDirection() == LEFT_DIR) {
                bullet.setSpeedX(-10);
                bullet.setPosX(bullet.getPosX() - 40);
                if (getSpeedX() != 0 && getSpeedY() == 0) {
                    bullet.setPosX(bullet.getPosX() - 10);
                    bullet.setPosY(bullet.getPosY() - 5);
                }
            } else {
                bullet.setSpeedX(10);
                bullet.setPosX(bullet.getPosX() + 40);
                if (getSpeedX() != 0 && getSpeedY() == 0) {
                    bullet.setPosX(bullet.getPosX() + 10);
                    bullet.setPosY(bullet.getPosY() - 5);
                }
            }
            if (isJumping())
                bullet.setPosY(bullet.getPosY() - 20);
            bullet.setTeamType(getTeamType());
            getGameWorld().getBulletManager().addObject(bullet);
            lastShootingTime = System.nanoTime();
            isShooting = true;
        }
    }
    */
	
	@Override
	public void hurtingCallback() {
		System.out.println("Call back hurting");
		//hurtingSound.play();
	}

}
