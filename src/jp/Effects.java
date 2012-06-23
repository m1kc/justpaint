/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp;

import java.util.Date;
import java.util.Random;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Makc
 */
public class Effects
{
    public static Image sun(Image img)
    {
        final int width = img.getWidth();
        final int height = img.getHeight();

        int[] pixel = new int[width * height];
        img.getRGB(pixel, 0, width, 0, 0, width, height);

        int sx = width / 4 * 3;
        int sy = height / 4;

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                int pix = pixel[y * width + x];

                int qa = (pix >> 24) & 0xff;
                int qr = (pix >> 16) & 0xff;
                int qg = (pix >> 8) & 0xff;
                int qb = pix & 0xff;

                int dist = (int) Math.sqrt((sx - x) * (sx - x) + (sy - y) * (sy - y));
                int md = width / 2;

                if (dist <= md)
                {
                    int k = 128 * (md - dist) / md;
                    qr = qr + k;
                    qg = qg + k;
                    qb = qb + k;

                    if (qr > 255) qr = 255;
                    if (qr < 0) qr = 0;
                    if (qg > 255) qg = 255;
                    if (qg < 0) qg = 0;
                    if (qb > 255) qb = 255;
                    if (qb < 0) qb = 0;
                }

                pixel[y * width + x] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
            }
        }

        return Image.createRGBImage(pixel, width, height, true);
    }

    public static Image paper(Image img)
    {
        JustPaint.c.Lock = true;

        Date d = new Date();
        Random r = new Random(d.getTime());

        final int width = img.getWidth();
        final int height = img.getHeight();

        int[] pixel = new int[width * height];
        img.getRGB(pixel, 0, width, 0, 0, width, height);

        for (int x = 0; x < width; x++)
        {
            int max = height - r.nextInt(height / 8);

            for (int y = 0; y < height; y++)
            {
                int qa = (pixel[y * width + x] >> 24) & 0xff;
                int qr = (pixel[y * width + x] >> 16) & 0xff;
                int qg = (pixel[y * width + x] >> 8) & 0xff;
                int qb = pixel[y * width + x] & 0xff;

                if (y <= max)
                {
                    qr = (qr + 128) / 2;
                    qg = (qg + 128) / 2;
                    qb = (qb + 64) / 2;
                }
                if (y > max)
                {
                    qa = 0;
                }

                if (r.nextInt() % 100 == 0)
                {
                    qa = 0;
                }

                pixel[y * width + x] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
            }
        }

        return Image.createRGBImage(pixel, width, height, true);
    }

    private static int getBrightness(int x)
    {
        int qr = (x >> 16) & 0xff;
        int qg = (x >> 8) & 0xff;
        int qb = x & 0xff;

        //return (qr+qg+qb)/3;
        return qr + qg + qb;
    }

    private static int setBrightness(int x, int b)
    {
        int qa = (x >> 24) & 0xff;
        int qr = (x >> 16) & 0xff;
        int qg = (x >> 8) & 0xff;
        int qb = x & 0xff;

        //int tb = (qr+qg+qb)/3;
        int tb = getBrightness(x);

        /*
         * while (tb>Math.abs(b)) { qr--; qg--; qb--;
         *
         * if (qr>255) qr=255; if (qr<0) qr=0; if (qg>255) qg=255; if (qg<0)
         * qg=0; if (qb>255) qb=255; if (qb<0) qb=0;
         *
         * tb = (qr+qg+qb)/3; }
         */

        int delta = (Math.abs(b) - tb) / 3;
        qr += delta;
        qg += delta;
        qb += delta;

        if (qr < 0) qr = 0;
        else if (qr > 255) qr = 255;

        if (qg < 0) qg = 0;
        else if (qg > 255) qg = 255;

        if (qb < 0) qb = 0;
        else if (qb > 255) qb = 255;


        /*
         *
         * while (tb<Math.abs(b)) { qr++; qg++; qb++;
         *
         * if (qr>255) qr=255; if (qr<0) qr=0; if (qg>255) qg=255; if (qg<0)
         * qg=0; if (qb>255) qb=255; if (qb<0) qb=0;
         *
         * tb = (qr+qg+qb)/3; }
         */

        if (b < 0)
        {
            qr = 255 - qr;
            qg = 255 - qg;
            qb = 255 - qb;
        }

        return (qa << 24) | (qr << 16) | (qg << 8) | qb;
    }

    public static Image matrix(Image img, int[][] matrix, int scale, int offset, boolean balance)
    {
        final int width = img.getWidth();
        final int height = img.getHeight();
        int[] pixel = new int[width * height];
        int[] pixel2 = new int[width * height];
        img.getRGB(pixel, 0, width, 0, 0, width, height);

        for (int x = 1; x < width - 1; x++)
        {
            for (int y = 1; y < height - 1; y++)
            {
                int m = pixel[y * width + x];

                int m11 = getBrightness(pixel[(y - 1) * width + (x - 1)]);
                int m12 = getBrightness(pixel[(y) * width + (x - 1)]);
                int m13 = getBrightness(pixel[(y + 1) * width + (x - 1)]);

                int m21 = getBrightness(pixel[(y - 1) * width + (x)]);
                int m22 = getBrightness(pixel[(y) * width + (x)]);
                int m23 = getBrightness(pixel[(y + 1) * width + (x)]);

                int m31 = getBrightness(pixel[(y - 1) * width + (x + 1)]);
                int m32 = getBrightness(pixel[(y) * width + (x + 1)]);
                int m33 = getBrightness(pixel[(y + 1) * width + (x + 1)]);
                /*
                 * m11 = getBrightness(m11) * matrix[0][0]; m12 =
                 * getBrightness(m12) * matrix[0][1]; m13 = getBrightness(m13) *
                 * matrix[0][2];
                 *
                 * m21 = getBrightness(m21) * matrix[1][0]; m22 =
                 * getBrightness(m22) * matrix[1][1]; m23 = getBrightness(m23) *
                 * matrix[1][2];
                 *
                 * m31 = getBrightness(m31) * matrix[2][0]; m32 =
                 * getBrightness(m32) * matrix[2][1]; m33 = getBrightness(m33) *
                 * matrix[2][2];
                 */
                m11 = m11 * matrix[0][0];
                m12 = m12 * matrix[0][1];
                m13 = m13 * matrix[0][2];

                m21 = m21 * matrix[1][0];
                m22 = m22 * matrix[1][1];
                m23 = m23 * matrix[1][2];

                m31 = m31 * matrix[2][0];
                m32 = m32 * matrix[2][1];
                m33 = m33 * matrix[2][2];

                int nk = m11 + m12 + m13 + m21 + m22 + m23 + m31 + m32 + m33;
                //if (balance) nk = nk/scale * 3;
                //if (!balance) nk = nk/scale;
                nk = nk / scale;
                nk = nk - offset;
                //if (nk<-765) nk=-765;
                //if (nk>765) nk=765;

                m = setBrightness(m, nk);

                //значения яркости окружающих пикселей умножаются на значения
                //ячеек, расположенных вокруг центральной ячейки;

                //пустые ячейки и соответствующие им пиксели игнорируются;

                //новое значение яркости текущего пикселя равняется сумме этих
                //произведений, поделенное на величину Scale (масштаб), от
                //результата вычитается значение Offset (смещение);

                pixel2[y * width + x] = m;
            }
        }

        return Image.createRGBImage(pixel2, width, height, true);
    }
}
