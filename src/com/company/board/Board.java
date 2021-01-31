package com.company.board;

import com.company.global_constants.Constants;
import com.company.tiles.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import static com.company.global_constants.Constants.*;

public class Board extends JFrame implements MouseListener {

    private Tile[][] board;
    private Tile selectedTile;
    private Tile currentYellowTile;

    public Board() {
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 830);
        this.getContentPane().setBackground(Color.BLACK);

        board = new Tile[8][8];
        setTiles();
        this.addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //painting the grid(tiles)
        visualizeInitialComponents(g);
    }

    private void visualizeInitialComponents(Graphics g) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //it's +30 because grid is 830 high (because of the bug with 0 row)
                board[i][j].render(g, j * 100, i * 100 + 30);
            }
        }
    }

    private void setTiles() {
        //set the Start
        set_StartingGpsTile();
        //set the Green GPS
        set_GreenGpsTiles();
        //set the Blue GPS
        set_PathlessGpsTiles();
        //set the remaining Red Gps
        set_UnexploredGpsTiles();
    }

    private void set_PathlessGpsTiles() {
        for (int i = 0; i < 5; i++) {
            Random random = new Random();

            int row = random.nextInt(8);
            int col = random.nextInt(8);

            while (board[row][col] != null) {
                row = random.nextInt(8);
                col = random.nextInt(8);
            }
            board[row][col] = new PathlessBlueTile(Color.blue, col * 100, row * 100 + 30);
        }
    }

    private void set_UnexploredGpsTiles() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == null) {
                    board[i][j] = new UnexploredGpsTile(new Color(241, 186, 216), j * 100, i * 100 + 30);
                }
            }
        }
    }

    private void set_GreenGpsTiles() {
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            int row = random.nextInt(8);
            int col = random.nextInt(8);

            while (board[row][col] != null) {
                row = random.nextInt(8);
                col = random.nextInt(8);
            }

            board[row][col] = new GreenGpsTile(new Color(151, 239, 151), col * 100, row + 30);
        }
    }

    private void set_StartingGpsTile() {

        Random random = new Random();
        int row = random.nextInt(8);
        while (row > 0 && row < 7) {
            row = random.nextInt(8);
        }

        int col = random.nextInt(8);
        while (col > 0 && col < 7) {
            col = random.nextInt(8);

        }
        board[row][col] = new YellowGpsTile(Color.YELLOW, col * 100, row * 100 + 30);
        currentYellowTile = board[row][col];
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = (e.getY() - 30) / 100;
        int col = e.getX() / 100;

        if (selectedTile == null) {
            if (board[row][col].getType().equals(YELLOW_GPS)) {
                selectedTile = board[row][col];
                return;
            }
        }

        if (selectedTile != null) {
            selectedTile.move(this.getGraphics(),board, row, col);
            //board[row][col] = selectedTile;
            visualizeInitialComponents(this.getGraphics());
            selectedTile = null;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
