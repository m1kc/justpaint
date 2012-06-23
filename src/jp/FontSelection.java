/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import javax.microedition.lcdui.*;

/**
 * @author пользователь
 */
public class FontSelection extends Canvas
{
    int[] faces = new int[]{Font.FACE_MONOSPACE,Font.FACE_PROPORTIONAL,Font.FACE_SYSTEM};
    int[] styles = new int[]{Font.STYLE_BOLD,Font.STYLE_ITALIC,Font.STYLE_PLAIN,Font.STYLE_UNDERLINED};
    int[] sizes = new int[]{Font.SIZE_LARGE,Font.SIZE_MEDIUM,Font.SIZE_SMALL};

    String[] facess = new String[]{
        l10n.LocalizationSupport.getMessage("MONOSPACED"),
        l10n.LocalizationSupport.getMessage("PROPORTIONAL"),
        l10n.LocalizationSupport.getMessage("SYSTEM")
    };
    String[] styless = new String[]{
        l10n.LocalizationSupport.getMessage("BOLD"),
        l10n.LocalizationSupport.getMessage("ITALIC"),
        l10n.LocalizationSupport.getMessage("PLAIN"),
        l10n.LocalizationSupport.getMessage("UNDERLINED")
    };
    String[] sizess = new String[]{
        l10n.LocalizationSupport.getMessage("LARGE"),
        l10n.LocalizationSupport.getMessage("MEDIUM"),
        l10n.LocalizationSupport.getMessage("SMALL")
    };

    // По умолчанию - системный, мелкий, обычный.
    int face = 2;
    int style = 2;
    int size = 2;
    
    Font f = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
    Font sf = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);

    boolean notSupported = false;

    /**
     * constructor
     */
    public FontSelection()
    {
        setFullScreenMode(true);
    } 
    
    /**
     * paint
     */
    public void paint(Graphics g)
    {
        UIPainter.paintBackground(g);

        int marker = 5;
        g.setColor(0x000000);
        g.setFont(sf);
        g.drawString("Example", 5, marker, Graphics.LEFT | Graphics.TOP);
        marker+=(sf.getHeight()+5);
        
        g.setFont(f);
        g.drawString(facess[face], 5, marker, Graphics.LEFT | Graphics.TOP);
        marker+=f.getHeight();
        g.drawString(styless[style], 5, marker, Graphics.LEFT | Graphics.TOP);
        marker+=f.getHeight();
        g.drawString(sizess[size], 5, marker, Graphics.LEFT | Graphics.TOP);
        marker+=f.getHeight();
        if (notSupported) g.drawString("(!) Not supported", 5, marker, Graphics.LEFT | Graphics.TOP);

        g.setFont(f);
        UIPainter.paintLeftSoft(g, Common.cancelString);
        UIPainter.paintRightSoft(g, Common.okString);

        repaint();
    }
    
    /**
     * Called when a key is pressed.
     */
    protected  void keyPressed(int keyCode)
    {
        try
        {
            notSupported = false;

            if (keyCode==KEY_NUM1 || keyCode==-25) face++;
            if (face>=faces.length) face=0;

            if (keyCode==KEY_NUM2 || keyCode==-13) style++;
            if (style>=styles.length) style=0;

            if (keyCode==KEY_NUM3 || keyCode==-14) size++;
            if (size>=sizes.length) size=0;

            sf = Font.getFont(faces[face], styles[style], sizes[size]);

            if (keyCode==-6)
            {
                sf = f;
                face = 2;
                style = 2;
                size = 2;
                JustPaint.display.setCurrent(JustPaint.mm);
            }

            if (keyCode==-7 || keyCode==-5)
            {
                JustPaint.c.tf = sf;
                JustPaint.c.brush = 10;
                JustPaint.display.setCurrent(JustPaint.c);
            }
        }
        catch(Throwable ex)
        {
            notSupported = true;
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
    }
    
    /**
     * Called when the pointer is dragged.
     */
    protected  void pointerDragged(int x, int y)
    {
    }

    /**
     * Called when the pointer is pressed.
     */
    protected  void pointerPressed(int x, int y)
    {
    }

    /**
     * Called when the pointer is released.
     */
    protected  void pointerReleased(int x, int y)
    {
    }

}