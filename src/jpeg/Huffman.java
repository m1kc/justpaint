package jpeg;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) deadcode space 
// Source File Name:   JpegEncoder.java

import java.io.*;
import java.util.Vector;

class Huffman
{

    public void HuffmanBlockEncoder(DataOutputStream outStream, int zigzag[], int prec, int DCcode, int ACcode)
    {
        NumOfDCTables = 2;
        NumOfACTables = 2;
        int temp2;
        int temp = temp2 = zigzag[0] - prec;
        if (temp < 0)
        {
            temp = -temp;
            temp2--;
        }
        int nbits = 0;
        for (; temp != 0; temp >>= 1)
            nbits++;

        bufferIt(outStream, ((int[][])(int[][])DC_matrix[DCcode])[nbits][0], ((int[][])(int[][])DC_matrix[DCcode])[nbits][1]);
        if (nbits != 0)
            bufferIt(outStream, temp2, nbits);
        int r = 0;
        for (int k = 1; k < 64; k++)
        {
            if ((temp = zigzag[JpegEncoder.jpegNaturalOrder[k]]) == 0)
            {
                r++;
                continue;
            }
            for (; r > 15; r -= 16)
                bufferIt(outStream, ((int[][])(int[][])AC_matrix[ACcode])[240][0], ((int[][])(int[][])AC_matrix[ACcode])[240][1]);

            temp2 = temp;
            if (temp < 0)
            {
                temp = -temp;
                temp2--;
            }
            for (nbits = 1; (temp >>= 1) != 0; nbits++);
            int i = (r << 4) + nbits;
            bufferIt(outStream, ((int[][])(int[][])AC_matrix[ACcode])[i][0], ((int[][])(int[][])AC_matrix[ACcode])[i][1]);
            bufferIt(outStream, temp2, nbits);
            r = 0;
        }

        if (r > 0)
            bufferIt(outStream, ((int[][])(int[][])AC_matrix[ACcode])[0][0], ((int[][])(int[][])AC_matrix[ACcode])[0][1]);
    }

    void bufferIt(DataOutputStream outStream, int code, int size)
    {
        int PutBuffer = code;
        int PutBits = bufferPutBits;
        PutBuffer &= (1 << size) - 1;
        PutBits += size;
        PutBuffer <<= 24 - PutBits;
        PutBuffer |= bufferPutBuffer;
        for (; PutBits >= 8; PutBits -= 8)
        {
            int c = PutBuffer >> 16 & 0xff;
            try
            {
                outStream.write(c);
            }
            catch (IOException e)
            {
                System.out.println("IO Error: " + e.getMessage());
            }
            if (c == 255)
                try
                {
                    outStream.write(0);
                }
                catch (IOException e)
                {
                    System.out.println("IO Error: " + e.getMessage());
                }
            PutBuffer <<= 8;
        }

        bufferPutBuffer = PutBuffer;
        bufferPutBits = PutBits;
    }

    void flushBuffer(DataOutputStream outStream)
    {
        int PutBuffer = bufferPutBuffer;
        int PutBits;
        for (PutBits = bufferPutBits; PutBits >= 8; PutBits -= 8)
        {
            int c = PutBuffer >> 16 & 0xff;
            try
            {
                outStream.write(c);
            }
            catch (IOException e)
            {
                System.out.println("IO Error: " + e.getMessage());
            }
            if (c == 255)
                try
                {
                    outStream.write(0);
                }
                catch (IOException e)
                {
                    System.out.println("IO Error: " + e.getMessage());
                }
            PutBuffer <<= 8;
        }

        if (PutBits > 0)
        {
            int c = PutBuffer >> 16 & 0xff;
            try
            {
                outStream.write(c);
            }
            catch (IOException e)
            {
                System.out.println("IO Error: " + e.getMessage());
            }
        }
    }

