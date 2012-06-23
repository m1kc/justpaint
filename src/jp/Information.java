/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import javax.microedition.lcdui.*;

/**
 * @author Makc
 */
public class Information extends Canvas
{
    /**
     * constructor
     */

    String[] items;
    int an = 10;
    int s = 0;

    public Information()
    {
	    // Set up this canvas to listen to command events
	    //setCommandListener(this);
	    // Add the Exit command
	    //addCommand(new Command("Exit", Command.EXIT, 1));

        setFullScreenMode(true);
    }

    public void prepare()
    {
        items = new String[6];
        items[0] = l10n.LocalizationSupport.getMessage("INFO");
        items[1] = " ";
        items[2] = l10n.LocalizationSupport.getMessage("IMAGESIZE")+":";
        items[3] = ""+JustPaint.c.getImage().getWidth()+"x"+JustPaint.c.getImage().getHeight();
        items[4] = " ";
        items[5] = l10n.LocalizationSupport.getMessage("BACK");
    }
    
    /**
     * paint
     */
    public void paint(Graphics g)
    {
        g.setFont(MainCanvas.nameFont);
        g.drawImage(JustPaint.c.canvas, 0, 0, Graphics.LEFT | Graphics.TOP);
        if (Common.maskEnabled)
        {
            if (Common.blackMask==null)
            {
                Common.blackMask = new int[getWidth()*getHeight()];
                for (int i=0; i<Common.blackMask.length; i++)
                {
                    Common.blackMask[i] = 0x80000000;
                }
            }
            g.drawRGB(Common.blackMask, 0, getWidth(), 0, 0, getWidth(), getHeight(), true);
        }

        g.setColor(255,255,255);
        g.fillRoundRect(-20-getWidth()/10*an, getHeight()/2-10-MainCanvas.nameFont.getHeight()*items.length/2, getWidth(), MainCanvas.nameFont.getHeight()*items.length+20, 10, 10);
        g.setColor(0,0,0);
        g.drawRoundRect(-20-getWidth()/10*an, getHeight()/2-10-MainCanvas.nameFont.getHeight()*items.length/2, getWidth(), MainCanvas.nameFont.getHeight()*items.length+20, 10, 10);

        s = items.length-1;

        Gradient.drawHGradient(g, 0, 128, 255, 255, 255, 255, -21-getWidth()/10*an, getHeight()/2-MainCanvas.nameFont.getHeight()*items.length/2+s*MainCanvas.nameFont.getHeight(), getWidth(), MainCanvas.nameFont.getHeight());
        g.setColor(0,0,0);
        g.drawLine(-20-getWidth()/10*an, getHeight()/2-MainCanvas.nameFont.getHeight()*items.length/2+s*MainCanvas.nameFont.getHeight(), getWidth()-20-getWidth()/10*an, getHeight()/2-MainCanvas.nameFont.getHeight()*items.length/2+s*MainCanvas.nameFont.getHeight());
        g.drawLine(-20-getWidth()/10*an, getHeight()/2-MainCanvas.nameFont.getHeight()*items.length/2+s*MainCanvas.nameFont.getHeight()+MainCanvas.nameFont.getHeight(), getWidth()-20-getWidth()/10*an, getHeight()/2-MainCanvas.nameFont.getHeight()*items.length/2+s*MainCanvas.nameFont.getHeight()+MainCanvas.nameFont.getHeight());

        int i;
        for (i=0; i<items.length; i++)
        {
            if (i==s) { g.setColor(255,255,255); } else { g.setColor(0,0,0); }
            g.drawString(items[i], 5-getWidth()/10*an, getHeight()/2-MainCanvas.nameFont.getHeight()*items.length/2+i*MainCanvas.nameFont.getHeight(), Graphics.LEFT | Graphics.TOP);
        }

        if (an>0) an--;

        repaint();
    }
    
    /**
     * Called when a key is pressed.
     */
    protected  void keyPressed(int keyCode)
    {
        if ((keyCode==-5)||(keyCode==53))
        {
            JustPaint.display.setCurrent(JustPaint.mm);
            an=10;
        }

        if (keyCode==-7)
        {
            JustPaint.display.setCurrent(JustPaint.c);
            an=10;
        }
    }
    
    /**
     * Called when a key is released.
     */
    protected  void keyReleased(int keyCode)
    {

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