package com.roek.www.render;

import android.graphics.Rect;

import com.roek.www.framework.simple.AndroidSound;
import com.roek.www.framework.simple.PixMap;

import java.util.ArrayList;
import java.util.Random;


/*
    Controla las animaciones de un objeto del juego
 */
public class AnimatedCharacter {

    enum State{
        Normal, Saliendo, Entrando
    }

    private State STATE;

    //Le anade un factor de probabilidad a realizar prioritariamente la primera animacion en el lugar
    private static int K = 6;

    private AndroidSound currentSound;
    private Random random;
    private ArrayList<Anim> inPlace;
    private ArrayList<Anim> inDisapear;
    private ArrayList<Anim> inApear;

    //Se toma como animacion inicial estandar al la primera animacion en el lugar en la lista

    public AnimatedCharacter(ArrayList<Anim> inPlace, ArrayList<Anim> inDisapear, ArrayList<Anim> inApear) {
        this.inPlace = inPlace;
        this.inDisapear = inDisapear;
        this.inApear = inApear;
        currentTimeAnimation = 0;
        currentAnimSprite = inPlace.get(0);
        currentSound = currentAnimSprite.getSound();
        random = new Random();

    }


    private long currentTimeAnimation;
    private Anim currentAnimSprite;

    public PixMap getCurrentAnimPixmap() {
        return currentAnimSprite.getSprite();
    }


    public Rect getCurrentAnimSpriteRect(long deltaTime){
        currentTimeAnimation+=deltaTime;
        //Cambiamos la animacion
        if(currentTimeAnimation > currentAnimSprite.getTotalTime()){
            currentTimeAnimation = currentAnimSprite.getTotalTime() - currentTimeAnimation;
            if(STATE == State.Normal){
                nextAnim();
            }
            else if(STATE == State.Saliendo){
                STATE = State.Entrando;
                nextAnim();
            }else {
                STATE = State.Normal;
                nextAnim();
            }

        }

        return currentAnimSprite.getRectSprite(currentTimeAnimation);
    }

    private void nextAnim() {
        int currentAnimSpriteIndex;
        if(STATE == State.Normal){
            currentAnimSpriteIndex = Math.abs(random.nextInt())%(inPlace.size()*K);
            if(currentAnimSpriteIndex >=inPlace.size()){
                currentAnimSpriteIndex = 0;
            }

            currentAnimSprite = inPlace.get(currentAnimSpriteIndex);

        }
        else if(STATE == State.Saliendo){
            currentAnimSpriteIndex = Math.abs(random.nextInt())%inDisapear.size();
            currentAnimSprite = inDisapear.get(currentAnimSpriteIndex);
        }else {
            currentAnimSpriteIndex = Math.abs(random.nextInt())%inApear.size();
            currentAnimSprite = inApear.get(currentAnimSpriteIndex);
        }
        if(currentSound != null)
            currentSound.stop();
        currentSound = currentAnimSprite.getSound();
        if(currentSound!=null){
            currentSound.play();
        }
    }
    public void setMoving () {
            STATE = State.Saliendo;
            currentTimeAnimation = 0;
            nextAnim();
    }

    public boolean isMoving(){
        return STATE != State.Normal;
    }

    public boolean isInDisapear(){
        return STATE == State.Saliendo;
    }

    public boolean isInApear(){
        return STATE == State.Entrando;
    }

}
