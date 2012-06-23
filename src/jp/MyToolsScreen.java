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
public class MyToolsScreen extends Canvas
{
    Vector myTools = new Vector();
    int se = 0;

    Font f = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);

    /**
     * constructor
     */
    public MyToolsScreen()
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

        int i;
        for (i=0; i<myTools.size(); i++)
        {
            if (se==i)
            {
                g.setColor(0, 0, 0);
                g.fillRect(0, i*MainCanvas.nameFont.getHeight(), getWidth(), MainCanvas.nameFont.getHeight());
                g.setColor(255,255,255);
                g.drawString(""+JustPaint.c.brs[((Integer) myTools.elementAt(i)).intValue()], 5, i*MainCanvas.nameFont.getHeight(), Graphics.LEFT | Graphics.TOP);
            }
            else
            {
                g.setColor(0,0,0);
                g.drawString(""+JustPaint.c.brs[((Integer) myTools.elementAt(i)).intValue()], 5, i*MainCanvas.nameFont.getHeight(), Graphics.LEFT | Graphics.TOP);
            }
        }

        UIPainter.paintLeftSoft(g, "Добавить");
        UIPainter.paintRightSoft(g, "Отмена");
    }

    /**
     * Called when a key is pressed.
     */
    protected  void keyPressed(int keyCode)
    {
        if ((keyCode==KEY_NUM2)||(keyCode==-1)) se--;
        if ((keyCode==KEY_NUM8)||(keyCode==-2)) se++;
        if (se<0) se=myTools.size()-1;
        if (se>=myTools.size()) se=0;

        if ((keyCode==-5)||(keyCode==KEY_NUM5))
        {
            JustPaint.c.brush = (((Integer) myTools.elementAt(se)).intValue());
            JustPaint.display.setCurrent(JustPaint.c);
        }

        //if (keyCode==KEY_NUM0) JustPaint.display.setCurrent(JustPaint.nf);
        if (keyCode==-6)
        {
            JustPaint.display.setCurrent(JustPaint.nmt);
        }

        if (keyCode==-7)
        {
            JustPaint.display.setCurrent(JustPaint.mm);
        }

        if (keyCode==-8)
        {
            myTools.removeElementAt(se);
            if (se>=myTools.size()) se--;
            if (se<0) se=0;
        }

        repaint();
    }
}