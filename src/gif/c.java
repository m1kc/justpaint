// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) deadcode space 

package gif;


final class c
{

    public c(byte abyte0[], int j, int k)
    {
        f = new int[256];
        g = new int[256];
        h = new int[256];
        i = new int[32];
        b = abyte0;
        c = j;
        d = k;
        e = new int[256][];
        for (int l = 0; l < 256; l++)
        {
            e[l] = new int[4];
            int ai[];
            (ai = e[l])[0] = ai[1] = ai[2] = (l << 12) / 256;
            h[l] = 256;
            g[l] = 0;
        }

    }

    public final byte[] a()
    {
        byte abyte0[] = new byte[768];
        int ai[] = new int[256];
        for (int j = 0; j < 256; j++)
            ai[e[j][3]] = j;

        int k = 0;
        for (int l = 0; l < 256; l++)
        {
            int i1 = ai[l];
            abyte0[k++] = (byte)e[i1][0];
            abyte0[k++] = (byte)e[i1][1];
            abyte0[k++] = (byte)e[i1][2];
        }

        return abyte0;
    }

    public final void b()
    {
        int i2 = 0;
        int j2 = 0;
        for (int j = 0; j < 256; j++)
        {
            int ai[] = e[j];
            int k1 = j;
            int l1 = ai[1];
            for (int k = j + 1; k < 256; k++)
            {
                int ai1[];
                if ((ai1 = e[k])[1] < l1)
                {
                    k1 = k;
                    l1 = ai1[1];
                }
            }

            int ai2[] = e[k1];
            if (j != k1)
            {
                int l = ai2[0];
                ai2[0] = ai[0];
                ai[0] = l;
                l = ai2[1];
                ai2[1] = ai[1];
                ai[1] = l;
                l = ai2[2];
                ai2[2] = ai[2];
                ai[2] = l;
                l = ai2[3];
                ai2[3] = ai[3];
                ai[3] = l;
            }
            if (l1 == i2)
                continue;
            f[i2] = j2 + j >> 1;
            for (int i1 = i2 + 1; i1 < l1; i1++)
                f[i1] = j;

            i2 = l1;
            j2 = j;
        }

        f[i2] = j2 + 255 >> 1;
        for (int j1 = i2 + 1; j1 < 256; j1++)
            f[j1] = 255;

    }

    public final void c()
    {
        if (c < 1509)
            d = 1;
        a = 30 + (d - 1) / 3;
        byte abyte0[] = b;
        int j3 = 0;
        int k3 = c;
        int i3;
        int l2 = (i3 = c / (3 * d)) / 100;
        int k2 = 1024;
        int i2 = 2048;
        int j2 = 32;
        for (int j = 0; j < 32; j++)
            i[j] = 1024 * (((1024 - j * j) * 256) / 1024);

        char c1;
        if (c < 1509)
            c1 = '\003';
        else
        if (c % 499 != 0)
            c1 = '\u05D9';
        else
        if (c % 491 != 0)
            c1 = '\u05C1';
        else
        if (c % 487 != 0)
            c1 = '\u05B5';
        else
            c1 = '\u05E5';
        int k = 0;
        do
        {
            if (k >= i3)
                break;
            int j1 = (abyte0[j3 + 0] & 0xff) << 4;
            int k1 = (abyte0[j3 + 1] & 0xff) << 4;
            int l1 = (abyte0[j3 + 2] & 0xff) << 4;
            int l = b(j1, k1, l1);
            b(k2, l, j1, k1, l1);
            if (j2 != 0)
                a(j2, l, j1, k1, l1);
            if ((j3 += c1) >= k3)
                j3 -= c;
            k++;
            if (l2 == 0)
                l2 = 1;
            if (k % l2 == 0)
            {
                k2 -= k2 / a;
                if ((j2 = (i2 -= i2 / 30) >> 6) <= 1)
                    j2 = 0;
                int i1 = 0;
                while (i1 < j2) 
                {
                    i[i1] = k2 * (((j2 * j2 - i1 * i1) * 256) / (j2 * j2));
                    i1++;
                }
            }
        } while (true);
    }

