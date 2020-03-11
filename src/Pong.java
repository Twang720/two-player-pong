import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Pong extends Applet implements Runnable, KeyListener {
    final int WIDTH = 700, HEIGHT = 500;
    int start1, start2;
    Thread thread;
    HumanPaddle p1, p2;
    AIPaddle p3;
    Ball b;
    Graphics gfx;
    Image img;

    // Runs on start
    public void init() {
        this.resize(WIDTH, HEIGHT);
        start1 = 0;
        start2 = 0;
        this.addKeyListener(this);
        p1 = new HumanPaddle(1);
        p2 = new HumanPaddle(2);
        b = new Ball();
        p3 = new AIPaddle(2, b);
        img = createImage(WIDTH, HEIGHT);
        gfx = img.getGraphics();
        thread = new Thread(this);
        thread.start();
    }

    // Paints background
    public void paint(Graphics g){
        gfx.setColor(Color.black);
        gfx.fillRect(0,0, WIDTH, HEIGHT);
        if(b.getX() < -10 || b.getX() > 710){
            gfx.setColor(Color.red);
            gfx.drawString("Game Over", 350, 250);
        }
        else if(start1 == 1){
            p1.draw(gfx);
            p2.draw(gfx);
            b.draw(gfx);
        }
        else if(start2 == 1){
            p1.draw(gfx);
            p3.draw(gfx);
            b.draw(gfx);
        }

        if(start1 == 0 && start2 == 0){
            gfx.setColor(Color.white);
            gfx.drawString("Pong", 340, 100);
            gfx.drawString("Press 1 for single player", 310, 130);
            gfx.drawString("Press 2 for two player", 310, 160);
        }

        g.drawImage(img, 0,0, this);
    }

    // Updates the screen when called
    public void update(Graphics g){
        paint(g);
    }

    // Continuously running code
    public void run(){
        while(true){
            if(start1 == 1) {
                start2 = 0;
                p1.move();
                p2.move();
                b.move();
                b.checkPaddleCollision(p1,p2);
            }
            else if(start2 == 1) {
                start1 = 0;
                p1.move();
                p3.move();
                b.move();
                b.checkPaddleCollision(p1, p3);
            }
            repaint();
            try{
                Thread.sleep(10);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W){
            p1.setUpAccel(true);
        }
        else if(e.getKeyCode() == KeyEvent.VK_S){
            p1.setDownAccel(true);
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            p2.setUpAccel(true);
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            p2.setDownAccel(true);
        }
        if(e.getKeyCode() == KeyEvent.VK_1){
            if(start1 != 1)
                start2 = 1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_2){
            if(start2 != 1)
                start1 = 1;
        }
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W){
            p1.setUpAccel(false);
        }
        else if(e.getKeyCode() == KeyEvent.VK_S){
            p1.setDownAccel(false);
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            p2.setUpAccel(false);
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            p2.setDownAccel(false);
        }
    }
}
