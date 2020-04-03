package com.ducdo.effect;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {

	private String name;
	private boolean isRepeated;
	private ArrayList<FrameImage> frameImages;	// tap hop cac frameimages
	private int currentFrame; 					// chi cai frame hien tai dang ve
	
	private ArrayList<Double> delayFrames;		// thoi gian delay giua ca frameimage
	private long beginTime;						// time bat dau
			
	private ArrayList<Boolean> ignoreFrames;	// mang chua kieu bool xet quy lai 1 hanh dong
	private boolean drawRectFrame;				// dung de khi viet ve vung 
	
	public Animation() {
		delayFrames = new ArrayList<Double>();
		beginTime = 0;
		currentFrame = 0;
		
		ignoreFrames = new ArrayList<Boolean>();
		
		frameImages = new ArrayList<FrameImage>();
		
		drawRectFrame = false;
		
		isRepeated = true;
		
	}
	
	public Animation(Animation animation) {
		
		beginTime = animation.beginTime;
		currentFrame = animation.currentFrame;
		drawRectFrame = animation.drawRectFrame;
		isRepeated = animation.isRepeated;
		
		delayFrames = new ArrayList<Double>();
		for(Double d : animation.delayFrames) {
			delayFrames.add(d);
		}
		
		ignoreFrames = new ArrayList<Boolean>();
		for(boolean ig : animation.ignoreFrames) {
			ignoreFrames.add(ig);
		}
		
		frameImages = new ArrayList<FrameImage>();
		for(FrameImage frame: animation.frameImages) {
			frameImages.add(new FrameImage(frame));
		}
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isRepeated() {
		return isRepeated;
	}

	public void setRepeated(boolean isRepeated) {
		this.isRepeated = isRepeated;
	}

	public ArrayList<FrameImage> getFrameImages() {
		return frameImages;
	}

	public void setFrameImages(ArrayList<FrameImage> frameImages) {
		this.frameImages = frameImages;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		if(currentFrame >= 0 && currentFrame < frameImages.size())
			this.currentFrame = currentFrame;
		else {
			this.currentFrame = 0;
		}
	}

	public ArrayList<Double> getDelayFrames() {
		return delayFrames;
	}

	public void setDelayFrames(ArrayList<Double> delayFrames) {
		this.delayFrames = delayFrames;
	}

	public long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}

	
	public ArrayList<Boolean> getIgnoreFrames() {
		return ignoreFrames;
	}

	public void setIgnoreFrames(ArrayList<Boolean> ignoreFrames) {
		this.ignoreFrames = ignoreFrames;
	}
	
	public boolean isIgmoreFrame(int id) {
		return ignoreFrames.get(id);
	}
	
	public void setIgnoreFrame(int id) {
		if(id >= 0 && id < ignoreFrames.size())
			ignoreFrames.set(id, true);
	}

	public void unIgnoreFrame(int id) {
		if(id >= 0 && id < ignoreFrames.size())
			ignoreFrames.set(id, false);
	} 
	
	public boolean isDrawRectFrame() {
		return drawRectFrame;
	}

	public void setDrawRectFrame(boolean drawRectFrame) {
		this.drawRectFrame = drawRectFrame;
	}
	
	public void reset() {
		currentFrame = 0;
		beginTime = 0;
		
		for(int i = 0; i < ignoreFrames.size(); i++) {
			ignoreFrames.set(i, false);
		}
		
	}
	
	public void add(FrameImage frameImage, double timeToNextFrame) {
		ignoreFrames.add(false);
		frameImages.add(frameImage);
		delayFrames.add(new Double(timeToNextFrame));
		
	}

	public BufferedImage getCurrentImage() {
		return frameImages.get(currentFrame).getImage();
	}
	
	public void Update(long currentTime) {
		
		if(beginTime == 0)	beginTime = currentTime;
		else {
			
			if(currentTime - beginTime > delayFrames.get(currentFrame)) {
				nextFrame();
				beginTime = currentTime;
			}
		}
		
	}
	
	private void nextFrame() {
		
		if(currentFrame >= frameImages.size() - 1) {
			
			if(isRepeated) currentFrame = 0;
			
		}else currentFrame++;
		
		if(ignoreFrames.get(currentFrame)) nextFrame();
		
	}
	
	public boolean isLastFrame() {
		if(currentFrame == frameImages.size() - 1)
			return true;
		else return false;
	}
	
	public void flipAllImage() {
		
		for(int i = 0; i < frameImages.size(); i++) {
			BufferedImage image = frameImages.get(i).getImage();
			
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-image.getWidth(), 0);
			
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			image = op.filter(image, null);
			
			frameImages.get(i).setImage(image);
			
		}
	}
	
	public void draw(int x, int y, Graphics2D g) {
		
		BufferedImage image = getCurrentImage();
		
		g.drawImage(image, x - image.getWidth()/2, y - image.getHeight()/2, null);
		if(drawRectFrame) {
			g.drawRect(x - image.getWidth()/2, y - image.getHeight()/2, image.getWidth(), image.getHeight());
		}
		
	}
	
}
