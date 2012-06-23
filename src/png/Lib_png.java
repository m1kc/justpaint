package png;

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) braces fieldsfirst space
// Source File Name:   Lib_png3.java

import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.Image;

public class Lib_png
{

    public static FileConnection fc = null;
    public static OutputStream os = null;

    public Lib_png()
    {
    }

    public static int save_png(Image img, String FileName)
    {
        byte ba[] = null;
        try
        {
            ba = Encoder.toPNG(img);
        }
        catch (Exception e) { }
        try
        {
            fc = (FileConnection)Connector.open("file:///" + FileName);
            if (fc.exists())
            {
                fc.delete();
            }
            fc.create();
            os = fc.openOutputStream();
            os.write(ba);
            if (os != null)
            {
                os.close();
            }
            if (fc != null)
            {
                fc.close();
            }
        }
        catch (Exception e)
        {
            return -1;
        }
        return 1;
    }

    public static String png_to_string(Image img)
    {
        byte ba[] = null;
        try
        {
            ba = Encoder.toPNG(img);
        }
        catch (Exception e)
        {
            return null;
        }
        return new String(ba);
    }

}
