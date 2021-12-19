package com.roek.www.framework.simple;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

// Solo puede existir un Stream de musica a la vez cargado en el MediaPlayer

// La clase que la contenga debe llevar control de los indentificadores para la SoundPool

public class Sound {

    Context mContext;
    SoundPool mSoundPool;
    AssetManager mAssetManager;


    public Sound(AppCompatActivity activity){
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        mContext = activity;
        mAssetManager = activity.getAssets();

        mSoundPool = new SoundPool(20, AudioManager.STREAM_MUSIC , 0);
    }

    public AndroidMusic loadStreamMusic(String path, float volume){

        try {
            return new AndroidMusic(mAssetManager.openFd(path),volume);

        } catch (IOException e) {
            Log.d("Sound", "Error al cargar la Musica");
        }
        return null;
    }

    public AndroidSound loadSimpleSound(String path, float normalVolume){
        try {
            return new AndroidSound(mSoundPool, mSoundPool.load(mAssetManager.openFd(path), 0 ),normalVolume);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void dispose() {
        mSoundPool.release();
    }
}
