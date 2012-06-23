/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import java.util.Vector;
import javax.microedition.lcdui.*;

/**
 * @author пользователь
 */
public class ImageDispatcher extends Canvas
{
    Vector images = new Vector();
    int s = 0;

    String newSlotString = l10n.LocalizationSupport.getMessage("NEW_SLOT");
    String unloadString = l10n.LocalizationSupport.getMessage("UNLOAD");

    /**
     * constructor
     */
    public ImageDispatcher()
    {
        setFullScreenMode(true);
        images.addElement(null);
    }

    public void doReplace() {
        if ((Image) images.elementAt(s) == null) {
            JustPaint.c.imageChanged(Image.createImage(1, 1));
        } else {
            JustPaint.c.imageChanged((Image) images.elementAt(s));
        }
    }

    public void backup()
    {
        Image i = Image.createImage((Image) images.elementAt(s));
        images.insertElementAt(i, s+1);
    }

    public void removeNulls()
    {
        for (int i=images.size()-1; i>=0; i--)
        {
            if (images.elementAt(i)==null) images.removeElementAt(i);
            else break;
        }
    }
    
    /**
     * paint
     */
    public void paint(Graphics g)
    {
        UIPainter.paintBackground(g);

        g.setClip(0, 0, getWidth(), getHeight()-UIPainter.mainFont.getHeight());
        if (images.elementAt(s) != null) g.drawImage((Image) images.elementAt(s), getWidth()/2, 0, Graphics.HCENTER | Graphics.TOP);
        g.setClip(0, 0, getWidth(), getHeight());

        g.setColor(0x000000);
        g.setFont(UIPainter.mainFont);
        g.drawString(""+(s+1)+"/"+images.size(), getWidth()/2, getHeight(), Graphics.HCENTER | Graphics.BOTTOM);
        UIPainter.paintLeftSoft(g, newSlotString);
        UIPainter.paintRightSoft(g, unloadString);
    }
    
    /**
     * Called when a key is pressed.
     */
    protected  void keyPressed(int keyCode)
    {
        if (getGameAction(keyCode)==LEFT) s--;
        if (s<0) s=images.size()-1;

        if (getGameAction(keyCode)==RIGHT) s++;
        if (s>=images.size()) s=0;
        doReplace();

        if (getGameAction(keyCode)==FIRE) JustPaint.display.setCurrent(JustPaint.c);
        
        if (keyCode==-6)
        {
            images.addElement(null);
            s = images.size()-1;
            doReplace();
        }
        if (keyCode==-7)
        {
            images.removeElementAt(s);
            System.gc();
            s--;
            if (s<0) s=0;
            if (images.isEmpty())
            {
                images.addElement(null);
                s = 0;
            }
            doReplace();
        }

        repaint();
    }

    public void pointerChanged(Image img)
    {
        images.setElementAt(img, s);
    }

}