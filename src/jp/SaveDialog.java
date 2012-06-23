/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import GUI2.dialogs.*;
import by.HulevichPetr.j2me.lib.swf.*;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

/**
 * @author Makc
 */
public class SaveDialog
{

    int k=1;

    Dialog dialog;
    Switch format;
    TextField filename;
    Button ok;
    Button cancel;
    Runnable okAction;
    Runnable cancelAction;


    public void init()
    {
        dialog = new Dialog(JustPaint.midlet);

        format = new Switch("Формат",new String[]{"PNG","JPEG HQ","JPEG LQ","SWF","GIF"});

        filename = new TextField("c:/other/jp_image");

        //ok.name = "OK";
        okAction = new Runnable()
        {
            public void run()
            {
                int k = format.s;
                if (k==0) png.Lib_png.save_png(JustPaint.c.getImage(), filename.getContent()+".png");
                if (k==1) jpeg.Lib_jpeg.saveimagetofile(JustPaint.c.getImage(), 100, "/"+filename.getContent()+".jpg");
                if (k==2) jpeg.Lib_jpeg.saveimagetofile(JustPaint.c.getImage(), 50, "/"+filename.getContent()+".jpg");
                if (k==3)
                {
                    try
                    {
                        FileConnection fc=(FileConnection)Connector.open("file:///"+filename.getContent()+".swf");
                        if(fc.exists()) fc.delete();
                        fc.create();
                        OutputStream out=fc.openOutputStream();
                        Image2SWF.convert(JustPaint.c.getImage(),80,out);
                        out.close();
                        fc.close();
                    }
                    catch (Throwable ex)
                    {
                    }
                }
                if (k==4) gif.Lib_gif._saveSingleImage(JustPaint.c.getImage(), filename.getContent()+".gif");
                JustPaint.display.setCurrent(JustPaint.c);
            }
        };
        ok = new Button("OK",okAction);

        //cancel.name = "Отмена";
        cancelAction = new Runnable()
        {
            public void run()
            {
                JustPaint.display.setCurrent(JustPaint.c);
            }
        };
        cancel = new Button("Отмена",cancelAction);

        dialog.header = "Сохранение";

        dialog.addElement(format);
        dialog.addElement(filename);
        dialog.addElement(ok);
        dialog.addElement(cancel);
    } 
    
    public void activate()
    {
        JustPaint.display.setCurrent(dialog);
    }
    
}