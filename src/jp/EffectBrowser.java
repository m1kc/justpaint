/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import javax.microedition.lcdui.*;

/**
 * @author Makc
 */
public class EffectBrowser extends Canvas
{

    Image preview;
    int e = 0;
    String[] eff;

    int rr=0;
    int rg=0;
    int rb=0;

    int sat=0;

    int alpha = 0;

    int opacity = 100;

    int[][] blur = new int[3][3];
    int[][] emboss = new int[3][3];
    int[][] average = new int[3][3];

    /**
     * constructor
     */
    public EffectBrowser() {
        //try {
	    // Set up this canvas to listen to command events
	    //setCommandListener(this);
	    // Add the Exit command
	    //addCommand(new Command("Exit", Command.EXIT, 1));
        //} catch(Exception e) {
        //    e.printStackTrace();
        //}

        setFullScreenMode(true);

        eff = new String[19];
        eff[0] = l10n.LocalizationSupport.getMessage("GRAYSCALE");
        eff[1] = l10n.LocalizationSupport.getMessage("NEGATIVE");
        eff[2] = l10n.LocalizationSupport.getMessage("DARKEN");
        eff[3] = l10n.LocalizationSupport.getMessage("LIGHTEN");
        eff[4] = l10n.LocalizationSupport.getMessage("SEPIA");
        eff[5] = l10n.LocalizationSupport.getMessage("BLUR");
        eff[6] = l10n.LocalizationSupport.getMessage("CORRUPTION");
        eff[7] = l10n.LocalizationSupport.getMessage("BLACK_WHITE");
        eff[8] = l10n.LocalizationSupport.getMessage("ADJUST_RED");
        eff[9] = l10n.LocalizationSupport.getMessage("ADJUST_GREEN");
        eff[10] = l10n.LocalizationSupport.getMessage("ADJUST_BLUE");
        eff[11] = l10n.LocalizationSupport.getMessage("SATURATION");
        eff[12] = l10n.LocalizationSupport.getMessage("TURN");
        eff[13] = l10n.LocalizationSupport.getMessage("SUN");
        eff[14] = l10n.LocalizationSupport.getMessage("DAMAGED_PAPER");
        eff[15] = l10n.LocalizationSupport.getMessage("MATRIX_BLUR");
        eff[16] = l10n.LocalizationSupport.getMessage("EMBOSS");
        eff[17] = l10n.LocalizationSupport.getMessage("AVERAGE");
        eff[18] = l10n.LocalizationSupport.getMessage("TRANSPARENCY");

        blur[0][0] = 1;
        blur[1][0] = 2;
        blur[2][0] = 1;

        blur[0][1] = 2;
        blur[1][1] = 4;
        blur[2][1] = 2;

        blur[0][2] = 1;
        blur[1][2] = 2;
        blur[2][2] = 1;

        emboss[0][0] = -1;
        emboss[1][0] = 0;
        emboss[2][0] = 0;

        emboss[0][1] = 0;
        emboss[1][1] = 0;
        emboss[2][1] = 0;

        emboss[0][2] = 0;
        emboss[1][2] = 0;
        emboss[2][2] = -1;

        average[0][0] = 1;
        average[1][0] = 1;
        average[2][0] = 1;
        average[0][1] = 1;
        average[1][1] = 1;
        average[2][1] = 1;
        average[0][2] = 1;
        average[1][2] = 1;
        average[2][2] = 1;
    } 

    public void prepare()
    {
        preview = JustPaint.c.getImage();
        e = -1;

        rr=rg=rb=0;

        sat=255;

        alpha = 0;

        opacity = 100;
    }

    private void append()
    {
        if (e==-1) { preview = JustPaint.c.getImage(); }
        if ((e>=0)&&(e<=7)) { preview = Lib_effects.effect(JustPaint.c.getImage(), e); }
        if (e==8) { preview = Lib_effects.change(JustPaint.c.getImage(), 1, rr); }
        if (e==9) { preview = Lib_effects.change(JustPaint.c.getImage(), 2, rg); }
        if (e==10) { preview = Lib_effects.change(JustPaint.c.getImage(), 3, rb); }
        if (e==11) { preview = Lib_effects.saturation(JustPaint.c.getImage(), sat); }
        if (e==12) { preview = Lib_turn.rotate(JustPaint.c.getImage(), alpha); }
        if (e==13) { preview = Effects.sun(JustPaint.c.getImage()); }
        if (e==14) { preview = Effects.paper(JustPaint.c.getImage()); }
        if (e==15) { preview = Effects.matrix(JustPaint.c.getImage(),blur,16,0,true); }
        if (e==16) { preview = Effects.matrix(JustPaint.c.getImage(),emboss,1,128,true); }
        if (e==17) { preview = Effects.matrix(JustPaint.c.getImage(),average,9,0,true); }
        if (e==18) { preview = Tools.modifyAlpha(JustPaint.c.getImage(), opacity); }
    }