    public final int a(int j, int k, int l)
    {
        int i3 = 1000;
        int j3 = -1;
        int i1;
        int j1 = (i1 = f[k]) - 1;
        do
        {
            if (i1 >= 256 && j1 < 0)
                break;
            int k1;
            int ai[];
            if (i1 < 256)
                if ((k1 = (ai = e[i1])[1] - k) >= i3)
                {
                    i1 = 256;
                } else
                {
                    i1++;
                    if (k1 < 0)
                        k1 = -k1;
                    int i2;
                    if ((i2 = ai[0] - j) < 0)
                        i2 = -i2;
                    if ((k1 += i2) < i3)
                    {
                        int j2;
                        if ((j2 = ai[2] - l) < 0)
                            j2 = -j2;
                        if ((k1 += j2) < i3)
                        {
                            i3 = k1;
                            j3 = ai[3];
                        }
                    }
                }
            if (j1 >= 0)
            {
                int ai1[] = e[j1];
                int l1;
                if ((l1 = k - ai1[1]) >= i3)
                {
                    j1 = -1;
                } else
                {
                    j1--;
                    if (l1 < 0)
                        l1 = -l1;
                    int k2;
                    if ((k2 = ai1[0] - j) < 0)
                        k2 = -k2;
                    if ((l1 += k2) < i3)
                    {
                        int l2;
                        if ((l2 = ai1[2] - l) < 0)
                            l2 = -l2;
                        if ((l1 += l2) < i3)
                        {
                            i3 = l1;
                            j3 = ai1[3];
                        }
                    }
                }
            }
        } while (true);
        return j3;
    }

    public final byte[] d()
    {
        c();
        e();
        b();
        return a();
    }

    public final void e()
    {
        for (int j = 0; j < 256; j++)
        {
            e[j][0] >>= 4;
            e[j][1] >>= 4;
            e[j][2] >>= 4;
            e[j][3] = j;
        }

    }

    protected final void a(int j, int k, int l, int i1, int j1)
    {
        int i2;
        if ((i2 = k - j) < -1)
            i2 = -1;
        int j2;
        if ((j2 = k + j) > 256)
            j2 = 256;
        int k1 = k + 1;
        int l1 = k - 1;
        int l2 = 1;
        do
        {
            if (k1 >= j2 && l1 <= i2)
                break;
            int k2 = i[l2++];
            if (k1 < j2)
            {
                int ai[] = e[k1++];
                try
                {
                    ai[0] -= (k2 * (ai[0] - l)) / 0x40000;
                    ai[1] -= (k2 * (ai[1] - i1)) / 0x40000;
                    ai[2] -= (k2 * (ai[2] - j1)) / 0x40000;
                }
                catch (Exception _ex) { }
            }
            if (l1 > i2)
            {
                int ai1[] = e[l1--];
                try
                {
                    ai1[0] -= (k2 * (ai1[0] - l)) / 0x40000;
                    ai1[1] -= (k2 * (ai1[1] - i1)) / 0x40000;
                    ai1[2] -= (k2 * (ai1[2] - j1)) / 0x40000;
                }
                catch (Exception _ex) { }
            }
        } while (true);
    }

    protected final void b(int j, int k, int l, int i1, int j1)
    {
        int ai[];
        (ai = e[k])[0] -= (j * (ai[0] - l)) / 1024;
        ai[1] -= (j * (ai[1] - i1)) / 1024;
        ai[2] -= (j * (ai[2] - j1)) / 1024;
    }

    protected final int b(int j, int k, int l)
    {
        int l2;
        int i3 = l2 = 0x7fffffff;
        int j2 = -1;
        int k2 = -1;
        for (int i1 = 0; i1 < 256; i1++)
        {
            int j1;
            int ai[];
            if ((j1 = (ai = e[i1])[0] - j) < 0)
                j1 = -j1;
            int k1;
            if ((k1 = ai[1] - k) < 0)
                k1 = -k1;
            j1 += k1;
            if ((k1 = ai[2] - l) < 0)
                k1 = -k1;
            if ((j1 += k1) < l2)
            {
                l2 = j1;
                j2 = i1;
            }
            int l1;
            if ((l1 = j1 - (g[i1] >> 12)) < i3)
            {
                i3 = l1;
                k2 = i1;
            }
            int i2 = h[i1] >> 10;
            h[i1] -= i2;
            g[i1] += i2 << 10;
        }

        h[j2] += 64;
        g[j2] -= 0x10000;
        return k2;
    }

    protected int a;
    protected byte b[];
    protected int c,d;
    protected int e[][];
    protected int f[],g[],h[],i[];
}
