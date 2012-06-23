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
public class Button extends Element
{
    public String s;
    public Runnable r;

    public Button()
    {
        this.s = "";
        r = null;
    }

    public Button(String s)
    {
        this.s = s;
        r = null;
    }

    public Button(String s, Runnable r)
    {
        this.s = s;
        this.r = r;
    }

    public void paint(Graphics g)
    {
        g.setColor(Data.fgcolor);
        g.setFont(Data.mainFont);
        if (hasFocus) g.drawRect(x+10, y, width-21, height-1);
        g.drawString(s, x+width/2, y+2, Graphics.HCENTER | Graphics.TOP);
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
        r.run();
    }

}
