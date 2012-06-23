/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import javax.microedition.lcdui.*;

/**
 * @author Makc
 */
public final class HPreview extends Canvas
{

    int x=50;
    int y=50;
    int angle=0;
    Image hr;

    String widthString = l10n.LocalizationSupport.getMessage("WIDTH")+": ";
    String heightString = l10n.LocalizationSupport.getMessage("HEIGHT")+": ";
    String angleString = l10n.LocalizationSupport.getMessage("ANGLE")+": ";

    public void genHren()
    {
        hr = Image.createImage(x*2, y*2);
        Graphics m = hr.getGraphics();
        m.setColor(12,234,255);
        m.fillRect(0, 0, x*2, y*2);
        int q = Lib_effects.get(hr, 0, 0, 1);
        int w = Lib_effects.get(hr, 0, 0, 2);
        int e = Lib_effects.get(hr, 0, 0, 3);
        m.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
        m.fillRect(x/2, y/2, x, y);
        hr = Lib_effects.changecolor(hr, q, w, e, 0, 0, 0, 0);
        hr = Lib_turn.rotate(hr, angle);
    }

    /**
     * constructor
     */
    public HPreview()
    {
        setFullScreenMode(true);

        genHren();
    } 
    
    /**
     * paint
     */
    public void paint(Graphics g)
    {
        g.setColor(128,128,128);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(hr, getWidth()/2-hr.getWidth()/2, getHeight()/2-hr.getHeight()/2, Graphics.LEFT | Graphics.TOP);
        g.setColor(255,255,255);
        g.fillRect(0, getHeight()-MainCanvas.nameFont.getHeight()*3, getWidth()/2, MainCanvas.nameFont.getHeight()*3);
        g.setColor(0,0,0);
        g.drawString(widthString+x, 0, getHeight()-MainCanvas.nameFont.getHeight()*3, Graphics.LEFT | Graphics.TOP);
        g.drawString(heightString+y, 0, getHeight()-MainCanvas.nameFont.getHeight()*2, Graphics.LEFT | Graphics.TOP);
        g.drawString(angleString+angle, 0, getHeight()-MainCanvas.nameFont.getHeight(), Graphics.LEFT | Graphics.TOP);

        repaint();
    }
    
    /**
     * Called when a key is pressed.
     */
    protected  void keyPressed(int keyCode)
    {
        if (keyCode==KEY_NUM4) x--;
        if (keyCode==KEY_NUM6) x++;
        if (keyCode==KEY_NUM2) y--;
        if (keyCode==KEY_NUM8) y++;
        if (keyCode==KEY_NUM1) angle--;
        if (keyCode==KEY_NUM3) angle++;

        if (x<1) x=1;
        if (y<1) y=1;

        if (angle==-1) angle=359;
        if (angle==360) angle=0;

        genHren();

        if (keyCode==KEY_NUM5)
        {
            JustPaint.c.isset = true;
            JustPaint.c.hr = hr;
            JustPaint.display.setCurrent(JustPaint.c);
        }

        if (keyCode==-5)
        {
            JustPaint.c.isset = true;
            JustPaint.c.hr = hr;
            JustPaint.display.setCurrent(JustPaint.c);
        }
    }
    
    /**
     * Called when a key is released.
     */
    protected  void keyReleased(int keyCode) {
    }

    /**
     * Called when a key is repeated (held down).
     */
    protected  void keyRepeated(int keyCode)
    {
        if (keyCode==KEY_NUM4) x--;
        if (keyCode==KEY_NUM6) x++;
        if (keyCode==KEY_NUM2) y--;
        if (keyCode==KEY_NUM8) y++;
        if (keyCode==KEY_NUM1) angle--;
        if (keyCode==KEY_NUM3) angle++;

        if (x<1) x=1;
        if (y<1) y=1;

        genHren();
    }
    
    /**
     * Called when the pointer is dragged.
     */
    protected  void pointerDragged(int x, int y) {
    }

    /**
     * Called when the pointer is pressed.
     */
    protected  void pointerPressed(int x, int y) {
    }

    /**
     * Called when the pointer is released.
     */
    protected  void pointerReleased(int x, int y) {
    }

}