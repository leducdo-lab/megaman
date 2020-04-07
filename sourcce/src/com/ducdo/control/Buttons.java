package com.ducdo.control;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Buttons {

	public static final int NONE = 0;
    public static final int PRESSED = 1;
    public static final int HOVER = 2;
    protected String text;
    protected int posX;
    protected int posY;
    protected int width;
    protected int height;
    protected int paddingTextX;
    protected int paddingTextY;
    protected boolean enabled;
    protected int state;
    protected Color bgColor;
    protected Color pressedBgColor;
    protected Color hoverBgColor;

    public Buttons(String text, int posX, int posY, int width, int height, int paddingTextX, int paddingTextY,
                  Color bgColor) {
        this.text = text;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.paddingTextX = paddingTextX;
        this.paddingTextY = paddingTextY;
        this.bgColor = bgColor;
        enabled = true;
    }
    
    public void setState(int state) {
        this.state = state;
    }

    public void setHoverBgColor(Color color) {
        hoverBgColor = color;
    }

    public void setPressedBgColor(Color color) {
        pressedBgColor = color;
    }

    public abstract void draw(Graphics g);
	
}
