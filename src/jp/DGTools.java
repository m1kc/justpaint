/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import com.nokia.mid.ui.*;
import javax.microedition.lcdui.*;

/**
 *
 * @author пользователь
 */
public class DGTools
{
    public static Image insertImage(Image img, Image img2, int x, int y)
    {
        if (!img.isMutable())
        {
            Image temp = DirectUtils.createImage(img.getWidth(), img.getHeight(), 0x00FFFFFF);
            Graphics g = temp.getGraphics();
            g.drawImage(img, 0, 0, Graphics.LEFT | Graphics.TOP);
            g.drawImage(img2, x, y, Graphics.LEFT | Graphics.TOP);
            return temp;
        }
        else
        {
            Graphics g = img.getGraphics();
            g.drawImage(img2, x, y, Graphics.LEFT | Graphics.TOP);
            return img;
        }
    }

    public static Image createImage(int x,int y,int color)
    {
        try
        {
            return DirectUtils.createImage(x, y, color);
        }
        catch (Throwable ex)
        {
            return Image.createImage(x, y);
        }
    }

    public static void setColor(Graphics graphics,int a,int r,int g,int b)
    {
        try
        {
            DirectUtils.getDirectGraphics(graphics).setARGBColor(color(a,r,g,b));
        }
        catch (Throwable ex)
        {
        }
    }

    public static int color(int alpha, int r, int g, int b)
    {
        return (alpha << 24) + (r << 16) + (g << 8) + b;
    }
}
