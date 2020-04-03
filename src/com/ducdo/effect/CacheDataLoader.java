package com.ducdo.effect;

import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

@SuppressWarnings("deprecation")
public class CacheDataLoader {

	private static CacheDataLoader instance = null;
	
	private String framefile = "data/frames.txt";
	private String animationfile = "data/animations.txt";
	private String physmapfile = "data/phys_map.txt";
	private String backgroundmapfile = "data/background_map.txt";
	private String soundfile = "data/sounds.txt";
	
	private Hashtable<String, FrameImage> frameImages;
	private Hashtable<String, Animation> animations;
	private Hashtable<String, AudioClip> sounds;
	
	private int[][] phys_map;
	private int[][] background_map;
	
	private CacheDataLoader() {
//		frameImages = new Hashtable<String, FrameImage>();
//		animations = new Hashtable<String, Animation>();
	}
	
	public static CacheDataLoader getInstance() {
		if(instance == null) {
			instance = new CacheDataLoader();
		}
		return instance;
	}
	
	public AudioClip getSounds(String name) {
		return instance.sounds.get(name);
	}
	
	// lấy ra một frameiamge là bản sao của frameimage có name 
	public FrameImage getFrameImage(String name) {
		
		FrameImage frameImage = new FrameImage(instance.frameImages.get(name));
		return frameImage;
	}
	
	// lấy ra một animation là bản sao của animation có name 
	public Animation getAnimation(String name) {
		
		Animation animation = new Animation(instance.animations.get(name));
		return animation;
	}
	
	// lấy ra 
	public int[][] getPhysicalMap() {
		return instance.phys_map;
	}
	
	public int[][] getBackgroundMap() {
		return instance.background_map;
	}
	
	public void LoadData() throws IOException{
		LoadFrames();
		LoadAnimation();
		LoadPhysMap();
		//LoadBackgroundMap();
		//LoadSounds();
	}
	
	@SuppressWarnings("resource")
	
	public void LoadSounds() throws IOException{
		sounds = new Hashtable<String, AudioClip>();
		
		FileReader fr = new FileReader(soundfile);
		BufferedReader br = new BufferedReader(fr);
		
		String line = null;
		
		if(br.readLine() == null) {
			System.out.println("No data");
			throw new IOException();
		}else {
			fr = new FileReader(soundfile);
			br = new BufferedReader(fr);
			
			while((line = br.readLine()).equals(""));
			int n = Integer.parseInt(line);
			
			for(int i = 0; i < n; i++) {
				
			}
		}
	}
	
	public void LoadPhysMap() throws IOException{
		
		FileReader fr = new FileReader(physmapfile);
		BufferedReader br = new BufferedReader(fr);
		
		String line = null;
		
		line = br.readLine();
		int numberOfRows = Integer.parseInt(line);
		
		line = br.readLine();
		int numberOfColumns = Integer.parseInt(line);
		
		instance.phys_map = new int[numberOfRows][numberOfColumns];
		
		for(int i = 0; i < numberOfRows; i++) {
			line = br.readLine();
			String[] strings = line.split(" ");
			for(int j = 0; j < numberOfColumns; j++) {
				instance.phys_map[i][j] = Integer.parseInt(strings[j]);
			}
		}
		
		for(int i = 0; i < numberOfRows; i++) {
			
			for(int j = 0; j < numberOfColumns; j++) {
				System.out.print(" "+phys_map[i][j]);
			}
			System.out.println();
		}
		
		br.close();
	}
	
	public void LoadBackgroundMap() throws IOException{
		
	}
	
	public void LoadFrames() throws IOException{
		
		frameImages = new Hashtable<String, FrameImage>();
		
		FileReader fr = new FileReader(framefile);
		BufferedReader br = new BufferedReader(fr);
		
		String line = null;
		
		if(br.readLine() == null) {
			System.out.println("No data");
			throw new IOException();
		}else {
			fr = new FileReader(framefile);
			br = new BufferedReader(fr);
			
			while((line = br.readLine()).equals(""));
			
			int n = Integer.parseInt(line);
			
			for(int i = 0; i < n; i++) {
				FrameImage frame = new FrameImage();
				while((line = br.readLine()).equals(""));
				frame.setName(line);
				
				while((line = br.readLine()).equals(""));
				String[] strings = line.split(" ");
				String path = strings[1];
				
				while((line = br.readLine()).equals(""));
				strings = line.split(" ");
				int x = Integer.parseInt(strings[1]);
				
				while((line = br.readLine()).equals(""));
				strings = line.split(" ");
				int y = Integer.parseInt(strings[1]);
				
				while((line = br.readLine()).equals(""));
				strings = line.split(" ");
				int w = Integer.parseInt(strings[1]);
				
				while((line = br.readLine()).equals(""));
				strings = line.split(" ");
				int h = Integer.parseInt(strings[1]);
				
				BufferedImage imageData = ImageIO.read(new File(path));
				BufferedImage image = imageData.getSubimage(x, y, w, h);
				frame.setImage(image);
				
				instance.frameImages.put(frame.getName(), frame);
			}
		}
		
		br.close();
	}
	
	public void LoadAnimation() throws IOException{
		animations = new Hashtable<String, Animation>();
		
		FileReader fr = new FileReader(animationfile);
		BufferedReader br = new BufferedReader(fr);
		
		String line = null;
		
		if(br.readLine() == null) {
			System.out.println("No data");
			throw new IOException();
		}else {
			fr = new FileReader(animationfile);
			br = new BufferedReader(fr);
			
			while((line = br.readLine()).equals(""));
			int n = Integer.parseInt(line);
			
			for(int i = 0; i < n; i++) {
				
				Animation animation = new Animation();
				
				while((line = br.readLine()).equals(""));
				animation.setName(line);
				
				while((line = br.readLine()).equals(""));
				String str[] = line.split(" ");
				
				for(int j = 0; j < str.length; j+=2) {
					animation.add(getFrameImage(str[j]), Double.parseDouble(str[j+1]));
				}
				
				instance.animations.put(animation.getName(), animation);
			}
		}
		
		br.close();
	}
	
}
