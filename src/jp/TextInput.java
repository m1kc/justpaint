/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import javax.microedition.lcdui.*;

/**
 * @author Makc
 */
public class TextInput extends Canvas
{

    String str="";
    char ts=' ';
    //boolean edit=false;
    boolean edit=false;
    boolean upcase = false;
    String uc="";

    /**
     * constructor
     */
    public TextInput() {
	    // Set up this canvas to listen to command events
	    //setCommandListener(this);
	    // Add the Exit command
	    //addCommand(new Command("Exit", Command.EXIT, 1));

        setFullScreenMode(true);
    } 
    
    /**
     * paint
     */
    public void paint(Graphics g) {
        g.setFont(MainCanvas.nameFont);

        g.setColor(255,255,255);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(0,0,0);

        if (upcase) { uc="[АБВ]"; } else { uc="[абв]"; }

        g.drawString("Введите текст:"+"     "+uc, 5, 5, Graphics.LEFT | Graphics.TOP);
        if (edit)
        {
            g.drawString(str+ts, 5, 25, Graphics.LEFT | Graphics.TOP);
        }
        else
        {
            g.drawString(str+ts+"_", 5, 25, Graphics.LEFT | Graphics.TOP);
        }

        repaint();
    }
    
    /**
     * Called when a key is pressed.
     */
    protected  void keyPressed(int keyCode)
    {
        if (keyCode==48) edit=false;
        if (keyCode==42) upcase=!upcase;
        if (keyCode==35)
        {
          str = str+ts;
          ts = ' ';
          str = str+" ";
          edit=false;
        }
        if ((keyCode==-8)||(keyCode==-6))
        {
          if ((str.length()>0)&&(str != " "))
          {
          if (edit)
          {
            edit=false;
            ts = str.charAt(str.length()-1);
            str = str.substring(0, str.length()-1);
          }
          else
          {
            ts = str.charAt(str.length()-1);
            str = str.substring(0, str.length()-1);
          }
          }
        }

        if (keyCode==-5)
        {
            JustPaint.c.txt = str.substring(1)+ts;
            JustPaint.display.setCurrent(JustPaint.c);
        }

        char[] c = new char[1];
        c[0] = ' ';

        // Таблицы
        //         символов

        if (keyCode==49)
        {
            c = new char[11];
            if (upcase)
            { c[0]='.'; c[1]=','; c[2]='!'; c[3]='?';
              c[4]='-'; c[5]='+'; c[6]='*'; c[7]='/';
              c[8]=':'; c[9]=';'; c[10]='1';}
            else
            { c[0]='.'; c[1]=','; c[2]='!'; c[3]='?';
              c[4]='-'; c[5]='+'; c[6]='*'; c[7]='/';
              c[8]=':'; c[9]=';'; c[10]='1';}
        }

        if (keyCode==50)
        {
            c = new char[8];
            if (upcase)
            { c[0]='А'; c[1]='Б'; c[2]='В'; c[3]='Г';
              c[4]='2'; c[5]='A'; c[6]='B'; c[7]='C'; }
            else
            { c[0]='а'; c[1]='б'; c[2]='в'; c[3]='г';
              c[4]='2'; c[5]='a'; c[6]='b'; c[7]='c'; }
        }

        if (keyCode==51)
        {
            c = new char[8];
            if (upcase)
            { c[0]='Д'; c[1]='Е'; c[2]='Ж'; c[3]='З';
              c[4]='3'; c[5]='D'; c[6]='E'; c[7]='F'; }
            else
            { c[0]='д'; c[1]='е'; c[2]='ж'; c[3]='з';
              c[4]='3'; c[5]='d'; c[6]='e'; c[7]='f'; }
        }

        if (keyCode==52)
        {
            c = new char[8];
            if (upcase)
            { c[0]='И'; c[1]='Й'; c[2]='К'; c[3]='Л';
              c[4]='4'; c[5]='G'; c[6]='H'; c[7]='I'; }
            else
            { c[0]='и'; c[1]='й'; c[2]='к'; c[3]='л';
              c[4]='4'; c[5]='g'; c[6]='h'; c[7]='i'; }
        }

        if (keyCode==53)
        {
            c = new char[8];
            if (upcase)
            { c[0]='М'; c[1]='Н'; c[2]='О'; c[3]='П';
              c[4]='5'; c[5]='J'; c[6]='K'; c[7]='L'; }
            else
            { c[0]='м'; c[1]='н'; c[2]='о'; c[3]='п';
              c[4]='5'; c[5]='j'; c[6]='k'; c[7]='l'; }
        }

        if (keyCode==54)
        {
            c = new char[8];
            if (upcase)
            { c[0]='Р'; c[1]='С'; c[2]='Т'; c[3]='У';
              c[4]='6'; c[5]='M'; c[6]='N'; c[7]='O'; }
            else
            { c[0]='р'; c[1]='с'; c[2]='т'; c[3]='у';
              c[4]='6'; c[5]='m'; c[6]='n'; c[7]='o'; }
        }

        if (keyCode==55)
        {
            c = new char[9];
            if (upcase)
            { c[0]='Ф'; c[1]='Х'; c[2]='Ц'; c[3]='Ч';
              c[4]='7'; c[5]='P'; c[6]='Q'; c[7]='R'; c[8]='S'; }
            else
            { c[0]='ф'; c[1]='х'; c[2]='ц'; c[3]='ч';
              c[4]='6'; c[5]='p'; c[6]='q'; c[7]='r'; c[8]='s'; }
        }

        if (keyCode==56)
        {
            c = new char[8];
            if (upcase)
            { c[0]='Ш'; c[1]='Щ'; c[2]='Ъ'; c[3]='Ы';
              c[4]='8'; c[5]='T'; c[6]='U'; c[7]='V'; }
            else
            { c[0]='ш'; c[1]='щ'; c[2]='ъ'; c[3]='ы';
              c[4]='8'; c[5]='t'; c[6]='u'; c[7]='v'; }
        }

        if (keyCode==57)
        {
            c = new char[9];
            if (upcase)
            { c[0]='Ь'; c[1]='Э'; c[2]='Ю'; c[3]='Я';
              c[4]='9'; c[5]='W'; c[6]='X'; c[7]='Y'; c[8]='Z'; }
            else
            { c[0]='ь'; c[1]='э'; c[2]='ю'; c[3]='я';
              c[4]='9'; c[5]='w'; c[6]='x'; c[7]='y'; c[8]='z'; }
        }

        // Закончили с таблицами.

        // Обратите внимание на замечательный код, идущий ниже. В этих скупых
        // строчках уместился весь код, отвечающий за ввод.

        if ((keyCode>=49)&&(keyCode<=57))
        {
            if (edit)
            {
                int i = 0;
                int f = -1;
                int flag = 0;
                for (i=0; i<c.length; i++)
                {
                  if (ts==c[i]) { flag=1; f=i; }
                }
                if (flag==1)
                {
                    f = f+1;
                    if (f>=c.length) { f=0; }
                    ts = c[f];
                }
                else
                {
                    str = str+ts;
                    ts = c[0];
                }
            }
            else
            {
                str = str+ts;
                ts = c[0];
                edit = true;
            }
        }
    }

}