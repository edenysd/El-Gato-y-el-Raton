package com.roek.www.framework.simple;

import android.media.SoundPool;
import android.util.Log;

public class AndroidSound {
    private SoundPool soundPool;
    private int id;
    public static float volume = 1.0f;
    private float normalVolume;
    AndroidSound(SoundPool soundPool, int id, float normalVolume){
        this.normalVolume = normalVolume;
        this.soundPool = soundPool;
        this.id = id;
    }

    public static void changeMuted(){
        if(volume == 1.0f){
            volume = 0.0f;
        }
        else{
            volume = 1.0f;
        }
    }

    public void play(){

        if(id==-1){
            Log.d("AndroidSound", "No se carga el sonido");
        }
        soundPool.play(id,volume*normalVolume, volume*normalVolume, 0 ,0 , 1);
    }

    public void stop(){
        if(id==-1){
            Log.d("AndroidSound", "No se carga el sonido");
        }
        soundPool.stop(id);
    }

}
