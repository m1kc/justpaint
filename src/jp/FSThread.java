package jp;

import java.io.InputStream;
import java.util.Enumeration;
import javax.microedition.io.Connector;
import javax.microedition.io.file.*;
import javax.microedition.lcdui.Image;

class FSThread extends Thread
{
    int mode=0;
    String dest="";

    String[] array;

    InputStream is;
    Image im;

    public void run()
    {
        int READ=1;
        int IMAGE=2;

        if (mode==READ)
        {
            if (dest.equals(""))
            {
               try
               {
                 Enumeration r = FileSystemRegistry.listRoots();
                 array = Tools.EnumerationToString(r, "");
               }
               catch (Throwable ex)
               {
                 array = new String[1];
                 array[0] = " "+ex.toString();
               }
            }
            else
            {
                try {
                    FileConnection fc = (FileConnection) Connector.open("file:///"+dest, Connector.READ);
                    Enumeration r = fc.list();
                    array = Tools.EnumerationToString(r,"");
                    fc.close();
                } catch (Throwable ex) {
                    array = new String[1];
                    array[0] = " "+ex.toString();
                }
            }
        }

        if (mode==IMAGE)
        {
            try {
                    FileConnection fc = (FileConnection) Connector.open("file:///"+dest, Connector.READ);
                    is = fc.openInputStream();
                    if (dest.endsWith("swf"))
                    {
                        byte[] b = new byte[is.available()];
                        is.read(b);
                        int location = 0;
                        for (int i=0; i<b.length-3; i++)
                        {
                            // FF D8 FF E0 - сигн JFIF
                            // FF D8 FF E0 00 10 4A 46 49 46 | яШяа..JFIF
                            // Это если полностью, но первых 4 байт
                            // должно хватить.
                            if (b[i]==(byte)0xFF && b[i+1]==(byte)0xD8 && b[i+2]==(byte)0xFF && b[i+3]==(byte)0xE0)
                            {
                                location = i;
                                break;
                            }
                        }
                        //JustPaint.showDebugInfo(this, null, "run()", "location: "+location);
                        im = Image.createImage(b, location, b.length-location);
                    }
                    else
                    {
                        im = Image.createImage(is);
                    }
                    fc.close();
                } catch (Throwable ex) {
                    im = Image.createImage(1, 1);
                    //JustPaint.showDebugInfo(this, ex, "run()", "halted!");
                }
        }

    }

    public void setMode(int m, String d)
    {
        mode=m;
        dest=d;
    }
}