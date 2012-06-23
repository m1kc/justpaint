/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import java.util.Vector;
import javax.microedition.lcdui.*;

/**
 * @author Makc
 */
public class NewFilter extends Canvas
{
    
    private static final String EFFECTS_SYMBOL_LIST = "gndlsbiw";
    private static final String[] EFFECTS_NAME_LIST = {
        "Обесцвечивание",
        "Негатив",
        "Затемнение",
        "Осветление",
        "Сепия",
        "Размытие",
        "Искажение",
        "Черно-белый"
    };
    
    Vector effect = new Vector();
    int se = 0;
    int qq=1;

    /**
     * constructor
     */
    public NewFilter()
    {
        setFullScreenMode(true);
    } 

    private String toName(String k) {
        // Замена серии условий на табличное значение.
        char effectSymbol = k.charAt(0);
        int effectId = EFFECTS_SYMBOL_LIST.indexOf(effectSymbol);
        return EFFECTS_NAME_LIST[effectId];
    }

    private String toName(int effectId)
    {
        return EFFECTS_NAME_LIST[effectId];
    }

    private String toCode(int k)
    {
        return String.valueOf(EFFECTS_SYMBOL_LIST.charAt(k));
    }

    /**
     * paint
     */
    public void paint(Graphics g) 
    {
        g.setFont(MainCanvas.nameFont);

        g.setColor(255,255,255);
        g.fillRect(0, 0, getWidth(), getHeight());
        UIPainter.paintBackground(g);

        g.setColor(0,0,0);

        int i;
        String s;
        for (i=0; i<effect.size(); i++)
        {
            s = (String) effect.elementAt(i);
            s = toName(s);
            g.drawString(s, 5, i*MainCanvas.nameFont.getHeight(), Graphics.LEFT | Graphics.TOP);
        }

        g.drawString("< "+toName(se)+" >", getWidth()/2, getHeight()-MainCanvas.nameFont.getHeight()*2, Graphics.HCENTER | Graphics.TOP);
        //g.drawString("0 - сохранить", getWidth()/2, getHeight()-MainCanvas.nameFont.getHeight(), Graphics.HCENTER | Graphics.TOP);
        UIPainter.paintRightSoft(g, "Создать");

        repaint();
    }
    
    /**
     * Called when a key is pressed.
     */
    protected  void keyPressed(int keyCode) 
    {
        if (keyCode==KEY_NUM4 || keyCode==-3) se--;
        if (keyCode==KEY_NUM6 || keyCode==-4) se++;
        if (se<0) se=0;
        if (se>7) se=7;

        if (keyCode==KEY_NUM5 || keyCode==-5) effect.addElement(toCode(se));
        if (keyCode==-7)
        {
            Vector e = new Vector();
            Filter f = new Filter();
            for (int j=0; j<effect.size(); j++)
            {
                e.addElement(effect.elementAt(j));
            }
            f.effects = e;
            f.name = "Фильтр "+qq;
            qq++;
            JustPaint.fl.filters.addElement(f);
            effect.removeAllElements();
            JustPaint.display.setCurrent(JustPaint.fl);
        }
    }

}