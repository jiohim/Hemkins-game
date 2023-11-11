package Hemilya.pl.pojmajHemkinsa;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameWindow extends JFrame {

    private static GameWindow gameWindow;
    private static long last_frame_time;
    private static Image background;
    private static Image game_over;
    private static Image Hemkins;
    private static float drop_left = 200;
    private static float drop_top = -100;
    private static float drop_v = 200;
    private static int score;


    public static void main(String[] args) throws IOException {
        background = ImageIO.read(GameWindow.class.getResourceAsStream("background.png"));
        game_over = ImageIO.read(GameWindow.class.getResourceAsStream("game_over.png"));
        Hemkins = ImageIO.read(GameWindow.class.getResourceAsStream("Hemkins.png"));
	gameWindow = new GameWindow();
    gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    gameWindow.setLocation(200,100);
    gameWindow.setSize(906,478);
    gameWindow.setResizable(false);
    last_frame_time = System.nanoTime();
    Gamefield gamefield = new Gamefield();
    gamefield.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
           int x = e.getX();
           int y = e.getY();
           float drop_right = drop_left + Hemkins.getWidth(null);
           float drop_bottom = drop_top + Hemkins.getHeight(null);
           boolean is_drop = x>=drop_left && x<=drop_right && y>= drop_top && y<= drop_bottom;
           if(is_drop){
               drop_top = -100;
               drop_left = (int) (Math.random() * (gamefield.getWidth() - Hemkins.getWidth(null)));
               drop_v=drop_v+20;
               score++;
               gameWindow.setTitle("Catched bunny:" + score);

           }
        }
    });
    gameWindow.add(gamefield);
    gameWindow.setVisible(true);

    }
    private static void onRepaint(Graphics g){
        long current_time = System.nanoTime();
        float delta_time = (current_time - last_frame_time)*0.000000001f;
        last_frame_time = current_time;

        drop_top = drop_top + drop_v*delta_time;

        g.drawImage(background,0,0,null);
        g.drawImage(Hemkins, (int) drop_left, (int) drop_top,null);
        if(drop_top > gameWindow.getHeight()) g.drawImage(game_over, 280,120,null);

    }
    private static class Gamefield extends JPanel{
       @Override
       protected void  paintComponent (Graphics g) {
        super.paintComponent(g);
        onRepaint(g);
        repaint();
       }
    }
}
