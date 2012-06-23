/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import java.util.Vector;
import javax.microedition.lcdui.*;

/**
 * @author Makc
 */
public class NewMyTool extends Canvas
{
    Vector effect = new Vector();
    int se = 1;

    /**
     * constructor
     */
    public NewMyTool()
    {
        setFullScreenMode(true);
    } 

    /**
     * paint
     */
    public void paint(Graphics g) 
    {
        g.setFont(MainCanvas.nameFont);

        g.setColor(255,255,255);
        g.fillRect(0, 0, getWidth(), getHeight());
        UIPainter.paintBackground(g);

        g.setColor(0,0,0);

        g.drawString("< "+JustPaint.c.brs[se]+" >", getWidth()/2, getHeight()-MainCanvas.nameFont.getHeight()*2, Graphics.HCENTER | Graphics.TOP);
        //UIPainter.paintRightSoft(g, "ОК");
    }
    
    /**
     * Called when a key is pressed.
     */
    protected  void keyPressed(int keyCode) 
    {
        if (keyCode==KEY_NUM4 || keyCode==-3) se--;
        if (keyCode==KEY_NUM6 || keyCode==-4) se++;
        if (se<1) se=JustPaint.c.brs.length-1;
        if (se>=JustPaint.c.brs.length) se=1;

        if (keyCode==KEY_NUM5 || keyCode==-5)
        //if (keyCode==-7)
        {
            JustPaint.mts.myTools.addElement(new Integer(se));
            JustPaint.display.setCurrent(JustPaint.mts);
        }

        repaint();
    }
    
    /**
     * Called when a key is released.
     */
    protected  void keyReleased(int keyCode) {
    }

    /**
     * Called when a key is repeated (held down).
     */
    protected  void keyRepeated(int keyCode) {
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