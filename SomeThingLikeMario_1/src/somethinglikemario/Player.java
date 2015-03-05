package somethinglikemario;

import java.awt.image.BufferedImage;

/**
 *
 * @author daniele
 */
public class Player 
{
    private int x,y,speed;
    private double jSpeed;
    private BufferedImage img;
    
    public Player(int x,int y,BufferedImage img,double jSpeed,int speed)
    {
        this.x=x;
        this.y=y;
        this.img=img;
        this.speed=speed;
        this.jSpeed=jSpeed;
    }
    
    int getX()
    {
        return x;
    }

    int getY()
    {
        return y;
    }

    BufferedImage getImg()
    {
        return img;
    }
    
    void setX(int x)
    {
        this.x=x;
    }
    
    void setY(int y)
    {
        this.y=y;
    }
    
    void setImg(BufferedImage img)
    {
        this.img=img;
    }
    
    void jumpUp()
    {
        y-=jSpeed*3;
    }
    
    void jumpDown()
    {
        y+=jSpeed*5;
    }
    
    
    void move(boolean dir)
    {
        if(dir)
            x+=speed;
        else
            x-=speed;
    } 
}
