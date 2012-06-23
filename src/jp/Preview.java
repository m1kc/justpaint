/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import javax.microedition.lcdui.*;

/**
 * @author Makc
 */
public class Preview extends Canvas
{

    Image img;

    int mode=1;
    
    int FULL=1;
    int PART=2;
    
    boolean lUp=false;
    boolean rUp=false;
    boolean uUp=false;
    boolean dUp=false;

    int sx=0;
    int sy=0;

    /**
     * constructor
     */
    public Preview() {
        //try {
	    // Set up this canvas to listen to command events
	    //setCommandListener(this);
	    // Add the Exit command
	    //addCommand(new Command("Exit", Command.EXIT, 1));
        //} catch(Exception e) {
        //    e.printStackTrace();
        //}

        setFullScreenMode(true);

        sx=0;
        sy=0;
    }

    public void prepare()
    {
        img = JustPaint.c.getImage();
        if (img.getWidth() > getWidth())
        {
            img = Resize.resize_image(img, getWidth(), (img.getHeight()*getWidth())/img.getWidth());
        }
        if (img.getHeight() > getHeight())
        {
            img = Resize.resize_image(img, (img.getWidth()*getHeight())/img.getHeight(), getHeight());
        }
            // newH = (H*newW)/W;
    }
    
    /**
     * paint
     */
    public void paint(Graphics g)
    {
        g.setColor(192,192,192);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (mode==FULL) g.drawImage(img, getWidth()/2-img.getWidth()/2, getHeight()/2-img.getHeight()/2, Graphics.LEFT | Graphics.TOP);

        if (mode==PART)
        {
            if (uUp) sy++;
            if (dUp) sy--;
            if (lUp) sx++;
            if (rUp) sx--;

            if (sx>0) sx=0;
            if (sx<getWidth()-JustPaint.c.getImage().getWidth()) { sx = getWidth()-JustPaint.c.getImage().getWidth(); }

            if (sy>0) sy=0;
            if (sy<getHeight()-JustPaint.c.getImage().getHeight()) { sy = getHeight()-JustPaint.c.getImage().getHeight(); }

            if (getWidth() > JustPaint.c.getImage().getWidth()) { sx = getWidth()/2-JustPaint.c.getImage().getWidth()/2; }
            if (getHeight() > JustPaint.c.getImage().getHeight()) { sy = getHeight()/2-JustPaint.c.getImage().getHeight()/2; }
            
            g.drawImage(JustPaint.c.getImage(), sx, sy, Graphics.LEFT | Graphics.TOP);

        }

        //g.setColor(0,0,0);
        //g.drawString(""+sx+" "+sy, 0, 0, Graphics.LEFT | Graphics.TOP);

        repaint();
    }
    
    /**
     * Called when a key is pressed.
     */
    protected  void keyPressed(int keyCode)
    {
        switch (keyCode)
        {
            case KEY_NUM5:
                mode++;
                if (mode==3) mode=1;
                break;
            case KEY_NUM4:
                lUp = true;
                break;
            case KEY_NUM6:
                rUp = true;
                break;
            case KEY_NUM2:
                uUp = true;
                break;
            case KEY_NUM8:
                dUp = true;
                break;
            default:
                JustPaint.display.setCurrent(JustPaint.c);
        }
    }
    
    /**
     * Called when a key is released.
     */
    protected  void keyReleased(int keyCode)
    {
        switch (keyCode)
        {
            case KEY_NUM4:
                lUp = false;
                break;
            case KEY_NUM6:
                rUp = false;
                break;
            case KEY_NUM2:
                uUp = false;
                break;
            case KEY_NUM8:
                dUp = false;
                break;
        }
    }

}