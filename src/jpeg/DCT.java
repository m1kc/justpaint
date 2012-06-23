package jpeg;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) deadcode space 
// Source File Name:   JpegEncoder.java


class DCT
{

    private void initMatrix(int quality)
    {
        double AANscaleFactor[] = {
            1.0D, 1.3870398450000001D, 1.3065629649999999D, 1.1758756020000001D, 1.0D, 0.785694958D, 0.54119609999999996D, 0.275899379D
        };
        int Quality = quality;
        if (Quality <= 0)
            Quality = 1;
        if (Quality > 100)
            Quality = 100;
        if (Quality < 50)
            Quality = 5000 / Quality;
        else
            Quality = 200 - Quality * 2;
        quantum_luminance[0] = 16;
        quantum_luminance[1] = 11;
        quantum_luminance[2] = 10;
        quantum_luminance[3] = 16;
        quantum_luminance[4] = 24;
        quantum_luminance[5] = 40;
        quantum_luminance[6] = 51;
        quantum_luminance[7] = 61;
        quantum_luminance[8] = 12;
        quantum_luminance[9] = 12;
        quantum_luminance[10] = 14;
        quantum_luminance[11] = 19;
        quantum_luminance[12] = 26;
        quantum_luminance[13] = 58;
        quantum_luminance[14] = 60;
        quantum_luminance[15] = 55;
        quantum_luminance[16] = 14;
        quantum_luminance[17] = 13;
        quantum_luminance[18] = 16;
        quantum_luminance[19] = 24;
        quantum_luminance[20] = 40;
        quantum_luminance[21] = 57;
        quantum_luminance[22] = 69;
        quantum_luminance[23] = 56;
        quantum_luminance[24] = 14;
        quantum_luminance[25] = 17;
        quantum_luminance[26] = 22;
        quantum_luminance[27] = 29;
        quantum_luminance[28] = 51;
        quantum_luminance[29] = 87;
        quantum_luminance[30] = 80;
        quantum_luminance[31] = 62;
        quantum_luminance[32] = 18;
        quantum_luminance[33] = 22;
        quantum_luminance[34] = 37;
        quantum_luminance[35] = 56;
        quantum_luminance[36] = 68;
        quantum_luminance[37] = 109;
        quantum_luminance[38] = 103;
        quantum_luminance[39] = 77;
        quantum_luminance[40] = 24;
        quantum_luminance[41] = 35;
        quantum_luminance[42] = 55;
        quantum_luminance[43] = 64;
        quantum_luminance[44] = 81;
        quantum_luminance[45] = 104;
        quantum_luminance[46] = 113;
        quantum_luminance[47] = 92;
        quantum_luminance[48] = 49;
        quantum_luminance[49] = 64;
        quantum_luminance[50] = 78;
        quantum_luminance[51] = 87;
        quantum_luminance[52] = 103;
        quantum_luminance[53] = 121;
        quantum_luminance[54] = 120;
        quantum_luminance[55] = 101;
        quantum_luminance[56] = 72;
        quantum_luminance[57] = 92;
        quantum_luminance[58] = 95;
        quantum_luminance[59] = 98;
        quantum_luminance[60] = 112;
        quantum_luminance[61] = 100;
        quantum_luminance[62] = 103;
        quantum_luminance[63] = 99;
        for (int j = 0; j < 64; j++)
        {
            int temp = (quantum_luminance[j] * Quality + 50) / 100;
            if (temp <= 0)
                temp = 1;
            if (temp > 255)
                temp = 255;
            quantum_luminance[j] = temp;
        }

        int index = 0;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                DivisorsLuminance[index] = 1.0D / ((double)quantum_luminance[index] * AANscaleFactor[i] * AANscaleFactor[j] * 8D);
                index++;
            }

        }

        quantum_chrominance[0] = 17;
        quantum_chrominance[1] = 18;
        quantum_chrominance[2] = 24;
        quantum_chrominance[3] = 47;
        quantum_chrominance[4] = 99;
        quantum_chrominance[5] = 99;
        quantum_chrominance[6] = 99;
        quantum_chrominance[7] = 99;
        quantum_chrominance[8] = 18;
        quantum_chrominance[9] = 21;
        quantum_chrominance[10] = 26;
        quantum_chrominance[11] = 66;
        quantum_chrominance[12] = 99;
        quantum_chrominance[13] = 99;
        quantum_chrominance[14] = 99;
        quantum_chrominance[15] = 99;
        quantum_chrominance[16] = 24;
        quantum_chrominance[17] = 26;
        quantum_chrominance[18] = 56;
        quantum_chrominance[19] = 99;
        quantum_chrominance[20] = 99;
        quantum_chrominance[21] = 99;
        quantum_chrominance[22] = 99;
        quantum_chrominance[23] = 99;
        quantum_chrominance[24] = 47;
        quantum_chrominance[25] = 66;
        quantum_chrominance[26] = 99;
        quantum_chrominance[27] = 99;
        quantum_chrominance[28] = 99;
        quantum_chrominance[29] = 99;
        quantum_chrominance[30] = 99;
        quantum_chrominance[31] = 99;
        quantum_chrominance[32] = 99;
        quantum_chrominance[33] = 99;
        quantum_chrominance[34] = 99;
        quantum_chrominance[35] = 99;
        quantum_chrominance[36] = 99;
        quantum_chrominance[37] = 99;
        quantum_chrominance[38] = 99;
        quantum_chrominance[39] = 99;
        quantum_chrominance[40] = 99;
        quantum_chrominance[41] = 99;
        quantum_chrominance[42] = 99;
        quantum_chrominance[43] = 99;
        quantum_chrominance[44] = 99;
        quantum_chrominance[45] = 99;
        quantum_chrominance[46] = 99;
        quantum_chrominance[47] = 99;
        quantum_chrominance[48] = 99;
        quantum_chrominance[49] = 99;
        quantum_chrominance[50] = 99;
        quantum_chrominance[51] = 99;
        quantum_chrominance[52] = 99;
        quantum_chrominance[53] = 99;
        quantum_chrominance[54] = 99;
        quantum_chrominance[55] = 99;
        quantum_chrominance[56] = 99;
        quantum_chrominance[57] = 99;
        quantum_chrominance[58] = 99;
        quantum_chrominance[59] = 99;
        quantum_chrominance[60] = 99;
        quantum_chrominance[61] = 99;
        quantum_chrominance[62] = 99;
        quantum_chrominance[63] = 99;
        for (int j = 0; j < 64; j++)
        {
            int temp = (quantum_chrominance[j] * Quality + 50) / 100;
            if (temp <= 0)
                temp = 1;
            if (temp >= 255)
                temp = 255;
            quantum_chrominance[j] = temp;
        }

        index = 0;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                DivisorsChrominance[index] = 1.0D / ((double)quantum_chrominance[index] * AANscaleFactor[i] * AANscaleFactor[j] * 8D);
                index++;
            }

        }

        quantum[0] = quantum_luminance;
        Divisors[0] = DivisorsLuminance;
        quantum[1] = quantum_chrominance;
        Divisors[1] = DivisorsChrominance;
    }

    public double[][] forwardDCTExtreme(float input[][])
    {
        double output[][] = new double[N][N];
        for (int v = 0; v < 8; v++)
        {
            for (int u = 0; u < 8; u++)
            {
                for (int x = 0; x < 8; x++)
                {
                    for (int y = 0; y < 8; y++)
                        output[v][u] += (double)input[x][y] * Math.cos(((double)(2 * x + 1) * (double)u * 3.1415926535897931D) / 16D) * Math.cos(((double)(2 * y + 1) * (double)v * 3.1415926535897931D) / 16D);

                }

                output[v][u] *= 0.25D * (u != 0 ? 1.0D : 1.0D / Math.sqrt(2D)) * (v != 0 ? 1.0D : 1.0D / Math.sqrt(2D));
            }

        }

        return output;
    }

    public double[][] forwardDCT(float input[][])
    {
        double output[][] = new double[N][N];
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
                output[i][j] = (double)input[i][j] - 128D;

        }

        for (int i = 0; i < 8; i++)
        {
            double tmp0 = output[i][0] + output[i][7];
            double tmp7 = output[i][0] - output[i][7];
            double tmp1 = output[i][1] + output[i][6];
            double tmp6 = output[i][1] - output[i][6];
            double tmp2 = output[i][2] + output[i][5];
            double tmp5 = output[i][2] - output[i][5];
            double tmp3 = output[i][3] + output[i][4];
            double tmp4 = output[i][3] - output[i][4];
            double tmp10 = tmp0 + tmp3;
            double tmp13 = tmp0 - tmp3;
            double tmp11 = tmp1 + tmp2;
            double tmp12 = tmp1 - tmp2;
            output[i][0] = tmp10 + tmp11;
            output[i][4] = tmp10 - tmp11;
            double z1 = (tmp12 + tmp13) * 0.70710678100000002D;
            output[i][2] = tmp13 + z1;
            output[i][6] = tmp13 - z1;
            tmp10 = tmp4 + tmp5;
            tmp11 = tmp5 + tmp6;
            tmp12 = tmp6 + tmp7;
            double z5 = (tmp10 - tmp12) * 0.38268343300000002D;
            double z2 = 0.54119609999999996D * tmp10 + z5;
            double z4 = 1.3065629649999999D * tmp12 + z5;
            double z3 = tmp11 * 0.70710678100000002D;
            double z11 = tmp7 + z3;
            double z13 = tmp7 - z3;
            output[i][5] = z13 + z2;
            output[i][3] = z13 - z2;
            output[i][1] = z11 + z4;
            output[i][7] = z11 - z4;
        }

        for (int i = 0; i < 8; i++)
        {
            double tmp0 = output[0][i] + output[7][i];
            double tmp7 = output[0][i] - output[7][i];
            double tmp1 = output[1][i] + output[6][i];
            double tmp6 = output[1][i] - output[6][i];
            double tmp2 = output[2][i] + output[5][i];
            double tmp5 = output[2][i] - output[5][i];
            double tmp3 = output[3][i] + output[4][i];
            double tmp4 = output[3][i] - output[4][i];
            double tmp10 = tmp0 + tmp3;
            double tmp13 = tmp0 - tmp3;
            double tmp11 = tmp1 + tmp2;
            double tmp12 = tmp1 - tmp2;
            output[0][i] = tmp10 + tmp11;
            output[4][i] = tmp10 - tmp11;
            double z1 = (tmp12 + tmp13) * 0.70710678100000002D;
            output[2][i] = tmp13 + z1;
            output[6][i] = tmp13 - z1;
            tmp10 = tmp4 + tmp5;
            tmp11 = tmp5 + tmp6;
            tmp12 = tmp6 + tmp7;
            double z5 = (tmp10 - tmp12) * 0.38268343300000002D;
            double z2 = 0.54119609999999996D * tmp10 + z5;
            double z4 = 1.3065629649999999D * tmp12 + z5;
            double z3 = tmp11 * 0.70710678100000002D;
            double z11 = tmp7 + z3;
            double z13 = tmp7 - z3;
            output[5][i] = z13 + z2;
            output[3][i] = z13 - z2;
            output[1][i] = z11 + z4;
            output[7][i] = z11 - z4;
        }

        return output;
    }

    public int[] quantizeBlock(double inputData[][], int code)
    {
        int outputData[] = new int[N * N];
        int index = 0;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                outputData[index] = (int)(inputData[i][j] * ((double[])(double[])Divisors[code])[index]);
                index++;
            }

        }

        return outputData;
    }

    public int[] quantizeBlockExtreme(double inputData[][], int code)
    {
        int outputData[] = new int[N * N];
        int index = 0;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                outputData[index] = (int)Math.floor(inputData[i][j] / (double)((int[])(int[])quantum[code])[index]);
                index++;
            }

        }

        return outputData;
    }

    public int N;
    public int QUALITY;
    public Object quantum[];
    public Object Divisors[];
    public int quantum_luminance[];
    public double DivisorsLuminance[];
    public int quantum_chrominance[];
    public double DivisorsChrominance[];

    public DCT(int QUALITY)
    {
        N = 8;
        this.QUALITY = 80;
        quantum = new Object[2];
        Divisors = new Object[2];
        quantum_luminance = new int[N * N];
        DivisorsLuminance = new double[N * N];
        quantum_chrominance = new int[N * N];
        DivisorsChrominance = new double[N * N];
        initMatrix(QUALITY);
    }
}
