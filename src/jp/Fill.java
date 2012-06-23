/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import javax.microedition.lcdui.*;

/**
 *
 * @author Makc
 */
public class Fill
{
    static Image img;
    static int[] pixel;
    static int qa,qr,qg,qb;

    //static Form form = new Form(null);

    public static void prepare(int x,int y)
    {
        pixel = new int[img.getWidth() * img.getHeight()];
        img.getRGB(pixel, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        qa = (pixel[y * img.getWidth() + x] >> 24) & 0xff;
        qr = (pixel[y * img.getWidth() + x] >> 16) & 0xff;
        qg = (pixel[y * img.getWidth() + x] >> 8) & 0xff;
        qb = pixel[y * img.getWidth() + x] & 0xff;

        //JustPaint.display.setCurrent(form);
    }

    public static Image fill(int x,int y,int r,int g,int b,boolean genImage)
    {
        //form.append("Обработка: "+x+","+y);


        try
        {
            pixel[y * img.getWidth() + x] = (qa << 24) | (r << 16) | (g << 8) | b;
        }
        catch (Throwable ex)
        {
            //form.append("Обработка: "+x+","+y+" failed");
        }

        int ua,ur,ug,ub;

        try
        {
            ua = (pixel[(y-1) * img.getWidth() + x] >> 24) & 0xff;
            ur = (pixel[(y-1) * img.getWidth() + x] >> 16) & 0xff;
            ug = (pixel[(y-1) * img.getWidth() + x] >> 8) & 0xff;
            ub = pixel[(y-1) * img.getWidth() + x] & 0xff;

            if ((ua==qa)&&(ur==qr)&&(ug==qg)&&(ub==qb)) fill(x,y-1,r,g,b,false);
        }
        catch (Throwable ex)
        {
        }

        try
        {
            ua = (pixel[(y+1) * img.getWidth() + x] >> 24) & 0xff;
            ur = (pixel[(y+1) * img.getWidth() + x] >> 16) & 0xff;
            ug = (pixel[(y+1) * img.getWidth() + x] >> 8) & 0xff;
            ub = pixel[(y+1) * img.getWidth() + x] & 0xff;

            if ((ua==qa)&&(ur==qr)&&(ug==qg)&&(ub==qb)) fill(x,y+1,r,g,b,false);
        }
        catch (Throwable ex)
        {
        }

        try
        {
            ua = (pixel[y * img.getWidth() + (x+1)] >> 24) & 0xff;
            ur = (pixel[y * img.getWidth() + (x+1)] >> 16) & 0xff;
            ug = (pixel[y * img.getWidth() + (x+1)] >> 8) & 0xff;
            ub = pixel[y * img.getWidth() + (x+1)] & 0xff;

            if ((ua==qa)&&(ur==qr)&&(ug==qg)&&(ub==qb)) fill(x+1,y,r,g,b,false);
        }
        catch (Throwable ex)
        {
        }

        try
        {
            ua = (pixel[y * img.getWidth() + (x-1)] >> 24) & 0xff;
            ur = (pixel[y * img.getWidth() + (x-1)] >> 16) & 0xff;
            ug = (pixel[y * img.getWidth() + (x-1)] >> 8) & 0xff;
            ub = pixel[y * img.getWidth() + (x-1)] & 0xff;

            if ((ua==qa)&&(ur==qr)&&(ug==qg)&&(ub==qb)) fill(x-1,y,r,g,b,false);
        }
        catch (Throwable ex)
        {
        }

        try
        {
            if (genImage) img = Image.createRGBImage(pixel, img.getWidth(), img.getHeight(), true);
        }
        catch (Throwable ex)
        {
            //form.append("Обработка: "+x+","+y+" failed");
        }

        /*try {
        Thread.sleep(5000);
        } catch (InterruptedException ex) {
        ex.printStackTrace();
        }
        JustPaint.display.setCurrent(JustPaint.c);*/

        return img;
    }
}
