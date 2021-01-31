package com.company.tiles;

import com.company.global_constants.Constants;

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
        g.setColor(getColor());
        g.drawRect(x, y, 100, 100);
        g.fillRect(x + 2,y + 2,98,98);
    }

    public int getRow() {
        return (this.y - 30) / 100;
    }

    public int getCol() {
        return this.x / 100;
    }

    public void move(Graphics g, Tile[][] board, int wantedRow, int wantedCol) {
        if (moveIsValid(g,board, wantedRow, wantedCol)) {
            setY(wantedRow * 100 + 30);
            setX(wantedCol * 100);
            board[wantedRow][wantedCol] = this;
        }
    }

    protected boolean moveIsValid(Graphics g, Tile[][] board, int wantedRow, int wantedCol) {
        int currentRow = getRow();
        int currentCol = getCol();
//        if (wantedRow == currentRow + 1 && wantedCol == currentCol) {
//            return true;
//        }
//        if (wantedRow == currentRow - 1 && wantedCol == currentCol){
//            return true;
//        }
//        if (wantedCol == currentCol + 1 && wantedRow == currentRow){
//            return true;
//        }
//        if (wantedCol == currentCol - 1 && wantedRow == currentRow){
//            return true;
//        }
        if (((wantedRow == currentRow + 1 && wantedCol == currentCol) || (wantedRow == currentRow - 1 && wantedCol == currentCol)
                || (wantedCol == currentCol + 1 && wantedRow == currentRow) || (wantedCol == currentCol - 1 && wantedRow == currentRow))
                && !board[wantedRow][wantedCol].getType().equals(Constants.BLUE_GPS)) {

            if (board[wantedRow][wantedCol].getType().equals(Constants.RED_GPS)) {

                Tile questionTile = new QuestionTile(g, new Color(0, 0, 0), wantedRow, wantedCol);
                ((QuestionTile) questionTile).render(g, wantedRow, wantedCol);

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                board[wantedRow][wantedCol] = questionTile;
                return false;
            }
            return true;
        }
        return false;
    }

    public abstract String getType();

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
