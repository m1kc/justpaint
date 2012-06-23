// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) deadcode space 

package gif;

import java.io.IOException;
import java.io.OutputStream;

final class b
{

    b(int i1, int j1, byte abyte0[], int k1)
    {
        b = 12;
        d = 4096;
        e = new int[5003];
        f = new int[5003];
        g = 5003;
        h = 0;
        i = false;
        m = 0;
        n = 0;
        q = new byte[256];
        r = i1;
        s = j1;
        t = abyte0;
        u = Math.max(2, k1);
    }

    final void a(byte byte0, OutputStream outputstream)
        throws IOException
    {
        q[p++] = byte0;
        if (p >= 254)
            c(outputstream);
    }

    final void a(OutputStream outputstream)
        throws IOException
    {
        a(g);
        h = k + 2;
        i = true;
        b(k, outputstream);
    }

    final void a(int i1)
    {
        for (int j1 = 0; j1 < i1; j1++)
            e[j1] = -1;

    }

    final void a(int i1, OutputStream outputstream)
        throws IOException
    {
        j = i1;
        i = false;
        a = j;
        c = b(a);
        k = 1 << i1 - 1;
        l = k + 1;
        h = k + 2;
        p = 0;
        int j2 = a();
        int i3 = 0;
        for (int j1 = g; j1 < 0x10000; j1 *= 2)
            i3++;

        i3 = 8 - i3;
        int l2 = g;
        a(l2);
        b(k, outputstream);
label0:
        do
        {
            int i2;
            if ((i2 = a()) == -1)
                break;
            int k1 = (i2 << b) + j2;
            int l1 = i2 << i3 ^ j2;
            if (e[l1] == k1)
            {
                j2 = f[l1];
                continue;
            }
            if (e[l1] >= 0)
            {
                int k2 = l2 - l1;
                if (l1 == 0)
                    k2 = 1;
                do
                {
                    if ((l1 -= k2) < 0)
                        l1 += l2;
                    if (e[l1] != k1)
                        continue;
                    j2 = f[l1];
                    continue label0;
                } while (e[l1] >= 0);
            }
            b(j2, outputstream);
            j2 = i2;
            if (h < d)
            {
                f[l1] = h++;
                e[l1] = k1;
            } else
            {
                a(outputstream);
            }
        } while (true);
        b(j2, outputstream);
        b(l, outputstream);
    }

    final void b(OutputStream outputstream)
        throws IOException
    {
        outputstream.write(u);
        v = r * s;
        w = 0;
        a(u + 1, outputstream);
        outputstream.write(0);
    }

    final void c(OutputStream outputstream)
        throws IOException
    {
        if (p > 0)
        {
            outputstream.write(p);
            outputstream.write(q, 0, p);
            p = 0;
        }
    }

    final int b(int i1)
    {
        return (1 << i1) - 1;
    }

    private int a()
    {
        if (v == 0)
        {
            return -1;
        } else
        {
            v--;
            byte byte0;
            return (byte0 = t[w++]) & 0xff;
        }
    }

    final void b(int i1, OutputStream outputstream)
        throws IOException
    {
        m &= o[n];
        if (n > 0)
            m |= i1 << n;
        else
            m = i1;
        for (n += a; n >= 8; n -= 8)
        {
            a((byte)(m & 0xff), outputstream);
            m >>= 8;
        }

        if (h > c || i)
            if (i)
            {
                c = b(a = j);
                i = false;
            } else
            {
                a++;
                if (a == b)
                    c = d;
                else
                    c = b(a);
            }
        if (i1 == l)
        {
            for (; n > 0; n -= 8)
            {
                a((byte)(m & 0xff), outputstream);
                m >>= 8;
            }

            c(outputstream);
        }
    }

    private int r;
    private int s;
    private byte t[];
    private int u;
    private int v;
    private int w;
    int a;
    int b;
    int c;
    int d;
    int e[];
    int f[];
    int g;
    int h;
    boolean i;
    int j;
    int k;
    int l;
    int m;
    int n;
    int o[] = {
        0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 
        1023, 2047, 4095, 8191, 16383, 32767, 65535
    };
    int p;
    byte q[];
}
