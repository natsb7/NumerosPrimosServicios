package com.nataliasep.hilosnumerosprimos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.net.ContentHandler;
import java.util.ArrayList;

public class ThreadAdapter extends RecyclerView.Adapter<ThreadAdapter.ThreadViewHolder> {

    private ArrayList<MyThread> threads;

    public void setThreads(ArrayList<MyThread> threads) {
        this.threads = threads;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ThreadAdapter.ThreadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thread, parent, false);
        return new ThreadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThreadAdapter.ThreadViewHolder holder, int position) {
        MyThread thread = threads.get(position);
        holder.bindThread(thread);
    }
    @Override
    public int getItemCount() {
        if (threads != null)
            return threads.size();
        return 0;
    }

    public class ThreadViewHolder extends RecyclerView.ViewHolder{

        TextView tvID, tvRango, tvPrimos, tvUltimoPrimo;

        public ThreadViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvID = itemView.findViewById(R.id.tvID);
            this.tvRango = itemView.findViewById(R.id.tvRango);
            this.tvPrimos = itemView.findViewById(R.id.tvPrimos);
            this.tvUltimoPrimo = itemView.findViewById(R.id.tvUltimoPrimo);
        }
        public void bindThread(MyThread thread){
            tvID.setText(String.valueOf(thread.getId()));
            tvRango.setText(thread.getRango());
            tvPrimos.setText(String.valueOf(thread.getPrimos()));
            tvUltimoPrimo.setText(String.valueOf(thread.getLast()));
        }

    }
}
