/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import javax.microedition.lcdui.*;

/**
 * @author Makc
 */
public class ColorSelecter extends Canvas
{

    private boolean rUp = false;
    private boolean gUp = false;
    private boolean bUp = false;
    private boolean rDown = false;
    private boolean gDown = false;
    private boolean bDown = false;

    int selection = 1;
    // Ну, типа, 0-1-2 == Red-Green-Blue.
    boolean weUseJoystick = false;

    private int dk = (getWidth()-20)/3;
    private String hr;
    private String hg;
    private String hb;

    /**
     * constructor
     */
    public ColorSelecter() {
	    // Set up this canvas to listen to command events
	    //setCommandListener(this);
	    // Add the Exit command
	    //addCommand(new Command("Exit", Command.EXIT, 1));

        setFullScreenMode(true);
    }

    /**
     * paint
     */
    public void paint(Graphics g)
    {
        if (rUp) JustPaint.r++;
        if (gUp) JustPaint.g++;
        if (bUp) JustPaint.b++;
        if (rDown) JustPaint.r--;
        if (gDown) JustPaint.g--;
        if (bDown) JustPaint.b--;

        if (JustPaint.r>255) JustPaint.r=0;
        if (JustPaint.g>255) JustPaint.g=0;
        if (JustPaint.b>255) JustPaint.b=0;
        if (JustPaint.r<0) JustPaint.r=255;
        if (JustPaint.g<0) JustPaint.g=255;
        if (JustPaint.b<0) JustPaint.b=255;

        g.setColor(0,0,0);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
        g.fillRect(5, 5+MainCanvas.nameFont.getHeight()*2, getWidth()-10, getHeight()-55-MainCanvas.nameFont.getHeight()*2);
        g.setColor(255,255,255);
        g.drawRect(5, 5+MainCanvas.nameFont.getHeight()*2, getWidth()-10, getHeight()-55-MainCanvas.nameFont.getHeight()*2);

        g.drawString(""+JustPaint.r+","+JustPaint.g+","+JustPaint.b, getWidth()/2, 0, Graphics.HCENTER | Graphics.TOP);
        hr = Integer.toHexString(JustPaint.r);
        hg = Integer.toHexString(JustPaint.g);
        hb = Integer.toHexString(JustPaint.b);
        if (hr.length()==1) hr = "0" + hr;
        if (hg.length()==1) hg = "0" + hg;
        if (hb.length()==1) hb = "0" + hb;

        g.drawString(hr+hg+hb, getWidth()/2, MainCanvas.nameFont.getHeight(), Graphics.HCENTER | Graphics.TOP);

        g.setColor(JustPaint.r, 0, 0);
        g.fillRect(5, getHeight()-45, dk, 40);
        if ((selection==0)&&(weUseJoystick))
        {
            g.setColor(0xFFFFFF);
            g.drawRect(5, getHeight()-45, dk, 40);
        }

        g.setColor(0, JustPaint.g, 0);
        g.fillRect(10+dk, getHeight()-45, dk, 40);
        if ((selection==1)&&(weUseJoystick))
        {
            g.setColor(0xFFFFFF);
            g.drawRect(10+dk, getHeight()-45, dk, 40);
        }

        g.setColor(0, 0, JustPaint.b);
        g.fillRect(15+dk+dk, getHeight()-45, dk, 40);
        if ((selection==2)&&(weUseJoystick))
        {
            g.setColor(0xFFFFFF);
            g.drawRect(15+dk+dk, getHeight()-45, dk, 40);
        }

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

        if (keyCode==52) { JustPaint.r++; }
        if (keyCode==55) { JustPaint.r--; }
        if (keyCode==53) { JustPaint.g++; }
        if (keyCode==56) { JustPaint.g--; }
        if (keyCode==54) { JustPaint.b++; }
        if (keyCode==57) { JustPaint.b--; }

        if (keyCode==51)
        {
            JustPaint.display.setCurrent(JustPaint.csp);
        }

        if ((keyCode==-1 || keyCode==-2 || keyCode==-3 || keyCode==-4)&&(!weUseJoystick))
        {
            weUseJoystick = true;
            return;
        }

        if (keyCode==-3) selection--;
        if (keyCode==-4) selection++;
        if (selection<0) selection=0;
        if (selection>2) selection=2;

        if (keyCode==-1)
        {
            if (selection==0) JustPaint.r++;
            if (selection==1) JustPaint.g++;
            if (selection==2) JustPaint.b++;
        }

        if (keyCode==-2)
        {
            if (selection==0) JustPaint.r--;
            if (selection==1) JustPaint.g--;
            if (selection==2) JustPaint.b--;
        }

        if (keyCode==-8)
        {
            JustPaint.r=JustPaint.g=JustPaint.b=0;
        }
    }
    
    /**
     * Called when a key is released.
     */
    protected  void keyReleased(int keyCode)
    {
        if (keyCode==52) { rUp = false; }
        if (keyCode==55) { rDown = false; }
        if (keyCode==53) { gUp = false; }
        if (keyCode==56) { gDown = false; }
        if (keyCode==54) { bUp = false; }
        if (keyCode==57) { bDown = false; }

        if (keyCode==-1)
        {
            if (selection==0) rUp = false;
            if (selection==1) gUp = false;
            if (selection==2) bUp = false;
        }

        if (keyCode==-2)
        {
            if (selection==0) rDown = false;
            if (selection==1) gDown = false;
            if (selection==2) bDown = false;
        }
    }

    /**
     * Called when a key is repeated (held down).
     */
    protected  void keyRepeated(int keyCode)
    {
        if (keyCode==52) { rUp = true; }
        if (keyCode==55) { rDown = true; }
        if (keyCode==53) { gUp = true; }
        if (keyCode==56) { gDown = true; }
        if (keyCode==54) { bUp = true; }
        if (keyCode==57) { bDown = true; }

        if (keyCode==-1)
        {
            if (selection==0) rUp = true;
            if (selection==1) gUp = true;
            if (selection==2) bUp = true;
        }

        if (keyCode==-2)
        {
            if (selection==0) rDown = true;
            if (selection==1) gDown = true;
            if (selection==2) bDown = true;
        }
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