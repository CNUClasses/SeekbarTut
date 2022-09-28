package com.example.seekbartut;
import android.graphics.Color;
/**
 * All this does is change the luminosity of the color gray
 * from black to white and all shades in between
 * Ripped off directly from
 * https://gist.github.com/martintreurnicht/f6bbb20a43211bc2060e
 */
public class colorutil {
    public static int mult(int color, double fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        red = multColor(red, fraction);
        green = multColor(green, fraction);
        blue = multColor(blue, fraction);
        int alpha = Color.alpha(color);
        return Color.argb(alpha, red, green, blue);
    }
    private static int multColor(int color, double fraction) {
        return (int)Math.max((color * fraction), 0);
    }
}