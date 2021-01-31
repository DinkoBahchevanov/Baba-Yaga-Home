package com.company.tiles;

import java.awt.*;

public abstract class Tile {
    private Color color;
    private int x;
    private int y;

    public Tile(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void render(Graphics g, int x, int y) {
        g.drawRect(x, y, 100, 100);
        g.setColor(getColor());
        g.fillRect(x + 2,y + 2,98,98);
    }
}
