package com.example.chatapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.ModelGrup;
import com.example.chatapp.ModelMesaj;
import com.example.chatapp.R;

import java.util.List;

public class MesajOlusturAdapter extends RecyclerView.Adapter<MesajOlusturAdapter.MesajViewHolder> {


    List<ModelMesaj> mesajListesi;

    public MesajOlusturAdapter(List<ModelMesaj> mesajListesi) {
        this.mesajListesi = mesajListesi;
    }

    @NonNull
    @Override
    public MesajOlusturAdapter.MesajViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MesajViewHolder mesajViewHolder = new MesajViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mesaj_olustur_layout,parent,false));
        return mesajViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MesajOlusturAdapter.MesajViewHolder holder, int position) {
        ModelMesaj modelMesaj = mesajListesi.get(position);
        holder.setData(modelMesaj);
    }

    @Override
    public int getItemCount() {
        return mesajListesi.size();
    }

    public class MesajViewHolder extends RecyclerView.ViewHolder {

        TextView mesaj_layout1,mesaj_layout2;
        public MesajViewHolder(@NonNull View itemView){
            super(itemView);
            mesaj_layout1 = itemView.findViewById(R.id.mesaj_layout1);
            mesaj_layout2 = itemView.findViewById(R.id.mesaj_layout2);
        }

        public void setData(ModelMesaj modelMesaj) {
            mesaj_layout1.setText(modelMesaj.getMessage());
            mesaj_layout2.setText(modelMesaj.getMessageAciklama());
        }
    }
}
