/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI2.dialogs;

import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Makc
 */
public class Tools
{

    public static int alpha(int i)
    {
        return ((i >> 24) & 0xff);
    }

    public static int red(int i)
    {
        return ((i >> 16) & 0xff);
    }

    public static int green(int i)
    {
        return ((i >> 8) & 0xff);
    }

    public static int blue(int i)
    {
        return ((i) & 0xff);
    }

    public static void drawVGradient(Graphics graphics, int color1, int color2, int xn, int yn, int wdh, int hgt)
    {
        drawVGradient(graphics, red(color1), green(color1), blue(color1), red(color2), green(color2), blue(color2), xn, yn, wdh, hgt);
    }

    public static void drawHGradient(Graphics graphics, int color1, int color2, int xn, int yn, int wdh, int hgt)
    {
        drawHGradient(graphics, red(color1), green(color1), blue(color1), red(color2), green(color2), blue(color2), xn, yn, wdh, hgt);
    }

    public static void drawVGradient(Graphics graphics, int r1, int g1, int b1, int r2, int g2, int b2, int xn, int yn, int wdh, int hgt)
    {
        try {
            int r,g,b;
            wdh=wdh+xn;
            hgt=hgt+yn;
            for (int i=yn; i<hgt; i++)
            {
                r=r1 + (i-hgt+1) * (r2-r1) / (hgt-yn-1) + (r2-r1);
                g=g1 + (i-hgt+1) * (g2-g1) / (hgt-yn-1) + (g2-g1);
                b=b1 + (i-hgt+1) * (b2-b1) / (hgt-yn-1) + (b2-b1);
                graphics.setColor(r,g,b);
                graphics.drawLine(xn,i,wdh-1,i);
            }
        }
        catch(Throwable ex)
        {

        }
    }

    public static void drawHGradient(Graphics graphics, int r1, int g1, int b1, int r2, int g2, int b2, int xn, int yn, int wdh, int hgt)
    {
        try {
            int r,g,b;
            wdh=wdh+xn;
            hgt=hgt+yn;
            for (int i=xn; i<wdh; i++)
            {
                r=r1 + (i-wdh+1) * (r2-r1) / (wdh-xn-1) + (r2-r1);
                g=g1 + (i-wdh+1) * (g2-g1) / (wdh-xn-1) + (g2-g1);
                b=b1 + (i-wdh+1) * (b2-b1) / (wdh-xn-1) + (b2-b1);
                if (r>255) r=255;
                if (g>255) g=255;
                if (b>255) b=255;
                if (r<0) r=0;
                if (g<0) g=0;
                if (b<0) b=0;
                graphics.setColor(r,g,b);
                graphics.drawLine(i,yn,i,hgt-1);
            }
        }
        catch(Throwable ex)
        {
            
        }
    }


}
