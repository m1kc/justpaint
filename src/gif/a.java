// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) deadcode space 

package gif;

import java.io.IOException;
import java.io.OutputStream;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

// Referenced classes of package Gif:
//            b, c

public final class a
{

    protected int a,b,c,d,e,f;
    protected boolean g;
    protected OutputStream h;
    protected Image i;
    protected byte[] j,k;
    protected int l;
    protected byte m[];
    protected boolean n[];
    protected int o,p;
    protected boolean q,r,s;
    protected int t;

    public a()
    {
        c = -1;
        e = -1;
        f = 0;
        g = false;
        n = new boolean[256];
        o = 7;
        p = -1;
        q = false;
        r = true;
        s = false;
        t = 10;
    }

    public final void a(int i1)
    {
        f = i1 / 10;
    }

    public final boolean a(Image image)
    {
        if (image == null || !g)
            return false;
        boolean flag = true;
        try
        {
            if (!s)
                a(image.getWidth(), image.getHeight());
            i = image;
            c();
            b();
            if (r)
            {
                f();
                h();
                if (e >= 0)
                    g();
            }
            d();
            e();
            if (!r)
                h();
            i();
            r = false;
        }
        catch (IOException _ex)
        {
            flag = false;
        }
        return flag;
    }

    public final boolean a()
    {
        if (!g)
            return false;
        boolean flag = true;
        g = false;
        try
        {
            h.write(59);
            h.flush();
            if (q)
                h.close();
        }
        catch (IOException _ex)
        {
            flag = false;
        }
        d = 0;
        h = null;
        i = null;
        j = null;
        k = null;
        m = null;
        q = false;
        r = true;
        return flag;
    }

    public final void b(int i1)
    {
        if (i1 < 1)
            i1 = 1;
        t = i1;
    }

    public final void a(int i1, int j1)
    {
        if (g && !r)
            return;
        a = i1;
        b = j1;
        if (a < 1)
            a = 320;
        if (b < 1)
            b = 240;
        s = true;
    }

    public final boolean a(OutputStream outputstream)
    {
        if (outputstream == null)
            return false;
        boolean flag = true;
        q = false;
        h = outputstream;
        try
        {
            a("GIF89a");
        }
        catch (IOException _ex)
        {
            flag = false;
        }
        return g = flag;
    }

    protected final void b()
    {
        int i1;
        int j1 = (i1 = j.length) / 3;
        k = new byte[j1];
        c c1 = new c(j, i1, t);
        m = c1.d();
        for (int k1 = 0; k1 < m.length; k1 += 3)
        {
            byte byte0 = m[k1];
            m[k1] = m[k1 + 2];
            m[k1 + 2] = byte0;
            n[k1 / 3] = false;
        }

        int l1 = 0;
        for (int i2 = 0; i2 < j1; i2++)
        {
            int j2 = c1.a(j[l1++] & 0xff, j[l1++] & 0xff, j[l1++] & 0xff);
            n[j2] = true;
            k[i2] = (byte)j2;
        }

        j = null;
        l = 8;
        o = 7;
        if (c != -1)
            d = c(c);
    }

    protected final int c(int i1)
    {
        if (m == null)
            return -1;
        int j1 = i1 >> 16 & 0xff;
        int k1 = i1 >> 8 & 0xff;
        int l1 = i1 >> 0 & 0xff;
        int i2 = 0;
        int j2 = 0x1000000;
        int k2 = m.length;
        for (int l2 = 0; l2 < k2; l2++)
        {
            int i3 = j1 - (m[l2++] & 0xff);
            int j3 = k1 - (m[l2++] & 0xff);
            int k3 = l1 - (m[l2] & 0xff);
            int l3 = i3 * i3 + j3 * j3 + k3 * k3;
            int i4 = l2 / 3;
            if (n[i4] && l3 < j2)
            {
                j2 = l3;
                i2 = i4;
            }
        }

        return i2;
    }

    protected final void c()
    {
        int i1 = i.getWidth();
        int j1 = i.getHeight();
        if (i1 != a || j1 != b)
        {
            Image image;
            Graphics g1;
            (g1 = (image = Image.createImage(a, b)).getGraphics()).drawImage(i, 0, 0, 20);
            i = image;
        }
        int ai[] = b(i);
        j = new byte[ai.length * 3];
        for (int k1 = 0; k1 < ai.length; k1++)
        {
            int l1 = ai[k1];
            int i2 = k1 * 3;
            j[i2++] = (byte)(l1 >> 0 & 0xff);
            j[i2++] = (byte)(l1 >> 8 & 0xff);
            j[i2] = (byte)(l1 >> 16 & 0xff);
        }

    }

    protected final int[] b(Image image)
    {
        int i1 = image.getWidth();
        int j1 = image.getHeight();
        int ai[] = new int[i1 * j1];
        image.getRGB(ai, 0, i1, 0, 0, i1, j1);
        return ai;
    }

    protected final void d()
        throws IOException
    {
        h.write(33);
        h.write(249);
        h.write(4);
        boolean flag;
        int i1;
        if (c == -1)
        {
            flag = false;
            i1 = 0;
        } else
        {
            flag = true;
            i1 = 2;
        }
        if (p >= 0)
            i1 = p & 7;
        i1 <<= 2;
        int fi = flag?1:0;
        h.write(0 | i1 | 0 | fi);
        d(f);
        h.write(d);
        h.write(0);
    }

    protected final void e()
        throws IOException
    {
        h.write(44);
        d(0);
        d(0);
        d(a);
        d(b);
        if (r)
        {
            h.write(0);
            return;
        } else
        {
            h.write(0x80 | o);
            return;
        }
    }

    protected final void f()
        throws IOException
    {
        d(a);
        d(b);
        h.write(0xf0 | o);
        h.write(0);
        h.write(0);
    }

    protected final void g()
        throws IOException
    {
        h.write(33);
        h.write(255);
        h.write(11);
        a("NETSCAPE2.0");
        h.write(3);
        h.write(1);
        d(e);
        h.write(0);
    }

    protected final void h()
        throws IOException
    {
        h.write(m, 0, m.length);
        int i1 = 768 - m.length;
        for (int j1 = 0; j1 < i1; j1++)
            h.write(0);

    }

    protected final void i()
        throws IOException
    {
        b b1;
        (b1 = new b(a, b, k, l)).b(h);
    }

    protected final void d(int i1)
        throws IOException
    {
        h.write(i1 & 0xff);
        h.write(i1 >> 8 & 0xff);
    }

    protected final void a(String s1)
        throws IOException
    {
        for (int i1 = 0; i1 < s1.length(); i1++)
            h.write((byte)s1.charAt(i1));

    }

}
