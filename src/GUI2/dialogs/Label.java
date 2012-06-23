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
public class Label extends Element
{
    String s;

    public Label()
    {
        this.s = "";
    }

    public Label(String s)
    {
        this.s = s;
    }

    public void paint(Graphics g)
    {
        g.setColor(Data.fgcolor);
        g.setFont(Data.mainFont);
        if (hasFocus) g.drawRect(x, y, width-1, height-1);
        g.drawString(s, x+2, y+2, Graphics.LEFT | Graphics.TOP);
    }

    public int getPreferredWidth()
    {
        return Data.mainFont.stringWidth(s)+4;
    }

    public int getPreferredHeight()
    {
        return Data.mainFont.getHeight()+4;
    }

}
