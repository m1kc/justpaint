/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI2.dialogs;

import java.util.*;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

/**
 * @author пользователь
 */
public class Dialog extends Canvas
{
    Vector elements = new Vector();

    int s = -1;
    Element selection = null;

    public int offset = 2;
    
    int layout = BOX;

    int camY = 0;

    final static int BOX = 0;

    public String header = null;

    boolean gradientsEnabled = true;

    /**
     * constructor
     */
    public Dialog(MIDlet midlet)
    {
        setFullScreenMode(true);
        Data.midlet = midlet;
        Data.display = Display.getDisplay(midlet);
    }

    /**
     * paint
     */
    public void paint(Graphics g)
    {
        g.setClip(0, 0, getWidth(), getHeight());

        g.setColor(Data.bgcolor);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (gradientsEnabled)
        {
            Tools.drawVGradient(g, Data.bGradient1, Data.bGradient2, 0, 0, getWidth(), getHeight());
        }

        g.translate(0, -camY);
        for (int i=0; i<elements.size(); i++)
        {
            Element e = ((Element)elements.elementAt(i));
            g.setClip(e.x, e.y, e.width, e.height);
            e.paint(g);
        }
        g.translate(0, camY);

        g.setClip(0, 0, getWidth(), getHeight());
        if (header != null)
        {
            g.setColor(Data.fgcolor);
            g.fillRect(0, 0, getWidth(), Data.headerFont.getHeight());
            if (gradientsEnabled)
            {
                Tools.drawVGradient(g, Data.shadowcolor, Data.fgcolor, 0, 0, getWidth(), Data.headerFont.getHeight()/2);
            }
            g.setColor(Data.bgcolor);
            g.setFont(Data.headerFont);
            g.drawString(header, getWidth()/2, 0, Graphics.HCENTER | Graphics.TOP);
        }

        repaint();
    }

    public int headerHeight()
    {
        return header==null ? 0 : Data.headerFont.getHeight();
    }

    public void addElement(Element e)
    {
        elements.addElement(e);
        appendLayout(layout);
        refocus();
    }

    public void removeElement(Element e)
    {
        elements.removeElement(e);
        appendLayout(layout);
        if (e==selection)
        {
            selection = null;
            refocus();
        }
    }

    public void appendLayout(int type)
    {
        if (type==BOX)
        {
            int y = headerHeight()+offset;
            for (int i=0; i<elements.size(); i++)
            {
                Element e = ((Element)elements.elementAt(i));

                e.width = getWidth();
                e.x = 0;
                e.y = y;
                e.height = e.getPreferredHeight();
                e.onResize();

                y+=e.height;
                y+=offset;
            }
        }
    }

    private void focusUp()
    {
        if (selection != null)
        {
            if (!selection.switchUp()) return;
        }
        setFocus(s-1);
    }

    public void focusDown()
    {
        if (selection != null)
        {
            if (!selection.switchDown()) return;
        }
        setFocus(s+1);
    }

    public void setFocus(int i)
    {
        if (selection != null)
        {
            selection.focusLost();
            selection.hasFocus = false;
            selection = null;
        }
        s = i;
        refocus();
    }

    public void refocus()
    {
        if ((s==-1)&&(!elements.isEmpty())) s = 0;
        if (s>=elements.size()) s=elements.size()-1;
        if (s>=0)
        {
            selection = (Element)elements.elementAt(s);
            selection.focusGained();
            selection.hasFocus = true;
            if (selection.y < camY+headerHeight()) camY = selection.y-headerHeight();
            if (selection.y+selection.height-camY > getHeight()) camY = selection.y + selection.height - getHeight();
        }
    }

    /**
     * Called when a key is pressed.
     */
    protected  void keyPressed(int keyCode)
    {
        if (elements.isEmpty()) return;

        selection.keyPressed(keyCode,getGameAction(keyCode));

        if (getGameAction(keyCode)==FIRE)
        {
            selection.onClick();
        }

        if (getGameAction(keyCode)==DOWN)
        {
            focusDown();
        }

        if (getGameAction(keyCode)==UP)
        {
            focusUp();
        }
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
        for (int i=0; i<elements.size(); i++)
        {
            Element e = (Element) elements.elementAt(i);
            if ((x>=e.x)&&(y>=e.y)&&(x<=e.x+e.width)&&(y<=e.y+e.height))
            {
                if (s==i)
                {
                    selection.onClick();
                }
                else
                {
                    setFocus(i);
                }
            }
        }
    }

    /**
     * Called when the pointer is released.
     */
    protected  void pointerReleased(int x, int y)
    {
    }

}
