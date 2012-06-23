/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

/**
 *
 * @author Makc
 */
public class HeapCleaner extends Thread {

    public void run() {
        while (true) {
            try
            {
                Thread.sleep(500L);
            }
            catch (InterruptedException ex)
            {
            }
            System.gc();
        }
    }
}
