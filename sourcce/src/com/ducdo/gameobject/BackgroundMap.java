package com.ducdo.gameobject;

import java.awt.Graphics2D;

import com.ducdo.effect.CacheDataLoader;
import com.ducdo.state.GameWorld;
import com.ducdo.userinterface.GameFrame;

public class BackgroundMap extends GameObject{

	public int[][] backmap;
	private int titleSize;
	
	public BackgroundMap(float x, float y, GameWorld gameWorld) {
		super(x, y, gameWorld);
		backmap = CacheDataLoader.getInstance().getBackgroundMap();
		titleSize = 30;
	}
	
	
	
	@Override
	public void Update() {}
	
	public void draw(Graphics2D g2) {
		Camera camera = getGameWorld().getCamera();
		
		for(int i = 0; i < backmap.length; i++) {
			for(int j = 0; j < backmap[0].length; j++) {
				if(backmap[i][j] != 0 && j * titleSize - camera.getPosX() > -30 && j * titleSize - camera.getPosX() < GameFrame.SCREEN_WIDTH
					&& i * titleSize - camera.getPosY() > -30 && i * titleSize - camera.getPosY() < GameFrame.SCREEEN_HEIGHT) {
					g2.drawImage(CacheDataLoader.getInstance().getFrameImage("tiled"+backmap[i][j]).getImage(), (int) getPosX() + j * titleSize - (int) camera.getPosX(),
							(int) getPosY() + i * titleSize - (int) camera.getPosY(), null);
				}
			}
		}
	}
	
}
