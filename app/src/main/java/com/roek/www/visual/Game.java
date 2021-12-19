package com.roek.www.visual;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.roek.www.framework.Assets;
import com.roek.www.framework.OnKeyHandler;
import com.roek.www.framework.OnTouchHandler;
import com.roek.www.framework.Screen;
import com.roek.www.framework.simple.AndroidMusic;
import com.roek.www.framework.simple.AndroidSound;
import com.roek.www.framework.simple.FileIO;
import com.roek.www.framework.simple.Graphics;
import com.roek.www.framework.simple.Sound;
import com.roek.www.gamelogic.GameTree;
import com.roek.www.render.FastRenderView;

import java.io.File;
import java.util.ArrayList;


public class Game extends AppCompatActivity {
    public static float defaultWidth = 720;
    public static float defaultHeight = 1280;
    public static int defaultCellSize = 90;
    public static int defaultMarginTop = 280;

    public static float scaleX ;
    public static float scaleY ;

    OnKeyHandler onKeyHandler;
    OnTouchHandler onTouchHandler;
    public Sound sound;
    FileIO fileIO;
    public Graphics graphics;
    Screen currentScreen;
    Bitmap framebuffer;
    /*
    todo
    Hacer el cambio de imagen del muted
     */
    public static void checkIfMutedStateChangedMusic(ArrayList<OnTouchHandler.Touch> touches , AndroidMusic currentMusic) {
        if(currentMusic == null) return;
        for(int i=0; i<touches.size(); i++){
            if(touches.get(i).x<105 && touches.get(i).y <105){
                AndroidMusic.changeMuted();
                currentMusic.setVolume(currentMusic.normalVolume()*AndroidMusic.volume);
            }

        }

    }

    public static void paintMutedStateMusic(Game game) {
        if(AndroidMusic.volume == 1.0f)
            game.graphics.drawPixmap(Assets.MUSIC_ON , 5 , 5 );
        else
            game.graphics.drawPixmap(Assets.MUSIC_OFF , 5 , 5 );
    }

    public static void checkIfMutedStateChangedSound(ArrayList<OnTouchHandler.Touch> touches) {
        for(int i=0; i<touches.size(); i++){
            if(touches.get(i).x>615 && touches.get(i).y <105){
                AndroidSound.changeMuted();
            }
        }
    }

    public static void paintMutedStateSound(Game game) {
        if(AndroidSound.volume == 1.0f)
            game.graphics.drawPixmap(Assets.SFX_ON , 615 , 5 );
        else
            game.graphics.drawPixmap(Assets.SFX_OFF , 615 , 5 );
    }

    public FastRenderView getRenderView() {
        return renderView;
    }

    FastRenderView renderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sound = new Sound(this);
        fileIO = new FileIO(this);

        framebuffer = Bitmap.createBitmap((int)defaultWidth,(int)defaultHeight, Bitmap.Config.ARGB_8888);

        graphics = new Graphics(this, framebuffer);
        setStartScreen();

        renderView = new FastRenderView(this, framebuffer);

        onTouchHandler = new OnTouchHandler(this);
        onKeyHandler = new OnKeyHandler(this);

        setContentView(renderView);
    }

    @Override
    protected void onResume() {
        Log.d("D","RESUMMEEEE!!!");
        super.onResume();
        renderView.onResume();
    }

    private void setStartScreen() {
        Assets.IMAGE_SPLASH_SCREEN = graphics.loadPixmap("images"+ File.separator + "splash.png",(int)defaultWidth,(int)defaultHeight);
        currentScreen = new SplashScreen(this);
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(Screen currentScreen) {

        this.currentScreen.onPause();
        this.currentScreen.dispose();

        currentScreen.onResume();
        currentScreen.update(0);

        this.currentScreen = currentScreen;
    }

    @Override
    protected void onPause() {

        super.onPause();
        Log.d("D","PAUSEEE!!!");
        renderView.onPause();

        if(isFinishing()){
            sound.dispose();
            sound = null;

            graphics.dispose();
            graphics = null;

            GameTree.dispose();
            Assets.IMAGE_MAIN_MENU=null;
        }
    }
}
