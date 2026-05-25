import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;


public class FlappyBird extends JPanel implements ActionListener , KeyListener {
    int boardWidth = 360;
    int boardHeight = 640;
    
    Image bgImage = new ImageIcon(getClass().getResource("/flappyBirdAssets/flappybirdbg.png")).getImage();
    Image birdImage = new ImageIcon(getClass().getResource("/flappyBirdAssets/flappybird.png")).getImage();
    Image toppipeImage = new ImageIcon(getClass().getResource("/flappyBirdAssets/toppipe.png")).getImage();
    Image bottompipeImage = new ImageIcon(getClass().getResource("/flappyBirdAssets/bottompipe.png")).getImage();
  
  //bird vars
  int birdX = boardWidth/8;
  int birdY = boardHeight/4;
    
  int birdWidth = 34;
  int birdHeight = 24;
  class Bird{
    int x = birdX;
    int y = birdY;
    int width = birdWidth;
    int height = birdHeight;
    Image image;
    
    Bird(Image image){
        this.image = image;
    }



  }


///pipes
  int pipeX = boardWidth;
  int pipeY = 0;
  int pipeWidth = 64;
  int pipeHeight = 512;

  class Pipe{

    int x = pipeX;
    int y = pipeY;
    int width = pipeWidth;
    int height = pipeHeight;
    Image pipImage;
    boolean passed = false;


    Pipe(Image img)
    {
        pipImage = img;

    }

  }


  //logic
  boolean gameOver = false;
  Bird bird;
  int VelocityY = 0;
  int VelocityX = -4;
  int gravity = 1;
  Timer gameloop ;
  Timer placePipeTimer;
  ArrayList<Pipe> pipeList;
  Random rand = new Random();
  double score;



    public FlappyBird(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        bird = new Bird(birdImage);
        pipeList = new ArrayList<Pipe>();

        setFocusable(true);
        addKeyListener(this);
        gameloop = new Timer(1000/60, this);
        gameloop.start();
        placePipeTimer = new Timer(1500,  new ActionListener() {
               @Override
    public void actionPerformed(ActionEvent e)
     {

        // TODO Auto-generated method stub
      placePipes();
    }
        });
        placePipeTimer.start();

    }
    

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, 360, 640, null);
       draw(g);
    }
    public void draw(Graphics g)
    {
        g.drawImage(bgImage, 0, 0, 360, 640, null);
       g.drawImage(bird.image, bird.x, bird.y, bird.width, bird.height, null);
       
        // g.drawImage(birdImage, 100, 200, null);
        // g.drawImage(toppipeImage, 200, 0, null);
        // g.drawImage(bottompipeImage, 200, 400, null);
        for(Pipe p : pipeList){
            g.drawImage(p.pipImage,p.x,p.y, p.width, p.height,null);
        }
        g.setColor(Color.WHITE);   
        g.setFont(new Font("Arial", Font.BOLD, 34));
           if(gameOver){
            g.drawString("GAME OVER " + String.valueOf((int) score), 10,35);
        }else{
             g.drawString("Score: " + String.valueOf((int) score), 10,35);

        }
    }

public void     placePipes(){
    int gap = boardHeight/4;
    int randomY =  (int) (pipeY-pipeHeight/4 - Math.random()*(pipeHeight/2));
    Pipe topPipe = new Pipe(toppipeImage);
    topPipe.y = randomY;
    pipeList.add(topPipe);
    Pipe botPipe = new Pipe(bottompipeImage);


    botPipe.y = topPipe.y+ pipeHeight +gap ;
    pipeList.add(botPipe);
    }
    public void move(){
        
        VelocityY+=gravity;
        bird.y +=VelocityY;
        bird.y = Math.max(bird.y, 0);
        System.out.println("\n\n"+bird.y);


        if(bird.y>boardHeight){
            gameOver = true;
        } 
       

        for(Pipe p : pipeList){
            p.x += VelocityX;
       if(!p.passed && bird.x > p.x+p.width){
        p.passed = true;
        score += .5;
       }
            if(CollisionCheck(bird,p))
        {
            gameOver = true;
        }
        }

     

    }
        public boolean CollisionCheck(Bird a, Pipe b)
        {
           return a.x < b.x + b.width &&  
           a.x + a.width > b.x &&  
           a.y < b.y + b.height &&
           a.y + a.height > b.y;  
                    

        }
    @Override
    public void actionPerformed(ActionEvent e)
     {

        // TODO Auto-generated method stub
        move();
        repaint();
        if(gameOver){
            placePipeTimer.stop();
            gameloop.stop();
        }
        
    }

    
    
    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
          VelocityY = -9;  
       
       
              if(gameOver){
                    bird = new Bird(birdImage);
                    pipeList.clear();
                    score = 0;
                    gameOver = false;
                    gameloop.start();
                    placePipeTimer.start();
                }
            
            
        }
    }
    
        @Override
        public void keyTyped(KeyEvent e) {
          
        }


    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }







    // @Override
    // public void actionPerformed(ActionEvent e){
    //     throw new UnsupportedOperationException("method unimplemented type shit");
    // }

}
