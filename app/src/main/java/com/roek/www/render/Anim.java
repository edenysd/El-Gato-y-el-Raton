package com.roek.www.render;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

import com.roek.www.framework.simple.AndroidSound;
import com.roek.www.framework.simple.PixMap;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Anim {


    PixMap sprite;
    AndroidSound sound;
    /*
        Representa la cantidad de veces que se repite un sprite con respecto a la suma total
        de este arreglo, mas tarde hacemos una tabla acumulativa para usar busqueda binaria en
        este arreglo y saber dada una fraccion de tiempo que sprite le toca mostrarse en pantalla
     */
    int [] repeat;

    public long getTotalTime() {
        return totalTime;
    }

    public PixMap getSprite() {
        return sprite;
    }

    //Tiempo en nanosegundos que debe durar la animacion
    long totalTime;

    public Anim(PixMap sprite, int [] repeat, long totalTime ){
        this.sprite = sprite;
        this.repeat = repeat;
        this.totalTime = totalTime;

        for (int i=1; i<repeat.length; i++){
            repeat[i]+=repeat[i-1];
        }
    }

    public AndroidSound getSound(){
        return sound;
    }

    public Anim(PixMap sprite, long totalTime){
        this.sprite = sprite;
        this.repeat = new int[sprite.getWidth()/sprite.getHeight()];
        this.totalTime = totalTime;

        Arrays.fill(repeat,1);

        for (int i=1; i<repeat.length; i++){
            repeat[i]+=repeat[i-1];
        }
    }

    public Anim(PixMap sprite, AndroidSound sound, long totalTime){
        this.sprite = sprite;
        this.repeat = new int[sprite.getWidth()/sprite.getHeight()];
        this.totalTime = totalTime;
        this.sound = sound;
        Arrays.fill(repeat,1);

        for (int i=1; i<repeat.length; i++){
            repeat[i]+=repeat[i-1];
        }
    }

    Rect rect = new Rect();
    double fr;
    int index;
    /*
        Devuelve el rectangulo del sprite que se debe mostrar pasado un tiempo(long time) luego de comenzar la animacion
     */
    public Rect getRectSprite(long time) {

           fr = (double) time/totalTime;
           fr = fr * repeat[repeat.length-1];
           index = Arrays.binarySearch(repeat, (int) fr);
           if(index < 0)index =0;

           rect.set(index*sprite.getImage().getHeight(),0,(index+1)*sprite.getImage().getHeight()-1,sprite.getImage().getHeight()-1);

           return rect;
    }


    private long timeInLine = 0;

    public Rect getRectSpriteInLine(long time, int L) {
        timeInLine += time;

        fr = (double) timeInLine/totalTime;
        double frac = (double) (sprite.getImage().getWidth() - L) / sprite.getImage().getWidth();
        if(fr > frac){
            fr = frac -fr;
        }
            timeInLine = (long) (fr * totalTime);


        rect.set((int) (fr*sprite.getImage().getWidth()),0,(int)(fr*sprite.getImage().getWidth() + L)-1,sprite.getImage().getHeight()-1);

        return rect;
    }
}
