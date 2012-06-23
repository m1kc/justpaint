package jp;

import javax.microedition.lcdui.Image;

class Lib_turn
{

    private static int sint[] = {
        0, 87, 174, 259, 342, 423, 500, 574, 643, 707,
        766, 819, 866, 906, 940, 966, 985, 996, 1000
    };

    Lib_turn()
    {
    }

    private static int cos(int i)
    {
        i %= 360;
        if (i < 0)
        {
            i = -i;
        }
        if (i <= 90)
        {
            return sinus(90 - i);
        }
        if (i <= 180)
        {
            return -sinus(i - 90);
        }
        if (i <= 270)
        {
            return -sinus(270 - i);
        } else
        {
            return sinus(i - 270);
        }
    }

    private static int sin(int i)
    {
        byte byte0 = 1;
        i %= 360;
        if (i < 0)
        {
            i = -i;
            byte0 = -1;
        }
        if (i <= 90)
        {
            return byte0 * sinus(i);
        }
        if (i <= 180)
        {
            return byte0 * sinus(180 - i);
        }
        if (i <= 270)
        {
            return -byte0 * sinus(i - 180);
        } else
        {
            return -byte0 * sinus(360 - i);
        }
    }

    private static int sinus(int i)
    {
        int j = i / 5;
        if (i % 5 == 0)
        {
            return sint[j];
        } else
        {
            return ((sint[j + 1] - sint[j]) * (i % 5)) / 5 + sint[j];
        }
    }

    public static Image rotate(Image image, int i)
    {
        int j = image.getWidth();
        int k = image.getHeight();
        int l1 = sin(i);
        int i2 = cos(i);
        int j2 = j / 2;
        int k2 = k / 2;
        int ai[] = new int[j * k];
        int ai1[] = new int[j * k];
        image.getRGB(ai, 0, j, 0, 0, j, k);
        for (int k1 = 0; k1 < k; k1++)
        {
            for (int j1 = 0; j1 < j; j1++)
            {
                ai1[k1 * j + j1] = 0;
                int l = (i2 * (j1 - j2) + l1 * (k1 - k2)) / 1000 + j2;
                int i1 = -(l1 * (j1 - j2) - i2 * (k1 - k2)) / 1000 + k2;
                if (i1 > -1 && l > -1 && i1 < k && l < j)
                {
                    ai1[k1 * j + j1] = ai[i1 * j + l];
                }
            }

        }

        return Image.createRGBImage(ai1, j, k, true);
    }

}
