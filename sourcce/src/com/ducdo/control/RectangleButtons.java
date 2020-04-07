package com.ducdo.control;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class RectangleButtons extends Buttons{

	public RectangleButtons(String text, int posX, int posY, int width, int height, int paddingTextX, int paddingTextY,
            Color bgColor) {
		super(text, posX, posY, width, height, paddingTextX, paddingTextY, bgColor);
	}
	
	@Override
	public void draw(Graphics g) {
        if (enabled) {
            switch (state) {
                case NONE:
                    g.setColor(bgColor);
                    break;
                case PRESSED:
                    g.setColor(pressedBgColor);
                    break;
                case HOVER:
                    g.setColor(hoverBgColor);
                    break;
            }
        } else {
            g.setColor(Color.GRAY);
        }
        g.fillRect(posX, posY, width, height);

        g.setColor(Color.PINK);
        g.drawRect(posX, posY, width, height);
        g.drawRect(posX + 1, posY + 1, width - 2, height - 2);

        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        g.drawString(text, posX + paddingTextX, posY + paddingTextY);
    }
	
}
