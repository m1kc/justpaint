/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import java.util.Vector;
import javax.microedition.lcdui.*;

/**
 * @author пользователь
 */
public class MainMenu extends Canvas
{
    Font f = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);

    Vector tabs = new Vector();

    int cTab = 0;
    int cItem = 0;

    int marker;

    /**
     * constructor
     */
    public MainMenu()
    {
        setFullScreenMode(true);

        makeMenu();
    } 
    
    /**
     * paint
     */
    public void paint(Graphics g)
    {
        UIPainter.paintBackground(g);
        g.setFont(f);
        g.setColor(0x000000);
        g.fillRect(0, 0, getWidth(), f.getHeight());
        Gradient.drawVGradient(g, 128, 128, 128, 0, 0, 0, 0, 0, getWidth(), f.getHeight()/2);
        g.setColor(0xFFFFFF);
        g.fillTriangle(0, f.getHeight()/2, 5, f.getHeight()/2-5, 5, f.getHeight()/2+5);
        g.fillTriangle(getWidth(), f.getHeight()/2, getWidth()-5, f.getHeight()/2-5, getWidth()-5, f.getHeight()/2+5);
        g.drawString(tabs.elementAt(cTab).toString(), getWidth()/2, 0, Graphics.HCENTER | Graphics.TOP);

        marker = f.getHeight()+5;

        MenuTab m = (MenuTab) tabs.elementAt(cTab);
        for (int i=0; i<m.items.size(); i++)
        {
            if (i==cItem)
            {
                g.setColor(0x000000);
                g.fillRect(5, marker, getWidth()-5, f.getHeight());
                //Gradient.drawHGradient(g, 0, 0, 0, 255, 255, 255, getWidth()/2, marker, getWidth()/2, f.getHeight());
                g.setColor(0xFFFFFF);
                g.drawString(m.items.elementAt(i).toString(), 10, marker, Graphics.LEFT | Graphics.TOP);
            }
            else
            {
                g.setColor(0x000000);
                g.drawString(m.items.elementAt(i).toString(), 5, marker, Graphics.LEFT | Graphics.TOP);
            }
            marker += f.getHeight();
        }

        UIPainter.paintRightSoft(g, "Отмена");

        //repaint();
    }
    
    /**
     * Called when a key is pressed.
     */
    protected  void keyPressed(int keyCode)
    {
        if (keyCode==-3 || keyCode==KEY_NUM4) cTab--;
        if (keyCode==-4 || keyCode==KEY_NUM6) cTab++;
        if (cTab >= tabs.size()) cTab=0;
        if (cTab < 0) cTab = tabs.size()-1;

        if (keyCode==KEY_NUM8 || keyCode == -2) cItem++;
        if (keyCode==KEY_NUM2 || keyCode == -1) cItem--;
        if (cItem >= ((MenuTab)tabs.elementAt(cTab)).items.size()) cItem=0;
        if (cItem < 0) cItem = ((MenuTab)tabs.elementAt(cTab)).items.size()-1;

        if (keyCode == -5 || keyCode==KEY_NUM5)
        {
            Runnable r = ((MenuItem)((MenuTab)tabs.elementAt(cTab)).items.elementAt(cItem)).action;
            if (r!=null) r.run();
        }

        if (keyCode==-7)
        {
            JustPaint.display.setCurrent(JustPaint.c);
        }

        repaint();
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

    private void makeMenu()
    {
        MenuTab t;
        Runnable r;

        ////////////////////////
        t = new MenuTab("Файл");
        tabs.addElement(t);

        r = new Runnable(){
            public void run()
            {
                JustPaint.ci.activate();
            }
        };
        t.items.addElement(new MenuItem("Создать",r));

        r = new Runnable(){
            public void run()
            {
                JustPaint.c.setImage(Image.createImage(JustPaint.c.getImage().getWidth(), JustPaint.c.getImage().getHeight()));
                JustPaint.display.setCurrent(JustPaint.c);
            }
        };
        t.items.addElement(new MenuItem("Очистить",r));

        r = new Runnable(){
            public void run()
            {
                JustPaint.c.Lock = false;
                JustPaint.display.setCurrent(JustPaint.c);
            }
        };
        t.items.addElement(new MenuItem("Разблокировать",r));

        r = new Runnable(){
            public void run()
            {
                JustPaint.display.setCurrent(JustPaint.fm);
            }
        };
        t.items.addElement(new MenuItem("Открыть",r));

        r = new Runnable(){
            public void run()
            {
                JustPaint.save.activate();
            }
        };
        t.items.addElement(new MenuItem("Сохранить",r));

        r = new Runnable(){
            public void run()
            {
                JustPaint.display.setCurrent(JustPaint.id);
            }
        };
        t.items.addElement(new MenuItem("Диспетчер",r));

        r = new Runnable(){
            public void run()
            {
                JustPaint.display.setCurrent(JustPaint.qd);
            }
        };
        t.items.addElement(new MenuItem("Диспетчер (фикс.4)",r));

        r = new Runnable(){
            public void run()
            {
                JustPaint.inf.prepare();
                JustPaint.display.setCurrent(JustPaint.inf);
            }
        };
        t.items.addElement(new MenuItem("Информация",r));

        r = new Runnable(){
            public void run()
            {
                JustPaint.display.setCurrent(null);
            }
        };
        t.items.addElement(new MenuItem("Свернуть",r));

        r = new Runnable(){
            public void run()
            {
                RMSWizard.writeOptions();
                JustPaint.midlet.exitMIDlet();
            }
        };
        t.items.addElement(new MenuItem("Выход",r));

        /////////////////////////
        t = new MenuTab("Холст");
        tabs.addElement(t);

        r = new Runnable(){
            public void run()
            {
                JustPaint.ci.setMode(JustPaint.ci.RESIZE);
                JustPaint.ci.activate();
            }
        };
        t.items.addElement(new MenuItem("Размер изображения",r));

        r = new Runnable(){
            public void run()
            {
                JustPaint.ci.setMode(JustPaint.ci.CANVAS);
                JustPaint.ci.activate();
            }
        };
        t.items.addElement(new MenuItem("Размер холста",r));

        r = new Runnable(){
            public void run()
            {
                JustPaint.ci.setMode(JustPaint.ci.CUT);
                JustPaint.ci.activate();
            }
        };
        t.items.addElement(new MenuItem("Центр.вырезка",r));


        r = new Runnable(){
            public void run()
            {
                Common.mask = Image.createImage(JustPaint.c.getImage());
                JustPaint.display.setCurrent(JustPaint.c);
            }
        };
        t.items.addElement(new MenuItem("Сохр. как маску",r));

        r = new Runnable(){
            public void run()
            {
                JustPaint.c.setImage(Tools.appendMask(JustPaint.c.getImage(), Common.mask));
                JustPaint.c.Lock = true;
                JustPaint.display.setCurrent(JustPaint.c);
            }
        };
        t.items.addElement(new MenuItem("Применить маску",r));

        r = new Runnable(){
            public void run()
            {
                //JustPaint.id.backup();
                JustPaint.id.removeNulls();
                JustPaint.id.images.addElement(Tools.appendMask(JustPaint.c.getImage(), Common.mask));
                JustPaint.id.s = JustPaint.id.images.size()-1;
                JustPaint.id.doReplace();
                //JustPaint.c.setImage(Tools.appendMask(JustPaint.c.getImage(), CommonData.mask));
                JustPaint.c.Lock = true;
                JustPaint.display.setCurrent(JustPaint.c);
            }
        };
        t.items.addElement(new MenuItem("Применить маску (копир.)",r));

        r = new Runnable(){
            public void run()
            {
                JustPaint.c.setImage(Tools.scale2x(JustPaint.c.getImage()));
                JustPaint.display.setCurrent(JustPaint.c);
            }
        };
        t.items.addElement(new MenuItem("Scale2x",r));

        ///////////////////////////////
        t = new MenuTab("Функции");
        tabs.addElement(t);

        r = new Runnable(){
            public void run()
            {
                JustPaint.pr.prepare();
                JustPaint.display.setCurrent(JustPaint.pr);
            }
        };
        t.items.addElement(new MenuItem("Просмотр",r));

        r = new Runnable(){
            public void run()
            {
                JustPaint.display.setCurrent(JustPaint.mts);
            }
        };
        t.items.addElement(new MenuItem("Мои инструменты",r));

        r = new Runnable(){
            public void run()
            {
                JustPaint.cl.reinit();
                JustPaint.display.setCurrent(JustPaint.cl);
            }
        };
        t.items.addElement(new MenuItem("Выбрать инструменты",r));

        r = new Runnable(){
            public void run()
            {
                JustPaint.display.setCurrent(JustPaint.cs);
            }
        };
        t.items.addElement(new MenuItem("Выбор цвета",r));

        r = new Runnable(){
            public void run()
            {
                JustPaint.display.setCurrent(JustPaint.csp);
            }
        };
        t.items.addElement(new MenuItem("Палитра",r));

        r = new Runnable(){
            public void run()
            {
                JustPaint.ef.prepare();
                JustPaint.display.setCurrent(JustPaint.ef);
            }
        };
        t.items.addElement(new MenuItem("Эффекты",r));

        r = new Runnable(){
            public void run()
            {
                JustPaint.display.setCurrent(JustPaint.ff);
            }
        };
        t.items.addElement(new MenuItem("Шрифт",r));

        r = new Runnable(){
            public void run()
            {
                JustPaint.display.setCurrent(JustPaint.fl);
            }
        };
        t.items.addElement(new MenuItem("Фильтры",r));

        r = new Runnable(){
            public void run()
            {
                JustPaint.display.setCurrent(JustPaint.me);
            }
        };
        t.items.addElement(new MenuItem("Матрица",r));

    }
}