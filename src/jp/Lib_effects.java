package jp;

import java.util.*;
import javax.microedition.lcdui.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author aNNiMON
 */
public class Lib_effects {
    
    private final static int CALPHA = 0,  CRED = 1,  CGREEN = 2,  CBLUE = 3;
    private static int[] cshift = {24, 16, 8, 0};
    private static int[] cmask = {~((1 << 24) - 1), (1 << 24) - (1 << 16), (1 << 16) - 256, 255};
    
    public static int red(int v) {return (v&cmask[CRED])>>>cshift[CRED];}
    public static int green(int v) {return (v&cmask[CGREEN])>>>cshift[CGREEN];}
    public static int blue(int v) {return (v&cmask[CBLUE])>>>cshift[CBLUE];}
    public static int alpha(int v) {return (v&cmask[CALPHA])>>>cshift[CALPHA];}
    
    public static int avg(int v, int r, int g, int b, int s) {
        return (r * red(v) + g * green(v) + b * blue(v)) / s;
    }
    
    public static int avg(int v) {
        return avg(v, 299, 587, 114, 1000);
    }
    
    public static Image band(Image img, int[] colors) {
        int l = colors.length;
        int[] data = new int[img.getWidth() * img.getHeight()];
        img.getRGB(data, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());
        for (int i = 0; i < data.length; i++) {
            data[i] = colors[(avg(data[i]) * l) / 256];
        }
        return Image.createRGBImage(data, img.getWidth(), img.getHeight(), true);
    }
    
