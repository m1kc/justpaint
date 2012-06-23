/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI2.dialogs;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

/**
 *
 * @author пользователь
 */
public class Data
{
    static int bgcolor = 0xFFFFFF;
    static int fgcolor = 0x000000;
    static int shadowcolor = 0x808080;

    static int bGradient1 = 0xFFFFFF;
    static int bGradient2 = 0x808080;

    static Font mainFont = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
    static Font headerFont = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);

    static MIDlet midlet;
    static Display display;
}
