/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI2.dialogs;

import javax.microedition.lcdui.*;

/**
 *
 * @author пользователь
 */
public class TextField extends Element implements CommandListener
{
    String content;

    TextBox t;
    Command c;
    Displayable f;

    public TextField()
    {
        this.content = "";
    }

    public TextField(String s)
    {
        this.content = s;
    }

    public void paint(Graphics g)
    {
        g.setFont(Data.mainFont);
        if (hasFocus) g.setColor(Data.fgcolor); else g.setColor(Data.shadowcolor);
        g.drawRect(x, y, width - 1, height - 1);
        g.setColor(Data.fgcolor);
        g.drawString(getContent(), x+2, y+2, Graphics.LEFT | Graphics.TOP);
    }

    public int getPreferredWidth()
    {
        return Data.mainFont.stringWidth(getContent())+4;
    }

    public int getPreferredHeight()
    {
        return Data.mainFont.getHeight()+4;
    }

    public void onClick()
    {
        f = Data.display.getCurrent();
        t = new TextBox(null, getContent(),Integer.MAX_VALUE,javax.microedition.lcdui.TextField.ANY);
        c = new Command("OK",Command.OK,1);
        t.addCommand(c);
        t.setCommandListener(this);
        Data.display.setCurrent(t);
    }

    public void commandAction(Command c, Displayable d)
    {
        if (c==this.c)
        {
            setContent(t.getString());
            Data.display.setCurrent(f);
            t = null;
            c = null;
            System.gc();
        }
    }

    /**
     * @return the content
     */
    public String getContent()
    {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content)
    {
        this.content = content;
    }

}
