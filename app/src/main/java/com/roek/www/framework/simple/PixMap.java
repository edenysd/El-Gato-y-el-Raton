package com.roek.www.framework.simple;

import android.graphics.Bitmap;

public class PixMap {
    private Bitmap image;
    private int width;
    private int height;

    public Bitmap getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public PixMap(Bitmap image, int width, int height) {
        this.image = image;
        this.width = width;
        this.height = height;
    }
}
