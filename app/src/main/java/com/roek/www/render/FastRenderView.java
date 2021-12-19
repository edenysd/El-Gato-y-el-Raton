package com.roek.www.render;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.roek.www.framework.Assets;
import com.roek.www.visual.Game;
import com.roek.www.visual.SplashScreen;

public class FastRenderView extends SurfaceView implements Runnable {
    private SurfaceHolder holder;
    private Game game;
    private volatile boolean running = false;
    private Bitmap framebuffer;
    private Canvas canvas;
    private Thread renderThread;
    private Rect dstRect;
    private Paint paint;

    public FastRenderView(Game game, Bitmap frameBuffer) {
        super(game);
        holder = getHolder();
        this.game = game;
        this.framebuffer = frameBuffer;
        dstRect = new Rect();
        paint = new Paint();
        holder.setFormat(PixelFormat.RGBA_8888);

    }

    @Override
    public void run() {

        //La imagen del splash antes de cargar todos los assets
        if(SplashScreen.class.isInstance(game.getCurrentScreen())){
            while (!holder.getSurface().isValid()){
                if(!running)return;
            }

            Game.scaleX = getWidth()/Game.defaultWidth;
            Game.scaleY = getHeight()/Game.defaultHeight;


            canvas = holder.lockCanvas();
            canvas.drawRGB(120,0,0);
            canvas.drawBitmap(Assets.IMAGE_SPLASH_SCREEN.getImage(), null, canvas.getClipBounds(), null);
            holder.unlockCanvasAndPost(canvas);
        }


        long startTime = System.nanoTime();
        while(running) {

            if(!holder.getSurface().isValid())
                continue;

            long deltaTime = (System.nanoTime()-startTime);
            startTime = System.nanoTime();

            game.getCurrentScreen().update(deltaTime);
            game.getCurrentScreen().present(deltaTime);

            canvas = holder.lockCanvas();
            canvas.getClipBounds(dstRect);

            canvas.drawBitmap(framebuffer, null, dstRect, null);

            holder.unlockCanvasAndPost(canvas);
        }
    }

    public synchronized void onPause(){
        game.getCurrentScreen().onPause();
        running = false;
        while(true) {
            try {
                renderThread.join();
                return;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void onResume(){
        game.getCurrentScreen().onResume();
        running = true;
        renderThread = new Thread(this);
        renderThread.start();
    }

}
