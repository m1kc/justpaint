/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import javax.microedition.lcdui.*;

/**
 * @author m1kc
 */
public class Checklist extends Canvas {

    String headerString = l10n.LocalizationSupport.getMessage("TOOLSMENU");

    boolean[] sel;
    String[] list;
    int cItem = 1;
    int cam = 5;

    /**
     * constructor
     */
    public Checklist() {
        setFullScreenMode(true);
    }

    public void init(String[] s)
    {
        this.list = s;
        this.sel = new boolean[s.length];
    }

    public void reinit()
    {
        for (int i=0; i<sel.length; i++) sel[i]=false;
        for (int i=0; i<JustPaint.mts.myTools.size(); i++)
        {
            int z = ((Integer)JustPaint.mts.myTools.elementAt(i)).intValue();
            sel[z] = true;
        }
    }
    
    /**
     * paint
     */
    public void paint(Graphics g)
    {
        UIPainter.paintBackground(g);
        g.setFont(Common.mainFont);

        int marker = Common.mainFont.getHeight()+cam;

        for (int i=1; i<list.length; i++)
        {
            if (i==cItem)
            {
                g.setColor(0x000000);
                g.fillRect(5, marker, getWidth()-5, Common.mainFont.getHeight());
                g.setColor(0xFFFFFF);
                if (sel[i]) g.drawString("*"+list[i], 10, marker, Graphics.LEFT | Graphics.TOP);
                else g.drawString(list[i], 10, marker, Graphics.LEFT | Graphics.TOP);
            }
            else
            {
                g.setColor(0x000000);
                if (sel[i]) g.drawString("*"+list[i], 5, marker, Graphics.LEFT | Graphics.TOP);
                else g.drawString(list[i], 5, marker, Graphics.LEFT | Graphics.TOP);
            }
            marker += Common.mainFont.getHeight();
        }

        g.setColor(0x000000);
        g.fillRect(0, 0, getWidth(), Common.mainFont.getHeight());
        Gradient.drawVGradient(g, 128, 128, 128, 0, 0, 0, 0, 0, getWidth(), Common.mainFont.getHeight()/2);
        g.setColor(0xFFFFFF);
        g.drawString(headerString, getWidth()/2, 0, Graphics.HCENTER | Graphics.TOP);

        UIPainter.paintSoftkeys(g, Common.okString, Common.cancelString);
    }
    
    /**
     * Called when a key is pressed.
     */
    protected  void keyPressed(int keyCode) 
    {
        if (keyCode==KEY_NUM8 || keyCode == -2) cItem++;
        if (keyCode==KEY_NUM2 || keyCode == -1) cItem--;
        if (cItem >= list.length) cItem=1;
        if (cItem < 1) cItem = list.length-1;

        while (cam+(cItem+2)*Common.mainFont.getHeight() > getHeight()) cam-=Common.mainFont.getHeight();
        while (cam+(cItem-1)*Common.mainFont.getHeight() < 0) cam+=Common.mainFont.getHeight();

        if (keyCode == -5 || keyCode==KEY_NUM5) sel[cItem]=!sel[cItem];

        if (keyCode==-6)
        {
            JustPaint.mts.myTools.removeAllElements();
            for (int i=1; i<list.length; i++)
            {
                if (sel[i]) JustPaint.mts.myTools.addElement(new Integer(i));
            }
            JustPaint.display.setCurrent(JustPaint.mm);
        }

        if (keyCode==-7)
        {
            JustPaint.display.setCurrent(JustPaint.mm);
        }

        repaint();
    }

}