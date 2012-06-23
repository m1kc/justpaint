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
public class FiltersScreen extends Canvas
{
    Vector filters = new Vector();
    int se = 0;

    Font f = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);

    /**
     * constructor
     */
    public FiltersScreen()
    {
        setFullScreenMode(true);

        Filter f1 = new Filter();
        f1.name = "Old photo";
        f1.effects.addElement("w");
        f1.effects.addElement("s");
        f1.effects.addElement("d");
        f1.effects.addElement("b");

        filters.addElement(f1);
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
        for (i=0; i<filters.size(); i++)
        {
            if (se==i)
            {
                g.setColor(0, 0, 0);
                g.fillRect(0, i*MainCanvas.nameFont.getHeight(), getWidth(), MainCanvas.nameFont.getHeight());
                g.setColor(255,255,255);
                g.drawString(((Filter) filters.elementAt(i)).name, 5, i*MainCanvas.nameFont.getHeight(), Graphics.LEFT | Graphics.TOP);
            }
            else
            {
                g.setColor(0,0,0);
                g.drawString(((Filter) filters.elementAt(i)).name, 5, i*MainCanvas.nameFont.getHeight(), Graphics.LEFT | Graphics.TOP);
            }
        }

        UIPainter.paintLeftSoft(g, Common.createString);
        UIPainter.paintRightSoft(g, Common.cancelString);

        repaint();
    }

    /**
     * Called when a key is pressed.
     */
    protected  void keyPressed(int keyCode)
    {
        if ((keyCode==KEY_NUM2)||(keyCode==-1)) se--;
        if ((keyCode==KEY_NUM8)||(keyCode==-2)) se++;
        if (se<0) se=filters.size()-1;
        if (se>=filters.size()) se=0;

        if ((keyCode==-5)||(keyCode==KEY_NUM5))
        {
            JustPaint.c.setImage(((Filter) filters.elementAt(se)).appendFilter(JustPaint.c.getImage()));
            JustPaint.display.setCurrent(JustPaint.c);
        }

        //if (keyCode==KEY_NUM0) JustPaint.display.setCurrent(JustPaint.nf);
        if (keyCode==-6)
        {
            JustPaint.display.setCurrent(JustPaint.nf);
        }

        if (keyCode==-7)
        {
            JustPaint.display.setCurrent(JustPaint.mm);
        }
    }

}