    private void appendEffect()
    {
        if ((e>=0)&&(e<=7)) { JustPaint.c.setImage(Lib_effects.effect(JustPaint.c.getImage(), e)); }
        if (e==8) { JustPaint.c.setImage(Lib_effects.change(JustPaint.c.getImage(), 1, rr)); }
        if (e==9) { JustPaint.c.setImage(Lib_effects.change(JustPaint.c.getImage(), 2, rg)); }
        if (e==10) { JustPaint.c.setImage(Lib_effects.change(JustPaint.c.getImage(), 3, rb)); }
        if (e==11) { JustPaint.c.setImage(Lib_effects.saturation(JustPaint.c.getImage(), sat)); }
        if (e==12) { JustPaint.c.setImage(Lib_turn.rotate(JustPaint.c.getImage(), alpha)); JustPaint.c.Lock = true; }
        if (e==13) { JustPaint.c.setImage(Effects.sun(JustPaint.c.getImage())); }
        if (e==14) { JustPaint.c.setImage(Effects.paper(JustPaint.c.getImage())); JustPaint.c.Lock = true; }
        if (e==15) { JustPaint.c.setImage(Effects.matrix(JustPaint.c.getImage(), blur, 16, 0, true)); }
        if (e==16) { JustPaint.c.setImage(Effects.matrix(JustPaint.c.getImage(), emboss, 1, 128, true)); }
        if (e==17) { JustPaint.c.setImage(Effects.matrix(JustPaint.c.getImage(), average, 9, 0, true)); }
        if (e==18) { JustPaint.c.setImage(Tools.modifyAlpha(JustPaint.c.getImage(), opacity)); }

        JustPaint.display.setCurrent(JustPaint.c);
    }

    private void checkRanges()
    {
        if (rr > 255) rr = 255;
        if (rr < -255) rr = -255;
        if (rg > 255) rg = 255;
        if (rg < -255) rg = -255;
        if (rb > 255) rb = 255;
        if (rb < -255) rb = -255;

        if (sat < 0) sat = 0;
        //if (sat > 255) sat = 255;

        if (alpha < 0) alpha = 359;
        if (alpha > 360) alpha = 1;

        if (opacity < 0) opacity = 0;
        if (opacity > 100) opacity = 100;
    }

    private String gen()
    {
        String c = "";
        if (e==8)
        {
            if (rr<0) c=""+rr; else c="+"+rr;
        }
        if (e==9)
        {
            if (rg<0) c=""+rg; else c="+"+rg;
        }
        if (e==10)
        {
            if (rb<0) c=""+rb; else c="+"+rb;
        }

        if (e==11)
        {
            c=""+sat;
        }

        if (e==12)
        {
            c=""+alpha+"'";
        }

        if (e==18)
        {
            c=""+opacity+"%";
        }

        return c;
    }

    /**
     * paint
     */
    public void paint(Graphics g)
    {
        g.setColor(0,0,0);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setClip(0, 30, getWidth(), getHeight()-60);
        g.drawImage(preview, getWidth()/2-preview.getWidth()/2, getHeight()/2-preview.getHeight()/2, Graphics.LEFT | Graphics.TOP);
        g.setClip(0, 0, getWidth(), getHeight());
        g.setColor(255,255,255);
        g.drawString("Эффекты", getWidth()/2, 15-MainCanvas.nameFont.getHeight()/2, Graphics.HCENTER | Graphics.TOP);
        if (e != -1) g.drawString(eff[e], getWidth()/2, getHeight()-30/4*3-MainCanvas.nameFont.getHeight()/2, Graphics.HCENTER | Graphics.TOP);
        g.drawString(gen(), getWidth()/2, getHeight()-30/4-MainCanvas.nameFont.getHeight()/2, Graphics.HCENTER | Graphics.TOP);

        repaint();
    }
    
    /**
     * Called when a key is pressed.
     */
    protected  void keyPressed(int keyCode)
    {
        if (keyCode==-26 || keyCode==-11)
        {
            JustPaint.display.setCurrent(JustPaint.c);
        }

        if (keyCode==-4) e++;
        if (keyCode==-3) e--;
        if (keyCode==KEY_NUM4) e--;
        if (keyCode==KEY_NUM6) e++;
        if (e<-1) e=eff.length-1;
        if (e>=eff.length) e=-1;

        if (keyCode==KEY_NUM2 || keyCode==-1)
        {
            if (e==8) rr++;
            if (e==9) rg++;
            if (e==10) rb++;

            if (e==11) sat++;

            if (e==12) alpha--;

            if (e==18) opacity++;
        }

        if (keyCode==KEY_NUM8 || keyCode==-2)
        {
            if (e==8) rr--;
            if (e==9) rg--;
            if (e==10) rb--;

            if (e==11) sat--;

            if (e==12) alpha++;

            if (e==18) opacity--;
        }

        checkRanges();

        if (keyCode==-5) appendEffect();
        if (keyCode==KEY_NUM5) appendEffect();

        append();
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
        if (keyCode==KEY_NUM2 || keyCode==-1)
        {
            if (e==8) rr+=10;
            if (e==9) rg+=10;
            if (e==10) rb+=10;

            if (e==11) sat+=10;

            if (e==12) alpha-=10;

            if (e==18) opacity+=10;
        }

        if (keyCode==KEY_NUM8 || keyCode==-2)
        {
            if (e==8) rr-=10;
            if (e==9) rg-=10;
            if (e==10) rb-=10;

            if (e==11) sat-=10;

            if (e==12) alpha+=10;

            if (e==18) opacity-=10;
        }

        checkRanges();

        append();
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