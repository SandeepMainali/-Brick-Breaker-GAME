package brickBracker;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener , ActionListener {

    private boolean play = false;
    private  int score = 0;
    private int totalBricks = 21;

    private Timer timer;
    private int delay =8;
    private int Playerx = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    private int balldirX = -1;
    private  int balldirY = -2;
    private MapGenerator map;

    public Gameplay(){    //add constructor
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();


    }

    public void paint(Graphics g){
        //For Background
        g.setColor(Color.black);
        g.fillRect(1,1,692,592);

        //drwaing map
        map.draw((Graphics2D)g);
        //Create border
        g.setColor(Color.blue);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        //scores
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(""+score,590,30);

        //the paddel
        g.setColor(Color.green);
        g.fillRect(Playerx,550,100,8);

        //the ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY,20,20);

        if(totalBricks<=0){
            play =false;
            balldirY =0;
            balldirX =0;
            g.setColor(Color.BLUE);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("YOU WON",260,300);
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Please Enter TO NEXT LEVEL",230,350);

        }

        if(ballposY>570){
            play =false;
            balldirY =0;
            balldirX =0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("GAME OVER",240,300);
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Please Enter to Restart",230,350);
        }
        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if(play){
            if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(Playerx,550,100,8))) {

                balldirY =-balldirY;

            }

            A:for(int i=0; i<map.map.length;i++){
                for(int j=0; j<map.map[0].length;j++){
                    if(map.map[i][j]>0){
                        int brickX= j *map.brickWidth+80;

                        int brickY= i *map.brickHeight+50;
                        int brickWidth =map.brickWidth;
                        int brickHeight =map.brickHeight;
                        Rectangle rectangle =new Rectangle(brickX,brickY,brickWidth,brickHeight);
                        Rectangle ballrectangle = new Rectangle(ballposX,ballposY,20,20);
                        Rectangle brickrectangle = rectangle;
                        if(ballrectangle.intersects(brickrectangle)){
                            map.setBrickValue(0,i,j);
                            totalBricks--;
                            score += 5;

                            if(ballposX +19<=brickrectangle.x || ballposY +1>= brickrectangle.x +brickrectangle.width){
                                balldirX = -balldirX;
                            }
                            else {
                                balldirY = -balldirY;
                            }
                            break A;
                        }

                    }
                }
            }


            ballposX +=balldirX;
            ballposY +=balldirY;
            if(ballposX<0){
                balldirX = -balldirX;
            }
            if(ballposY<0){
                balldirY = -balldirY;
            }
            if(ballposX>670){
                balldirX = -balldirX;
            }

        }
        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (Playerx >= 600) {
                Playerx = 600;

            } else {
                moveRight();
            }
        }

            if (e.getKeyCode() == KeyEvent.VK_LEFT)
            {
                if(Playerx < 10)
                {
                    Playerx= 10;
                }
                else
                {
                    moveLeft();
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                if(!play){
                    play = true;
                    ballposX = 120;
                    ballposY =350;
                    balldirX = -1;
                    balldirY = -2;
                    Playerx = 310;
                    score = 0;
                    totalBricks = 21;
                    map = new MapGenerator(3,7);
                    repaint();
                }
            }

        }


    private void moveRight() {
        play=true;
        Playerx+=20;
    }

    private void moveLeft() {
        play=true;
        Playerx-=20;
    }


}


