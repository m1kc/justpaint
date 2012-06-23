/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI2.dialogs;

import javax.microedition.lcdui.Graphics;

/**
 *
 * @author пользователь
 */
public abstract class Element
{
    int x,y,width,height;
    boolean hasFocus = false;
    //boolean focusable = true;

    public abstract void paint(Graphics g);

    public boolean switchUp() { return true; }
    public boolean switchDown() { return true; }

    public abstract int getPreferredWidth();
    public abstract int getPreferredHeight();

    public void onResize() { /* Empty method */ }
    public void focusGained() { /* Empty method */ }
    public void focusLost() { /* Empty method */ }
    public void keyPressed(int keyCode, int action) { /* Empty method */ }

    public void onClick() { /* Empty method */ }
}
