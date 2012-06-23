package jpeg;

import java.io.*;
import javax.microedition.lcdui.*;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;


public class Lib_jpeg {

    public static void saveimagetofile(Image img, int Quality, String FileName) {
        FileConnection fc = null;
        try {
            fc = (FileConnection) Connector.open("file://" + FileName);
            fc.create();
            OutputStream os = fc.openDataOutputStream();
            JpegEncoder je = new JpegEncoder (img, Quality, os);
            os.flush();
            os.close();
            fc.close();
        } catch (IOException ex) {
        }
    }

    
    public static String saveimagetostring(Image img, int Quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JpegEncoder je = new JpegEncoder (img, Quality, baos);
        return baos.toString();
    }
    
}
