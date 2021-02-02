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

    /**
     * Checking if move is valid.
     * If board[wantedRow][wantedCol] are blue Pathless GPS tiles
     * or Yellow GPS tiles, move is impossible.
     * Movement is possible on left, right, up and down.
     * @param g - graphics
     * @param board - main board(matrix)
     * @param wantedRow - clicked row for moving
     * @param wantedCol - clicked col for moving
     * @return
     */
    public boolean moveIsValid(Graphics g, Tile[][] board, int wantedRow, int wantedCol) {
        int currentRow = getRow();
        int currentCol = getCol();

        if (((wantedRow == currentRow + 1 && wantedCol == currentCol) || (wantedRow == currentRow - 1 && wantedCol == currentCol)
                || (wantedCol == currentCol + 1 && wantedRow == currentRow) || (wantedCol == currentCol - 1 && wantedRow == currentRow))
                && (!board[wantedRow][wantedCol].getType().equals(Constants.BLUE_GPS)
                && (!board[wantedRow][wantedCol].getType().equals(Constants.YELLOW_GPS)))) {

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
