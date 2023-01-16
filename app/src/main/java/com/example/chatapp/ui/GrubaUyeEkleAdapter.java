package com.example.chatapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.ModelGrup;
import com.example.chatapp.R;

import java.util.List;

public class GrubaUyeEkleAdapter extends RecyclerView.Adapter<GrubaUyeEkleAdapter.GrupViewHolder> {

    List<ModelGrup> grupListesi;

    public GrubaUyeEkleAdapter(List<ModelGrup> grupListesi) {
        this.grupListesi = grupListesi;
    }

    @NonNull
    @Override
    public GrupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GrupViewHolder grupViewHolder = new GrupViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.grube_uye_ekle_layout,parent,false));
        return grupViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GrupViewHolder holder, int position) {
        ModelGrup modelGrup = grupListesi.get(position);
        holder.setData(modelGrup);
    }

    @Override
    public int getItemCount() {
        return grupListesi.size();
    }

    public class GrupViewHolder extends RecyclerView.ViewHolder{

        ImageView uyeEkle_grupResmi;
        TextView uyeEkle_grupAdi,uyeEkle_grupAciklama;

        public GrupViewHolder( View itemView) {
            super(itemView);
            uyeEkle_grupResmi = itemView.findViewById(R.id.uyeEkle_grupResmi);
            uyeEkle_grupAdi = itemView.findViewById(R.id.uyeEkle_grupAdi);
            uyeEkle_grupAciklama = itemView.findViewById(R.id.uyeEkle_grupAciklama);
        }

        public void setData(ModelGrup modelGrup) {
            uyeEkle_grupAdi.setText(modelGrup.getGrupadi());
            uyeEkle_grupAciklama.setText(modelGrup.getGrupaciklama());
        }
    }
}
