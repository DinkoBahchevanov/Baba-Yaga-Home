package com.company.board;

import com.company.tiles.*;
import com.company.win_lose_frame.MyFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.company.global_constants.Constants.*;

public class Board extends JFrame implements MouseListener {

    private Tile[][] board;
    private Tile selectedTile;

    private boolean waitingForQuestion;

    //row and col for Baba Qga house
    private int babaRow;
    private int babaCol;

    public Board() {
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 830);
        this.getContentPane().setBackground(Color.BLACK);

        babaRow = -1;
        babaCol = -1;

        board = new Tile[8][8];
        waitingForQuestion = false;
        setTiles();
        this.addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //painting the grid(tiles)
        visualizeComponents(g);
    }

    /**
     * Drawing all the Tiles
     * @param g - graphics param
     */
    private void visualizeComponents(Graphics g) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //it's +30 because grid is 830 high (because of the bug with 0 row)
                board[i][j].render(g, j * 100, i * 100 + 30);
            }
        }
    }

    /**
     * Main method of setting the GPS-tiles.
     */
    private void setTiles() {
        //set the Start GPS
        set_StartingGpsTile();
        //set the Green GPS
        set_GreenGpsTiles();
        //set the Blue GPS
        set_PathlessGpsTiles();
        //set the remaining Red Gps
        set_UnexploredGpsTiles();
        //setting Baba Qga's house
        set_BabaQgaHouse();
    }

    /**
     * set_BabaQgaHouse is responsible for giving values to babaRow and babaCol.
     * We iterate through board and find all the Green Gps Tiles.
     * We do put them in a map with their row and col values.
     * Lastly, we choose a random Green Gps Tile from the map and
     * setting babaRow and babaCol with taken values.
     */
    private void set_BabaQgaHouse() {
        Map<Integer, ArrayList<Integer>> data = new HashMap<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getType().equals(GREEN_GPS)) {
                    data.put(data.size() + 1, new ArrayList<>());
                    data.get(data.size()).add(i);
                    data.get(data.size()).add(j);
                }
            }
        }
        Random random = new Random();
        int index = random.nextInt(data.size()) + 1;

        babaRow = data.get(index).get(0);
        babaCol = data.get(index).get(1);

        //For easier testing
        System.out.println("For easier testing: ");
        System.out.println("Baba Qga row: " + babaRow);
        System.out.println("Baba Qga col: " + babaCol);
        System.out.println();
    }

    /**
     * set_PathlessGpsTiles is responsible for setting the Pathless Gps Tiles(blue ones).
     * We choose random row and col. If board[row][col] is null,
     * we do set it to a PathlessBlueTile .
     */
    private void set_PathlessGpsTiles() {
        for (int i = 0; i < 5; i++) {
            Random random = new Random();

            int row = random.nextInt(8);
            int col = random.nextInt(8);

            while (board[row][col] != null) {
                row = random.nextInt(8);
                col = random.nextInt(8);
            }
            board[row][col] = new PathlessBlueTile(new Color(30, 47, 236), col * 100, row * 100 + 30);
        }
    }

    /**
     * set_UnexploredGpsTiles is responsible for setting the unexplored (pink/red) Gps Tiles.
     * We set board[row][col] to a new UnexploredGpsTile if board[row][col] is null.
     */
    private void set_UnexploredGpsTiles() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == null) {
                    board[i][j] = new UnexploredGpsTile(new Color(243, 165, 207), j * 100, i * 100 + 30);
                }
            }
        }
    }

    /**
     * set_GreenGpsTiles is responsible for setting the Green GPS.
     * We choose a random row and col. Until board[row][col] is not null,
     * we set board[row][col] to a new GreenGpsTile.
     */
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

    /**
     * set_StartingGpsTile is responsible for setting the starting (yellow) GPS.
     * We choose a random row and col until they're 0 or 7 (in the angles of the board)
     */
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
    }

    @Override
    /**
     * Main logic of moving is here
     */
    public void mouseClicked(MouseEvent e) {
        //taking coordinates of clicked place
        int row = (e.getY() - 30) / 100;
        int col = e.getX() / 100;

        //if user had chosen a red tile to move, waitingQuestion will be true
        //if clicked place is different from the question tile,
        //user can't do anything until pressing again the question tile
        if (waitingForQuestion) {
            if (board[row][col].getType().equals(QUESTION)) {
                checkQuestionResult(row, col);
            }
            return;
        }

        //if selected tile is null and current clicked row anc col
        //are coordinates of Yellow GPS, we set selectedTile to this GPS
        if (selectedTile == null) {
            if (board[row][col].getType().equals(YELLOW_GPS)) {
                selectedTile = board[row][col];
                return;
            }
        }

        //logic of setting question if moving on Unexplored Red tile
        if (selectedTile != null) {
            if (selectedTile.moveIsValid(getGraphics(), board, row, col)
                    && board[row][col].getType().equals(RED_GPS)) {
                setQuestionTile(row, col);
                return;
            }

            // checking for possible win (finding the Baba Qga house)
            if (row == babaRow && col == babaCol) {
                new MyFrame(WIN);
                setVisible(false);
                dispose();
                return;
            }

            //moving the tile if everything is correct
            selectedTile.move(this.getGraphics(),board, row, col);
            visualizeComponents(this.getGraphics());

            // checking if there is nowhere to go
            if (noChanceForMove()) {
                new MyFrame(BUSTED);
                setVisible(false);
                dispose();
            }
            selectedTile = null;
        }
    }

    /**
     * Setting and drawing the Question tile
     * @param row - current row of question
     * @param col - current col of question
     */
    private void setQuestionTile(int row, int col) {
        Tile questionTile = new QuestionTile(getGraphics(), new Color(0, 0, 0), row, col);
        questionTile.render(getGraphics(), row, col);
        board[row][col] = questionTile;
        waitingForQuestion = true;
    }

    /**
     * Checking if there is any chance of moving anywhere on the board.
     * @return
     */
    private boolean noChanceForMove() {
        if (!moveOnLeftIsPossible() && !moveOnRightIsPossible() && !moveDownIsPossible() && !moveUpIsPossible()) {
            return true;
        }
        return false;
    }

    /**
     * Checking if move on up is possible.
     * @return boolean
     */
    private boolean moveUpIsPossible() {
        if (selectedTile.getRow() - 1 < 0) {
            return false;
        }
        if ((board[selectedTile.getRow() - 1][selectedTile.getCol()].getType().equals(BLUE_GPS)
            || board[selectedTile.getRow() - 1][selectedTile.getCol()].getType().equals(YELLOW_GPS))) {
            return false;
        }
        return true;
    }

    /**
     * Checking if move on down is possible.
     * @return boolean
     */
    private boolean moveDownIsPossible() {
        if (selectedTile.getRow() + 1 >= board.length) return false;
        if ((board[selectedTile.getRow() + 1][selectedTile.getCol()].getType().equals(BLUE_GPS)
        || board[selectedTile.getRow() + 1][selectedTile.getCol()].getType().equals(YELLOW_GPS))) {
            return false;
        }
        return true;
    }

    /**
     * Checking if move on right is possible.
     * @return boolean
     */
    private boolean moveOnRightIsPossible() {
        if (selectedTile.getCol() + 1 >= board.length) return false;
        if ((board[selectedTile.getRow()][selectedTile.getCol() + 1].getType().equals(BLUE_GPS)
        ||board[selectedTile.getRow()][selectedTile.getCol() + 1].getType().equals(YELLOW_GPS))) {
            return false;
        }
        return true;
    }

    /**
     * Checking if move on left is possible.
     * @return boolean
     */
    private boolean moveOnLeftIsPossible() {
        if (selectedTile.getCol() - 1 < 0) return false;
        if ((board[selectedTile.getRow()][selectedTile.getCol() - 1].getType().equals(BLUE_GPS)
        ||board[selectedTile.getRow()][selectedTile.getCol() - 1].getType().equals(YELLOW_GPS))) {
            return false;
        }
        return true;
    }

    /**
     * We do calculate the chance of getting Blue or Yellow tile
     * @param row - current clicked row coordinate
     * @param col - current clicked col coordinate
     */
    private void checkQuestionResult(int row, int col) {
        Random random = new Random();
        int chance = random.nextInt(10) + 1;

        if (chance <= 2) {
            board[row][col] = new PathlessBlueTile(new Color(30, 47, 236), col * 100, row * 100 + 30);
            visualizeComponents(getGraphics());

            //if noChanceForMove is true, game ends (after 3 seconds)
            if (noChanceForMove()) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MyFrame frame = new MyFrame(BUSTED);
                setVisible(false);
                dispose();
            }
            selectedTile = null;
        } else {
            selectedTile.move(getGraphics(), board, row, col);
            visualizeComponents(getGraphics());
            selectedTile = null;
        }
        waitingForQuestion = false;
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
