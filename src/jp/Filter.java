/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import java.util.Vector;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Makc
 */
public class Filter
{
    String name;
    Vector effects = new Vector();

    public Image appendFilter(Image iq)
    {
        Image img = iq;
        int i;
        String s;
        for (i=0; i<effects.size(); i++)
        {
            s = (String) effects.elementAt(i);
            if (s.equals("g")) img = Lib_effects.effect(img, 0);
            if (s.equals("n")) img = Lib_effects.effect(img, 1);
            if (s.equals("d")) img = Lib_effects.effect(img, 2);
            if (s.equals("l")) img = Lib_effects.effect(img, 3);
            if (s.equals("s")) img = Lib_effects.effect(img, 4);
            if (s.equals("b")) img = Lib_effects.effect(img, 5);
            if (s.equals("i")) img = Lib_effects.effect(img, 6);
            if (s.equals("w")) img = Lib_effects.effect(img, 7);
        }
        return img;
    }
}
