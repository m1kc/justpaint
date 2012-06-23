/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import javax.microedition.lcdui.*;

/**
 * @author Makc
 */
public class StartScreen extends Canvas
{
    Image title,back,title_small;

    Font nameFont = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);

    int b;

    /**
     * constructor
     */
    public StartScreen() 
    {
        this.setFullScreenMode(true);

        try
        {
            title = Image.createImage("/title.png");
            title_small = Image.createImage("/title_small.png");
            back = Image.createImage("/back.png");
        }
        catch (Throwable ex)
        {
            title = title_small = back = Image.createImage(1,1);
        }

        b = getHeight();
    } 

    /**
     * paint
     */
    public void paint(Graphics g)
    {
        //Gradient.drawVGradient(g, 232, 241, 247, 75, 111, 108, 0, 0, getWidth(), getHeight());
        //g.drawImage(logo, getWidth()/2-logo.getWidth()/2, getHeight()/2-logo.getHeight()/2, Graphics.LEFT | Graphics.TOP);
        //g.setColor(43,94,136);
        g.setColor(0x000000);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(back, getWidth(), getHeight(), Graphics.RIGHT | Graphics.BOTTOM);
        if (getWidth()>=240) g.drawImage(title, 0, 0, Graphics.LEFT | Graphics.TOP);
        else g.drawImage(title_small, 0, 0, Graphics.LEFT | Graphics.TOP);

        g.setFont(nameFont);
        //g.setColor(32,32,32);
        //g.fillRect(0, b, nameFont.stringWidth("Создать"), nameFont.getHeight());
        //g.fillRect(getWidth()-nameFont.stringWidth("Открыть"), b, nameFont.stringWidth("Открыть"), nameFont.getHeight());
        g.setColor(255,255,255);
        g.drawString("Создать", 0, b, Graphics.LEFT | Graphics.TOP);
        g.drawString("Открыть", getWidth(), b, Graphics.RIGHT | Graphics.TOP);

        if (b>getHeight()-nameFont.getHeight()) b--;

        repaint();
    }
    
    /**
     * Called when a key is pressed.
     */
    protected  void keyPressed(int keyCode)
    {
        if (keyCode==-6) JustPaint.ci.activate();
        if (keyCode==-7) JustPaint.display.setCurrent(JustPaint.fm);
    }
    
    /**
     * Called when a key is released.
     */
    protected  void keyReleased(int keyCode) {
    }

    /**
     * Called when a key is repeated (held down).
     */
    protected  void keyRepeated(int keyCode) {
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
