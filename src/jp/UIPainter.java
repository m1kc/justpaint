/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import javax.microedition.lcdui.*;
/**
 *
 * @author пользователь
 */
public class UIPainter
{
    public static Font mainFont = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);

    public static void paintLeftSoft(Graphics g, String text)
    {
        Displayable d = JustPaint.display.getCurrent();
        Font f = g.getFont();

        g.setColor(0,0,0);
        g.fillRect(0, d.getHeight()-f.getHeight(), f.stringWidth(text), f.getHeight());
        g.fillTriangle(f.stringWidth(text), d.getHeight()-f.getHeight(),
                f.stringWidth(text), d.getHeight(),
                f.stringWidth(text)+f.getHeight(), d.getHeight());
        g.setColor(0xFFFFFF);
        g.drawString(text, 0, d.getHeight(), Graphics.LEFT | Graphics.BOTTOM);
    }

    public static void paintRightSoft(Graphics g, String text)
    {
        Displayable d = JustPaint.display.getCurrent();
        Font f = g.getFont();

        g.setColor(0,0,0);
        g.fillRect(d.getWidth()-f.stringWidth(text), d.getHeight()-f.getHeight(), f.stringWidth(text), f.getHeight());
        g.fillTriangle(d.getWidth()-f.stringWidth(text), d.getHeight()-f.getHeight(),
                d.getWidth()-f.stringWidth(text), d.getHeight(),
                d.getWidth()-f.stringWidth(text)-f.getHeight(), d.getHeight());
        g.setColor(0xFFFFFF);
        g.drawString(text, d.getWidth(), d.getHeight(), Graphics.RIGHT | Graphics.BOTTOM);
    }

    public static void paintSoftkeys(Graphics g, String left, String right)
    {
        if (left != null) paintLeftSoft(g,left);
        if (right != null) paintRightSoft(g,right);
    }

    public static void paintBackground(Graphics g)
    {
        Displayable d = JustPaint.display.getCurrent();

        g.setColor(0xFFFFFF);
        g.fillRect(0, 0, d.getWidth(), d.getHeight());
        Gradient.drawVGradient(g, 255, 255, 255, 128, 128, 128, 0, 0, d.getWidth(), d.getHeight());
    }
}
