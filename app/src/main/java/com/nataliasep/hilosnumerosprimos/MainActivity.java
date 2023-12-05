package com.nataliasep.hilosnumerosprimos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity{

    private ArrayList<MyThread> threads;
    private Button bStop;
    private MyService myService;
    private ThreadAdapter threadAdapter;
    private Timer timer;

    private OnStopListener listener;

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(MainActivity.class.getSimpleName(), "Estoy conectandome");
            myService = ((MyService.MyBinder)service).getMyService();
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    updateUI();
                    Log.d("msg", "entro en updateUI");
                }
            }, 0, 500);
            /*
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    updateUI();
                    Log.d("msg", "entro en updateUI");
                }
            }, 0,500, TimeUnit.MILLISECONDS);
            */

            Log.d(MainActivity.class.getSimpleName(), "Conectado al servicio");

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            myService = null;
        }

    };



    public void updateUI(){

        threads = myService.getMyThreads();
        Log.d("Mensaje", "array rellena");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                threadAdapter.setThreads(threads);
            }
        });

        Log.d("mensajito", "soy concha, entro");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.rvThreads);
        threadAdapter= new ThreadAdapter();
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(threadAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        bStop = findViewById(R.id.bStop);

        bStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myService.stopService();

            }
        });

        Intent intent = new Intent(MainActivity.this, MyService.class);
        startService(intent);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        Log.d("Mensaje", "mandamos el intent");

    }



}