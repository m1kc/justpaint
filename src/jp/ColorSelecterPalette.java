/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.Sprite;

/**
 * @author Makc
 */
public class ColorSelecterPalette extends Canvas
{

    Image palette;
    Image c;
    //Image c2;
    int x;
    int y;

    boolean uUp = false;
    boolean dUp = false;
    boolean lUp = false;
    boolean rUp = false;

    /**
     * constructor
     */
    public ColorSelecterPalette()
    {
        setFullScreenMode(true);

        try
        {
            palette = Image.createImage("/palette.png");
            palette = Resize.resize_image(palette, getWidth(), getHeight()-20);
        }
        catch (Throwable ex)
        {
            palette = Image.createImage(getWidth(), getHeight()-20);
            System.out.println("Palette turned off.");
            System.out.println("Reason: "+ex.toString()+".");
        }

        x = getWidth()/2;
        y = getHeight()/2;
    } 
    
    /**
     * paint
     */

    public void paint(Graphics g)
    {
        if (uUp) y=y-1;
        if (dUp) y=y+1;
        if (lUp) x=x-1;
        if (rUp) x=x+1;
        if (x>=getWidth()) {x = getWidth()-1;}
        if (y>=getHeight()) {y = getHeight()-1;}
        if (x<0) {x=0;}
        if (y<20) {y=20;}

        c = Image.createImage(palette, x, y-20, 1, 1, Sprite.TRANS_NONE);
        JustPaint.r = Lib_effects.get(c, 0, 0, 1);
        JustPaint.g = Lib_effects.get(c, 0, 0, 2);
        JustPaint.b = Lib_effects.get(c, 0, 0, 3);
        //c2 = Resize.resize_image(c, 10, 10);
        c = Resize.resize_image(c, getWidth(), 10);

        g.setColor(0,0,0);
        g.fillRect(0,0,getWidth(), getHeight());
        g.drawImage(c, 0, 5, Graphics.LEFT | Graphics.TOP);
        g.drawImage(palette, 0, 20, Graphics.LEFT | Graphics.TOP);
        g.setColor(255,255,255);
        g.drawLine(x-3, y, x-1, y);
        g.drawLine(x+1, y, x+3, y);
        g.drawLine(x, y-3, x, y-1);
        g.drawLine(x, y+1, x, y+3);
        //g.drawImage(c2, x+2, y+2, Graphics.LEFT | Graphics.TOP);
        //g.setColor(0,0,0);
        //g.drawRect(x+2, y+2, 10, 10);

        repaint();
    }
    
    /**
     * Called when a key is pressed.
     */
    protected  void keyPressed(int keyCode)
    {
        if (keyCode==35 || keyCode==-6 || keyCode==-7)
        {
            JustPaint.display.setCurrent(JustPaint.c);
        }

        if (keyCode==49)
        {
            JustPaint.display.setCurrent(JustPaint.cs);
        }

        if (keyCode==50) uUp = true;
        if (keyCode==56) dUp = true;
        if (keyCode==52) lUp = true;
        if (keyCode==54) rUp = true;

        if (keyCode==-1) uUp = true;
        if (keyCode==-2) dUp = true;
        if (keyCode==-3) lUp = true;
        if (keyCode==-4) rUp = true;

    }
    
    /**
     * Called when a key is released.
     */
    protected  void keyReleased(int keyCode)
    {

        if (keyCode==50) uUp = false;
        if (keyCode==56) dUp = false;
        if (keyCode==52) lUp = false;
        if (keyCode==54) rUp = false;

        if (keyCode==-1) uUp = false;
        if (keyCode==-2) dUp = false;
        if (keyCode==-3) lUp = false;
        if (keyCode==-4) rUp = false;
    }

    /**
     * Called when a key is repeated (held down).
     */
    protected  void keyRepeated(int keyCode)
    {

    }
    
    /**
     * Called when the pointer is dragged.
     */
    protected  void pointerDragged(int x, int y) {
    }

    /**
     * Called when the pointer is pressed.
     */
    protected  void pointerPressed(int x, int y) {
    }

    /**
     * Called when the pointer is released.
     */
    protected  void pointerReleased(int x, int y) {
    }
    
    /**
     * Called when action should be handled
     */
    public void commandAction(Command command, Displayable displayable) {
    }

}