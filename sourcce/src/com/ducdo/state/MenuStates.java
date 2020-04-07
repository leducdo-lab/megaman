package com.ducdo.state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import com.ducdo.control.Buttons;
import com.ducdo.control.RectangleButtons;
import com.ducdo.userinterface.GameFrame;
import com.ducdo.userinterface.GamePanel;


public class MenuStates extends State{

	public final int NUMBER_OF_BUTTON = 2;
    private BufferedImage bufferedImage;
    public Graphics graphicsPaint;
    
    private Buttons[] buttons;
    private int buttonSelected = 0;
    
    public MenuStates(GamePanel gamePanel) {
    	super(gamePanel);
    	
    	bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        buttons = new Buttons[NUMBER_OF_BUTTON];

        buttons[0] = new RectangleButtons("NEW GAME", 300, 100, 100, 40, 15, 25, Color.ORANGE);
        buttons[0].setHoverBgColor(Color.BLUE);
        buttons[0].setPressedBgColor(Color.GREEN);

        buttons[1] = new RectangleButtons("EXIT", 300, 160, 100, 40, 15, 25, Color.ORANGE);
        buttons[1].setHoverBgColor(Color.BLUE);
        buttons[1].setPressedBgColor(Color.GREEN);
    }
    
    @Override
    public void Update() {
        for (int i = 0; i < NUMBER_OF_BUTTON; i++) {
            if (i == buttonSelected) {
                buttons[i].setState(Buttons.HOVER);
            } else {
                buttons[i].setState(Buttons.NONE);
            }
        }
    }
    
    @Override
    public void Render() {
        if (bufferedImage == null) {
            bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            return;
        }
        graphicsPaint = bufferedImage.getGraphics();
        if (graphicsPaint == null) {
            graphicsPaint = bufferedImage.getGraphics();
            return;
        }
//        graphicsPaint.setColor(Color.CYAN);
//        graphicsPaint.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        Image image = new ImageIcon("data/megaman_man.jpg").getImage();
        graphicsPaint.drawImage(image, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
        for (Buttons bt : buttons) {
            bt.draw(graphicsPaint);
        }
    }
    
    @Override
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }
    
    @Override
    public void setPressedButton(int code) {
        switch (code) {
            case KeyEvent.VK_DOWN:
                buttonSelected++;
                if (buttonSelected >= NUMBER_OF_BUTTON) {
                    buttonSelected = 0;
                }
                break;
            case KeyEvent.VK_UP:
                buttonSelected--;
                if (buttonSelected < 0) {
                    buttonSelected = NUMBER_OF_BUTTON - 1;
                }
                break;
            case KeyEvent.VK_ENTER:
                actionMenu();
                break;
        }
    }

    @Override
    public void setReleasedButton(int code) {
    }

    private void actionMenu() {
        switch (buttonSelected) {
            case 0:
                gamePanel.setState(new GameWorld(gamePanel));
                break;
            case 1:
                System.exit(0);
                break;
        }
    }
	
}
