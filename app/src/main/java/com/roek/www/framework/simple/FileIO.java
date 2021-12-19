package com.roek.www.framework.simple;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;

public class FileIO {
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    AssetManager assetManager;
    public FileIO(Context context){
        this.context = context;
        assetManager =context.getAssets();
        sharedPreferences = context.getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
    }

    public Object leerObjeto(String name){
        Object o = null;
        ObjectInputStream objectInputStream;
        try {

            objectInputStream= new ObjectInputStream(assetManager.open("objects"+ File.separator + name));
            o = objectInputStream.readObject();
            objectInputStream.close();

        } catch (IOException ignored) {
            Log.d("I/O" , "Error al abrir el asset "+"objects"+ File.separator + name);
        } catch (ClassNotFoundException e) {
            Log.d("I/O" , "Error al encontrar la clase en el asset "+"objects"+ File.separator + name);
        }
        return o;
    }

    public void put(String id, String value ){
        editor = sharedPreferences.edit();
        editor.putString(id,value);
        editor.apply();
    }

    public String get(String id){
        return sharedPreferences.getString(id, null);
    }
}
