package png;

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) braces fieldsfirst space 
// Source File Name:   ZStream.java

public final class ZStream
{

    private static final int MAX_WBITS = 15;
    private static final int DEF_WBITS = 15;
    private static final int Z_NO_FLUSH = 0;
    private static final int Z_PARTIAL_FLUSH = 1;
    private static final int Z_SYNC_FLUSH = 2;
    private static final int Z_FULL_FLUSH = 3;
    private static final int Z_FINISH = 4;
    private static final int MAX_MEM_LEVEL = 9;
    private static final int Z_OK = 0;
    private static final int Z_STREAM_END = 1;
    private static final int Z_NEED_DICT = 2;
    private static final int Z_ERRNO = -1;
    private static final int Z_STREAM_ERROR = -2;
    private static final int Z_DATA_ERROR = -3;
    private static final int Z_MEM_ERROR = -4;
    private static final int Z_BUF_ERROR = -5;
    private static final int Z_VERSION_ERROR = -6;
    byte next_in[];
    int next_in_index;
    int avail_in;
    long total_in;
    byte next_out[];
    int next_out_index;
    int avail_out;
    long total_out;
    String msg;
    Deflate dstate;
    int data_type;
    long adler;
    Adler32 _adler;

    ZStream()
    {
        _adler = new Adler32();
    }

    int deflateInit(int level)
    {
        return deflateInit(level, 15);
    }

    int deflateInit(int level, boolean nowrap)
    {
        return deflateInit(level, 15, nowrap);
    }

    int deflateInit(int level, int bits)
    {
        return deflateInit(level, bits, false);
    }

    int deflateInit(int level, int bits, boolean nowrap)
    {
        dstate = new Deflate();
        return dstate.deflateInit(this, level, nowrap ? -bits : bits);
    }

    int deflate(int flush)
    {
        if (dstate == null)
        {
            return -2;
        } else
        {
            return dstate.deflate(this, flush);
        }
    }

    int deflateEnd()
    {
        if (dstate == null)
        {
            return -2;
        } else
        {
            int ret = dstate.deflateEnd();
            dstate = null;
            return ret;
        }
    }

    int deflateParams(int level, int strategy)
    {
        if (dstate == null)
        {
            return -2;
        } else
        {
            return dstate.deflateParams(this, level, strategy);
        }
    }

    int deflateSetDictionary(byte dictionary[], int dictLength)
    {
        if (dstate == null)
        {
            return -2;
        } else
        {
            return dstate.deflateSetDictionary(this, dictionary, dictLength);
        }
    }

    void flush_pending()
    {
        int len = dstate.pending;
        if (len > avail_out)
        {
            len = avail_out;
        }
        if (len == 0)
        {
            return;
        }
        if (dstate.pending_buf.length <= dstate.pending_out || next_out.length <= next_out_index || dstate.pending_buf.length < dstate.pending_out + len || next_out.length < next_out_index + len)
        {
            System.out.println(dstate.pending_buf.length + ", " + dstate.pending_out + ", " + next_out.length + ", " + next_out_index + ", " + len);
            System.out.println("avail_out=" + avail_out);
        }
        System.arraycopy(dstate.pending_buf, dstate.pending_out, next_out, next_out_index, len);
        next_out_index += len;
        dstate.pending_out += len;
        total_out += len;
        avail_out -= len;
        dstate.pending -= len;
        if (dstate.pending == 0)
        {
            dstate.pending_out = 0;
        }
    }

    int read_buf(byte buf[], int start, int size)
    {
        int len = avail_in;
        if (len > size)
        {
            len = size;
        }
        if (len == 0)
        {
            return 0;
        }
        avail_in -= len;
        if (dstate.noheader == 0)
        {
            adler = _adler.adler32(adler, next_in, next_in_index, len);
        }
        System.arraycopy(next_in, next_in_index, buf, start, len);
        next_in_index += len;
        total_in += len;
        return len;
    }

    void free()
    {
        next_in = null;
        next_out = null;
        msg = null;
        _adler = null;
    }
}
