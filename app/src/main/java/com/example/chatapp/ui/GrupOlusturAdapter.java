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

import java.util.ArrayList;
import java.util.List;

public class GrupOlusturAdapter extends RecyclerView.Adapter<GrupOlusturAdapter.GrupViewHolder> {

        List<ModelGrup> grupListesi;

        public GrupOlusturAdapter(List<ModelGrup> grupListesi) {
            this.grupListesi = grupListesi;
        }

        @NonNull
        @Override
        public GrupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
             GrupViewHolder grupViewHolder = new GrupViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.grup_olustur_layout,parent,false));
                return grupViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull GrupViewHolder holder, int position) {
            ModelGrup modelGrup = grupListesi.get(position);
            holder.setData(modelGrup);
//            holder.textViewGrupAdi.setText(modelGrup.getGrupadi());
//            holder.textViewGrupAciklama.setText(modelGrup.getGrupaciklama());
//            Picasso.get().load(modelGrup.getGrupimage()).into(holder.imageViewGrupSimge);
        }

        @Override
        public int getItemCount() {
            return grupListesi.size();
        }

        public class GrupViewHolder extends RecyclerView.ViewHolder{

            ImageView imageViewlayout;
            TextView textViewlayout1,textViewlayout2;

            public GrupViewHolder(@NonNull View itemView) {
                super(itemView);
                imageViewlayout = itemView.findViewById(R.id.imageViewlayout);
                textViewlayout1 = itemView.findViewById(R.id.textViewlayout1);
                textViewlayout2 = itemView.findViewById(R.id.textViewlayout2);
            }

            public void setData(ModelGrup modelGrup) {
                textViewlayout1.setText(modelGrup.getGrupadi());
                textViewlayout2.setText(modelGrup.getGrupaciklama());
            }
        }
}
