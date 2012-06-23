/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import javax.microedition.lcdui.*;

/**
 * @author Makc
 */
public class FileManager extends Canvas
{

    FSThread fst;

    String[] array;

    int s=0;

    String path="";

    boolean waitingFS = false;
    boolean waitingFSImage = false;
    boolean noConnection = true;

    int sy;
    int list;

    Font f = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);

    /**     *
     * constructor
     */
    public FileManager() {
	    // Set up this canvas to listen to command events
	    //setCommandListener(this);
	    // Add the Exit command
	    //addCommand(new Command("Exit", Command.EXIT, 1));

        setFullScreenMode(true);
    } 
    
    /**
     * paint
     */
    public void paint(Graphics g)
    {
        
        if ((waitingFS)&&(!fst.isAlive()))
        {
            array = fst.array;
            s = 0;
            waitingFS = false;
        }

        if ((waitingFSImage)&&(!fst.isAlive()))
        {
            JustPaint.c.setImage(fst.im);
            JustPaint.c.Lock = true;

            //JustPaint.c.img = Tools.makeMutable(JustPaint.c.img);

            JustPaint.c.x = 20;
            JustPaint.c.y = 20;

            JustPaint.display.setCurrent(JustPaint.c);
            waitingFSImage = false;
        }

        if (noConnection)
        {
            path = "";
            fst = new FSThread();
            fst.setMode(1, "");
            fst.start();
            waitingFS = true;
            noConnection = false;
            array = new String[0];
        }

        list = (getHeight()-50)/MainCanvas.nameFont.getHeight();

        if (s>list/2) sy = (s-list/2)*MainCanvas.nameFont.getHeight(); else sy=0;

        g.setFont(MainCanvas.nameFont);
        
        g.setColor(255,255,255);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(0,0,0);

        g.drawLine(0, 45, getWidth(), 45);

        g.drawString(path, 5, 5, Graphics.LEFT | Graphics.TOP);

        if (array.length>0)
        {
            g.drawString(array[s].substring(1), 5, 5+MainCanvas.nameFont.getHeight(), Graphics.LEFT | Graphics.TOP);
        }

        g.setClip(0, 46, getWidth(), getHeight());
        UIPainter.paintBackground(g);

        int i;
        for (i=0; i<array.length; i++)
        {
            if (i==s)
            {
                //Gradient.drawHGradient(g, 0, 128, 255, 255, 255, 255, 0, 50+MainCanvas.nameFont.getHeight()*i-sy, getWidth(), MainCanvas.nameFont.getHeight());
                g.setColor(0x000000);
                g.fillRect(0, 50+MainCanvas.nameFont.getHeight()*i-sy, getWidth(), MainCanvas.nameFont.getHeight());
                g.setColor(255,255,255);
                g.drawString(array[i].substring(1), 5, 50+MainCanvas.nameFont.getHeight()*i-sy, Graphics.LEFT | Graphics.TOP);
            }
            else
            {
                g.setColor(0,0,0);
                g.drawString(array[i].substring(1), 5, 50+MainCanvas.nameFont.getHeight()*i-sy, Graphics.LEFT | Graphics.TOP);
            }
        }

        if (!path.equals("")) UIPainter.paintLeftSoft(g, Common.upString);
        UIPainter.paintRightSoft(g, Common.cancelString);

        g.setClip(0, 0, getWidth(), getHeight());

        repaint();
    }
    
    /**
     * Called when a key is pressed.
     */
    protected  void keyPressed(int keyCode) 
    {
        if ((keyCode==-5)||(keyCode==KEY_NUM5))
        {
            if (array[s].charAt(array[s].length()-1) == '/')
            {
                path = path + array[s].substring(1);
                fst = new FSThread();
                fst.setMode(1, path);
                fst.start();

                waitingFS = true;
            }
            else
            {
                fst = new FSThread();
                fst.setMode(2, path + array[s].substring(1));
                fst.start();

                waitingFSImage = true;
                JustPaint.save.filename.setContent(path + array[s].substring(1,array[s].length()-4) + "-1");
            }
        }

        if (keyCode==-1) s--;
        if (keyCode==-2) s++;
        if (keyCode==KEY_NUM2) s--;
        if (keyCode==KEY_NUM8) s++;
        if (s<0) s=array.length-1;
        if (s>=array.length) s=0;

        if (keyCode==-6)
        {
            try { path = path.substring(0, path.length()-1); }
            catch (Throwable ex) { path = ""; }
            
            while ((!(path.endsWith("/")))&&(path.length()>0))
            {
                try { path = path.substring(0, path.length()-1); }
                catch (Throwable ex) { path = ""; }
            }

            fst = new FSThread();
            fst.setMode(1, path);
            fst.start();

            waitingFS = true;
        }

        if (keyCode==-7)
        {
            //JustPaint.display.setCurrent(JustPaint.c);
            JustPaint.display.setCurrent(JustPaint.mm);
        }
    }
    
    /**
     * Called when a key is released.
     */
    protected  void keyReleased(int keyCode) 
    {

    }

    /**
     * Called when a key is repeated (held down).
     */
    protected  void keyRepeated(int keyCode) 
    {
        if (keyCode==-1) s--;
        if (keyCode==-2) s++;
        if (keyCode==KEY_NUM2) s--;
        if (keyCode==KEY_NUM8) s++;
        if (s<0) s=array.length-1;
        if (s>=array.length) s=0;
    }
    
    /**
     * Called when the pointer is dragged.
     */
    protected  void pointerDragged(int x, int y) {
    }

    /**
     * Called when the pointer is pressed.
     */
    protected  void pointerPressed(int x, int y) {
    }

    /**
     * Called when the pointer is released.
     */
    protected  void pointerReleased(int x, int y) {
    }
    
    /**
     * Called when action should be handled
     */
    public void commandAction(Command command, Displayable displayable) {
    }

}