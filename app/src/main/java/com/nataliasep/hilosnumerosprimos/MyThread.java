package com.nataliasep.hilosnumerosprimos;

import android.util.Log;

import java.util.ArrayList;

public class MyThread extends Thread{

    private int id;
    private int numHilos;
    private int rango; //cantidad de num a procesar
    private ArrayList<Integer> numerosPrimos = new ArrayList<>();
    private int inicio =0;
    private int fin = 0;
    private boolean detener;


    public MyThread(int id, int numHilos, int rango){
        this.id = id;
        this.numHilos = numHilos;
        this.rango = rango;
    }


    @Override
    public void run() {

            inicio = id * rango; //Nos dice el inicio del rango al principio, al pasarle el ID del hilo, sabremos donde debe empezar
            // el primer hilo. Luego el inicio irá avanzando como veremos más adelante. Rango se refiere a la cantidad de números a procesar
            while (true && !detener) {
                fin = inicio + rango; //el fin es el inicio (1 en un principio), más el rango (1000000 en este caso)
                for (int i = inicio; i<fin; i++){
                    if(esPrimo(i)){
                        numerosPrimos.add(i); //si es primo, devuelve true y se añade a la array de primos
                        //Log.d("mensajito", "se ha encontrado un primo");
                    }
                }
                inicio += rango * numHilos; //cuando se crea el último hilo desde el service, el inicio será tantos núcleos como tenga el dispositivo
                // por lo que el inicio de la segunda vuelta del calculo de primos en el primer hilo, será igual al inicio del último rango numeros
                // más el rango que se calcula (y ya se ha procesado) por el número de nucleos que hay
                Log.d("Estos son los primos q hay", numerosPrimos.toString());
            }
    }

    public void stopThread(){
        detener = true;
    }

    private boolean esPrimo(int num){
        // Este método recibe un número y nos dice con booleanos si lo es o no, true si lo es y false si no.
        if (num <= 1){
            return false;
        }
        if(num == 2 || num == 3){
            return false;
        }
        for(int i = 2; i<num; i++){
            if(num%i == 0){
                return false;
            }
        }
        return true;
    }

    public String getRango(){
        return inicio + " - " + fin;
    }



    public int getPrimos(){
        return numerosPrimos.size();
    }

    public int getLast(){
        if(numerosPrimos == null || numerosPrimos.size() == 0){
            return -1;
        }
        return numerosPrimos.get(numerosPrimos.size()-1);
    }

    @Override
    public long getId() {
        return id;
    }
}
