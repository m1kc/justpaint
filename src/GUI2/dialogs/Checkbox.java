/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI2.dialogs;

import javax.microedition.lcdui.Graphics;

/**
 *
 * @author пользователь
 */
public class Checkbox extends Element
{
    String s;
    public boolean checked = false;

    public Checkbox()
    {
        this.s = "";
    }

    public Checkbox(String s)
    {
        this.s = s;
    }

    public Checkbox(String s, boolean checked)
    {
        this.s = s;
        this.checked = checked;
    }

    public void paint(Graphics g)
    {
        g.setColor(Data.fgcolor);
        g.setFont(Data.mainFont);
        if (hasFocus) g.drawRect(x, y, width-1, height-1);
        g.drawString(s, x+2+Data.mainFont.getHeight()+2, y+2, Graphics.LEFT | Graphics.TOP);
        g.drawRect(x+2, y+2, Data.mainFont.getHeight(), Data.mainFont.getHeight());
        if (checked)
        {
            g.drawLine(x+2, y+2, x+2+Data.mainFont.getHeight(), y+2+Data.mainFont.getHeight());
            g.drawLine(x+2+Data.mainFont.getHeight(), y+2, x+2, y+2+Data.mainFont.getHeight());
        }
    }

    public int getPreferredWidth()
    {
        return Data.mainFont.stringWidth(s)+4;
    }

    public int getPreferredHeight()
    {
        return Data.mainFont.getHeight()+4;
    }

    public void onClick()
    {
        checked = !checked;
    }

}
