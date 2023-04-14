package org.tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

    private final BufferedImage pause;
    private final BufferedImage refresh;

    public static final int BOARD_HEIGHT = 20, BOARD_WIDTH = 10, BLOCK_SIZE = 30;
    private final Color[][] board = new Color[BOARD_HEIGHT][BOARD_WIDTH];

    private final Shape[] shapes = new Shape[7];

    private static Shape currentShape, nextShape;

    private final Timer looper;

    private final int FPS = 60;

    private int delay = 1000 / FPS;

    private int mouseX, mouseY;

    private boolean leftClick = false;

    private final Rectangle stopBounds;
    private final Rectangle refreshBounds;

    private boolean gamePaused = false;

    private boolean gameOver = false;

    private final Color[] colors = {Color.decode("#ed1c24"), Color.decode("#ff7f27"), Color.decode("#fff200"),
            Color.decode("#22b14c"), Color.decode("#00a2e8"), Color.decode("#a349a4"), Color.decode("#3f48cc")};
    private final Random random = new Random();
    private final Timer buttonLapse = new Timer(300, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            buttonLapse.stop();
        }
    });

    private int score = 0;

    public Board() {

        pause = ImageLoader.loadImage(Objects.requireNonNull(Board.class.getClassLoader().getResource("pause.png")).getPath());
        refresh = ImageLoader.loadImage(Objects.requireNonNull(Board.class.getClassLoader().getResource("refresh.png")).getPath());

        mouseX = 0;
        mouseY = 0;

        stopBounds = new Rectangle(350, 500, pause.getWidth(), pause.getHeight() + pause.getHeight() / 2);
        refreshBounds = new Rectangle(350, 500 - refresh.getHeight() - 20, refresh.getWidth(),
                refresh.getHeight() + refresh.getHeight() / 2);

        // create game looper
        looper = new Timer(delay, new GameLooper());

        // create shapes
        shapes[0] = new Shape(new int[][]{
                {1, 1, 1, 1} // I
        }, this, colors[0]);

        shapes[1] = new Shape(new int[][]{
                {1, 1, 1},
                {0, 1, 0}, // T
        }, this, colors[1]);

        shapes[2] = new Shape(new int[][]{
                {1, 1, 1},
                {1, 0, 0}, // L
        }, this, colors[2]);

        shapes[3] = new Shape(new int[][]{
                {1, 1, 1},
                {0, 0, 1}, // J
        }, this, colors[3]);

        shapes[4] = new Shape(new int[][]{
                {0, 1, 1},
                {1, 1, 0}, // S
        }, this, colors[4]);

        shapes[5] = new Shape(new int[][]{
                {1, 1, 0},
                {0, 1, 1}, // Z
        }, this, colors[5]);

        shapes[6] = new Shape(new int[][]{
                {1, 1},
                {1, 1}, // O
        }, this, colors[6]);

    }

    private void update() {
        if (stopBounds.contains(mouseX, mouseY) && leftClick && !buttonLapse.isRunning() && !gameOver) {
            buttonLapse.start();
            gamePaused = !gamePaused;
        }

        if (refreshBounds.contains(mouseX, mouseY) && leftClick) {
            startGame();
        }

        if (gamePaused || gameOver) {
            return;
        }
        currentShape.update();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, getWidth(), getHeight());

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] != null) {
                    graphics.setColor(board[row][col]);
                    graphics.fillRect(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }

        graphics.setColor(nextShape.getColor());
        for (int row = 0; row < nextShape.getCoords().length; row++) {
            for (int col = 0; col < nextShape.getCoords()[0].length; col++) {
                if (nextShape.getCoords()[row][col] != 0) {
                    graphics.fillRect(col * 30 + 320, row * 30 + 50, Board.BLOCK_SIZE, Board.BLOCK_SIZE);
                }
            }
        }
        currentShape.render(graphics);

        if (stopBounds.contains(mouseX, mouseY)) {
            graphics.drawImage(pause.getScaledInstance(pause.getWidth() + 3, pause.getHeight() + 3, BufferedImage.SCALE_DEFAULT), stopBounds.x + 3, stopBounds.y + 3, null);
        } else {
            graphics.drawImage(pause, stopBounds.x, stopBounds.y, null);
        }

        if (refreshBounds.contains(mouseX, mouseY)) {
            graphics.drawImage(refresh.getScaledInstance(refresh.getWidth() + 3, refresh.getHeight() + 3,
                    BufferedImage.SCALE_DEFAULT), refreshBounds.x + 3, refreshBounds.y + 3, null);
        } else {
            graphics.drawImage(refresh, refreshBounds.x, refreshBounds.y, null);
        }

        if (gamePaused) {
            String gamePausedString = "GAME PAUSED";
            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font("Georgia", Font.BOLD, 30));
            graphics.drawString(gamePausedString, 35, WindowGame.HEIGHT / 2);
        }
        if (gameOver) {
            String gameOverString = "GAME OVER";
            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font("Georgia", Font.BOLD, 30));
            graphics.drawString(gameOverString, 50, WindowGame.HEIGHT / 2);
        }
        graphics.setColor(Color.WHITE);

        graphics.setFont(new Font("Georgia", Font.BOLD, 20));

        graphics.drawString("SCORE", WindowGame.WIDTH - 125, WindowGame.HEIGHT / 2);
        graphics.drawString(score + "", WindowGame.WIDTH - 125, WindowGame.HEIGHT / 2 + 30);

        graphics.setColor(Color.WHITE);

        for (int i = 0; i <= BOARD_HEIGHT; i++) {
            graphics.drawLine(0, i * BLOCK_SIZE, BOARD_WIDTH * BLOCK_SIZE, i * BLOCK_SIZE);
        }
        for (int j = 0; j <= BOARD_WIDTH; j++) {
            graphics.drawLine(j * BLOCK_SIZE, 0, j * BLOCK_SIZE, BOARD_HEIGHT * 30);
        }
    }

    public void setNextShape() {
        int index = random.nextInt(shapes.length);
        int colorIndex = random.nextInt(colors.length);
        nextShape = new Shape(shapes[index].getCoords(), this, colors[colorIndex]);
    }

    public void setCurrentShape() {
        currentShape = nextShape;
        setNextShape();

        for (int row = 0; row < currentShape.getCoords().length; row++) {
            for (int col = 0; col < currentShape.getCoords()[0].length; col++) {
                if (currentShape.getCoords()[row][col] != 0) {
                    if (board[currentShape.getY() + row][currentShape.getX() + col] != null) {
                        gameOver = true;
                    }
                }
            }
        }
    }

    public Color[][] getBoard() {
        return board;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            currentShape.rotateShape();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            currentShape.setDeltaX(1);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            currentShape.setDeltaX(-1);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            currentShape.speedUp();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            currentShape.speedDown();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void startGame() {
        stopGame();
        setNextShape();
        setCurrentShape();
        gameOver = false;
        looper.start();
    }

    public void stopGame() {
        score = 0;

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col] = null;
            }
        }
        looper.stop();
    }

    class GameLooper implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            update();
            repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClick = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClick = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void addScore() {
        score++;
    }

}