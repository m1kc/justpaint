/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import javax.microedition.lcdui.*;

/**
 * @author Makc
 */
public class MatrixEditor extends Canvas
{

    int sx=0;
    int sy=0;

    int[] data;

    Font f = MainCanvas.nameFont;

    int[][] matrix = new int[3][3];
    int scale,offset;
    boolean balance;

    /**
     * constructor
     */
    public MatrixEditor()
    {
        setFullScreenMode(true);

        data = new int[12];
        for (int i=0; i<12; i++) { data[i]=0; }
        data[11] = 1;
    } 

    private String ist(int x)
    {
        if (x==1) return "Да"; else return "Нет";
    }

    private int invert(int x)
    {
        if (x==0) return 1; else return 0;
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

        g.drawString(""+data[0]+" "+data[1]+" "+data[2], 5, 5, Graphics.LEFT | Graphics.TOP);
        g.drawString(""+data[3]+" "+data[4]+" "+data[5], 5, 5+MainCanvas.nameFont.getHeight(), Graphics.LEFT | Graphics.TOP);
        g.drawString(""+data[6]+" "+data[7]+" "+data[8], 5, 5+MainCanvas.nameFont.getHeight()*2, Graphics.LEFT | Graphics.TOP);

        g.drawString("Делитель: "+data[9], 5, 5+MainCanvas.nameFont.getHeight()*4, Graphics.LEFT | Graphics.TOP);
        g.drawString("Смещение: "+data[10], 5, 5+MainCanvas.nameFont.getHeight()*5, Graphics.LEFT | Graphics.TOP);
        g.drawString("Баланс яркости: "+ist(data[11]), 5, 5+MainCanvas.nameFont.getHeight()*6, Graphics.LEFT | Graphics.TOP);

        g.drawString("OK", 5, 5+MainCanvas.nameFont.getHeight()*8, Graphics.LEFT | Graphics.TOP);

        g.drawString("Отмена", 5, 5+MainCanvas.nameFont.getHeight()*10, Graphics.LEFT | Graphics.TOP);

        if ((sx==0)&&(sy==0)) g.drawRect(4, 5, f.stringWidth(""+data[0]), f.getHeight());
        if ((sx==1)&&(sy==0)) g.drawRect(4+f.stringWidth(""+data[0]+" "), 5, f.stringWidth(""+data[1]), f.getHeight());
        if ((sx==2)&&(sy==0)) g.drawRect(4+f.stringWidth(""+data[0]+" "+data[1]+" "), 5, f.stringWidth(""+data[2]), f.getHeight());

        if ((sx==0)&&(sy==1)) g.drawRect(4, 5+f.getHeight(), f.stringWidth(""+data[3]), f.getHeight());
        if ((sx==1)&&(sy==1)) g.drawRect(4+f.stringWidth(""+data[3]+" "), 5+f.getHeight(), f.stringWidth(""+data[4]), f.getHeight());
        if ((sx==2)&&(sy==1)) g.drawRect(4+f.stringWidth(""+data[3]+" "+data[4]+" "), 5+f.getHeight(), f.stringWidth(""+data[5]), f.getHeight());

        if ((sx==0)&&(sy==2)) g.drawRect(4, 5+f.getHeight()*2, f.stringWidth(""+data[6]), f.getHeight());
        if ((sx==1)&&(sy==2)) g.drawRect(4+f.stringWidth(""+data[6]+" "), 5+f.getHeight()*2, f.stringWidth(""+data[7]), f.getHeight());
        if ((sx==2)&&(sy==2)) g.drawRect(4+f.stringWidth(""+data[6]+" "+data[7]+" "), 5+f.getHeight()*2, f.stringWidth(""+data[8]), f.getHeight());

        if (sy==3) g.drawRect(4, 5+f.getHeight()*4, f.stringWidth("Делитель: "+data[9]), f.getHeight());
        if (sy==4) g.drawRect(4, 5+f.getHeight()*5, f.stringWidth("Смещение: "+data[10]), f.getHeight());
        if (sy==5) g.drawRect(4, 5+f.getHeight()*6, f.stringWidth("Баланс яркости: "+ist(data[11])), f.getHeight());

        if (sy==6) g.drawRect(4, 5+f.getHeight()*8, f.stringWidth("OK"), f.getHeight());

        if (sy==7) g.drawRect(4, 5+f.getHeight()*10, f.stringWidth("Отмена"), f.getHeight());

        repaint();
    }
    
    /**
     * Called when a key is pressed.
     */
    protected  void keyPressed(int keyCode) 
    {
        if (keyCode==-1) sy--;
        if (keyCode==-2) sy++;
        if (keyCode==-3) sx--;
        if (keyCode==-4) sx++;

        if (sx<0) sx=0;
        if (sx>2) sx=2;
        if (sy<0) sy=7;
        if (sy>7) sy=0;

        if (keyCode==-5)
        {
            if (sy==5) data[11]=invert(data[11]);
            if (sy==6)
            {
                for (int i=0; i<3; i++)
                {
                    for (int j=0; j<3; j++)
                    {
                        int k = i + j*3;
                        matrix[i][j] = data[k];
                    }
                }
                scale = data[9];
                offset = data[10];
                if (data[11]==0) balance=false;
                if (data[11]==1) balance=true;

                JustPaint.c.setImage(Effects.matrix(JustPaint.c.getImage(), matrix, scale, offset, balance));

                JustPaint.display.setCurrent(JustPaint.c);
            }
            if (sy==7)
            {
                JustPaint.display.setCurrent(JustPaint.c);
            }
        }

        if ((keyCode>=KEY_NUM0)&&(keyCode<=KEY_NUM9))
        {
            int n = keyCode-48;
            if ((sy>=0)&&(sy<=2))
            {
                int k = sx + sy*3;
                data[k] = data[k]*10+n;
            }
            if (sy==3) data[9]=data[9]*10+n;
            if (sy==4) data[10]=data[10]*10+n;
        }

        if (keyCode==-8)
        {
            if ((sy>=0)&&(sy<=2))
            {
                int k = sx + sy*3;
                data[k] = data[k]/10;
            }
            if (sy==3) data[9]=data[9]/10;
            if (sy==4) data[10]=data[10]/10;
        }

        if (keyCode==42)
        {
            if ((sy>=0)&&(sy<=2))
            {
                int k = sx + sy*3;
                data[k] = -data[k];
            }
        }
    }
    
    /**
     * Called when a key is released.
     */
    protected  void keyReleased(int keyCode) {
    }

    /**
     * Called when a key is repeated (held down).
     */
    protected  void keyRepeated(int keyCode) {
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

}