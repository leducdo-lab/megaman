package com.ducdo.gameobject;

import java.awt.Graphics2D;

import com.ducdo.userinterface.GameFrame;

public class GameWorld {
	
	private Megaman megaman;
	private PhysicalMap physicalMap;
	private Camera camera;
	private BulletManager bulletManager;
	private ParticularObjectManager particularObjectManager;
	
	
	
	public GameWorld() {
		this.megaman  = new Megaman(400, 400, this);
		this.physicalMap = new PhysicalMap(0f , 0f, this);
		this.camera = new Camera(0,  0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEEN_HEIGHT, this);
		
		this.bulletManager = new BulletManager(this);
		
	}
	
	
	
	
	public BulletManager getBulletManager() {
		return this.bulletManager;
	}

	public ParticularObjectManager getParticularObjectManager() {
		return this.particularObjectManager;
	}
	
	public Camera getCamera() {
		return this.camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	/**
	 * @return the megaman
	 */
	public Megaman getMegaman() {
		return this.megaman;
	}
	
	/**
	 * @param megaman the megaman to set
	 */
	public void setMegaman(Megaman megaman) {
		this.megaman = megaman;
	}

	/**
	 * @return the physicalMap
	 */
	public PhysicalMap getPhysicalMap() {
		return physicalMap;
	}

	/**
	 * @param physicalMap the physicalMap to set
	 */
	public void setPhysicalMap(PhysicalMap physicalMap) {
		this.physicalMap = physicalMap;
	}



	public void Update() {
		this.megaman.Update();
		this.camera.Update();
		
		this.bulletManager.UpdateObjects();
		
	}
	
	public void Render(Graphics2D g2) {
		
		this.physicalMap.draw(g2);
		this.megaman.draw(g2);
		this.bulletManager.draw(g2);
		
	}

	

}
