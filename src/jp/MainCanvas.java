/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import java.util.Date;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.Sprite;
import java.util.Random;

/**
 * @author Makc
 */
public final class MainCanvas extends Canvas
{
    Graphics m;
    int x = 20;
    int y = 20;
    int x2 = 0;
    int y2 = 0;
    Image canvas;
    private Image img;
    Image buffer;
    Image buffer2;
    Image hr;
    Image zoom;
    int zx;
    int zy;

    boolean useMyTools = false;
    int brush = 1;
    int mtbrush = 0;

    int scale = 1;
    public boolean isset = false;
    public String txt;
    String[] brs;

    boolean uUp = false;
    boolean dUp = false;
    boolean lUp = false;
    boolean rUp = false;

    public static Font nameFont = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
    Font tf = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);

    int zoom2x = 1;
    int zoom4x = 2;
    int zoom8x = 3;
    int standard = 4;
    int compact = 5;
    int off = 6;

    int panelsize = 80;

    int sx = 0;
    int sy = 0;

    boolean Lock = false;

    boolean firePressed = false;

    boolean pick = false;

    Image pixBuffer = null; // Буфер 1х1. Тот пиксель, что находится под курсором.

    boolean unlocked = false;
    boolean cleaned = false;

    /**
     * constructor
     */
    public MainCanvas() 
    {
        setFullScreenMode(true);

        setImage(Image.createImage(128,128));
        buffer = Image.createImage(1, 1);
        buffer2 = Image.createImage(1, 1);

        brs = new String[40];

        brs[0]  = "Инструменты не выбраны!";
        brs[1]  = "Карандаш";
        brs[2]  = "Кисть";
        brs[3]  = "Линия";
        brs[4]  = "Прямоугольник";
        brs[5]  = "Закр.прямоугольник";
        brs[6]  = "Эллипс";
        brs[7]  = "Закр.эллипс";
        brs[8]  = "Скр.прямоугольник";
        brs[9]  = "Закр.скр.прямоугольник";
        brs[10] = "Текст";
        brs[11] = "Обрезка";
        brs[12] = "Копирование";
        brs[13] = "Вырезка";
        brs[14] = "Вставка";
        brs[15] = "Пипетка";
        brs[16] = "Замена цвета";
        brs[17] = "Прозр.карандаш";
        brs[18] = "Гориз.градиент";
        brs[19] = "Верт.градиент";
        brs[20] = "Прозрачность";
        brs[21] = "Полупрозрачность";
        brs[22] = "Распыление";
        brs[23] = "Хреноугольник";
        brs[24] = "Прозр.прямоугольник";
        brs[25] = "Заливка";
        brs[26] = "Блик";
        brs[27] = "Гор.градиент с заменой";
        brs[28] = "Верт.градиент с заменой";
        brs[29] = "Заливка фона";
        brs[30] = "Полупрозрачная вставка";
        brs[31] = "Радиальный градиент";
        brs[32] = "Очистка области";
        brs[33] = "Заполнить все";
        brs[34] = "Оставить эллипс";
        brs[35] = "Smart-вставка";
        brs[36] = "Оставить круг";
        brs[37] = "Отражение";
        brs[38] = "Текст с обводкой";
        brs[39] = "Непрозрачное отражение";
    } 

    /**
     * paint
     */
    public void paint(Graphics g)
    {
        prePaint();

        // Финал. Отрисовка под фанфары.

        g.drawImage(canvas, 0, 0, Graphics.LEFT | Graphics.TOP);

        repaint();
    }

    public void prePaint()
    {
        if (canvas==null) canvas = Image.createImage(getWidth(), getHeight());

        if ((!img.isMutable())&&(!Lock)) setImage(Tools.makeMutable(getImage()));

        m = canvas.getGraphics();
        m.setFont(nameFont);

        if (uUp) y=y-1;
        if (dUp) y=y+1;
        if (lUp) x=x-1;
        if (rUp) x=x+1;

        // Прокрутка

        /*if ((x+sx+getWidth()/8 > getWidth())&&(img.getWidth()>getWidth()))
        {
        sx = getWidth() - x - getWidth()/8;
        }

        if ((x+sx < getWidth()/8)&&(img.getWidth()>getWidth()))
        {
        sx = getWidth() - x - getWidth()/8*7;
        }*/

        sx = getWidth()/2 - x;

        if (sx < getWidth()-getImage().getWidth())
        {
            sx = getWidth() - getImage().getWidth();
        }

        if (sx > 0) sx = 0;

        //

        /*if ((y+sy+panelsize+getWidth()/8 > getHeight())&&(img.getHeight()>getHeight()-panelsize))
        {
        sy = getHeight() - y - getWidth()/8 - panelsize;
        if (!(y+sy > 0)) sy=0;
        }

        if ((y+sy < getWidth()/8)&&(img.getHeight()>getHeight()-panelsize))
        {
        sy = getHeight() - y - getWidth()/8*7 - panelsize;
        if (!(y+sy > 0)) sy=0;
        }*/

        sy = getHeight()/2 - panelsize/2 - y;

        if (sy < getHeight()-panelsize-getImage().getHeight())
        {
            sy = getHeight() - panelsize - getImage().getHeight();
        }

        if (sy > 0) sy = 0;

        // Кисть & распыление

        if ((brush == 2)&&(isset))
        {
            if (!Lock)
            {
                m = getImage().getGraphics();
                m.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
                m.drawRect(x, y, 0, 0);
                m = canvas.getGraphics();
            }
            if (Lock) setImage(Tools.putPixel(getImage(), x, y));
        }

        if ((firePressed)&&(brush==22))
        {
            Date d = new Date();
            Random r = new Random (d.getTime());
            int n1 = Math.abs(r.nextInt()%41)-20;
            int n2 = Math.abs(r.nextInt()%41)-20;
            m = getImage().getGraphics();
            m.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
            if (n1*n1+n2*n2 <= 400) m.drawRect(x+n1, y+n2, 0, 0);
            m = canvas.getGraphics();
        }

        // Рисуем первый вариант канваса. С курсором-точкой.

        m.setColor(128,128,128);
        m.fillRect(0, 0, getWidth(), getHeight());

        m.setFont(nameFont);

        m.drawImage(getImage(), 0+sx, 0+sy, Graphics.LEFT | Graphics.TOP);

        drawToolPreviews();

        try
        {
            pixBuffer = Image.createImage(canvas, x+sx, y+sy, 1, 1, Sprite.TRANS_NONE);
        }
        catch (Throwable ex)
        {
            System.out.println(ex.toString());
        }

        m.setColor(255,0,0);
        m.drawRect(x+sx, y+sy, 0, 0);

        // Зум.

        if (scale==zoom2x)
        {
            zx = x + sx - 20;
            zy = y + sy - 20;
            if (zx<0) zx=0;
            if (zy<0) zy=0;
            if (zx+40>canvas.getWidth()) zx=canvas.getWidth()-40;
            if (zy+40>canvas.getHeight()) zy=canvas.getHeight()-40;
            zoom = Image.createImage(canvas, zx, zy, 40, 40, Sprite.TRANS_NONE);
            zoom = Resize.resize_image(zoom, 80, 80);
        }

        if (scale==zoom4x)
        {
            zx = x + sx - 10;
            zy = y + sy - 10;
            if (zx<0) zx=0;
            if (zy<0) zy=0;
            if (zx+20>canvas.getWidth()) zx=canvas.getWidth()-20;
            if (zy+20>canvas.getHeight()) zy=canvas.getHeight()-20;
            zoom = Image.createImage(canvas, zx, zy, 20, 20, Sprite.TRANS_NONE);
            zoom = Resize.resize_image(zoom, 80, 80);
        }

        if (scale==zoom8x)
        {
            zx = x + sx - 5;
            zy = y + sy - 5;
            if (zx<0) zx=0;
            if (zy<0) zy=0;
            if (zx+10>canvas.getWidth()) zx=canvas.getWidth()-10;
            if (zy+10>canvas.getHeight()) zy=canvas.getHeight()-10;
            zoom = Image.createImage(canvas, zx, zy, 10, 10, Sprite.TRANS_NONE);
            zoom = Resize.resize_image(zoom, 80, 80);
        }

        // Рисуем второй вариант канваса. С нормальным курсором.

        /*
        m.setFont(nameFont);

        m.setColor(128,128,128);
        m.fillRect(0, 0, getWidth(), getHeight());

        m.drawImage(img, 0+sx, 0+sy, Graphics.LEFT | Graphics.TOP);

        drawToolPreviews();

        m.setFont(nameFont);

         */

        m.drawImage(pixBuffer, x+sx, y+sy, Graphics.LEFT | Graphics.TOP);

        m.setColor(0,0,0);
        m.drawLine(x-3+sx, y+sy, x-1+sx, y+sy);
        m.drawLine(x+1+sx, y+sy, x+3+sx, y+sy);
        m.drawLine(x+sx, y-3+sy, x+sx, y-1+sy);
        m.drawLine(x+sx, y+1+sy, x+sx, y+3+sy);

        m.setFont(nameFont);

        // Нижняя панель.

        if ((scale==zoom2x)||(scale==zoom4x)||(scale==zoom8x))
        {
            //m.setColor(128,128,255);
            //m.fillRect(0, getHeight()-80, getWidth(), 80);
            Gradient.drawVGradient(m, 128, 128, 128, 255, 255, 255, 0, getHeight()-80, getWidth(), 80);
            m.setColor(0,0,0);
            m.drawString(""+x+","+y+" ["+sx+","+sy+"]", 5, getHeight()-80, Graphics.LEFT | Graphics.TOP);
            m.drawString((useMyTools ? "[M] "+brs[brush] : brs[brush]), 5, getHeight()-80+nameFont.getHeight(), Graphics.LEFT | Graphics.TOP);
            m.setColor(255,255,255);
            m.drawLine(0, getHeight()-nameFont.getHeight()-1, getWidth(), getHeight()-nameFont.getHeight()-1);
            m.setColor(0,0,0);
            if (scale==zoom2x) m.drawString("Zoom: 2x", 5, getHeight()-nameFont.getHeight(), Graphics.LEFT | Graphics.TOP);
            if (scale==zoom4x) m.drawString("Zoom: 4x", 5, getHeight()-nameFont.getHeight(), Graphics.LEFT | Graphics.TOP);
            if (scale==zoom8x) m.drawString("Zoom: 8x", 5, getHeight()-nameFont.getHeight(), Graphics.LEFT | Graphics.TOP);
            m.setColor(JustPaint.r2,JustPaint.g2,JustPaint.b2);
            m.fillRect(getWidth()-80-16, getHeight()-nameFont.getHeight()-16, 16, 16);
            m.setColor(JustPaint.r,JustPaint.g,JustPaint.b);
            m.fillRect(getWidth()-80-16-4, getHeight()-nameFont.getHeight()-16-4, 16, 16);
            m.drawImage(zoom, getWidth()-80, getHeight()-80, Graphics.LEFT | Graphics.TOP);

            panelsize=80;
        }

        if (scale==standard)
        {
            m.setColor(128,128,255);
            //m.fillRect(0, getHeight()-nameFont.getHeight()*2, getWidth(), nameFont.getHeight()*2);
            Gradient.drawVGradient(m, 128, 128, 128, 255, 255, 255, 0, getHeight()-nameFont.getHeight()*2, getWidth(), nameFont.getHeight()*2);
            m.setColor(0,0,0);
            m.drawString(""+x+","+y, 5, getHeight()-nameFont.getHeight()*2, Graphics.LEFT | Graphics.TOP);
            m.drawString((useMyTools ? "[M] "+brs[brush] : brs[brush]), 5, getHeight()-nameFont.getHeight(), Graphics.LEFT | Graphics.TOP);
            m.setColor(JustPaint.r2,JustPaint.g2,JustPaint.b2);
            m.fillRect(getWidth()-16, getHeight()-16, 16, 16);
            m.setColor(JustPaint.r,JustPaint.g,JustPaint.b);
            m.fillRect(getWidth()-16-4, getHeight()-16-4, 16, 16);

            panelsize = nameFont.getHeight()*2;
        }

        if (scale==compact)
        {
            m.setColor(128,128,255);
            //m.fillRect(0, getHeight()-nameFont.getHeight(), getWidth(), nameFont.getHeight());
            Gradient.drawVGradient(m, 128, 128, 128, 255, 255, 255, 0, getHeight()-nameFont.getHeight(), getWidth(), nameFont.getHeight());
            m.setColor(0,0,0);
            m.drawString(""+x+","+y+" "+(useMyTools ? "[M] "+brs[brush] : brs[brush]), 5, getHeight()-nameFont.getHeight(), Graphics.LEFT | Graphics.TOP);
            m.setColor(JustPaint.r2,JustPaint.g2,JustPaint.b2);
            m.fillRect(getWidth()-16, getHeight()-16, 16, 16);
            m.setColor(JustPaint.r,JustPaint.g,JustPaint.b);
            m.fillRect(getWidth()-16-4, getHeight()-16-4, 16, 16);

            panelsize = nameFont.getHeight();
        }

        if (scale==off) panelsize=0;

        if (Lock)
        {
            m.setColor(255,255,255);
            m.fillRect(getWidth()-nameFont.stringWidth("Блокировка"), 0, nameFont.stringWidth("Блокировка"), nameFont.getHeight());
            m.fillTriangle(getWidth()-nameFont.stringWidth("Блокировка"), 0,
                    getWidth()-nameFont.stringWidth("Блокировка"), nameFont.getHeight(),
                    getWidth()-nameFont.stringWidth("Блокировка")-nameFont.getHeight(), 0);
            m.setColor(0,0,0);
            m.drawString("Блокировка", getWidth(), 0, Graphics.RIGHT | Graphics.TOP);
        }
    }

    public void drawToolPreviews() {
        if ((brush == 3) && (isset)) {
            m.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
            m.drawLine(x + sx, y + sy, x2 + sx, y2 + sy);
        }
        if ((brush == 4) && (isset)) {
            m.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
            m.drawRect(minx() + sx, miny() + sy, maxx() - minx(), maxy() - miny());
        }
        if ((brush == 5) && (isset)) {
            m.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
            m.fillRect(minx() + sx, miny() + sy, maxx() - minx() + 1, maxy() - miny() + 1);
        }
        if ((brush == 6) && (isset)) {
            m.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
            m.drawArc(minx() + sx, miny() + sy, maxx() - minx(), maxy() - miny(), 0, 360);
        }
        if ((brush == 7) && (isset)) {
            m.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
            m.fillArc(minx() + sx, miny() + sy, maxx() - minx() + 1, maxy() - miny() + 1, 0, 360);
        }
        if ((brush == 8) && (isset)) {
            m.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
            m.drawRoundRect(minx() + sx, miny() + sy, maxx() - minx(), maxy() - miny(), 11, 11);
        }
        if ((brush == 9) && (isset)) {
            m.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
            m.fillRoundRect(minx() + sx, miny() + sy, maxx() - minx() + 1, maxy() - miny() + 1, 11, 11);
        }
        if ((brush == 10) && (isset)) {
            m.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
            m.setFont(tf);
            m.drawString(txt, x + sx, y + sy, Graphics.LEFT | Graphics.TOP);
        }
        if ((brush == 11) && (isset)) {
            m.setColor(255, 0, 0);
            m.drawRect(minx() + sx, miny() + sy, maxx() - minx(), maxy() - miny());
        }
        if ((brush == 12) && (isset)) {
            m.setColor(255, 0, 0);
            m.drawRect(minx() + sx, miny() + sy, maxx() - minx(), maxy() - miny());
        }
        if ((brush == 13) && (isset)) {
            m.setColor(255, 0, 0);
            m.drawRect(minx() + sx, miny() + sy, maxx() - minx(), maxy() - miny());
        }
        if (brush == 14) {
            m.drawImage(buffer, x + sx, y + sy, Graphics.LEFT | Graphics.TOP);
        }
        if ((brush == 18) && (isset)) {
            Gradient.drawHGradient(m, JustPaint.r, JustPaint.g, JustPaint.b, JustPaint.r2, JustPaint.g2, JustPaint.b2, minx() + sx, miny() + sy, maxx() - minx() + 1, maxy() - miny() + 1);
        }
        if ((brush == 19) && (isset)) {
            Gradient.drawVGradient(m, JustPaint.r, JustPaint.g, JustPaint.b, JustPaint.r2, JustPaint.g2, JustPaint.b2, minx() + sx, miny() + sy, maxx() - minx() + 1, maxy() - miny() + 1);
        }
        if ((brush == 23) && (isset)) {
            m.drawImage(hr, x + sx, y + sy, Graphics.LEFT | Graphics.TOP);
        }
        if ((brush == 24) && (isset)) {
            int w = maxx() - minx() + 1;
            int h = maxy() - miny() + 1;
            Image i = Image.createImage(w, h);
            m = i.getGraphics();
            m.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
            m.fillRect(0, 0, w, h);
            int q = Lib_effects.get(i, 0, 0, 1);
            int a = Lib_effects.get(i, 0, 0, 2);
            int e = Lib_effects.get(i, 0, 0, 3);
            i = Lib_effects.changecolor(i, q, a, e, 128, q, a, e);
            m = canvas.getGraphics();
            m.drawImage(i, minx() + sx, miny() + sy, Graphics.LEFT | Graphics.TOP);
        }
        if (brush == 30) {
            m.drawImage(buffer2, x + sx, y + sy, Graphics.LEFT | Graphics.TOP);
        }
        if ((brush == 31) && (isset)) {
            m.drawImage(Tools.radialGradient(maxx() - minx(), maxx() - minx(), JustPaint.r, JustPaint.g, JustPaint.b, JustPaint.r2, JustPaint.g2, JustPaint.b2), minx(), miny(), Graphics.LEFT | Graphics.TOP);
        }
        if ((brush == 32) && (isset)) {
            m.setColor(255, 0, 0);
            m.fillRect(minx() + sx, miny() + sy, maxx() - minx() + 1, maxy() - miny() + 1);
        }
        if ((brush == 34) && (isset)) {
            m.setColor(255,0,0);
            m.fillArc(minx() + sx, miny() + sy, maxx() - minx() + 1, maxy() - miny() + 1, 0, 360);
        }
        if (brush == 35) {
            m.drawImage(buffer, x + sx, y + sy, Graphics.LEFT | Graphics.TOP);
        }
        if ((brush == 36) && (isset)) {
            m.setColor(255,0,0);
            m.fillArc(minx() + sx, miny() + sy, maxx() - minx() + 1, maxx() - minx() + 1, 0, 360);
        }
        if ((brush == 37) && (isset)) {
            m.setColor(255, 0, 0);
            m.drawRect(minx() + sx, miny() + sy, maxx() - minx(), maxy() - miny());
        }
        if ((brush == 38) && (isset)) {
            m.setColor(JustPaint.r2, JustPaint.g2, JustPaint.b2);
            DGTools.setColor(m, 128, JustPaint.r2, JustPaint.g2, JustPaint.b2);
            m.setFont(tf);
            m.drawString(txt, x-1+sx, y+sy, Graphics.LEFT | Graphics.TOP);
            m.drawString(txt, x+1+sx, y+sy, Graphics.LEFT | Graphics.TOP);
            m.drawString(txt, x+sx, y-1+sy, Graphics.LEFT | Graphics.TOP);
            m.drawString(txt, x+sx, y+1+sy, Graphics.LEFT | Graphics.TOP);
                  
            m.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
            m.drawString(txt, x+sx, y+sy, Graphics.LEFT | Graphics.TOP);
        }
        if ((brush == 39) && (isset)) {
            m.setColor(255, 0, 0);
            m.drawRect(minx() + sx, miny() + sy, maxx() - minx(), maxy() - miny());
        }


    }

    private int maxx()
    {
      if (x>x2) {return x;} else {return x2;}
    }

    private int maxy()
    {
      if (y>y2) {return y;} else {return y2;}
    }

    private int minx()
    {
      if (x<x2) {return x;} else {return x2;}
    }

    private int miny()
    {
      if (y<y2) {return y;} else {return y2;}
    }

    /**
     * Вызывается при нажатии джойстика, клавиши 5 или стилуса.
     */
    private void OnPress()
    {
          if (brush==1)
          {
              if (!Lock)
              {
                m = getImage().getGraphics();
                m.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
                m.drawRect(x, y, 0, 0);
              }
              if (Lock) setImage(Tools.putPixel(getImage(), x, y));
          }

          if (brush==2)
          {
              isset = !isset;
          }

          if (brush==3)
          {
              if (!isset)
              {
                  isset = true;
                  x2=x;
                  y2=y;
              }
              else
              {
                  isset = false;
                  m = getImage().getGraphics();
                  m.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
                  m.drawLine(x, y, x2, y2);
              }
          }

          if (brush==4)
          {
              if (!isset)
              {
                  isset = true;
                  x2=x;
                  y2=y;
              }
              else
              {
                  isset = false;
                  m = getImage().getGraphics();
                  m.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
                  m.drawRect(minx(), miny(), maxx()-minx(), maxy()-miny());
              }
          }

          if (brush==5)
          {
              if (!isset)
              {
                  isset = true;
                  x2=x;
                  y2=y;
              }
              else
              {
                  isset = false;
                  m = getImage().getGraphics();
                  m.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
                  m.fillRect(minx(), miny(), maxx()-minx()+1, maxy()-miny()+1);
              }
          }

          if (brush==6)
          {
              if (!isset)
              {
                  isset = true;
                  x2=x;
                  y2=y;
              }
              else
              {
                  isset = false;
                  m = getImage().getGraphics();
                  m.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
                  m.drawArc(minx(), miny(), maxx()-minx(), maxy()-miny(), 0, 360);
              }
          }

          if (brush==7)
          {
              if (!isset)
              {
                  isset = true;
                  x2=x;
                  y2=y;
              }
              else
              {
                  isset = false;
                  m = getImage().getGraphics();
                  m.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
                  m.fillArc(minx(), miny(), maxx()-minx()+1, maxy()-miny()+1, 0, 360);
              }
          }

          if (brush==8)
          {
              if (!isset)
              {
                  isset = true;
                  x2=x;
                  y2=y;
              }
              else
              {
                  isset = false;
                  m = getImage().getGraphics();
                  m.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
                  m.drawRoundRect(minx(), miny(), maxx()-minx(), maxy()-miny(), 11, 11);
              }
          }

          if (brush==9)
          {
              if (!isset)
              {
                  isset = true;
                  x2=x;
                  y2=y;
              }
              else
              {
                  isset = false;
                  m = getImage().getGraphics();
                  m.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
                  m.fillRoundRect(minx(), miny(), maxx()-minx()+1, maxy()-miny()+1, 11, 11);
              }
          }

          if (brush==10)
          {
              if (!isset)
              {
                  isset = true;
                  final TextBox f = new TextBox(null,txt,Integer.MAX_VALUE,TextField.ANY);
                  CommandListener cl = new CommandListener() 
                  {
                    public void commandAction(Command cmnd, Displayable dsplbl) 
                    {
                        JustPaint.display.setCurrent(JustPaint.c);
                        txt = f.getString();
                    }
                  };
                  f.setCommandListener(cl);
                  Command c = new Command("OK", Command.OK, 1);
                  f.addCommand(c);
                  JustPaint.display.setCurrent(f);
              }
              else
              {
                  isset = false;
                  m = getImage().getGraphics();
                  m.setFont(tf);
                  m.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
                  m.drawString(txt, x, y, Graphics.LEFT | Graphics.TOP);
              }
          }

          if (brush==11)
          {
              if (!isset)
              {
                  isset = true;
                  x2=x;
                  y2=y;
              }
              else
              {
                  isset = false;
                  setImage(Image.createImage(getImage(), minx(), miny(), maxx() - minx() + 1, maxy() - miny() + 1, Sprite.TRANS_NONE));
              }
          }

          if (brush==12)
          {
              if (!isset)
              {
                  isset = true;
                  x2=x;
                  y2=y;
              }
              else
              {
                  isset = false;
                  buffer = Image.createImage( getImage(), minx(), miny(), maxx()-minx()+1, maxy()-miny()+1, Sprite.TRANS_NONE);
                  buffer2 = Tools.halfAlpha(buffer);
              }
          }

          if (brush==13)
          {
              if (!isset)
              {
                  isset = true;
                  x2=x;
                  y2=y;
              }
              else
              {
                  isset = false;
                  buffer = Image.createImage( getImage(), minx(), miny(), maxx()-minx()+1, maxy()-miny()+1, Sprite.TRANS_NONE);
                  buffer2 = Tools.halfAlpha(buffer);
                  m = getImage().getGraphics();
                  m.setColor(255,255,255);
                  m.fillRect(minx(), miny(), maxx()-minx()+1, maxy()-miny()+1);
              }
          }

          if (brush==14)
          {
              if (!Lock)
              {
                m = getImage().getGraphics();
                m.drawImage(buffer, x, y, Graphics.LEFT | Graphics.TOP);
              }
              if (Lock)
              {
                  setImage(Tools.putImage(getImage(), buffer, x, y));
              }
          }

          if (brush==15)
          {
              JustPaint.r = Lib_effects.get(getImage(),x, y, 1);
              JustPaint.g = Lib_effects.get(getImage(),x, y, 2);
              JustPaint.b = Lib_effects.get(getImage(),x, y, 3);
          }

          if (brush==16)
          {
              setImage(Lib_effects.changecolor(getImage(), Lib_effects.get(getImage(), x, y, 1), Lib_effects.get(getImage(), x, y, 2), Lib_effects.get(getImage(), x, y, 3), 255, JustPaint.r, JustPaint.g, JustPaint.b));
          }

          if (brush==17)
          {
              m = getImage().getGraphics();

              int q,w,e;

              q = Lib_effects.get(getImage(),x, y, 1);
              w = Lib_effects.get(getImage(),x, y, 2);
              e = Lib_effects.get(getImage(),x, y, 3);

              q = (q + JustPaint.r) / 2;
              w = (w + JustPaint.g) / 2;
              e = (e + JustPaint.b) / 2;

              m.setColor(q, w, e);

              m.drawRect(x, y, 0, 0);
          }

          if (brush==18)
          {
              if (!isset)
              {
                  isset = true;
                  x2=x;
                  y2=y;
              }
              else
              {
                  isset = false;
                  m = getImage().getGraphics();
                  Gradient.drawHGradient(m, JustPaint.r, JustPaint.g, JustPaint.b, JustPaint.r2, JustPaint.g2, JustPaint.b2, minx(), miny(), maxx()-minx()+1, maxy()-miny()+1);
              }
          }

          if (brush==19)
          {
              if (!isset)
              {
                  isset = true;
                  x2=x;
                  y2=y;
              }
              else
              {
                  isset = false;
                  m = getImage().getGraphics();
                  Gradient.drawVGradient(m, JustPaint.r, JustPaint.g, JustPaint.b, JustPaint.r2, JustPaint.g2, JustPaint.b2, minx(), miny(), maxx()-minx()+1, maxy()-miny()+1);
              }
          }

          if (brush==20)
          {
              int q = Lib_effects.get(getImage(),x, y, 1);
              int w = Lib_effects.get(getImage(),x, y, 2);
              int e = Lib_effects.get(getImage(),x, y, 3);
              setImage(Lib_effects.changecolor(getImage(), q, w, e, 0, 0, 0, 0));
              Lock = true;
          }

          if (brush==21)
          {
              int q = Lib_effects.get(getImage(),x, y, 1);
              int w = Lib_effects.get(getImage(),x, y, 2);
              int e = Lib_effects.get(getImage(),x, y, 3);
              setImage(Lib_effects.changecolor(getImage(), q, w, e, 128, q, w, e));
              Lock = true;
          }

          if (brush==22)
          {
              Date d = new Date();
              Random r = new Random (d.getTime());
              int n1 = Math.abs(r.nextInt()%41)-20;
              int n2 = Math.abs(r.nextInt()%41)-20;
              m = getImage().getGraphics();
              m.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
              if (n1*n1+n2*n2 <= 400) m.drawRect(x+n1, y+n2, 0, 0);
          }

          if (brush==23)
          {
              if (!isset)
              {
                  JustPaint.hp.genHren();
                  JustPaint.display.setCurrent(JustPaint.hp);
              }
              else
              {
                  m = getImage().getGraphics();
                  m.drawImage(hr, x, y, Graphics.LEFT | Graphics.TOP);
                  isset = false;
              }
          }

          if (brush==24)
          {
              if (!isset)
              {
                  isset = true;
                  x2=x;
                  y2=y;
              }
              else
              {
                  isset = false;
                  int w = maxx()-minx()+1;
                  int h = maxy()-miny()+1;
                  Image i = Image.createImage(w,h);
                  m = i.getGraphics();
                  m.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
                  m.fillRect(0,0,w,h);
                  int q = Lib_effects.get(i, 0, 0, 1);
                  int a = Lib_effects.get(i, 0, 0, 2);
                  int e = Lib_effects.get(i, 0, 0, 3);
                  i = Lib_effects.changecolor(i, q, a, e, 128, q, a, e);
                  m = getImage().getGraphics();
                  m.drawImage(i, minx(), miny(), Graphics.LEFT | Graphics.TOP);
              }
          }

          if (brush==25)
          {
              Fill.setimage(getImage());
              Fill.prepare(x, y);
              setImage(Fill.fill(x, y, JustPaint.r, JustPaint.g, JustPaint.b, true));
          }

          if (brush==26)
          {
              Image k = Image.createImage(16, 16);
              m = k.getGraphics();
              m.setColor(12,23,233);
              m.fillRect(0, 0, 16, 16);
              int q = Lib_effects.get(k, 0, 0, 1);
              int w = Lib_effects.get(k, 0, 0, 2);
              int e = Lib_effects.get(k, 0, 0, 3);
              m.setColor(Lib_effects.get(getImage(),x, y, 1),Lib_effects.get(getImage(),x, y, 2),Lib_effects.get(getImage(),x, y, 3));
              m.fillArc(0, 0, 16, 16, 0, 360);
              int q2 = Lib_effects.get(k, 8, 8, 1);
              int w2 = Lib_effects.get(k, 8, 8, 2);
              int e2 = Lib_effects.get(k, 8, 8, 3);
              k = Lib_effects.changecolor(k, q2, w2, e2, 128, q2, w2, e2);
              k = Lib_effects.changecolor(k, q, w, e, 0, 0, 0, 0);
              m = getImage().getGraphics();
              m.drawImage(k, x-8, y-8, Graphics.LEFT | Graphics.TOP);
          }

          if (brush==27)
          {
              int q = Lib_effects.get(getImage(),x, y, 1);
              int a = Lib_effects.get(getImage(),x, y, 2);
              int e = Lib_effects.get(getImage(),x, y, 3);
              setImage(Tools.replaceHGradient(getImage(), q, a, e, JustPaint.r, JustPaint.g, JustPaint.b, JustPaint.r2, JustPaint.g2, JustPaint.b2));
          }

          if (brush==28)
          {
              int q = Lib_effects.get(getImage(),x, y, 1);
              int a = Lib_effects.get(getImage(),x, y, 2);
              int e = Lib_effects.get(getImage(),x, y, 3);
              setImage(Tools.replaceVGradient(getImage(), q, a, e, JustPaint.r, JustPaint.g, JustPaint.b, JustPaint.r2, JustPaint.g2, JustPaint.b2));
          }

          if (brush==29)
          {
              int q = Lib_effects.get(getImage(),x, y, 1);
              int a = Lib_effects.get(getImage(),x, y, 2);
              int e = Lib_effects.get(getImage(),x, y, 3);
              setImage(Tools.fillBackground(getImage(), q, a, e, JustPaint.r, JustPaint.g, JustPaint.b));
          }

          if (brush==30)
          {
              m = getImage().getGraphics();
              m.drawImage(buffer2, x, y, Graphics.LEFT | Graphics.TOP);
          }

          if (brush==31)
          {
              if (isset)
              {
                  m = getImage().getGraphics();
                  m.drawImage(Tools.radialGradient(maxx()-minx(), maxx()-minx(), JustPaint.r, JustPaint.g, JustPaint.b, JustPaint.r2, JustPaint.g2, JustPaint.b2), minx(), miny(), Graphics.LEFT | Graphics.TOP);
                  isset=false;
              }
              else
              {
                  x2 = x;
                  y2 = y;
                  isset = true;
              }
          }

          if (brush==32)
          {
              if (!isset)
              {
                  isset = true;
                  x2=x;
                  y2=y;
              }
              else
              {
                  isset = false;
                  setImage(Tools.clearAreaInImage(getImage(), minx(), miny(), maxx() - minx() + 1, maxy() - miny() + 1));
                  Lock = true;
              }
          }

          if (brush==33)
          {
              setImage(Tools.fillEverything(getImage()));
          }

          if (brush==34)
          {
              if (!isset)
              {
                  isset = true;
                  x2=x;
                  y2=y;
              }
              else
              {
                  isset = false;
                  Lock = true;
                  setImage(Tools.leaveOnlyEllipse(getImage(), minx(), miny(), maxx() - minx() + 1, maxy() - miny() + 1));
              }
          }

          if (brush==35)
          {
              setImage(DGTools.insertImage(getImage(), buffer, x, y));
          }

          if (brush==36)
          {
              if (!isset)
              {
                  isset = true;
                  x2=x;
                  y2=y;
              }
              else
              {
                  isset = false;
                  Lock = true;
                  setImage(Tools.leaveOnlyEllipse(getImage(), minx(), miny(), maxx() - minx() + 1, maxx() - minx() + 1));
              }
          }

          if (brush==37)
          {
              if (!isset)
              {
                  isset = true;
                  x2=x;
                  y2=y;
              }
              else
              {
                  isset = false;
                  if (!Lock)
                  {
                      Image i = Image.createImage(getImage(), minx(), miny(), maxx() - minx() + 1, maxy() - miny() + 1, Sprite.TRANS_MIRROR_ROT180);
                      i = Tools.halfAlphaWithGradient(i);
                      Graphics g = getImage().getGraphics();
                      g.drawImage(i, minx(), maxy(), Sprite.TRANS_NONE);
                  }
                  else
                  {
                      Image i = Image.createImage(getImage(), minx(), miny(), maxx() - minx() + 1, maxy() - miny() + 1, Sprite.TRANS_MIRROR_ROT180);
                      i = Tools.halfAlpha(i);
                      setImage(Tools.putImage(img, i, minx(), maxy()));
                  }
              }
          }

          if (brush==38)
          {
              if (!isset)
              {
                  isset = true;
                  final TextBox f = new TextBox(null,txt,Integer.MAX_VALUE,TextField.ANY);
                  CommandListener cl = new CommandListener()
                  {
                      public void commandAction(Command cmnd, Displayable dsplbl)
                      {
                          JustPaint.display.setCurrent(JustPaint.c);
                          txt = f.getString();
                      }
                  };
                  f.setCommandListener(cl);
                  Command c = new Command("OK", Command.OK, 1);
                  f.addCommand(c);
                  JustPaint.display.setCurrent(f);
              }
              else
              {
                  isset = false;
                  m = getImage().getGraphics();
                  m.setFont(tf);

                  m.setColor(JustPaint.r2, JustPaint.g2, JustPaint.b2);
                  DGTools.setColor(m, 128, JustPaint.r2, JustPaint.g2, JustPaint.b2);
                  m.drawString(txt, x-1, y, Graphics.LEFT | Graphics.TOP);
                  m.drawString(txt, x+1, y, Graphics.LEFT | Graphics.TOP);
                  m.drawString(txt, x, y-1, Graphics.LEFT | Graphics.TOP);
                  m.drawString(txt, x, y+1, Graphics.LEFT | Graphics.TOP);

                  m.setColor(JustPaint.r, JustPaint.g, JustPaint.b);
                  m.drawString(txt, x, y, Graphics.LEFT | Graphics.TOP);
              }
          }

          if (brush==39)
          {
              if (!isset)
              {
                  isset = true;
                  x2=x;
                  y2=y;
              }
              else
              {
                  isset = false;
                  if (!Lock)
                  {
                      Image i = Image.createImage(getImage(), minx(), miny(), maxx() - minx() + 1, maxy() - miny() + 1, Sprite.TRANS_MIRROR_ROT180);
                      Graphics g = getImage().getGraphics();
                      g.drawImage(i, minx(), maxy(), Sprite.TRANS_NONE);
                  }
                  else
                  {
                      Image i = Image.createImage(getImage(), minx(), miny(), maxx() - minx() + 1, maxy() - miny() + 1, Sprite.TRANS_MIRROR_ROT180);
                      setImage(Tools.putImage(img, i, minx(), maxy()));
                  }
              }
          }
    }

    public void switchUseMyTools()
    {
        useMyTools = !useMyTools;
        if ((!useMyTools) && (brush == 0)) brush = 1;
        if (useMyTools)
        {
            changeBrush(0); // Just update.
        }
    }

    public void changeBrush(int delta)
    {
        if (useMyTools)
        {
            mtbrush+=delta;
            isset = false;
            if (mtbrush >= JustPaint.mts.myTools.size()) mtbrush = 0;
            if (mtbrush < 0) mtbrush = JustPaint.mts.myTools.size()-1;
            if (JustPaint.mts.myTools.isEmpty())
            {
                brush = 0;
            }
            else
            {
                brush = ((Integer) JustPaint.mts.myTools.elementAt(mtbrush)).intValue();
            }
        }
        else
        {
            brush+=delta;
            isset = false;
            if (brush >= brs.length) brush = 1;
            if (brush < 1) brush = brs.length-1;
        }
    }

    /**
     * Called when a key is pressed.
     */
    protected  void keyPressed(int keyCode)
    {
        if (keyCode==50) y = y - 1;
        if (keyCode==56) y = y + 1;
        if (keyCode==52) x = x - 1;
        if (keyCode==54) x = x + 1;

        if (keyCode==-1) y = y - 1;
        if (keyCode==-2) y = y + 1;
        if (keyCode==-3) x = x - 1;
        if (keyCode==-4) x = x + 1;

        if (keyCode==35) { JustPaint.display.setCurrent(JustPaint.cs); }

        if (keyCode==-7)
        {
            JustPaint.display.setCurrent(JustPaint.mm);
        }

        /*
        if (keyCode==KEY_NUM7)
        {
            JustPaint.display.setCurrent(JustPaint.mm);
        }
         */

        if (keyCode==KEY_NUM9)
        {
            scale++;
            if (scale>off) scale=zoom2x;
        }

        if (keyCode==-26)
        {
            JustPaint.ef.prepare();
            JustPaint.display.setCurrent(JustPaint.ef);
        }

        if (keyCode==KEY_NUM1 || keyCode==-13) { changeBrush(+1); }
        if (keyCode==KEY_NUM3 || keyCode==-14) { changeBrush(-1); }

        if (keyCode==KEY_NUM0) { switchUseMyTools(); }

        if ((keyCode==53)||(keyCode==-5))
        {
            OnPress();
            firePressed = true;
        }
    }
    
    /**
     * Called when a key is released.
     */
    protected  void keyReleased(int keyCode)
    {
        if (keyCode==50) uUp = false;
        if (keyCode==56) dUp = false;
        if (keyCode==52) lUp = false;
        if (keyCode==54) rUp = false;

        if (keyCode==-1) uUp = false;
        if (keyCode==-2) dUp = false;
        if (keyCode==-3) lUp = false;
        if (keyCode==-4) rUp = false;

        if ((keyCode==53)||(keyCode==-5))
        {
            firePressed = false;
        }


        if (keyCode==42)
        {
            if (!pick)
            {
                int q,w,e;
                q = JustPaint.r;
                w = JustPaint.g;
                e = JustPaint.b;
                JustPaint.r = JustPaint.r2;
                JustPaint.g = JustPaint.g2;
                JustPaint.b = JustPaint.b2;
                JustPaint.r2 = q;
                JustPaint.g2 = w;
                JustPaint.b2 = e;
            }
            pick = false;
        }

        if (keyCode==-10)
        {
            if (!unlocked)
            {
                scale++;
                if (scale>off) scale=zoom2x;
            }
            unlocked = false;
        }

        if (keyCode==-8)
        {
            if (!cleaned)
            {
                JustPaint.display.setCurrent(JustPaint.hi);
            }
            cleaned = false;
        }
    }

    /**
     * Called when a key is repeated (held down).
     */
    protected  void keyRepeated(int keyCode)
    {
        if (keyCode==50) uUp = true;
        if (keyCode==56) dUp = true;
        if (keyCode==52) lUp = true;
        if (keyCode==54) rUp = true;

        if (keyCode==-1) uUp = true;
        if (keyCode==-2) dUp = true;
        if (keyCode==-3) lUp = true;
        if (keyCode==-4) rUp = true;

        if (keyCode==KEY_STAR)
        {
            JustPaint.r = Lib_effects.get(getImage(),x, y, 1);
            JustPaint.g = Lib_effects.get(getImage(),x, y, 2);
            JustPaint.b = Lib_effects.get(getImage(),x, y, 3);
            pick = true;
        }

        if (keyCode==-36)
        {
            JustPaint.ci.setMode(JustPaint.ci.CANVAS);
            JustPaint.ci.cameFromMainCanvas = true;
            JustPaint.ci.activate();
        }

        if (keyCode==-37)
        {
            JustPaint.ci.setMode(JustPaint.ci.RESIZE);
            JustPaint.ci.cameFromMainCanvas = true;
            JustPaint.ci.activate();
        }

        if (keyCode==-8)
        {
            //JustPaint.c.setImage(Image.createImage(JustPaint.c.getImage().getWidth(), JustPaint.c.getImage().getHeight()));
            JustPaint.c.setImage(DGTools.createImage(JustPaint.c.getImage().getWidth(), JustPaint.c.getImage().getHeight(), 0x00000000));
            Lock = true;
            JustPaint.display.setCurrent(JustPaint.c);
            cleaned = true;
        }

        if (keyCode==-10)
        {
            Lock = false;
            unlocked = true;
        }

        if (keyCode==-25) { changeBrush(-1); }
    }
    
    /**
     * Called when the pointer is dragged.
     */
    protected  void pointerDragged(int x, int y)
    {
        this.x = x-sx;
        this.y = y-sy;
    }

    /**
     * Called when the pointer is pressed.
     */
    protected  void pointerPressed(int x, int y)
    {
        this.x = x-sx;
        this.y = y-sy;
        OnPress();
        firePressed = true;
    }

    /**
     * Called when the pointer is released.
     */
    protected  void pointerReleased(int x, int y)
    {
        this.x = x-sx;
        this.y = y-sy;
        OnPress();
        firePressed = false;
    }

    /**
     * @return the img
     */
    public Image getImage()
    {
        return img;
    }

    /**
     * @param img the img to set
     */
    public void setImage(Image img)
    {
        this.img = img;
        JustPaint.id.pointerChanged(img);
    }

    public void imageChanged(Image img)
    {
        this.img = img;
    }

}