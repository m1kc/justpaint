package jp;

import javax.microedition.lcdui.Image;

public class Resize {

    private static int[] reescalaArray(int[] ini, int x, int y, int x2, int y2) {
        int[] out = new int[x2 * y2];
        for (int yy = 0; yy < y2; yy++) {
            int dy = yy * y / y2;
            for (int xx = 0; xx < x2; xx++) {
                int dx = xx * x / x2;
                out[(x2 * yy) + xx] = ini[(x * dy) + dx];
            }
        }
        return out;
    }

    public static Image resize_image(Image temp, int newX, int newY) {
        final int width = temp.getWidth();
        final int height = temp.getHeight();
        //Need an array (for RGB, with the size of original image)
        int[] rgb = new int[width *height];
        //Get the RGB array of image into "rgb"
        temp.getRGB(rgb, 0, width, 0, 0, width, height);
        //Call to our function and obtain RGB2
        int[] rgb2 = reescalaArray(rgb, width, height, newX, newY);
        //Create an image with that RGB array
        return Image.createRGBImage(rgb2, newX, newY, true);
        //    System.gc();
    }
}