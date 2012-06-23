/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import javax.microedition.lcdui.*;

/**
 *
 * @author пользователь
 */
public class Common
{
    static int[] blackMask;
    static boolean maskEnabled = false;

    static Image mask = null;

    static Font mainFont = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);

    static String okString = l10n.LocalizationSupport.getMessage("OK");
    static String cancelString = l10n.LocalizationSupport.getMessage("CANCEL");
    static String upString = l10n.LocalizationSupport.getMessage("UP");
    static String createString = l10n.LocalizationSupport.getMessage("CREATE");
    static String backString = l10n.LocalizationSupport.getMessage("BACK");
}
