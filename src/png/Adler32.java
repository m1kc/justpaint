package png;

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) braces fieldsfirst space 
// Source File Name:   Adler32.java


final class Adler32
{

    private static final int BASE = 65521;
    private static final int NMAX = 5552;

    Adler32()
    {
    }

    long adler32(long adler, byte buf[], int index, int len)
    {
        if (buf == null)
        {
            return 1L;
        }
        long s1 = adler & 65535L;
        long s2;
        for (s2 = adler >> 16 & 65535L; len > 0; s2 %= 65521L)
        {
            int k = len >= 5552 ? 5552 : len;
            len -= k;
            for (; k >= 16; k -= 16)
            {
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
            }

            if (k != 0)
            {
                do
                {
                    s1 += buf[index++] & 0xff;
                    s2 += s1;
                } while (--k != 0);
            }
            s1 %= 65521L;
        }

        return s2 << 16 | s1;
    }
}
