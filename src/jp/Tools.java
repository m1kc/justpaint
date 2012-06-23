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
        final int width = img.getWidth();
        final int height = img.getHeight();
        int[] pixel = new int[width * height];
        img.getRGB(pixel, 0, width, 0, 0, width, height);

        pixel[y * width + x] = (255 << 24) | (JustPaint.r << 16) | (JustPaint.g << 8) | JustPaint.b;

        return Image.createRGBImage(pixel, width, height, true);
    }

    public static Image putImage(Image img,Image img2,int x,int y)
    {
        final int width = img.getWidth();
        final int height = img.getHeight();
        final int width2 = img2.getWidth();
        final int height2 = img2.getHeight();
        
        int[] pixel = new int[width * height];
        img.getRGB(pixel, 0, width, 0, 0, width, height);
        

        int[] pixel2 = new int[width2 * height2];
        img2.getRGB(pixel2, 0, width2, 0, 0, width2, height2);

        int i,j;
        for (i=0; i<width2; i++)
        {
            for (j=0; j<height2; j++)
            {
                int qqa = (pixel2[j * width2 + i] >> 24) & 0xff;
                int qqr = (pixel2[j * width2 + i] >> 16) & 0xff;
                int qqg = (pixel2[j * width2 + i] >> 8) & 0xff;
                int qqb = pixel2[j * width2 + i] & 0xff;

                int qa = (pixel[(j+y) * width + (x+i)] >> 24) & 0xff;
                int qr = (pixel[(j+y) * width + (x+i)] >> 16) & 0xff;
                int qg = (pixel[(j+y) * width + (x+i)] >> 8) & 0xff;
                int qb = pixel[(j+y) * width + (x+i)] & 0xff;

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

                pixel[(j+y) * width + (x+i)] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
            }
        }

        return Image.createRGBImage(pixel, width, height, true);
    }

    public static Image replaceHGradient(Image img, int r,int g,int b,int r1,int g1,int b1,int r2,int g2,int b2)
    {
        final int width = img.getWidth();
        final int height = img.getHeight();
        
        int[] pixel = new int[width * height];
        img.getRGB(pixel, 0, width, 0, 0, width, height);

        int i,j;
        for (i=0; i<width; i++)
        {
            for (j=0; j<height; j++)
            {
                int qa = (pixel[j * width + i] >> 24) & 0xff;
                int qr = (pixel[j * width + i] >> 16) & 0xff;
                int qg = (pixel[j * width + i] >> 8) & 0xff;
                int qb = pixel[j * width + i] & 0xff;

                if ((qa>0)&&(qr==r)&&(qg==g)&&(qb==b))
                {
                    qr=r1 + (i-width+1) * (r2-r1) / (width-1) + (r2-r1);
                    qg=g1 + (i-width+1) * (g2-g1) / (width-1) + (g2-g1);
                    qb=b1 + (i-width+1) * (b2-b1) / (width-1) + (b2-b1);
                }

                pixel[j * width + i] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
            }
        }

        return Image.createRGBImage(pixel, width, height, true);
    }

    public static Image replaceVGradient(Image img, int r,int g,int b,int r1,int g1,int b1,int r2,int g2,int b2)
    {
        final int width = img.getWidth();
        final int height = img.getHeight();
        
        int[] pixel = new int[width * height];
        img.getRGB(pixel, 0, width, 0, 0, width, height);

        int i,j;
        for (i=0; i<width; i++)
        {
            for (j=0; j<height; j++)
            {
                final int pix = pixel[j * width + i];
                int qa = (pix >> 24) & 0xff;
                int qr = (pix >> 16) & 0xff;
                int qg = (pix >> 8) & 0xff;
                int qb = pix & 0xff;

                if ((qa>0)&&(qr==r)&&(qg==g)&&(qb==b))
                {
                    qr=r1 + (j-height+1) * (r2-r1) / (height-1) + (r2-r1);
                    qg=g1 + (j-height+1) * (g2-g1) / (height-1) + (g2-g1);
                    qb=b1 + (j-height+1) * (b2-b1) / (height-1) + (b2-b1);
                }

                pixel[j * width + i] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
            }
        }

        return Image.createRGBImage(pixel, width, height, true);
    }

    public static Image fillBackground(Image img,int r,int g,int b,int r1,int g1,int b1)
    {
        final int width = img.getWidth();
        final int height = img.getHeight();
        
        int[] pixel = new int[width * height];
        img.getRGB(pixel, 0, width, 0, 0, width, height);

        int j;
        for (int i =0; i<width; i++)
        {
            boolean brake = false;
            for (j=0; j<height; j++)
            {
                final int pix = pixel[j * width + i];
                int qa = (pix >> 24) & 0xff;
                int qr = (pix >> 16) & 0xff;
                int qg = (pix >> 8) & 0xff;
                int qb = pix & 0xff;

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

                pixel[j * width + i] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
            }
            brake = false;
            for (j=height-1; j>=0; j--)
            {
                final int pix = pixel[j * width + i];
                int qa = (pix >> 24) & 0xff;
                int qr = (pix >> 16) & 0xff;
                int qg = (pix >> 8) & 0xff;
                int qb = pix & 0xff;

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

                pixel[j * width + i] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
            }
        }

        return Image.createRGBImage(pixel, width, height, true);
    }

    public static Image halfAlpha(Image img)
    {
        final int width = img.getWidth();
        final int height = img.getHeight();
        
        int[] pixel = new int[width * height];
        img.getRGB(pixel, 0, width, 0, 0, width, height);

        for (int i=0; i<width; i++)
        {
            for (int j=0; j<height; j++)
            {
                final int pix = pixel[j * width + i];
                int qa = (pix >> 24) & 0xff;
                qa = qa / 2;

                pixel[j * width + i] = (qa << 24) | (pix & 0x00FFFFFF);
            }
        }

        return Image.createRGBImage(pixel, width, height, true);
    }

    public static Image halfAlphaWithGradient(Image img)
    {
        final int width = img.getWidth();
        final int height = img.getHeight();
        
        int[] pixel = new int[width * height];
        img.getRGB(pixel, 0, width, 0, 0, width, height);

        int i,j;
        for (i=0; i<width; i++)
        {
            for (j=0; j<height; j++)
            {
                final int pix = pixel[j * width + i];
                //int qa = (pixel[j * width + i] >> 24) & 0xff;
                //qa = 128 * (height-j) / height;
                int qa = 128 * (height-j) / height;

                pixel[j * width + i] = (qa << 24) | (pix & 0x00FFFFFF);
            }
        }

        return Image.createRGBImage(pixel, width, height, true);
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
        final int imgWidth = img.getWidth();
        final int imgHeight = img.getHeight();
        
        int[] pixel = new int[imgWidth * imgHeight];
        img.getRGB(pixel, 0, imgWidth, 0, 0, imgWidth, imgHeight);

        int i,j;
        for (i=0; i<width; i++)
        {
            for (j=0; j<height; j++)
            {
                pixel[(j+y) * imgWidth + (x+i)] = 0;
            }
        }

        return Image.createRGBImage(pixel, imgWidth, imgHeight, true);
    }

    public static Image fillEverything(Image img)
    {
        final int width = img.getWidth();
        final int height = img.getHeight();
        
        int[] pixel = new int[width * height];
        img.getRGB(pixel, 0, width, 0, 0, width, height);

        for (int i=0; i<width; i++)
        {
            for (int j=0; j<height; j++)
            {
                int qa = (pixel[j * width + i] >> 24) & 0xff;
                if (qa > 0) {
                    pixel[j * width + i] = (qa << 24) | (JustPaint.r << 16) |
                            (JustPaint.g << 8) | JustPaint.b;
                }
            }
        }

        return Image.createRGBImage(pixel, width, height, true);
    }

    static Image leaveOnlyEllipse(Image img, int x, int y, int w, int h)
    {
        final int width = img.getWidth();
        final int height = img.getHeight();
        int[] pixel = new int[width * height];
        img.getRGB(pixel, 0, width, 0, 0, width, height);

        Image img2 = Image.createImage(w, h);
        Graphics g = img2.getGraphics();
        g.setColor(0xFF0000);
        g.fillArc(0, 0, w, h, 0, 360);
        final int width2 = img2.getWidth();
        final int height2 = img2.getHeight();
        int[] pixel2 = new int[width2 * height2];
        img2.getRGB(pixel2, 0, width2, 0, 0, width2, height2);

        int i,j;
        for (i=0; i<width2; i++)
        {
            for (j=0; j<height2; j++)
            {
                int qqa = (pixel2[j * width2 + i] >> 24) & 0xff;
                int qqr = (pixel2[j * width2 + i] >> 16) & 0xff;
                int qqg = (pixel2[j * width2 + i] >> 8) & 0xff;
                int qqb = pixel2[j * width2 + i] & 0xff;

                int qa = (pixel[(j+y) * width + (x+i)] >> 24) & 0xff;
                int qr = (pixel[(j+y) * width + (x+i)] >> 16) & 0xff;
                int qg = (pixel[(j+y) * width + (x+i)] >> 8) & 0xff;
                int qb = pixel[(j+y) * width + (x+i)] & 0xff;

                if (qqb != 0) qa=qr=qg=qb=0;

                pixel[(j+y) * width + (x+i)] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
            }
        }

        for (i=0; i<width; i++)
        {
            for (j=0; j<height; j++)
            {
                if ((i<x)||(i>=x+w)||(j<y)||(j>=y+h)) {
                    pixel[j * width + i] = 0;
                }
            }
        }

        return Image.createRGBImage(pixel, width, height, true);
    }

    public static Image modifyAlpha(Image img, int p)
    {
        final int width = img.getWidth();
        final int height = img.getHeight();
        
        int[] pixel = new int[width * height];
        img.getRGB(pixel, 0, width, 0, 0, width, height);

        int i,j;
        for (i=0; i<width; i++)
        {
            for (j=0; j<height; j++)
            {
                final int pix = pixel[j * width + i];
                int qa = (pix >> 24) & 0xff;
                qa = qa * p / 100;

                pixel[j * width + i] = (qa << 24) | (pix & 0x00FFFFFF);
            }
        }

        return Image.createRGBImage(pixel, width, height, true);
    }

    static Image appendMask(Image img, Image mask)
    {
        final int width = img.getWidth();
        final int height = img.getHeight();
        int[] pixel = new int[width * height];
        img.getRGB(pixel, 0, width, 0, 0, width, height);
        
        final int maskWidth = mask.getWidth();
        final int maskHeight = mask.getHeight();
        int[] pixel2 = new int[maskWidth * maskHeight];
        mask.getRGB(pixel2, 0, maskWidth, 0, 0, maskWidth, maskHeight);

        int i,j;
        for (i=0; i<maskWidth; i++)
        {
            for (j=0; j<maskHeight; j++)
            {
                int qqa = (pixel2[j * maskWidth + i] >> 24) & 0xff;
                int qqr = (pixel2[j * maskWidth + i] >> 16) & 0xff;
                int qqg = (pixel2[j * maskWidth + i] >> 8) & 0xff;
                int qqb = pixel2[j * maskWidth + i] & 0xff;

                int qa = (pixel[j * width + i] >> 24) & 0xff;
                int qr = (pixel[j * width + i] >> 16) & 0xff;
                int qg = (pixel[j * width + i] >> 8) & 0xff;
                int qb = pixel[j * width + i] & 0xff;

                if (qqr == 0) qa = 0;
                else if (qqr == 255) qa = 255;
                else qa = qa * qqr / 255;

                pixel[j * width + i] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
            }
        }

        return Image.createRGBImage(pixel, width, height, true);
    }

    public static Image scale2x(Image img)
    {
        final int width = img.getWidth();
        final int height = img.getHeight();
        
        int[] pixel = new int[width * height];
        img.getRGB(pixel, 0, width, 0, 0, width, height);

        int[] pixel2 = new int[width*2 * height*2];

        int i,j;

        for (i=0; i<width; i++)
        {
            for (j=0; j<height; j++)
            {
                int p = pixel[j * width + i];

                pixel2[j*2 * width*2 + i*2] = p;
                pixel2[j*2 * width*2 + i*2+1] = p;
                pixel2[(j*2+1) * width*2 + i*2] = p;
                pixel2[(j*2+1) * width*2 + i*2+1] = p;
            }
        }

        for (i=1; i<width-1; i++)
        {
            for (j=1; j<height-1; j++)
            {
                int p = pixel[j * width + i];

                int a = pixel[(j-1) * width + i];
                int b = pixel[j * width + (i+1)];
                int c = pixel[j * width + (i-1)];
                int d = pixel[(j+1) * width + i];

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

                pixel2[(j*2) * width*2 + (i*2)] = p1;
                pixel2[(j*2) * width*2 + (i*2+1)] = p2;
                pixel2[(j*2+1) * width*2 + (i*2)] = p3;
                pixel2[(j*2+1) * width*2 + (i*2+1)] = p4;
            }
        }

        return Image.createRGBImage(pixel2, width*2, height*2, true);
    }
}
