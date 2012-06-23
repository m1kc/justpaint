/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import javax.microedition.lcdui.*;

/**
 * @author пользователь
 */
public class HeapIndicator extends Canvas
{
    Font f = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);

    /**
     * constructor
     */
    public HeapIndicator()
    {
        setFullScreenMode(true);
    } 
    
    /**
     * paint
     */
    public void paint(Graphics g)
    {
        UIPainter.paintBackground(g);
        UIPainter.paintRightSoft(g, Common.backString);

        g.setColor(0x000000);
        g.setFont(f);
        int total = (int) Runtime.getRuntime().totalMemory()/1024;
        int free = (int) Runtime.getRuntime().freeMemory()/1024;
        g.drawString(""+free+"/"+total+" Kb", 5, 5, Graphics.LEFT | Graphics.TOP);

        repaint();
    }
    
    /**
     * Called when a key is pressed.
     */
    protected  void keyPressed(int keyCode)
    {
        if (keyCode==-7) JustPaint.display.setCurrent(JustPaint.c);
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
    protected  void pointerDragged(int x, int y)
    {
    }

    /**
     * Called when the pointer is pressed.
     */
    protected  void pointerPressed(int x, int y)
    {
    }

    /**
     * Called when the pointer is released.
     */
    protected  void pointerReleased(int x, int y)
    {
    }

}