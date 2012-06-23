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
public class Filter {
    
    private static final String EFFECTS_SYMBOL_LIST = "gndlsbiw";
    
    String name;
    Vector effects = new Vector();

    public Image appendFilter(Image iq)
    {
        Image img = iq;
        for (int i = 0; i < effects.size(); i++) {
            // Замена серии условий на табличное значение.
            char effectSymbol = ((String) effects.elementAt(i)).charAt(0);
            int effectId = EFFECTS_SYMBOL_LIST.indexOf(effectSymbol);
            if (effectId != -1) img = Lib_effects.effect(img, effectId);
        }
        return img;
    }
}
