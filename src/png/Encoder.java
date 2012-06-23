package png;

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) braces fieldsfirst space 
// Source File Name:   Encoder.java

import java.io.*;
import javax.microedition.lcdui.Image;

public class Encoder
{

    private static int crcTable[] = null;

    private Encoder()
    {
    }

    public static Image toImage(int width, int height, byte alpha[], byte red[], byte green[], byte blue[])
    {
        byte png[] = null;
        try {
            png = toPNG(width, height, alpha, red, green, blue);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return Image.createImage(png, 0, png.length);
    }

    public static Image toImage(byte png[])
    {
        return Image.createImage(png, 0, png.length);
    }

    public static byte[] toPNG(int width, int height, byte alpha[], byte red[], byte green[], byte blue[])
        throws IOException
    {
        byte signature[] = {
            -119, 80, 78, 71, 13, 10, 26, 10
        };
        byte header[] = createHeaderChunk(width, height);
        byte data[] = createDataChunk(width, height, alpha, red, green, blue);
        byte trailer[] = createTrailerChunk();
        ByteArrayOutputStream png = new ByteArrayOutputStream(signature.length + header.length + data.length + trailer.length);
        png.write(signature);
        png.write(header);
        png.write(data);
        png.write(trailer);
        return png.toByteArray();
    }

    public static byte[] toPNG(Image image)
    {
        try {
            byte[] a;
            byte[] r;
            byte[] g;
            byte[] b;
            int imageSize = image.getWidth() * image.getHeight();
            int[] rgbs = new int[imageSize];
            image.getRGB(rgbs, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            a = new byte[imageSize];
            r = new byte[imageSize];
            g = new byte[imageSize];
            b = new byte[imageSize];
            for (int i = 0; i < imageSize; i++) {
                int colorToDecode = rgbs[i];
                a[i] = (byte) ((colorToDecode & 0xff000000) >>> 24);
                r[i] = (byte) ((colorToDecode & 0xff0000) >>> 16);
                g[i] = (byte) ((colorToDecode & 0xff00) >>> 8);
                b[i] = (byte) (colorToDecode & 0xff);
            }
            return toPNG(image.getWidth(), image.getHeight(), a, r, g, b);
        } catch (IOException ex) {
            ex.printStackTrace();
            return new byte[1];
        }
    }

    private static byte[] createHeaderChunk(int width, int height)
        throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(13);
        DataOutputStream chunk = new DataOutputStream(baos);
        chunk.writeInt(width);
        chunk.writeInt(height);
        chunk.writeByte(8);
        chunk.writeByte(6);
        chunk.writeByte(0);
        chunk.writeByte(0);
        chunk.writeByte(0);
        return toChunk("IHDR", baos.toByteArray());
    }

    private static byte[] createDataChunk(int width, int height, byte alpha[], byte red[], byte green[], byte blue[])
        throws IOException
    {
        int source = 0;
        int dest = 0;
        byte raw[] = new byte[4 * (width * height) + height];
        for (int y = 0; y < height; y++)
        {
            raw[dest++] = 0;
            for (int x = 0; x < width; x++)
            {
                raw[dest++] = red[source];
                raw[dest++] = green[source];
                raw[dest++] = blue[source];
                raw[dest++] = alpha[source++];
            }

        }

        return toChunk("IDAT", toZLIB(raw));
    }

    private static byte[] createTrailerChunk()
        throws IOException
    {
        return toChunk("IEND", new byte[0]);
    }

    private static byte[] toChunk(String id, byte raw[])
        throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(raw.length + 12);
        DataOutputStream chunk = new DataOutputStream(baos);
        chunk.writeInt(raw.length);
        byte bid[] = new byte[4];
        for (int i = 0; i < 4; i++)
        {
            bid[i] = (byte)id.charAt(i);
        }

        chunk.write(bid);
        chunk.write(raw);
        int crc = -1;
        crc = updateCRC(crc, bid);
        crc = updateCRC(crc, raw);
        chunk.writeInt(~crc);
        return baos.toByteArray();
    }

    private static void createCRCTable()
    {
        crcTable = new int[256];
        for (int i = 0; i < 256; i++)
        {
            int c = i;
            for (int k = 0; k < 8; k++)
            {
                c = (c & 1) <= 0 ? c >>> 1 : 0xedb88320 ^ c >>> 1;
            }

            crcTable[i] = c;
        }

    }

    private static int updateCRC(int crc, byte raw[])
    {
        if (crcTable == null)
        {
            createCRCTable();
        }
        for (int i = 0; i < raw.length; i++)
        {
            crc = crcTable[(crc ^ raw[i]) & 0xff] ^ crc >>> 8;
        }

        return crc;
    }

    private static byte[] toZLIB(byte raw[])
        throws IOException
    {
        int iStatus = 0;
        byte bBuffer[] = new byte[raw.length];
        ZStream jStream = new ZStream();
        iStatus = jStream.deflateInit(9);
        if (iStatus != 0)
        {
            throw new IOException("Failure in deflateInit(JZlib.Z_BEST_COMPRESSION)");
        }
        jStream.next_in = raw;
        jStream.next_in_index = 0;
        jStream.next_out = bBuffer;
        jStream.next_out_index = 0;
        while (jStream.total_in != (long)raw.length && jStream.total_out < (long)bBuffer.length) 
        {
            jStream.avail_in = jStream.avail_out = 1;
            iStatus = jStream.deflate(0);
            if (iStatus != 0)
            {
                throw new IOException("Failure in deflate(JZlib.Z_NO_FLUSH)");
            }
        }
        do
        {
            jStream.avail_out = 1;
            iStatus = jStream.deflate(4);
            if (iStatus != 1)
            {
                if (iStatus != 0)
                {
                    throw new IOException("Failure in deflate(JZlib.Z_FINISH)");
                }
            } else
            {
                iStatus = jStream.deflateEnd();
                if (iStatus != 0)
                {
                    throw new IOException("Failure in deflateEnd()");
                } else
                {
                    byte bReturn[] = new byte[(int)jStream.total_out];
                    System.arraycopy(bBuffer, 0, bReturn, 0, (int)jStream.total_out);
                    return bReturn;
                }
            }
        } while (true);
    }

}
