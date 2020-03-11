import java.awt.*;

public class Ball {
    double x, y, xVel, yVel;

    public Ball(){
        x = 350;
        y = 250;
        xVel = -1;
        yVel = 1;
    }

    public void draw(Graphics g){
        g.setColor(Color.white);
        g.fillOval((int)x - 10, (int)y - 10, 20, 20);
    }

    public void checkPaddleCollision(Paddle p){
        if(x <= 50 || x >= 650){
            if(p.getY() <= y && (p.getY() + 80) >= y){
                xVel = -xVel;
            }
        }
    }

    public void move(){
        x += xVel;
        y += yVel;

        if(y < 10 || y > 490){
            yVel = -yVel;
        }

    }

    public int getX(){
        return (int)x;
    }

    public int getY(){
        return (int)y;
    }
}
