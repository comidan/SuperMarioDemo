package somethinglikemario;
import java.awt.*;
import MGui.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
/**
 *
 * @author daniele
 */
 class Game extends MFrame implements Runnable
 {
    private BufferedImage image;
    private Cube[] cube,_cube,__cube,___cube,coins,_coins,terrain;
    private Player mario;
    private Enemy goomban,_goomban,__goomban;
    private AudioClip clipCoins,initClip;
    private MyKeyListener listener;
    private boolean[] keys,coinsVisibility;
    private boolean jumpAvaiable;
    private int score,translateXAxis;
    private Thread t;
    
    public Game(int w, int h) 
    {        
        super(w,h);
        super.setTitle("Super Mario");
        super.setResizable(true); 
        super.setLocationRelativeTo(null);
        super.setCanvasBackground(new Color(0,255,255));
        setUp(w,h);
        t=new Thread(this);
        t.start();
    }
    
    @Override
    public void setup(){
    }

    @Override
    public void mpaint (Graphics2D g2)
    {
        GraphSet.setColor(g2,255,98,0);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        try
        {
            g2.setFont(new Font("Serif", Font.BOLD, 30));
            g2.drawString("Score : "+score,450,50);
            g2.translate(translateXAxis,0);
            for(int i=0;i<cube.length;i++)
            {
                g2.drawImage(cube[i].getImage(),cube[i].getX(),cube[i].getY(),this);
                g2.drawImage(_cube[i].getImage(),_cube[i].getX(),_cube[i].getY(),this);
                g2.drawImage(__cube[i].getImage(),__cube[i].getX(),__cube[i].getY(),this);
                g2.drawImage(___cube[i].getImage(),___cube[i].getX(),___cube[i].getY(),this);
            }
            for(int i=0;i<terrain.length;i++)
                g2.drawImage(terrain[i].getImage(),terrain[i].getX(),terrain[i].getY(),this);
            for(int i=0;i<coins.length;i++)
            {
                if(coinsVisibility[i])
                    g2.drawImage(coins[i].getImage(),coins[i].getX(),coins[i].getY(),this);
                if(coinsVisibility[i+coins.length])
                    g2.drawImage(_coins[i].getImage(),_coins[i].getX(),_coins[i].getY(),this);
            }
            g2.drawImage(mario.getImg(),mario.getX(),mario.getY(),this);
            g2.drawImage(goomban.getImg(),goomban.getX(),goomban.getY(),this);
            g2.drawImage(_goomban.getImg(),_goomban.getX(),_goomban.getY(),this);
            g2.drawImage(__goomban.getImg(),__goomban.getX(),__goomban.getY(),this);
            
        }
        catch(NullPointerException exc)
        {
        }
    }
    
    private void setUp(int w,int h)
    {
        listener = new MyKeyListener();
        keys=new boolean[256];
        for(int i=0;i<keys.length;i++)
            keys[i]=false;
	addKeyListener(listener);
	setFocusable(true);
        score=0;
        try {
            image=ImageIO.read(SomeThingLikeMario.class.getResource("cubeBlock.gif"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        cube=new Cube[10];
        _cube=new Cube[10];
        __cube=new Cube[10];
        ___cube=new Cube[10];
        for(int i=0;i<cube.length;i++)
        {
            cube[i]=new Cube(50+i*32,250,image);
            _cube[i]=new Cube(30+i*32,380,image);
            __cube[i]=new Cube(70+i*32,480,image);
            ___cube[i]=new Cube(800+i*32,480,image);
        }
        try {
            image=ImageIO.read(SomeThingLikeMario.class.getResource("ground.gif"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        terrain=new Cube[100];
        for(int i=0;i<terrain.length;i++)
            terrain[i]=new Cube(0+i*32,h-32,image);
        try {
            image=ImageIO.read(SomeThingLikeMario.class.getResource("coin.gif"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        coins=new Cube[10];
        _coins=new Cube[10];
        for(int i=0;i<coins.length;i++)
        {
            coins[i]=new Cube(50+i*32,cube[i].getY()-34,image);
            _coins[i]=new Cube(800+i*32,___cube[i].getY()-34,image);
        }
        try {
            image=ImageIO.read(SomeThingLikeMario.class.getResource("mario.gif"));
            mario=new Player(300,600-64,image,0.5,2);
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            goomban=new Enemy(60,_cube[0].getY()-_cube[0].getImage().getHeight(),ImageIO.read(SomeThingLikeMario.class.getResource("goomban.gif")),2,false);
            _goomban=new Enemy(90,__cube[0].getY()-__cube[0].getImage().getHeight(),ImageIO.read(SomeThingLikeMario.class.getResource("goomban.gif")),2,true);
            __goomban=new Enemy(800,536,ImageIO.read(SomeThingLikeMario.class.getResource("goomban.gif")),2,true);
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        coinsVisibility=new boolean[coins.length*2];
        for(int i=0;i<coinsVisibility.length;i++)
            coinsVisibility[i]=true;
        jumpAvaiable=true;
        translateXAxis=0;
        clipCoins=Applet.newAudioClip(SomeThingLikeMario.class.getResource("coin.wav"));
        initClip=Applet.newAudioClip(SomeThingLikeMario.class.getResource("mario.wav"));
        initClip.loop();
    }
    
    private void reset()
    {
        setUp(600,600);
    }
    
    BufferedImage rotatePlayer()                                                     //changing mario direction
    {
        try 
        {
            image=ImageIO.read(SomeThingLikeMario.class.getResource("mario.gif"));
        } 
        catch (IOException ex) 
        {
            
        }
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-image.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return image=op.filter(image, null);
    }
    
    private void endGame(String msg,String ttl)
    {
        if(JOptionPane.showConfirmDialog(this,msg,ttl,
           JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
        {
            initClip.stop();
            reset();
        }
        else
        {
              dispose();
              System.exit(0);
        }
    }
    
    boolean checkBlocksCollision()
    {
        for(int i=0;i<cube.length;i++)
            if(((cube[i].getX()<=mario.getX()&&(cube[i].getX()+cube[i].getImage().getWidth())>=mario.getX())&&
               (cube[i].getY()-cube[i].getImage().getHeight())>=mario.getY()-5&&
               (cube[i].getY()-cube[i].getImage().getHeight())<=mario.getY()+5)||
               ((_cube[i].getX()<=mario.getX()&&(_cube[i].getX()+_cube[i].getImage().getWidth())>=mario.getX())&&
               (_cube[i].getY()-_cube[i].getImage().getHeight())>=mario.getY()-5&&
               (_cube[i].getY()-_cube[i].getImage().getHeight())<=mario.getY()+5)||
               ((__cube[i].getX()<=mario.getX()&&(__cube[i].getX()+__cube[i].getImage().getWidth())>=mario.getX())&&
               (__cube[i].getY()-__cube[i].getImage().getHeight())>=mario.getY()-5&&
               (__cube[i].getY()-__cube[i].getImage().getHeight())<=mario.getY()+5)||
               ((___cube[i].getX()<=mario.getX()&&(___cube[i].getX()+___cube[i].getImage().getWidth())>=mario.getX())&&
               (___cube[i].getY()-___cube[i].getImage().getHeight())>=mario.getY()-5&&
               (___cube[i].getY()-___cube[i].getImage().getHeight())<=mario.getY()+5))
                return true;
        return false;
    }
    
    boolean playerOnTheGround()
    {
        return (terrain[0].getY()-mario.getImg().getHeight())-mario.getY()==0;                    
    }
    
    boolean playerOnBlocks()
    {
        return checkBlocksCollision();
    }
    
    void checkCoinsCollisions()
    {
        for(int i=0;i<coins.length;i++)
            if((coins[i].getX()<=mario.getX()&&(coins[i].getX()+coins[i].getImage().getWidth())>=mario.getX())&&
               (coins[i].getY()+2>=mario.getY()&&
                coins[i].getY()-2<=mario.getY()||
                coins[i].getY()==mario.getY())&&coinsVisibility[i])
            {
                    clipCoins.play();
                    score++;
                    coinsVisibility[i]=false;
            }
            else if((_coins[i].getX()<=mario.getX()&&(_coins[i].getX()+_coins[i].getImage().getWidth())>=mario.getX())&&
               (_coins[i].getY()+2>=mario.getY()&&
                _coins[i].getY()-2<=mario.getY()||
                _coins[i].getY()==mario.getY())&&coinsVisibility[i+coins.length])
            {
                clipCoins.play();
                score++;
                coinsVisibility[i+coins.length]=false;
            }
    }
    
    boolean checkEnemeyCollision()
    {
        return ((goomban.getX()<=mario.getX()+4&&goomban.getX()>=mario.getX()-4)&&goomban.getY()==mario.getY()+4)||
               ((_goomban.getX()<=mario.getX()+4&&_goomban.getX()>=mario.getX()-4)&&_goomban.getY()==mario.getY()+4)||
               ((__goomban.getX()<=mario.getX()+4&&__goomban.getX()>=mario.getX()-4)&&__goomban.getY()==mario.getY());
    }
    
    void moveEnemy()
    {
        goomban.setFirstBlockX(_cube[0].getX());
        goomban.setLastBlockX(_cube[_cube.length-1].getX());
        _goomban.setFirstBlockX(__cube[0].getX());
        _goomban.setLastBlockX(__cube[__cube.length-1].getX());
        __goomban.setFirstBlockX(___cube[0].getX());
        __goomban.setLastBlockX(___cube[___cube.length-1].getX());
    }
    
    private void updateStatus()
    {
       if(!playerOnBlocks()&&!playerOnTheGround())
       {
            mario.jumpDown();
       }
       if(keys[KeyEvent.VK_SPACE]&&mario.getY()>0&&(playerOnBlocks()||playerOnTheGround()))
            for(int i=0;i<70;i++)
                 mario.jumpUp(); 
       if(keys[KeyEvent.VK_LEFT])
       {
           if(mario.getX()>0)
           {
            mario.setImg(rotatePlayer());
            mario.move(false);
           }
          
            if(_cube[0].getX()-translateXAxis>=31)
                translateXAxis+=2;
            else
                translateXAxis=0;
            
       }
       if(keys[KeyEvent.VK_RIGHT]&&mario.getX()<1200)
       {
           try {
               mario.setImg((image=ImageIO.read(SomeThingLikeMario.class.getResource("mario.gif"))));
           } catch (IOException ex) {
               mario.setImg(image);
           }
          
             mario.move(true);
             if(mario.getX()>300)
               translateXAxis-=2;
      
       }
    }

    @Override
    public void run() {
        while(true)
        {
            updateStatus();
            moveEnemy();
            if(checkEnemeyCollision())
                endGame("Wanna continue playing?","You LOSE!");
            else if(score==20)
                endGame("Wanna continue playing?","You WIN!");
            checkCoinsCollisions();
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                
            }
        }
    }
    
    private class MyKeyListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
                    keys[e.getKeyCode()]=true;
		}

		@Override
		public void keyPressed(KeyEvent e) {
                    keys[e.getKeyCode()]=true;
		}

		@Override
		public void keyReleased(KeyEvent e) {
                    keys[e.getKeyCode()]=false;
		}
	}
}