package com.nataliasep.hilosnumerosprimos;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyService extends Service{

        private ArrayList<MyThread> myThreads = new ArrayList<>();
        private final MyBinder myBinder = new MyBinder();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Mensaje1", "Comienza el servicio");
        int numeroNucleos = Runtime.getRuntime().availableProcessors();
        int rango = 1000000;


        // Crear y empezar los hilos, crea tantos hilos como nucleos tenga el dispositivo en el que se ejecuta
        for (int i = 0; i < numeroNucleos; i++) {
            MyThread myThread = new MyThread(i, numeroNucleos, rango);
            myThreads.add(myThread);
            myThread.start();
            Log.d("Mensaje2", "Se ejecuta el primer hilo");
        }

        return START_STICKY;
    }

    public void stopService() {
        for (MyThread thread : myThreads) {
            thread.stopThread();
        }
        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }


    public class MyBinder extends Binder {
        public MyService getMyService(){
            return MyService.this;
        }
    }

    public ArrayList<MyThread> getMyThreads(){
        return this.myThreads;
    }
}
