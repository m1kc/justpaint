/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI2.dialogs;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author пользователь
 */
public class Number extends Element
{
    public int i;
    boolean edit = false;
    boolean editAnyway = false;
    String title = null;
    private Runnable onChange = null;

    public Number()
    {
        this.i = 0;
    }

    public Number(int i)
    {
        this.i = i;
    }

    public Number(String title, int i)
    {
        this.title = title;
        this.i = i;
    }

    public void paint(Graphics g)
    {
        g.setColor(Data.fgcolor);
        g.setFont(Data.mainFont);
        if (hasFocus) g.drawRect(x, y, width-1, height-1);
        String s = "";
        if (title != null) s = title+": ";
        s = s+i;
        if (edit) s = s + "_";
        g.drawString(s, x+2, y+2, Graphics.LEFT | Graphics.TOP);
    }

    public int getPreferredWidth()
    {
        return Data.mainFont.stringWidth(""+Integer.MAX_VALUE)+4;
    }

    public int getPreferredHeight()
    {
        return Data.mainFont.getHeight()+4;
    }

    public void onClick()
    {
        edit = !edit;
        if (editAnyway)
        {
            edit = true;
            editAnyway = false;
        }
    }

    public void keyPressed(int k,int a)
    {
        if (edit)
        {
            if ((k>=Canvas.KEY_NUM0)&&(k<=Canvas.KEY_NUM9))
            {
                i *= 10;
                i += (k - Canvas.KEY_NUM0);
            }
            if ((k==-8)||(k==Canvas.KEY_STAR))
            {
                i /= 10;
            }
            if (k==Canvas.KEY_NUM5) editAnyway = true;
            if (onChange != null) onChange.run();
        }
    }

    public boolean switchUp()
    {
        return !edit;
    }

    public boolean switchDown()
    {
        return !edit;
    }

    public void setOnChange(Runnable onChange)
    {
        this.onChange = onChange;
    }
}
