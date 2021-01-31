package com.company.tiles;

import java.awt.*;

public class QuestionTile extends Tile {

    private Graphics g;
    public QuestionTile(Graphics g, Color color, int x, int y) {
        super(color, x, y);
        this.g = g;
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public void render(Graphics g ,int wantedRow, int wantedCol) {
        //draw the pink tile
        g.drawRect(wantedCol * 100, wantedRow * 100 + 30, 100, 100);
//        g.setColor(new Color(241, 186, 216));
//        g.fillRect(wantedCol * 100 + 2,wantedRow * 100 + 32,98,98);

        g.setFont(new Font("Serif", Font.PLAIN, 35));
        g.drawString("?", wantedCol * 100 + 44 , wantedRow * 100 + 90);
    }
}
