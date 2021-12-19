package com.roek.www.framework;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.roek.www.framework.simple.Pool;
import com.roek.www.visual.Game;
import com.roek.www.visual.SplashScreen;

import java.util.ArrayList;


public class OnTouchHandler implements View.OnTouchListener {

    Touch temp;
    Game game;
    private ArrayList <Touch> buffer;
    private ArrayList <Touch> events;

    @SuppressLint("ClickableViewAccessibility")
    public OnTouchHandler(Game game) {
        this.pool = new Pool<>(new Pool.Factory<Touch>() {
            @Override
            public Touch create() {
                return new Touch();
            }
        },200);
        this.game = game;
        buffer = new ArrayList<>();
        events = new ArrayList<>();

        game.getRenderView().setOnTouchListener(this);
    }

    private Pool<Touch> pool;

    public class Touch {
        Touch(){
            x=y=-1;
        }
        public int x, y;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if(SplashScreen.class.isInstance(game.getCurrentScreen()))return false;

        if((event.getAction() & MotionEvent.ACTION_MASK)  ==  MotionEvent.ACTION_DOWN) {
            temp = pool.getIntace();

            //Se guardan los Touch con las coordenadas relativas al framebuffer standard

            temp.x = (int) (event.getX() / Game.scaleX);
            temp.y = (int) (event.getY() / Game.scaleY);

            buffer.add(temp);

        }

        return false;
    }

    public ArrayList<Touch> consumeEvents(){
            for (int i=0; i<events.size(); i++){
                pool.setInstance(events.get(i));
            }

            events.clear();
            events.addAll(buffer);
            buffer.clear();
            return events;
    }
}
