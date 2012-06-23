/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

/**
 *
 * @author пользователь
 */
public class MenuItem
{

    public MenuItem(String title)
    {
        this.title = title;
    }

    public MenuItem(String title, Runnable action)
    {
        this.title = title;
        this.action = action;
    }

    public String toString()
    {
        return title;
    }

    String title;
    Runnable action;
}
