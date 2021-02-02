package com.company.win_lose_frame;

import com.company.board.Board;
import com.company.global_constants.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.company.global_constants.Constants.*;

public class MyFrame extends JFrame {

    public MyFrame(String message) {
        this.setVisible(true);

        JLabel label = new JLabel(message);

        if (message.equals(BUSTED)) {
            this.setSize(new Dimension(400, 200));
            this.setLayout(new GridLayout(1, 3));
            JPanel panel = new JPanel();
            panel.setBackground(new Color(255, 0, 0));
            this.setBackground(new Color(255, 0, 0));
            panel.add(label);
            JPanel panel1 = new JPanel();
            panel1.setBackground(new Color(255, 0, 0));
            this.add(panel1);

            this.add(panel);

            JPanel panel3 = new JPanel();
            panel3.setBackground(new Color(255, 0, 0));
            this.add(panel3);
            return;
        }
        this.setSize(new Dimension(615, 404));
        BufferedImage bf = null;
        try {
            bf = ImageIO.read(new File("src/com/company/resources/QgaOriginal.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setContentPane(new BackImage(bf));

        JButton button = new JButton("Restart");
        button.setBounds(260,177,80,37);
        button.addActionListener(new RestartGame(this));

        button.setFocusPainted(false);
        button.setBackground(new Color(239, 45, 45));

        this.add(button);
    }

    public void closeCurrentFrame() {
        setVisible(false);
        dispose();
    }

    /**
     * A private inner class responsible for
     * restarting the game (if user wins and clicks the restart button).
     */
    private static class RestartGame implements ActionListener {
        private MyFrame myFrame;

        public RestartGame(MyFrame frame) {
            myFrame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new Board();
            myFrame.closeCurrentFrame();
        }
    }

    /**
     * A private inner class responsible for setting
     * the image on the win frame.
     */
    private static class BackImage extends JComponent {

        Image i;
        public BackImage(Image i) {
            this.i = i;
        }

        @Override
        public void paintComponent(Graphics g) {
            g.drawImage(i, 0, 0, null);  // Drawing image using drawImage method
        }
    }
}
