package somethinglikemario;

import java.awt.image.BufferedImage;
/**
 *
 * @author daniele
 */
public class Cube {
    
    private int x,y;
    private BufferedImage img;
    
    public Cube(int x,int y,BufferedImage img)
    {
        this.x=x;
        this.y=y;
        this.img=img;
    }
    
    BufferedImage getImage()
    {
        return img;
    }
    
    void setX(int x)
    {
        this.x=x;
    }
    
    int getX()
    {
        return x;
    }
    
    int getY()
    {
        return y;
    }
}