    public void initHuf()
    {
        DC_matrix0 = new int[12][2];
        DC_matrix1 = new int[12][2];
        AC_matrix0 = new int[255][2];
        AC_matrix1 = new int[255][2];
        DC_matrix = new Object[2];
        AC_matrix = new Object[2];
        int huffsize[] = new int[257];
        int huffcode[] = new int[257];
        int p = 0;
        for (int l = 1; l <= 16; l++)
        {
            for (int i = 1; i <= bitsDCchrominance[l]; i++)
                huffsize[p++] = l;

        }

        huffsize[p] = 0;
        int lastp = p;
        int code = 0;
        int si = huffsize[0];
        for (p = 0; huffsize[p] != 0;)
        {
            while (huffsize[p] == si) 
            {
                huffcode[p++] = code;
                code++;
            }
            code <<= 1;
            si++;
        }

        for (p = 0; p < lastp; p++)
        {
            DC_matrix1[valDCchrominance[p]][0] = huffcode[p];
            DC_matrix1[valDCchrominance[p]][1] = huffsize[p];
        }

        p = 0;
        for (int l = 1; l <= 16; l++)
        {
            for (int i = 1; i <= bitsACchrominance[l]; i++)
                huffsize[p++] = l;

        }

        huffsize[p] = 0;
        lastp = p;
        code = 0;
        si = huffsize[0];
        for (p = 0; huffsize[p] != 0;)
        {
            while (huffsize[p] == si) 
            {
                huffcode[p++] = code;
                code++;
            }
            code <<= 1;
            si++;
        }

        for (p = 0; p < lastp; p++)
        {
            AC_matrix1[valACchrominance[p]][0] = huffcode[p];
            AC_matrix1[valACchrominance[p]][1] = huffsize[p];
        }

        p = 0;
        for (int l = 1; l <= 16; l++)
        {
            for (int i = 1; i <= bitsDCluminance[l]; i++)
                huffsize[p++] = l;

        }

        huffsize[p] = 0;
        lastp = p;
        code = 0;
        si = huffsize[0];
        for (p = 0; huffsize[p] != 0;)
        {
            while (huffsize[p] == si) 
            {
                huffcode[p++] = code;
                code++;
            }
            code <<= 1;
            si++;
        }

        for (p = 0; p < lastp; p++)
        {
            DC_matrix0[valDCluminance[p]][0] = huffcode[p];
            DC_matrix0[valDCluminance[p]][1] = huffsize[p];
        }

        p = 0;
        for (int l = 1; l <= 16; l++)
        {
            for (int i = 1; i <= bitsACluminance[l]; i++)
                huffsize[p++] = l;

        }

        huffsize[p] = 0;
        lastp = p;
        code = 0;
        si = huffsize[0];
        for (p = 0; huffsize[p] != 0;)
        {
            while (huffsize[p] == si) 
            {
                huffcode[p++] = code;
                code++;
            }
            code <<= 1;
            si++;
        }

        for (int q = 0; q < lastp; q++)
        {
            AC_matrix0[valACluminance[q]][0] = huffcode[q];
            AC_matrix0[valACluminance[q]][1] = huffsize[q];
        }

        DC_matrix[0] = DC_matrix0;
        DC_matrix[1] = DC_matrix1;
        AC_matrix[0] = AC_matrix0;
        AC_matrix[1] = AC_matrix1;
    }

