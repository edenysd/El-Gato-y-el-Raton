package com.roek.www.framework.simple;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;

import com.roek.www.framework.Assets;
import com.roek.www.visual.Game;

import java.io.File;
import java.io.IOException;

public class Graphics {
    private Bitmap temp1;
    private Bitmap temp2;
    private Rect rectDst;
    Canvas canvas;
    Bitmap frameBuffer;
    Paint paint;
    AssetManager assetManager;
    // Escala en pixeles con respecto a la pantalla en la cual se diseñó el juego (720x1280) siempre portrait

    public Graphics (Context context, Bitmap frameBuffer){
            rectDst = new Rect();
            canvas = new Canvas(frameBuffer);
            this.frameBuffer = frameBuffer;
            assetManager = context.getAssets();
            paint = new Paint();
            paint.setTypeface(Typeface.createFromAsset(assetManager,"fonts"+ File.separator + "fuente.ttf"));
            paint.setColor(Color.argb(200,225,123,123));
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(80);
            paint.setTextAlign(Paint.Align.CENTER);
    }

    public PixMap loadPixmap(String path, int width, int height){
        try {

            temp1 = BitmapFactory.decodeStream(assetManager.open(path));
            temp2 = Bitmap.createScaledBitmap(temp1 , width , height , false);
            if(temp1 != temp2) {
                temp1 = null;
            }

            return  new PixMap(temp2, width , height);

        } catch (IOException e) {
            Log.d("Graphics", "No se pudo cargar el archivo");
        }
        return null;
    }

    public void drawRGB(int R, int G, int B){
        canvas.drawRGB(R,G,B);
    }

    public void drawPixmap(PixMap pixmap,int x, int y){
        if( null == pixmap || pixmap.getImage() == null )return;

        rectDst.set(x,y, x+pixmap.getWidth(), y+pixmap.getHeight());
        canvas.drawBitmap(pixmap.getImage(),null,rectDst, null);
    }

    public void drawPixmap(PixMap pixmap,Rect rect , int x, int y){
        if(pixmap.getImage() == null)return;
        rectDst.set(x,y, (int)(x+(rect.right-rect.left)*((float)pixmap.getWidth()/pixmap.getImage().getWidth())), (int)(y+(rect.bottom-rect.top)*((float)pixmap.getHeight()/pixmap.getImage().getHeight())));

        canvas.drawBitmap(pixmap.getImage(),rect,rectDst, null);
    }

    public void drawRect(Rect rect){

        canvas.drawRect(rect , new Paint());

    }

    public void drawText(String text, int x , int y ){
        canvas.drawText(text, x , y, paint);
    }

    public void dispose(){
    }
}
