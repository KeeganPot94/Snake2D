/*
 * A 2D snake game created to practice coding fundamentals
 */

import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {

        // game window
        int boardWidth = 1000;
        int boardHeight = boardWidth;

        JFrame frame = new JFrame("Snake 2D");
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Snake2D snake2D = new Snake2D(boardWidth, boardHeight);
        frame.add(snake2D); // snake2D contained with frame
        frame.pack();
        snake2D.requestFocus();

    }
}
