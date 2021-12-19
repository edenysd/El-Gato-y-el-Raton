package com.roek.www.framework;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.roek.www.visual.Game;
import com.roek.www.visual.MainMenuScreen;
import com.roek.www.visual.SplashScreen;

/**
    Adaptado para el el KEYCODE_BACK ya q se usa una sola Activity y manejar los estados por nosotros mismos y programar
    el flujo de la app manualmente
*/
public class OnKeyHandler implements View.OnKeyListener {


    Game game;

    /**
         0: No se ha pulsado KEY_CODE_BACK
         1: KEY_CODE_BACK esta DOWN
         2: KEY_CODE_BACK esta UP sin ningun KeyEvent
            intermedio por lo que se procede al estado
            anterior de nuestro flujo de la aplicacion
     */
    private int isBack=0;
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(SplashScreen.class.isInstance(game.getCurrentScreen()))
            if(keyCode == KeyEvent.KEYCODE_BACK)
                return true;

        if(keyCode == KeyEvent.KEYCODE_BACK){
            isBack++;
            return true;
        }else {
            isBack=0;
        }

        return false;
    }

    public OnKeyHandler(Game game){
        game.getRenderView().setFocusableInTouchMode(true);
        game.getRenderView().requestFocus();
        game.getRenderView().setOnKeyListener(this);
        this.game = game;
    }

    public boolean getIsBack() {
        if(isBack >= 2) {
            isBack = 0;
            return true;
        }
        return false;
    }
}
