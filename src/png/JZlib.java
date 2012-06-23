package png;

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) braces fieldsfirst space 
// Source File Name:   JZlib.java


public final class JZlib
{

    private static final String version = "1.0.2";
    static final int Z_NO_COMPRESSION = 0;
    static final int Z_BEST_SPEED = 1;
    static final int Z_BEST_COMPRESSION = 9;
    static final int Z_DEFAULT_COMPRESSION = -1;
    static final int Z_FILTERED = 1;
    static final int Z_HUFFMAN_ONLY = 2;
    static final int Z_DEFAULT_STRATEGY = 0;
    static final int Z_NO_FLUSH = 0;
    static final int Z_PARTIAL_FLUSH = 1;
    static final int Z_SYNC_FLUSH = 2;
    static final int Z_FULL_FLUSH = 3;
    static final int Z_FINISH = 4;
    static final int Z_OK = 0;
    static final int Z_STREAM_END = 1;
    static final int Z_NEED_DICT = 2;
    static final int Z_ERRNO = -1;
    static final int Z_STREAM_ERROR = -2;
    static final int Z_DATA_ERROR = -3;
    static final int Z_MEM_ERROR = -4;
    static final int Z_BUF_ERROR = -5;
    static final int Z_VERSION_ERROR = -6;

    public static String version()
    {
        return "1.0.2";
    }

    protected JZlib()
    {
    }
}
