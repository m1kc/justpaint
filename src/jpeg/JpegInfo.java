package jpeg;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) deadcode space 
// Source File Name:   JpegEncoder.java

import javax.microedition.lcdui.Image;

class JpegInfo
{

    public void setComment(String comment)
    {
        Comment.concat(comment);
    }

    public String getComment()
    {
        return Comment;
    }

    private void getYCCArray()
    {
        int values[] = new int[imageWidth * imageHeight];
        imageobj.getRGB(values, 0, imageWidth, 0, 0, imageWidth, imageHeight);
        MaxHsampFactor = 1;
        MaxVsampFactor = 1;
        for (int y = 0; y < NumberOfComponents; y++)
        {
            MaxHsampFactor = Math.max(MaxHsampFactor, HsampFactor[y]);
            MaxVsampFactor = Math.max(MaxVsampFactor, VsampFactor[y]);
        }

        for (int y = 0; y < NumberOfComponents; y++)
        {
            compWidth[y] = ((imageWidth % 8 == 0 ? imageWidth : (int)Math.ceil((double)imageWidth / 8D) * 8) / MaxHsampFactor) * HsampFactor[y];
            if (compWidth[y] != (imageWidth / MaxHsampFactor) * HsampFactor[y])
                lastColumnIsDummy[y] = true;
            BlockWidth[y] = (int)Math.ceil((double)compWidth[y] / 8D);
            compHeight[y] = ((imageHeight % 8 == 0 ? imageHeight : (int)Math.ceil((double)imageHeight / 8D) * 8) / MaxVsampFactor) * VsampFactor[y];
            if (compHeight[y] != (imageHeight / MaxVsampFactor) * VsampFactor[y])
                lastRowIsDummy[y] = true;
            BlockHeight[y] = (int)Math.ceil((double)compHeight[y] / 8D);
        }

        float Y[][] = new float[compHeight[0]][compWidth[0]];
        float Cr1[][] = new float[compHeight[0]][compWidth[0]];
        float Cb1[][] = new float[compHeight[0]][compWidth[0]];
        float Cb2[][] = new float[compHeight[1]][compWidth[1]];
        float Cr2[][] = new float[compHeight[2]][compWidth[2]];
        int index = 0;
        for (int y = 0; y < imageHeight; y++)
        {
            for (int x = 0; x < imageWidth; x++)
            {
                int r = values[index] >> 16 & 0xff;
                int g = values[index] >> 8 & 0xff;
                int b = values[index] & 0xff;
                Y[y][x] = (float)(0.29899999999999999D * (double)(float)r + 0.58699999999999997D * (double)(float)g + 0.114D * (double)(float)b);
                Cb1[y][x] = 128F + (float)((-0.16874D * (double)(float)r - 0.33126D * (double)(float)g) + 0.5D * (double)(float)b);
                Cr1[y][x] = 128F + (float)(0.5D * (double)(float)r - 0.41869000000000001D * (double)(float)g - 0.081309999999999993D * (double)(float)b);
                index++;
            }

        }

        Components[0] = Y;
        Components[1] = Cb1;
        Components[2] = Cr1;
    }

    float[][] DownSample(float C[][], int comp)
    {
        int inrow = 0;
        int incol = 0;
        float output[][] = new float[compHeight[comp]][compWidth[comp]];
        for (int outrow = 0; outrow < compHeight[comp]; outrow++)
        {
            int bias = 1;
            for (int outcol = 0; outcol < compWidth[comp]; outcol++)
            {
                output[outrow][outcol] = (C[inrow][incol++] + C[inrow++][incol--] + C[inrow][incol++] + C[inrow--][incol++] + (float)bias) / 4F;
                bias ^= 3;
            }

            inrow += 2;
            incol = 0;
        }

        return output;
    }

    String Comment;
    public Image imageobj;
    public int imageHeight;
    public int imageWidth;
    public int BlockWidth[];
    public int BlockHeight[];
    public int Precision;
    public int NumberOfComponents;
    public Object Components[];
    public int CompID[] = {
        1, 2, 3
    };
    public int HsampFactor[] = {
        1, 1, 1
    };
    public int VsampFactor[] = {
        1, 1, 1
    };
    public int QtableNumber[] = {
        0, 1, 1
    };
    public int DCtableNumber[] = {
        0, 1, 1
    };
    public int ACtableNumber[] = {
        0, 1, 1
    };
    public boolean lastColumnIsDummy[] = {
        false, false, false
    };
    public boolean lastRowIsDummy[] = {
        false, false, false
    };
    public int Ss;
    public int Se;
    public int Ah;
    public int Al;
    public int compWidth[];
    public int compHeight[];
    public int MaxHsampFactor;
    public int MaxVsampFactor;

    public JpegInfo(Image image)
    {
        Precision = 8;
        NumberOfComponents = 3;
        Ss = 0;
        Se = 63;
        Ah = 0;
        Al = 0;
        Components = new Object[NumberOfComponents];
        compWidth = new int[NumberOfComponents];
        compHeight = new int[NumberOfComponents];
        BlockWidth = new int[NumberOfComponents];
        BlockHeight = new int[NumberOfComponents];
        imageobj = image;
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
        Comment = "JPEG Encoder Copyright 1998, James R. Weeks and BioElectroMech.  ";
        getYCCArray();
    }
}