    public static int[][] Array2d(int[] d1, int w, int h) {
        int[][] d2 = new int[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                d2[i][j]=d1[i*h+j];
            }
        }
        return d2;
    } 
    
    public static int[] Array1d(int[][] d1, int w, int h) {
        int[] d2 = new int[h*w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                d2[i*h+j]=d1[i][j];
            }
        }
        return d2;
    } 
    
    

    
    public static Image fill(Image img, int x, int y, int r, int g, int b) {
        int[] pixel = new int[img.getWidth() * img.getHeight()];
        img.getRGB(pixel, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());
        int[][] ar2 = new int[img.getHeight()][img.getWidth()]; // С„СЂР°РєС‚Р°Р»С‹ РµС‰Рµ РґРѕР±Р°РІРёС‚СЊ
        return Image.createRGBImage(pixel, img.getWidth(), img.getHeight(), true);
    }
    
    public static int color(int alpha, int r, int g, int b) {
        int result = (alpha << 24) + (r << 16) + (g << 8) + b;
        return result;
    }
 
    public static Image effect(Image img, int mode) {
        int[] pixel = new int[img.getWidth() * img.getHeight()];
        img.getRGB(pixel, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());
        if (mode == 5) { // РЎРіР»Р°Р¶РёРІР°РЅРёРµ
            int imwi = img.getWidth();
            for (int io = 1 + imwi; io < pixel.length - 1 - imwi; io++) {
                int qa = (pixel[io] >> 24) & 0xff;

                int qr00 = (pixel[io - 1 - imwi] >> 16) & 0xff;
                int qg00 = (pixel[io - 1 - imwi] >> 8) & 0xff;
                int qb00 = pixel[io - 1 - imwi] & 0xff;

                int qr01 = (pixel[io - imwi] >> 16) & 0xff;
                int qg01 = (pixel[io - imwi] >> 8) & 0xff;
                int qb01 = pixel[io - imwi] & 0xff;

                int qr02 = (pixel[io + 1 - imwi] >> 16) & 0xff;
                int qg02 = (pixel[io + 1 - imwi] >> 8) & 0xff;
                int qb02 = pixel[io + 1 - imwi] & 0xff;

                int qr10 = (pixel[io - 1] >> 16) & 0xff;
                int qg10 = (pixel[io - 1] >> 8) & 0xff;
                int qb10 = pixel[io - 1] & 0xff;

                int qr11 = (pixel[io] >> 16) & 0xff;
                int qg11 = (pixel[io] >> 8) & 0xff;
                int qb11 = pixel[io] & 0xff;

                int qr12 = (pixel[io + 1] >> 16) & 0xff;
                int qg12 = (pixel[io + 1] >> 8) & 0xff;
                int qb12 = pixel[io + 1] & 0xff;

                int qr20 = (pixel[io - 1 + imwi] >> 16) & 0xff;
                int qg20 = (pixel[io - 1 + imwi] >> 8) & 0xff;
                int qb20 = pixel[io - 1 + imwi] & 0xff;

                int qr21 = (pixel[io + imwi] >> 16) & 0xff;
                int qg21 = (pixel[io + imwi] >> 8) & 0xff;
                int qb21 = pixel[io + imwi] & 0xff;

                int qr22 = (pixel[io + 1 + imwi] >> 16) & 0xff;
                int qg22 = (pixel[io + 1 + imwi] >> 8) & 0xff;
                int qb22 = pixel[io + 1 + imwi] & 0xff;
                qr11 = (qr00 + qr01 + qr02 + qr10 + qr11 + qr12 + qr20 + qr21 + qr22) / 9;
                qg11 = (qg00 + qg01 + qg02 + qg10 + qg11 + qg12 + qg20 + qg21 + qg22) / 9;
                qb11 = (qb00 + qb01 + qb02 + qb10 + qb11 + qb12 + qb20 + qb21 + qb22) / 9;
                pixel[io] = (qa << 24) | (qr11 << 16) | (qg11 << 8) | qb11;
            }
            return Image.createRGBImage(pixel, img.getWidth(), img.getHeight(), true);
        }
        for (int io = 0; io < pixel.length; io++) {
            int qa = (pixel[io] >> 24) & 0xff;
            int qr = (pixel[io] >> 16) & 0xff;
            int qg = (pixel[io] >> 8) & 0xff;
            int qb = pixel[io] & 0xff;
            if (mode == 0) { // РѕР±РµСЃС†РІРµС‡
                int qs = qr + qg + qb;
                qr = qs / 3;
                qg = qs / 3;
                qb = qs / 3;
            }
            if (mode == 1) { // РЅРµРіР°С‚РёРІ
                qr = 255 - qr;
                qg = 255 - qg;
                qb = 255 - qb;
            }
            if (mode == 2) { // Р·Р°С‚РµРјРЅРµРЅРёРµ
                qr = qr - 20 < 0 ? 0 : qr - 20;
                qg = qg - 20 < 0 ? 0 : qg - 20;
                qb = qb - 20 < 0 ? 0 : qb - 20;
            }
            if (mode == 3) { // РѕСЃРІРµС‚Р»РµРЅРёРµ
                qr = qr + 20 > 255 ? 255 : qr + 20;
                qg = qg + 20 > 255 ? 255 : qg + 20;
                qb = qb + 20 > 255 ? 255 : qb + 20;
            }
            if (mode == 4) { // СЃРµРїРёСЏ
                int depth = 40;
                qb = (qr + qg + qb) / 3;
                qr = qb + depth * 2;
                qg = qb + depth;
                if (qr > 255) {
                    qr = 255;
                }
                if (qg > 255) {
                    qg = 255;
                }
            }
            if (mode == 6) { // РСЃРєР°Р¶РµРЅРёРµ
                Random rnd = new Random();
                if (((rnd.nextInt() >>> 1) % 10) < 4 && io > 0) {
                    pixel[io-1] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
                    qa = (pixel[io - 1] >> 24) & 0xff;
                    qr = (pixel[io - 1] >> 16) & 0xff;
                    qg = (pixel[io - 1] >> 8) & 0xff;
                    qb = pixel[io - 1] & 0xff;
                }
            }
            if (mode == 7) { // С‡/Р±
                if ((qr+qg+qb)/3 > 128) {
                    qr=qg=qb=255;
                }else {
                    qr=qg=qb=0;
                }
            }
            pixel[io] = (qa << 24) | (qr << 16) | (qg << 8) | qb;

        }
        return Image.createRGBImage(pixel, img.getWidth(), img.getHeight(), true);
    }

    public static Image change(Image img, int ch, int val) {
        int[] pixel = new int[img.getWidth() * img.getHeight()];
        img.getRGB(pixel, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());
        for (int io = 0; io < pixel.length; io++) {
            int qa = (pixel[io] >> 24) & 0xff;
            int qr = (pixel[io] >> 16) & 0xff;
            int qg = (pixel[io] >> 8) & 0xff;
            int qb = pixel[io] & 0xff;

            if (ch == 0) {  // alpha
                qa = qa + val;
                if (qa > 255) {
                    qa = 255;
                }
                if (qa < 0) {
                    qa = 0;
                }
            }

            if (ch == 1) {  // red
                qr = qr + val;
                if (qr > 255) {
                    qr = 255;
                }
                if (qr < 0) {
                    qr = 0;
                }
            }

            if (ch == 2) {  // green
                qg = qg + val;
                if (qg > 255) {
                    qg = 255;
                }
                if (qg < 0) {
                    qg = 0;
                }
            }

            if (ch == 3) {  // blue
                qb = qb + val;
                if (qb > 255) {
                    qb = 255;
                }
                if (qb < 0) {
                    qb = 0;
                }
            }

            pixel[io] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
        }
        return Image.createRGBImage(pixel, img.getWidth(), img.getHeight(), true);
    }

    public static int get(Image img, int x, int y, int ch) {
        int[] pixel = new int[img.getWidth() * img.getHeight()];
        img.getRGB(pixel, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        int qa = (pixel[y * img.getWidth() + x] >> 24) & 0xff;
        int qr = (pixel[y * img.getWidth() + x] >> 16) & 0xff;
        int qg = (pixel[y * img.getWidth() + x] >> 8) & 0xff;
        int qb = pixel[y * img.getWidth() + x] & 0xff;

        if (ch == 0) {  // alpha
            return qa;
        }

        if (ch == 1) {  // red
            return qr;
        }

        if (ch == 2) {  // green
            return qg;
        }

        if (ch == 3) {  // blue
            return qb;
        }
        return 0;
    }

    public static Image saturation(Image img, int ch) {
        int[] pixelArray = new int[img.getWidth() * img.getHeight()];
        int Amount = ch;
        img.getRGB(pixelArray, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());
        for (int io = 0; io < pixelArray.length; io++) {
            int qa = (pixelArray[io] >> 24) & 0xff;
            int qr = (pixelArray[io] >> 16) & 0xff;
            int qg = (pixelArray[io] >> 8) & 0xff;
            int qb = pixelArray[io] & 0xff;
            int gray = (qr + qg + qb) / 3;
            qr = (gray + (((qr - gray) * Amount) / 255));
            qg = (gray + (((qg - gray) * Amount) / 255));
            qb = (gray + (((qb - gray) * Amount) / 255));
            // modified
            if (qr>255) qr=255;
            if (qr<0) qr=0;
            if (qg>255) qg=255;
            if (qg<0) qg=0;
            if (qb>255) qb=255;
            if (qb<0) qb=0;
            // -->
            pixelArray[io] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
        }
        return Image.createRGBImage(pixelArray, img.getWidth(), img.getHeight(), true);
    }
    
    public static Image changecolor(Image img, int r1, int g1, int b1, int a2, int r2, int g2, int b2) {
        int[] pixelArray = new int[img.getWidth() * img.getHeight()];
        img.getRGB(pixelArray, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());
        for (int io = 0; io < pixelArray.length; io++) {
            int qa = (pixelArray[io] >> 24) & 0xff;
            int qr = (pixelArray[io] >> 16) & 0xff;
            int qg = (pixelArray[io] >> 8) & 0xff;
            int qb = pixelArray[io] & 0xff;
            if (qr==r1 && qg==g1 && qb==b1 && qa!=0) {
                qa=a2; qr=r2; qg=g2; qb=b2;
            }
            pixelArray[io] = (qa << 24) | (qr << 16) | (qg << 8) | qb;
        }
        return Image.createRGBImage(pixelArray, img.getWidth(), img.getHeight(), true);
    }
    
    
    
    public static Image brightness(Image img, int difa, int difr, int difg, int difb) {
        return linear(img, 1, difr, 1, difg, 1, difb, 1, difa);
    }
    
    public static Image contrast(Image img, double sr,double sg,double sb,double sa) {
        return linear(img, sr,128-128*sr,sg,128-128*sg,sb,128-128*sb,sa,128-128*sa);
 }
    
    public static Image gamma(Image img, double ga, double gr, double gg, double gb) {
        int v, r, g, b, a;
        gr=1/gr;gg=1/gg;gb=1/gb;ga=1/ga;
        int[] pixel = new int[img.getWidth() * img.getHeight()];
        img.getRGB(pixel, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());
        for (int i = 0; i < pixel.length; i++) {
            v = pixel[i];
            r = range(0, 256, 255 * pow((red(v) / 255.0), gr));
            g = range(0, 256, 255 * pow((green(v) / 255.0), gg));
            b = range(0, 256, 255 * pow((blue(v) / 255.0), gb));
            a = range(0, 256, 255 * pow((alpha(v) / 255.0), ga));
            pixel[i] = color(a, r, g, b);
        }
        return Image.createRGBImage(pixel, img.getWidth(), img.getHeight(), true);
    }
    
    private static Image linear(Image img, double sr, double dr, double sg, double dg,
            double sb, double db, double sa, double da) {
        int[] pixel = new int[img.getWidth() * img.getHeight()];
        img.getRGB(pixel, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());
        int v, r, g, b, a;
        for (int i = 0; i < pixel.length; i++) {
            v = pixel[i];
            r = range(0, 256, red(v) * sr + dr);
            g = range(0, 256, green(v) * sg + dg);
            b = range(0, 256, blue(v) * sb + db);
            a = range(0, 256, alpha(v) * sa + da);
            pixel[i] = color(a, r, g, b);
        }
        return Image.createRGBImage(pixel, img.getWidth(), img.getHeight(), true);
    }
    
    private static int range(int min, int max, double dv) {
        int v = (int) dv;
        if (v < min) {
            return min;
        }
        return (v >= max) ? max - 1 : (int) v;
    }
    
    /** Square root from 3 */
  final static public double SQRT3 = 1.732050807568877294;
  /** Log10 constant */
  final static public double LOG10 = 2.302585092994045684;
  /** ln(0.5) constant */
  final static public double LOGdiv2 = -0.6931471805599453094;
  //
  static public double acos(double x)
  {
    double f=asin(x);
    if(f==Double.NaN)
      return f;
    return Math.PI/2-f;
  }

  static public double asin(double x)
  {
    if( x<-1. || x>1. ) return Double.NaN;
    if( x==-1. ) return -Math.PI/2;
    if( x==1 ) return Math.PI/2;
    return atan(x/Math.sqrt(1-x*x));
  }

  static public double atan(double x)
  {
      boolean signChange=false;
      boolean Invert=false;
      int sp=0;
      double x2, a;
      // check up the sign change
      if(x<0.)
      {
          x=-x;
          signChange=true;
      }
      // check up the invertation
      if(x>1.)
      {
          x=1/x;
          Invert=true;
      }
      // process shrinking the domain until x<PI/12
      while(x>Math.PI/12)
      {
          sp++;
          a=x+SQRT3;
          a=1/a;
          x=x*SQRT3;
          x=x-1;
          x=x*a;
      }
      // calculation core
      x2=x*x;
      a=x2+1.4087812;
      a=0.55913709/a;
      a=a+0.60310579;
      a=a-(x2*0.05160454);
      a=a*x;
      // process until sp=0
      while(sp>0)
      {
          a=a+Math.PI/6;
          sp--;
      }
      // invertation took place
      if(Invert) a=Math.PI/2-a;
      // sign change took place
      if(signChange) a=-a;
      //
      return a;
  }

  static public double atan2(double y, double x)
  {
    // if x=y=0
    if(y==0. && x==0.)
      return 0.;
    // if x>0 atan(y/x)
    if(x>0.)
      return atan(y/x);
    // if x<0 sign(y)*(pi - atan(|y/x|))
    if(x<0.)
    {
      if(y<0.)
        return -(Math.PI-atan(y/x));
      else
        return Math.PI-atan(-y/x);
    }
    // if x=0 y!=0 sign(y)*pi/2
    if(y<0.)
      return -Math.PI/2.;
    else
      return Math.PI/2.;
  }

  static public double exp(double x)
  {
    if(x==0.)
      return 1.;
    //
    double f=1;
    long d=1;
    double k;
    boolean isless=(x<0.);
    if(isless)
      x=-x;
    k=x/d;
    //
    for(long i=2; i<50; i++)
    {
      f=f+k;
      k=k*x/i;
    }
    //
    if(isless)
      return 1/f;
    else
      return f;
  }

  static private double _log(double x)
  {
    if(!(x>0.))
      return Double.NaN;
    //
    double f=0.0;
    //
    int appendix=0;
    while(x>0.0 && x<=1.0)
    {
      x*=2.0;
      appendix++;
    }
    //
    x/=2.0;
    appendix--;
    //
    double y1=x-1.;
    double y2=x+1.;
    double y=y1/y2;
    //
    double k=y;
    y2=k*y;
    //
    for(long i=1; i<50; i+=2)
    {
      f+=k/i;
      k*=y2;
    }
    //
    f*=2.0;
    for(int i=0; i<appendix; i++)
      f+=LOGdiv2;
    //
    return f;
  }

  static public double log(double x)
   {
     if(!(x>0.))
       return Double.NaN;
     //
     if(x==1.0)
       return 0.0;
     // Argument of _log must be (0; 1]
     if (x>1.)
     {
       x=1/x;
       return -_log(x);
     };
     //
     return _log(x);
   }

   static public double log10(double x)
   {
    return log(x)/LOG10;
   }


  static public double pow(double x, double y)
  {
    if(y==0.)
      return 1.;
    if(y==1.)
      return x;
    if(x==0.)
      return 0.;
    if(x==1.)
      return 1.;
    //
    long l=(long)Math.floor(y);
    boolean integerValue=(y==(double)l);
    //
    if(integerValue)
    {
      boolean neg=false;
      if(y<0.)
        neg=true;
      //
      double result=x;
      for(long i=1; i<(neg?-l:l); i++)
        result=result*x;
      //
      if(neg)
        return 1./result;
      else
        return result;
    }
    else
    {
      if(x>0.)
        return exp(y*log(x));
      else
        return Double.NaN;
    }
  }


  private static double[] a(double ad[], double d1, double d2)
  {
        double d3 = ad[0];
        double d4 = ad[1];
        ad[0] = (d3 * d3 - d4 * d4) + d1;
        ad[1] = 2D * d3 * d4 + d2;
        return ad;
  }

  private static double a(double ad[])
  {
        return ad[0] * ad[0] + ad[1] * ad[1];
  }

  public static Image fractalmandelbrot(int i, int j, double d1, double d2, double d3, double d4)
  {
        int ai[] = new int[i * j];
        for (int k = 0; k < ai.length; k++)
        {
            ai[k] = 0;
        }

        double ad[] = new double[2];
        for (int l = 0; l < j; l++)
        {
            for (int i1 = 0; i1 < i; i1++)
            {
                double d5 = d1 + ((double)i1 * d3) / (double)i;
                double d6 = d2 + ((double)l * d4) / (double)j;
                ad[0] = ad[1] = 0.0D;
                int j1 = 0;
                do
                {
                    if (j1 >= 255)
                    {
                        break;
                    }
                    a(ad, d5, d6);
                    if (a(ad) > 4D)
                    {
                        break;
                    }
                    j1++;
                } while (true);
                ai[l * i + i1] = 0xff000000 + (j1 << 16) + (j1 << 8) + j1;
            }

        }

        return Image.createRGBImage(ai, i, j, true);
    }
}

