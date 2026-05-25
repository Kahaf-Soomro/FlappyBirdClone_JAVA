import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;


public class App{

    public static void main(String[] args){
        System.out.println("123456789");

        int boardWidth = 360;
        int boardHeight = 640;
        JFrame frame = new JFrame("FlappyBird 25SW043");
        frame.setSize(boardWidth, boardHeight);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        
        
       FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack();  
        flappyBird.requestFocus();
        
        frame.setVisible(true);
    }

    }