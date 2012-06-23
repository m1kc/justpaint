/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import java.util.*;
import javax.microedition.lcdui.*;

/**
 *
 * @author Makc
 */
public class Tools
{
    public static Image makeMutable(Image i)
    {
        Image c = Image.createImage(i.getWidth(), i.getHeight());
        Graphics m = c.getGraphics();
        m.drawImage(i, 0, 0, Graphics.LEFT | Graphics.TOP);
        return c;
    }

    public static String[] EnumerationToString(Enumeration enumeration, String s)
    {
        Vector vector = new Vector();
        for (; enumeration.hasMoreElements(); vector.addElement(enumeration.nextElement())) { }
        String as[] = new String[vector.size()];
        for (int i = 0; i < vector.size(); i++)
        {
            as[i] = (s.length() != 0 ? s : "/") + vector.elementAt(i);
        }

        return as;
    }
    
    public static Image putPixel(Image img,int x,int y)
    {
        int[] pixel = new int[img.getWidth() * img.getHeight()];
        img.getRGB(pixel, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        pixel[y * img.getWidth() + x] = (255 << 24) | (JustPaint.r << 16) | (JustPaint.g << 8) | JustPaint.b;

        return Image.createRGBImage(pixel, img.getWidth(), img.getHeight(), true);
    }

    public static Image putImage(Image img,Image img2,int x,int y)
    {
        int[] pixel = new int[img.getWidth() * img.getHeight()];
        img.getRGB(pixel, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        int[] pixel2 = new int[img2.getWidth() * img2.getHeight()];
        img2.getRGB(pixel2, 0, img2.getWidth(), 0, 0, img2.getWidth(), img2.getHeight());

        int i,j;
        for (i=0; i<img2.getWidth(); i++)
        {
            for (j=0; j<img2.getHeight(); j++)
            {
                int qqa = (pixel2[j * img2.getWidth() + i] >> 24) & 0xff;
                int qqr = (pixel2[j * img2.getWidth() + i] >> 16) & 0xff;
                int qqg = (pixel2[j * img2.getWidth() + i] >> 8) & 0xff;
                int qqb = pixel2[j * img2.getWidth() + i] & 0xff;

                int qa = (pixel[(j+y) * img.getWidth() + (x+i)] >> 24) & 0xff;
                int qr = (pixel[(j+y) * img.getWidth() + (x+i)] >> 16) & 0xff;
                int qg = (pixel[(j+y) * img.getWidth() + (x+i)] >> 8) & 0xff;
                int qb = pixel[(j+y) * img.getWidth() + (x+i)] & 0xff;

                if ((qqa >= 64)&&(qqa<=192))
                {
                    // Типа блендинг
                    qr = (qr+qqr)/2;
                    qg = (qg+qqg)/2;
                    qb = (qb+qqb)/2;
                }

                if (qqa == 255)
                {
                    qr = qqr;
                    qg = qqg;
                    qb = qqb;
                    qa = 255;
                }

                if (qa == 0)
                {
                    qa = qqa;
                    qr = qqr;
                    qg = qqg;
                    qb = qqb;
                }

                pixel[(j+y) * img.getWidth() + (x+i)] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
            }
        }

        return Image.createRGBImage(pixel, img.getWidth(), img.getHeight(), true);
    }

    public static Image replaceHGradient(Image img, int r,int g,int b,int r1,int g1,int b1,int r2,int g2,int b2)
    {
        int[] pixel = new int[img.getWidth() * img.getHeight()];
        img.getRGB(pixel, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        int i,j;
        for (i=0; i<img.getWidth(); i++)
        {
            for (j=0; j<img.getHeight(); j++)
            {
                int qa = (pixel[j * img.getWidth() + i] >> 24) & 0xff;
                int qr = (pixel[j * img.getWidth() + i] >> 16) & 0xff;
                int qg = (pixel[j * img.getWidth() + i] >> 8) & 0xff;
                int qb = pixel[j * img.getWidth() + i] & 0xff;

                if ((qa>0)&&(qr==r)&&(qg==g)&&(qb==b))
                {
                    qr=r1 + (i-img.getWidth()+1) * (r2-r1) / (img.getWidth()-1) + (r2-r1);
                    qg=g1 + (i-img.getWidth()+1) * (g2-g1) / (img.getWidth()-1) + (g2-g1);
                    qb=b1 + (i-img.getWidth()+1) * (b2-b1) / (img.getWidth()-1) + (b2-b1);
                }

                pixel[j * img.getWidth() + i] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
            }
        }

        return Image.createRGBImage(pixel, img.getWidth(), img.getHeight(), true);
    }

    public static Image replaceVGradient(Image img, int r,int g,int b,int r1,int g1,int b1,int r2,int g2,int b2)
    {
        int[] pixel = new int[img.getWidth() * img.getHeight()];
        img.getRGB(pixel, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        int i,j;
        for (i=0; i<img.getWidth(); i++)
        {
            for (j=0; j<img.getHeight(); j++)
            {
                int qa = (pixel[j * img.getWidth() + i] >> 24) & 0xff;
                int qr = (pixel[j * img.getWidth() + i] >> 16) & 0xff;
                int qg = (pixel[j * img.getWidth() + i] >> 8) & 0xff;
                int qb = pixel[j * img.getWidth() + i] & 0xff;

                if ((qa>0)&&(qr==r)&&(qg==g)&&(qb==b))
                {
                    qr=r1 + (j-img.getHeight()+1) * (r2-r1) / (img.getHeight()-1) + (r2-r1);
                    qg=g1 + (j-img.getHeight()+1) * (g2-g1) / (img.getHeight()-1) + (g2-g1);
                    qb=b1 + (j-img.getHeight()+1) * (b2-b1) / (img.getHeight()-1) + (b2-b1);
                }

                pixel[j * img.getWidth() + i] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
            }
        }

        return Image.createRGBImage(pixel, img.getWidth(), img.getHeight(), true);
    }

    public static Image fillBackground(Image img,int r,int g,int b,int r1,int g1,int b1)
    {
        int[] pixel = new int[img.getWidth() * img.getHeight()];
        img.getRGB(pixel, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        int i,j;
        for (i=0; i<img.getWidth(); i++)
        {
            boolean brake = false;
            for (j=0; j<img.getHeight(); j++)
            {
                int qa = (pixel[j * img.getWidth() + i] >> 24) & 0xff;
                int qr = (pixel[j * img.getWidth() + i] >> 16) & 0xff;
                int qg = (pixel[j * img.getWidth() + i] >> 8) & 0xff;
                int qb = pixel[j * img.getWidth() + i] & 0xff;

                if ((!brake)&&(qa>0)&&(qr==r)&&(qg==g)&&(qb==b))
                {
                    qr = r1;
                    qg = g1;
                    qb = b1;
                }
                else
                {
                    brake=true;
                }

                pixel[j * img.getWidth() + i] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
            }
            brake = false;
            for (j=img.getHeight()-1; j>=0; j--)
            {
                int qa = (pixel[j * img.getWidth() + i] >> 24) & 0xff;
                int qr = (pixel[j * img.getWidth() + i] >> 16) & 0xff;
                int qg = (pixel[j * img.getWidth() + i] >> 8) & 0xff;
                int qb = pixel[j * img.getWidth() + i] & 0xff;

                if ((!brake)&&(qa>0)&&(qr==r)&&(qg==g)&&(qb==b))
                {
                    qr = r1;
                    qg = g1;
                    qb = b1;
                }
                else
                {
                    brake=true;
                }

                pixel[j * img.getWidth() + i] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
            }
        }

        return Image.createRGBImage(pixel, img.getWidth(), img.getHeight(), true);
    }

    public static Image halfAlpha(Image img)
    {
        int[] pixel = new int[img.getWidth() * img.getHeight()];
        img.getRGB(pixel, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        int i,j;
        for (i=0; i<img.getWidth(); i++)
        {
            for (j=0; j<img.getHeight(); j++)
            {
                int qa = (pixel[j * img.getWidth() + i] >> 24) & 0xff;
                int qr = (pixel[j * img.getWidth() + i] >> 16) & 0xff;
                int qg = (pixel[j * img.getWidth() + i] >> 8) & 0xff;
                int qb = pixel[j * img.getWidth() + i] & 0xff;

                qa = qa / 2;

                pixel[j * img.getWidth() + i] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
            }
        }

        return Image.createRGBImage(pixel, img.getWidth(), img.getHeight(), true);
    }

    public static Image halfAlphaWithGradient(Image img)
    {
        int[] pixel = new int[img.getWidth() * img.getHeight()];
        img.getRGB(pixel, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        int i,j;
        for (i=0; i<img.getWidth(); i++)
        {
            for (j=0; j<img.getHeight(); j++)
            {
                int qa = (pixel[j * img.getWidth() + i] >> 24) & 0xff;
                int qr = (pixel[j * img.getWidth() + i] >> 16) & 0xff;
                int qg = (pixel[j * img.getWidth() + i] >> 8) & 0xff;
                int qb = pixel[j * img.getWidth() + i] & 0xff;

                qa = 128 * (img.getHeight()-j) / img.getHeight();

                pixel[j * img.getWidth() + i] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
            }
        }

        return Image.createRGBImage(pixel, img.getWidth(), img.getHeight(), true);
    }

    public static Image radialGradient(int x,int y,int r1,int g1,int b1,int r2,int g2,int b2)
    {
        try
        {
            int[] pixel = new int[x * y];

            int cx = x/2;
            int cy = y/2;
            int radius = x/2;

            int i,j;
            for (i=0; i<x; i++)
            {
                for (j=0; j<y; j++)
                {
                    int qa = 0;
                    int qr = 0;
                    int qg = 0;
                    int qb = 0;

                    if (Math.sqrt((i-cx)*(i-cx)+(j-cy)*(j-cy))>=radius)
                    {
                    }
                    else
                    {
                        int dist = (int) Math.sqrt((i-cx)*(i-cx)+(j-cy)*(j-cy));

                        //double e = dist/radius;
                        //qr = (int) ((r1 - r2) * e + r1);
                        //qg = (int) ((g1 - g2) * e + g1);
                        //qb = (int) ((b1 - b2) * e + b1);

                        qa = 255;

                        qr=r1 + (dist-radius+1) * (r2-r1) / (radius-1) + (r2-r1);
                        qg=g1 + (dist-radius+1) * (g2-g1) / (radius-1) + (g2-g1);
                        qb=b1 + (dist-radius+1) * (b2-b1) / (radius-1) + (b2-b1);
                    }

                    pixel[j * x + i] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
                }
            }

            return Image.createRGBImage(pixel, x, y, true);
        }
        catch (Throwable ex)
        {
            return Image.createImage(1, 1);
        }
    }

    public static Image clearAreaInImage(Image img,int x,int y,int width,int height)
    {
        int[] pixel = new int[img.getWidth() * img.getHeight()];
        img.getRGB(pixel, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        int i,j;
        for (i=0; i<width; i++)
        {
            for (j=0; j<height; j++)
            {
                int qa = (pixel[(j+y) * img.getWidth() + (x+i)] >> 24) & 0xff;
                int qr = (pixel[(j+y) * img.getWidth() + (x+i)] >> 16) & 0xff;
                int qg = (pixel[(j+y) * img.getWidth() + (x+i)] >> 8) & 0xff;
                int qb = pixel[(j+y) * img.getWidth() + (x+i)] & 0xff;

                qa = qr = qg = qb = 0;

                pixel[(j+y) * img.getWidth() + (x+i)] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
            }
        }

        return Image.createRGBImage(pixel, img.getWidth(), img.getHeight(), true);
    }

    public static Image fillEverything(Image img)
    {
        int[] pixel = new int[img.getWidth() * img.getHeight()];
        img.getRGB(pixel, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        int i,j;
        for (i=0; i<img.getWidth(); i++)
        {
            for (j=0; j<img.getHeight(); j++)
            {
                int qa = (pixel[j * img.getWidth() + i] >> 24) & 0xff;
                int qr = (pixel[j * img.getWidth() + i] >> 16) & 0xff;
                int qg = (pixel[j * img.getWidth() + i] >> 8) & 0xff;
                int qb = pixel[j * img.getWidth() + i] & 0xff;

                if (qa > 0)
                {
                    qr = JustPaint.r;
                    qg = JustPaint.g;
                    qb = JustPaint.b;
                }

                pixel[j * img.getWidth() + i] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
            }
        }

        return Image.createRGBImage(pixel, img.getWidth(), img.getHeight(), true);
    }

    static Image leaveOnlyEllipse(Image img, int x, int y, int w, int h)
    {
        int[] pixel = new int[img.getWidth() * img.getHeight()];
        img.getRGB(pixel, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        Image img2 = Image.createImage(w, h);
        Graphics g = img2.getGraphics();
        g.setColor(0xFF0000);
        g.fillArc(0, 0, w, h, 0, 360);
        int[] pixel2 = new int[img2.getWidth() * img2.getHeight()];
        img2.getRGB(pixel2, 0, img2.getWidth(), 0, 0, img2.getWidth(), img2.getHeight());

        int i,j;
        for (i=0; i<img2.getWidth(); i++)
        {
            for (j=0; j<img2.getHeight(); j++)
            {
                int qqa = (pixel2[j * img2.getWidth() + i] >> 24) & 0xff;
                int qqr = (pixel2[j * img2.getWidth() + i] >> 16) & 0xff;
                int qqg = (pixel2[j * img2.getWidth() + i] >> 8) & 0xff;
                int qqb = pixel2[j * img2.getWidth() + i] & 0xff;

                int qa = (pixel[(j+y) * img.getWidth() + (x+i)] >> 24) & 0xff;
                int qr = (pixel[(j+y) * img.getWidth() + (x+i)] >> 16) & 0xff;
                int qg = (pixel[(j+y) * img.getWidth() + (x+i)] >> 8) & 0xff;
                int qb = pixel[(j+y) * img.getWidth() + (x+i)] & 0xff;

                if (qqb != 0) qa=qr=qg=qb=0;

                pixel[(j+y) * img.getWidth() + (x+i)] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
            }
        }

        for (i=0; i<img.getWidth(); i++)
        {
            for (j=0; j<img.getHeight(); j++)
            {
                int qa = (pixel[j * img.getWidth() + i] >> 24) & 0xff;
                int qr = (pixel[j * img.getWidth() + i] >> 16) & 0xff;
                int qg = (pixel[j * img.getWidth() + i] >> 8) & 0xff;
                int qb = pixel[j * img.getWidth() + i] & 0xff;

                if ((i<x)||(i>=x+w)||(j<y)||(j>=y+h)) qa=qr=qg=qb=0;

                pixel[j * img.getWidth() + i] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
            }
        }

        return Image.createRGBImage(pixel, img.getWidth(), img.getHeight(), true);
    }

    public static Image modifyAlpha(Image img, int p)
    {
        int[] pixel = new int[img.getWidth() * img.getHeight()];
        img.getRGB(pixel, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        int i,j;
        for (i=0; i<img.getWidth(); i++)
        {
            for (j=0; j<img.getHeight(); j++)
            {
                int qa = (pixel[j * img.getWidth() + i] >> 24) & 0xff;
                int qr = (pixel[j * img.getWidth() + i] >> 16) & 0xff;
                int qg = (pixel[j * img.getWidth() + i] >> 8) & 0xff;
                int qb = pixel[j * img.getWidth() + i] & 0xff;

                qa = qa * p / 100;

                pixel[j * img.getWidth() + i] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
            }
        }

        return Image.createRGBImage(pixel, img.getWidth(), img.getHeight(), true);
    }

    static Image appendMask(Image img, Image mask)
    {
        int[] pixel = new int[img.getWidth() * img.getHeight()];
        img.getRGB(pixel, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        int[] pixel2 = new int[mask.getWidth() * mask.getHeight()];
        mask.getRGB(pixel2, 0, mask.getWidth(), 0, 0, mask.getWidth(), mask.getHeight());

        int i,j;
        for (i=0; i<mask.getWidth(); i++)
        {
            for (j=0; j<mask.getHeight(); j++)
            {
                int qqa = (pixel2[j * mask.getWidth() + i] >> 24) & 0xff;
                int qqr = (pixel2[j * mask.getWidth() + i] >> 16) & 0xff;
                int qqg = (pixel2[j * mask.getWidth() + i] >> 8) & 0xff;
                int qqb = pixel2[j * mask.getWidth() + i] & 0xff;

                int qa = (pixel[j * img.getWidth() + i] >> 24) & 0xff;
                int qr = (pixel[j * img.getWidth() + i] >> 16) & 0xff;
                int qg = (pixel[j * img.getWidth() + i] >> 8) & 0xff;
                int qb = pixel[j * img.getWidth() + i] & 0xff;

                if (qqr == 0) qa = 0;
                else if (qqr == 255) qa = 255;
                else qa = qa * qqr / 255;

                pixel[j * img.getWidth() + i] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
            }
        }

        return Image.createRGBImage(pixel, img.getWidth(), img.getHeight(), true);
    }

    public static Image scale2x(Image img)
    {
        int[] pixel = new int[img.getWidth() * img.getHeight()];
        img.getRGB(pixel, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        int[] pixel2 = new int[img.getWidth()*2 * img.getHeight()*2];

        int i,j;

        for (i=0; i<img.getWidth(); i++)
        {
            for (j=0; j<img.getHeight(); j++)
            {
                int p = pixel[j * img.getWidth() + i];

                pixel2[j*2 * img.getWidth()*2 + i*2] = p;
                pixel2[j*2 * img.getWidth()*2 + i*2+1] = p;
                pixel2[(j*2+1) * img.getWidth()*2 + i*2] = p;
                pixel2[(j*2+1) * img.getWidth()*2 + i*2+1] = p;
            }
        }

        for (i=1; i<img.getWidth()-1; i++)
        {
            for (j=1; j<img.getHeight()-1; j++)
            {
                int p = pixel[j * img.getWidth() + i];

                int a = pixel[(j-1) * img.getWidth() + i];
                int b = pixel[j * img.getWidth() + (i+1)];
                int c = pixel[j * img.getWidth() + (i-1)];
                int d = pixel[(j+1) * img.getWidth() + i];

        //   A    --\ 1 2
        // C P B  --/ 3 4
        //   D
        // 1=P; 2=P; 3=P; 4=P;
        // Если C==A и C!=D и A!=B => 1=A
        // Если A==B и A!=C и B!=D => 2=B
        // Если B==D и B!=A и D!=C => 4=D
        // Если D==C и D!=B и C!=A => 3=C

                int p1,p2,p3,p4;
                p1 = p2 = p3 = p4 = p;

                if ((c==a)&&(c!=d)&&(a!=b)) p1=a;
                if ((a==b)&&(a!=c)&&(b!=d)) p2=b;
                if ((b==d)&&(b!=a)&&(d!=c)) p4=d;
                if ((d==c)&&(d!=b)&&(c!=a)) p3=c;

                pixel2[(j*2) * img.getWidth()*2 + (i*2)] = p1;
                pixel2[(j*2) * img.getWidth()*2 + (i*2+1)] = p2;
                pixel2[(j*2+1) * img.getWidth()*2 + (i*2)] = p3;
                pixel2[(j*2+1) * img.getWidth()*2 + (i*2+1)] = p4;
            }
        }

        return Image.createRGBImage(pixel2, img.getWidth()*2, img.getHeight()*2, true);
    }
}
