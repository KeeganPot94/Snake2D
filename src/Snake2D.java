import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Snake2D extends JPanel implements ActionListener, KeyListener {

    // tile class to hold co-ordinates
    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int boardWidth;
    int boardHeight;
    int tileSize = 20;

    // snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    // food
    Tile food;
    Random random;

    // game logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    Snake2D(int boardWidth, int boardHeight) {

        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;

        // window properties
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(new Color(130, 185, 130));
        addKeyListener(this);
        setFocusable(true);

        // snake spawn position
        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();
        random = new Random();
        spawnSnake();

        // food spawn position
        food = new Tile(10, 10);
        spawnFood();

        // snake velocity
        velocityX = 0;
        velocityY = 0;

        gameLoop = new Timer(100, this); // fps
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        int offSet = 1;

        /*
         * // display grid
         * for (int i = 0; i < boardWidth / tileSize; i++) {
         * g.drawLine(i * tileSize, 0, i * tileSize, boardHeight);
         * g.drawLine(0, i * tileSize, boardWidth, i * tileSize);
         * g.setColor(Color.white);
         * }
         * 
         */

        // food
        g.setColor(Color.red);
        g.fill3DRect((food.x * tileSize) + offSet, (food.y * tileSize) + offSet, tileSize - offSet, tileSize - offSet,
                true);

        // snake head
        g.setColor(new Color(50, 100, 0));
        g.fill3DRect((snakeHead.x * tileSize) + offSet, (snakeHead.y * tileSize) + offSet, tileSize - offSet,
                tileSize - offSet, true);

        // snake body
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            g.setColor(new Color(75, 150, 0));
            g.fill3DRect((snakePart.x * tileSize) + offSet, (snakePart.y * tileSize) + offSet, tileSize - offSet,
                    tileSize - offSet, true);
        }

        // score
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(Color.black);
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game Over", boardWidth / 2, boardHeight / 2);
            g.drawString("You scored " + String.valueOf(snakeBody.size()) + " points", boardWidth / 2,
                    (boardHeight / 2) + tileSize);
        } else {
            g.drawString("Scored: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
    }

    public void spawnSnake() {
        snakeHead.x = random.nextInt(boardWidth / tileSize);
        snakeHead.y = random.nextInt(boardHeight / tileSize);
    }

    public void spawnFood() {
        food.x = random.nextInt(boardWidth / tileSize);
        food.y = random.nextInt(boardHeight / tileSize);
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        // eat food
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            spawnFood();
        }

        // snake body - iterate backwards
        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        // snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // game over conditions
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            // game over if snake head collides with snake body
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }
        // game over if snake head collides with game window boundary
        if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth || snakeHead.y * tileSize < 0
                || snakeHead.y * tileSize > boardHeight) {
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
        }
    }

    // snake movement
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1 || e.getKeyCode() == KeyEvent.VK_W && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1
                || e.getKeyCode() == KeyEvent.VK_S && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1
                || e.getKeyCode() == KeyEvent.VK_D && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1
                || e.getKeyCode() == KeyEvent.VK_A && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        }
    }

    // not needed
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
