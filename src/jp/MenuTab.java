/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import java.util.Vector;

/**
 *
 * @author пользователь
 */
public class MenuTab
{

    public MenuTab(String title)
    {
        this.title = title;
    }

    public String toString()
    {
        return title;
    }

    String title;
    Vector items = new Vector();
}
