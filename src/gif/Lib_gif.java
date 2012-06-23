package gif;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.Image;

public final class Lib_gif
{

    Lib_gif()
    {
    }

    public static void init(int i)
    {
        a = new ByteArrayOutputStream();
        b = new a();
        b.a(a);
        b.b(i);
    }
    
    public static  void set_delay(int i)
    {
        b.a(i);
    }
    
    public static int add_frame(Image image)
    {
        boolean flag;
        return !(flag = b.a(image)) ? -1 : 1;
    }

    public static int close(String s)
    {
        b.a();
        b = null;
        int i;
        try
        {
            FileConnection fileconnection;
            if((fileconnection = (FileConnection)Connector.open("file:///" + s)).exists())
                fileconnection.delete();
            fileconnection.create();
            OutputStream outputstream;
            (outputstream = fileconnection.openOutputStream()).write(a.toByteArray());
            outputstream.flush();
            outputstream.close();
            fileconnection.close();
            i = 1;
        }
        catch(Exception _ex)
        {
            i = -1;
        }
        try
        {
            a.close();
        }
        catch(Exception _ex) { }
        a = null;
        System.gc();
        return i;
    }

    private static ByteArrayOutputStream a = null;
    private static a b = null;

    public static void _saveSingleImage(Image img, String filename)
    {
        // Warning, m1kc was here
        gif.Lib_gif.init(1);
        gif.Lib_gif.set_delay(500);
        gif.Lib_gif.add_frame(img);
        gif.Lib_gif.close(filename);
    }

}