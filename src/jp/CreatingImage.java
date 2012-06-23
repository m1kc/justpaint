/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import GUI2.dialogs.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.Sprite;

/**
 * @author Makc
 */
public class CreatingImage
{
    int x;
    int y;
    private int w = 1;
    private int h = 1;

    public final int CREATE = 0;
    public final int CANVAS = 1;
    public final int CUT = 2;
    public final int RESIZE = 3;

    private int mode = CREATE;

    Dialog dialog;
    Number n1;
    Number n2;
    Button ok;
    Button cancel;
    Runnable okPress,cancelPress;
    Checkbox kar;

    boolean cameFromMainCanvas = false;


    public void init()
    {
        dialog = new Dialog(JustPaint.midlet);

        n1 = new Number(l10n.LocalizationSupport.getMessage("WIDTH"),JustPaint.c.getWidth());
        {
            Runnable r = new Runnable() {
                public void run() {
                    if (kar.checked)
                    n2.i = n1.i*getH()/getW();
                    System.out.println(""+getH()+" "+getW());
                }
            };
            n1.setOnChange(r);
        }
        n2 = new Number(l10n.LocalizationSupport.getMessage("HEIGHT"),JustPaint.c.getHeight());
        {
            Runnable r = new Runnable() {
                public void run() {
                    if (kar.checked)
                    n1.i = n2.i*getW()/getH();
                }
            };
            n2.setOnChange(r);
        }
        w = n1.i; h = n2.i;

        //ok.name = "OK";
        okPress = new Runnable()
        {
            public void run()
            {
                x = n1.i;
                y = n2.i;

                if (mode==CREATE)
                {
                    JustPaint.c.setImage(Image.createImage(x, y));
                }

                if (mode==CANVAS)
                {
                    Image g = Image.createImage(x, y);
                    Graphics f = g.getGraphics();
                    f.drawImage(JustPaint.c.getImage(), 0, 0, Graphics.LEFT | Graphics.TOP);
                    JustPaint.c.setImage(g);
                }

                if (mode==CUT)
                {
                    try
                    {
                        JustPaint.c.setImage(Image.createImage(JustPaint.c.getImage(), JustPaint.c.getImage().getWidth() / 2 - x / 2, JustPaint.c.getImage().getHeight() / 2 - y / 2, x, y, Sprite.TRANS_NONE));
                    }
                    catch (Throwable ex)
                    {

                    }
                }

                if (mode==RESIZE)
                {
                    JustPaint.c.setImage(Resize.resize_image(JustPaint.c.getImage(), x, y));
                }

                JustPaint.display.setCurrent(JustPaint.c);
                setMode(CREATE);
                cameFromMainCanvas = false;
            }

        };
        ok = new Button(Common.okString, okPress);

        //cancel.name = "Отмена";
        cancelPress = new Runnable(){
            public void run()
            {
                if (cameFromMainCanvas)
                {
                    JustPaint.display.setCurrent(JustPaint.c);
                }
                else
                {
                    JustPaint.display.setCurrent(JustPaint.mm);
                }
                setMode(CREATE);
                cameFromMainCanvas = false;
            }
        };
        cancel = new Button(Common.cancelString, cancelPress);

        kar = new Checkbox(l10n.LocalizationSupport.getMessage("KEEPRATIO"));

        dialog.header = l10n.LocalizationSupport.getMessage("CREATEIMAGE");
        dialog.offset = (JustPaint.c.getHeight()-MainCanvas.nameFont.getHeight()*5)/8;

        dialog.addElement(n1);
        dialog.addElement(n2);
        dialog.addElement(kar);
        dialog.addElement(ok);
        dialog.addElement(cancel);
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(int mode)
    {
        this.mode = mode;
        String[] s = new String[]{
            l10n.LocalizationSupport.getMessage("CREATEIMAGE"),
            l10n.LocalizationSupport.getMessage("CANVASSIZE"),
            l10n.LocalizationSupport.getMessage("CROPFROMCENTER"),
            l10n.LocalizationSupport.getMessage("IMAGESIZE")
        };
        dialog.header = s[mode];
        n1.i = JustPaint.c.getImage().getWidth();
        n2.i = JustPaint.c.getImage().getHeight();
    }

    public void activate()
    {
        setW(n1.i);
        setH(n2.i);
        JustPaint.display.setCurrent(dialog);
    }

    /**
     * @return the w
     */
    public int getW() {
        return w;
    }

    /**
     * @param w the w to set
     */
    public void setW(int w) {
        this.w = w;
    }

    /**
     * @return the h
     */
    public int getH() {
        return h;
    }

    /**
     * @param h the h to set
     */
    public void setH(int h) {
        this.h = h;
    }

}