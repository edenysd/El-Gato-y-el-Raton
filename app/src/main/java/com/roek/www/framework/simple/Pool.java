package com.roek.www.framework.simple;

import java.util.ArrayList;

public class Pool<E> {
    private ArrayList<E> pool;
    private int max;
    private Factory<E> factory;
    public interface Factory<E>{
        public E create();
    }

    public Pool(Factory<E> factory, int max){
        this.factory = factory;
        this.max = max;
        pool = new ArrayList<>(max);
    }

    public E getIntace(){
        E object = null;
        if(pool.size()>0){
            object = pool.remove(pool.size()-1);
        }else {
            object = factory.create();
        }
        return object;
    }

    public void setInstance(E e){
        if(pool.size()<max){
            pool.add(e);
        }
    }
}
