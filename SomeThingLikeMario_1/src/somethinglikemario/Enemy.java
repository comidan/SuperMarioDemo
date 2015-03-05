package somethinglikemario;

import java.awt.image.BufferedImage;
/**
 *
 * @author daniele
 */
public class Enemy implements Runnable
{
 
    private int x,y,speed,lastX,firstX;
    private BufferedImage img;
    private boolean dir;
    private Thread t;
    
    public Enemy(int x,int y,BufferedImage img,int speed,boolean dir)
    {
        this.x=x;
        this.y=y;
        this.img=img;
        this.speed=speed;
        this.dir=dir;
        t=new Thread(this);
        t.start();
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
    
    boolean getDir()
    {
        return dir;
    }
    
    void setDir(boolean dir)
    {
        this.dir=dir;
    }
            
    void setX(int x)
    {
        this.x=x;
    }
    
    void setFirstBlockX(int firstX)
    {
        this.firstX=firstX;
    }
    
    void setLastBlockX(int lastX)
    {
        this.lastX=lastX;
    }
    
    private void checkDirection()
    {
        if(x==firstX||x==lastX)
            setDir(!dir);
    }
    
    void setSpeed(int speed)
    {
        this.speed=speed;
    }
    
    private void move()
    {
        if(dir)
            x+=speed;
        else
            x-=speed;
    } 

    @Override
    public void run() {
        while(true)
        {
            checkDirection();
            move();
            try 
            {
                Thread.sleep(10);
            } catch (InterruptedException ex) 
            {
                
            }
        }
    }
}
    