    int bufferPutBits;
    int bufferPutBuffer;
    public int ImageHeight;
    public int ImageWidth;
    public int DC_matrix0[][];
    public int AC_matrix0[][];
    public int DC_matrix1[][];
    public int AC_matrix1[][];
    public Object DC_matrix[];
    public Object AC_matrix[];
    public int code;
    public int NumOfDCTables;
    public int NumOfACTables;
    public int bitsDCluminance[] = {
        0, 0, 1, 5, 1, 1, 1, 1, 1, 1, 
        0, 0, 0, 0, 0, 0, 0
    };
    public int valDCluminance[] = {
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 
        10, 11
    };
    public int bitsDCchrominance[] = {
        1, 0, 3, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 0, 0, 0, 0, 0
    };
    public int valDCchrominance[] = {
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 
        10, 11
    };
    public int bitsACluminance[] = {
        16, 0, 2, 1, 3, 3, 2, 4, 3, 5, 
        5, 4, 4, 0, 0, 1, 125
    };
    public int valACluminance[] = {
        1, 2, 3, 0, 4, 17, 5, 18, 33, 49, 
        65, 6, 19, 81, 97, 7, 34, 113, 20, 50, 
        129, 145, 161, 8, 35, 66, 177, 193, 21, 82, 
        209, 240, 36, 51, 98, 114, 130, 9, 10, 22, 
        23, 24, 25, 26, 37, 38, 39, 40, 41, 42, 
        52, 53, 54, 55, 56, 57, 58, 67, 68, 69, 
        70, 71, 72, 73, 74, 83, 84, 85, 86, 87, 
        88, 89, 90, 99, 100, 101, 102, 103, 104, 105, 
        106, 115, 116, 117, 118, 119, 120, 121, 122, 131, 
        132, 133, 134, 135, 136, 137, 138, 146, 147, 148, 
        149, 150, 151, 152, 153, 154, 162, 163, 164, 165, 
        166, 167, 168, 169, 170, 178, 179, 180, 181, 182, 
        183, 184, 185, 186, 194, 195, 196, 197, 198, 199, 
        200, 201, 202, 210, 211, 212, 213, 214, 215, 216, 
        217, 218, 225, 226, 227, 228, 229, 230, 231, 232, 
        233, 234, 241, 242, 243, 244, 245, 246, 247, 248, 
        249, 250
    };
    public int bitsACchrominance[] = {
        17, 0, 2, 1, 2, 4, 4, 3, 4, 7, 
        5, 4, 4, 0, 1, 2, 119
    };
    public int valACchrominance[] = {
        0, 1, 2, 3, 17, 4, 5, 33, 49, 6, 
        18, 65, 81, 7, 97, 113, 19, 34, 50, 129, 
        8, 20, 66, 145, 161, 177, 193, 9, 35, 51, 
        82, 240, 21, 98, 114, 209, 10, 22, 36, 52, 
        225, 37, 241, 23, 24, 25, 26, 38, 39, 40, 
        41, 42, 53, 54, 55, 56, 57, 58, 67, 68, 
        69, 70, 71, 72, 73, 74, 83, 84, 85, 86, 
        87, 88, 89, 90, 99, 100, 101, 102, 103, 104, 
        105, 106, 115, 116, 117, 118, 119, 120, 121, 122, 
        130, 131, 132, 133, 134, 135, 136, 137, 138, 146, 
        147, 148, 149, 150, 151, 152, 153, 154, 162, 163, 
        164, 165, 166, 167, 168, 169, 170, 178, 179, 180, 
        181, 182, 183, 184, 185, 186, 194, 195, 196, 197, 
        198, 199, 200, 201, 202, 210, 211, 212, 213, 214, 
        215, 216, 217, 218, 226, 227, 228, 229, 230, 231, 
        232, 233, 234, 242, 243, 244, 245, 246, 247, 248, 
        249, 250
    };
    public Vector bits;
    public Vector val;

    public Huffman(int Width, int Height)
    {
        bits = new Vector();
        bits.addElement(bitsDCluminance);
        bits.addElement(bitsACluminance);
        bits.addElement(bitsDCchrominance);
        bits.addElement(bitsACchrominance);
        val = new Vector();
        val.addElement(valDCluminance);
        val.addElement(valACluminance);
        val.addElement(valDCchrominance);
        val.addElement(valACchrominance);
        initHuf();
        ImageWidth = Width;
        ImageHeight = Height;
    }
}
