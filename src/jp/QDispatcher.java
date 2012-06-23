/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import java.util.Vector;
import javax.microedition.lcdui.*;

/**
 * @author пользователь
 */
public class QDispatcher extends Canvas
{
    /**
     * constructor
     */
    public QDispatcher()
    {
        setFullScreenMode(true);
    }
    
    /**
     * paint
     */
    public void paint(Graphics g)
    {
        UIPainter.paintBackground(g);

        Vector images = JustPaint.id.images;
        int s = JustPaint.id.s;

        while (images.size()<4) images.addElement(null);

        g.setClip(0, 0, getWidth(), getHeight()-UIPainter.mainFont.getHeight());
        if (images.elementAt(s) != null) g.drawImage((Image) images.elementAt(s), getWidth()/2, 0, Graphics.HCENTER | Graphics.TOP);
        g.setClip(0, 0, getWidth(), getHeight());

        g.setColor(0x000000);
        g.setFont(UIPainter.mainFont);
        g.drawString(""+(s+1)+"/"+images.size(), getWidth()/2, getHeight(), Graphics.HCENTER | Graphics.BOTTOM);
        UIPainter.paintRightSoft(g, "Выгрузить");
    }
    
    /**
     * Called when a key is pressed.
     */
    protected  void keyPressed(int keyCode)
    {
        Vector images = JustPaint.id.images;

        while (images.size()<4) images.addElement(null);

        if (getGameAction(keyCode)==UP) JustPaint.id.s=0;
        if (getGameAction(keyCode)==LEFT) JustPaint.id.s=1;
        if (getGameAction(keyCode)==RIGHT) JustPaint.id.s=2;
        if (getGameAction(keyCode)==DOWN) JustPaint.id.s=3;

        JustPaint.id.doReplace();

        if (getGameAction(keyCode)==FIRE) JustPaint.display.setCurrent(JustPaint.c);
        
        if (keyCode==-7)
        {
            images.setElementAt(null, JustPaint.id.s);
            System.gc();
            JustPaint.id.doReplace();
        }

        repaint();
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