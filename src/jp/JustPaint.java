/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jp;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 * @author Makc
 */
public class JustPaint extends MIDlet
{

    public static Display display;

    public static StartScreen ss;// = new StartScreen();
    public static ImageDispatcher id;// = new ImageDispatcher();
    public static QDispatcher qd;// = new QDispatcher();
    public static MainCanvas c;// = new MainCanvas();
    public static ColorSelecter cs;// = new ColorSelecter();
    public static ColorSelecterPalette csp;// = new ColorSelecterPalette();
    public static SaveDialog save;// = new SaveDialog();
    public static TextInput ti;// = new TextInput();
    public static CreatingImage ci;// = new CreatingImage();
    public static Information inf;// = new Information();
    public static FileManager fm;// = new FileManager();
    public static EffectBrowser ef;// = new EffectBrowser();
    public static Preview pr;// = new Preview();
    public static HPreview hp;// = new HPreview();
    public static FiltersScreen fl;// = new FiltersScreen();
    public static NewFilter nf;// = new NewFilter();
    public static MatrixEditor me;// = new MatrixEditor();
    public static FontSelection ff;// = new FontSelection();
    public static HeapIndicator hi;// = new HeapIndicator();
    public static MainMenu mm;// = new MainMenu();
    public static MyToolsScreen mts;// = new MyToolsScreen();
    public static NewMyTool nmt;// = new NewMyTool();
    public static Checklist cl;// = new Checklist();

    private boolean midletPaused = false;

    public static int r = 0;
    public static int g = 0;
    public static int b = 0;
    public static int r2 = 255;
    public static int g2 = 255;
    public static int b2 = 255;

    public static HeapCleaner hc;// = new HeapCleaner();

    public static JustPaint midlet;

    public static Form debugForm;

    /**
     * The JustPaint constructor.
     */
    public JustPaint()
    {

    }

    /**
     * Exits MIDlet.
     */
    public void exitMIDlet()
    {
        display.setCurrent(null);
        destroyApp(true);
        notifyDestroyed();
    }

    /**
     * Called when MIDlet is started.
     * Checks whether the MIDlet have been already started and initialize/starts or resumes the MIDlet.
     */
    public void startApp()
    {
        int i=0;
        try
        {
            // инициализация
            midletPaused = false; i=101;
            midlet = this; i=102;
            display = Display.getDisplay(this); i=103;
            l10n.LocalizationSupport.initLocalizationSupport(); i=104;
            // конструкторы
            ss = new StartScreen(); i=201;
            id = new ImageDispatcher(); i=202;
            qd = new QDispatcher(); i=203;
            c = new MainCanvas(); i=204;
            cs = new ColorSelecter(); i=205;
            csp = new ColorSelecterPalette(); i=206;
            save = new SaveDialog(); i=207;
            ti = new TextInput(); i=208;
            ci = new CreatingImage(); i=209;
            inf = new Information(); i=210;
            fm = new FileManager(); i=211;
            ef = new EffectBrowser(); i=212;
            pr = new Preview(); i=213;
            hp = new HPreview(); i=214;
            fl = new FiltersScreen(); i=215;
            nf = new NewFilter(); i=216;
            me = new MatrixEditor(); i=217;
            ff = new FontSelection(); i=218;
            hi = new HeapIndicator(); i=219;
            mm = new MainMenu(); i=220;
            mts = new MyToolsScreen(); i=221;
            nmt = new NewMyTool(); i=222;
            cl = new Checklist(); i=223;
            // старт
            display.setCurrent(ss); i=301;
            hc = new HeapCleaner(); i=302;
            hc.start(); i=303;
            ci.init(); i=304;
            save.init(); i=305;
            cl.init(c.brs); i=306;
            RMSWizard.readOptions(); i=307;
        }
        catch (Throwable ex)
        {
            showDebugInfo(this,ex,"startApp()","Строка "+i);
        }
    }

    /**
     * Called when MIDlet is paused.
     */
    public void pauseApp() {
        midletPaused = true;
    }

    /**
     * Called to signal the MIDlet to terminate.
     * @param unconditional if true, then the MIDlet has to be unconditionally terminated and all resources has to be released.
     */
    public void destroyApp(boolean unconditional) {
    }

    public static void showDebugInfo(Object sender, Throwable ex, String method, String comment)
    {
        debugForm = new Form("Debug Form");
        if (sender!=null) debugForm.append("Получено из класса: "+sender.getClass().getName());
        if (ex!=null) debugForm.append("Исключение: "+ex.toString());
        if (method!=null) debugForm.append("Метод: "+method);
        if (comment!=null) debugForm.append("Дополнительная информация: "+comment);
        display.setCurrent(debugForm);
    }

}