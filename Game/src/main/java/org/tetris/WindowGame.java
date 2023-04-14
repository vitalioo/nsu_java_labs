package org.tetris;

import javax.swing.*;

public class WindowGame {
    public static final int WIDTH = 440, HEIGHT = 635;
    private final Board board;
    private final Title title;
    private final JFrame frame;

    public WindowGame() {
        frame = new JFrame("Tetris");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        board = new Board();
        title = new Title(this);

        frame.addKeyListener(board);
        frame.addKeyListener(title);

        frame.add(title);

        frame.setVisible(true);
    }

    public void startTetris() {
        frame.remove(title);
        frame.addMouseMotionListener(board);
        frame.addMouseListener(board);
        frame.add(board);
        board.startGame();
        frame.revalidate();
    }

    public static void main(String[] args) {
        new WindowGame();
    }
}
