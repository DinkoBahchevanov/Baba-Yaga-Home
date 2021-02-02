package com.company.tiles;

import com.company.global_constants.Constants;

import java.awt.*;

public class QuestionTile extends Tile {

    private Graphics g;
    public QuestionTile(Graphics g, Color color, int x, int y) {
        super(color, x, y);
        this.g = g;
    }

    @Override
    public String getType() {
        return Constants.QUESTION;
    }

    @Override
    public void render(Graphics g ,int wantedRow, int wantedCol) {
        g.drawRect(wantedCol * 100, wantedRow * 100 + 30, 100, 100);

        g.setFont(new Font("Serif", Font.PLAIN, 35));
        g.drawString("?", wantedCol * 100 + 44 , wantedRow * 100 + 90);
    }
}
