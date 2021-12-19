package com.roek.www.framework.simple;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

public class AndroidMusic implements MediaPlayer.OnCompletionListener {
    public static float volume = 1.0f;

    public static void changeMuted(){
        if(volume == 1.0f){
            mute();
        }
        else{
            unMute();
        }
    }

    private static void mute(){
        volume = 0.0f;
    }

    private static void unMute(){
        volume = 1.0f;
    }


    private MediaPlayer mediaPlayer;
    private boolean isPrepared;
    private float normalVolume;

    AndroidMusic (AssetFileDescriptor fd,float normalVolume){
        this.normalVolume = normalVolume;
        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(fd.getFileDescriptor(),fd.getStartOffset(),fd.getLength());
            mediaPlayer.prepare();
            isPrepared = true;
            mediaPlayer.setOnCompletionListener(this);
        } catch (IOException e) {
            Log.d("AndroidMusic", "Error con el FileDescriptor");
        }
    }

    public void setLooping(){
        if(isPrepared){
            synchronized (this){
                mediaPlayer.setLooping(true);
            }
        }
    }
    public void setVolume(float f){
        mediaPlayer.setVolume(f,f);
    }

    public void stop(){
        mediaPlayer.stop();
        isPrepared = false;
    }

    public void dispose(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }

    public void pause(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    public void play(){
            if(mediaPlayer.isPlaying())return;
            synchronized (this){
                try {
                    if(!isPrepared)
                         mediaPlayer.prepare();
                    mediaPlayer.start();
                    setVolume(normalVolume* volume);
                    isPrepared = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

    }




    @Override
    public void onCompletion(MediaPlayer mp) {
        isPrepared = false;
    }

    public float normalVolume() {
        return normalVolume;
    }

}
