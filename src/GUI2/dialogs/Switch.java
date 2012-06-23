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
public class Switch extends Element
{
    String title;
    String[] vars;
    public int s;

    public Switch(String title, String[] vars)
    {
        this.title = title;
        this.vars = vars;
        s = 0;
    }

    public Switch(String title, String[] vars, int defaultVar)
    {
        this.title = title;
        this.vars = vars;
        this.s = defaultVar;
    }

    public void paint(Graphics g)
    {
        g.setColor(Data.fgcolor);
        g.setFont(Data.mainFont);
        if (hasFocus) g.drawRect(x, y, width-1, height-1);
        g.drawString(title, x+2, y+2, Graphics.LEFT | Graphics.TOP);
        g.drawString(vars[s], x+width-2, y+2, Graphics.RIGHT | Graphics.TOP);
    }

    public int getPreferredWidth()
    {
        int l = 0, l2;
        for (int i=0; i<vars.length; i++)
        {
            if (Data.mainFont.stringWidth(vars[i])>l) l = Data.mainFont.stringWidth(vars[i]);
        }
        l2 = Data.mainFont.stringWidth(title);
        return l+l2+4;
    }

    public int getPreferredHeight()
    {
        return Data.mainFont.getHeight()+4;
    }

    public void keyPressed(int k, int a)
    {
        if (a==Canvas.LEFT) s--;
        if (a==Canvas.RIGHT) s++;
        if (s<0) s=vars.length-1;
        if (s>=vars.length) s=0;
    }

}
