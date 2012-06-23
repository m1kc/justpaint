/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import javax.microedition.rms.*;

/**
 *
 * @author m1kc
 */
public class RMSWizard
{
    public static void readOptions()
    {
        int n = 0;
        try
        {
            // Открываем RecordStore. Нету, ну и хрен с ней.
            RecordStore rs = RecordStore.openRecordStore("JustPaint", false); n=1;
            // Читаем
            byte[] b = rs.getRecord(rs.getNextRecordID()-1); n=2;
            // Выводим
            for (int i=0; i<b.length; i++)
            {
                JustPaint.mts.myTools.addElement(new Integer(b[i]));
            } n=3;
            // И пшла вон.
            rs.closeRecordStore(); n=4;
        }
        catch (Throwable ex)
        {
            System.out.println("JUSTPAINT: "+ex.toString()+" at RMSWizard.readOptions(), строка "+n);
        }
    }

    public static void writeOptions()
    {
        int n = 0;
        try
        {
            // Открываем, если нету - создаем
            RecordStore rs = RecordStore.openRecordStore("JustPaint", true);
            // А что писать-то будем?
            byte[] b = new byte[JustPaint.mts.myTools.size()];
            for (int i=0; i<JustPaint.mts.myTools.size(); i++)
            {
                b[i] = ((Integer) JustPaint.mts.myTools.elementAt(i)).byteValue();
            }
            // Есть запись - set, нету - add
            if (rs.getNumRecords()==0)
            {
                System.out.println("JUSTPAINT: added");
                n=1; rs.addRecord(b, 0, b.length);
            }
            else
            {
                System.out.println("JUSTPAINT: set, ID: "+(rs.getNextRecordID()-1));
                n=2; rs.setRecord(rs.getNextRecordID()-1, b, 0, b.length);
            }
            // И все.
            n=3; rs.closeRecordStore();
        }
        catch (Throwable ex)
        {
            System.out.println("JUSTPAINT: "+ex.toString()+" at RMSWizard.writeOptions(), строка "+n);
        }
    }
}
