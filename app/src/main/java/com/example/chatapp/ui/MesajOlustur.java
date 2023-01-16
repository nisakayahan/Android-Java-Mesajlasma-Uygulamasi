package com.example.chatapp.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatapp.ModelMesaj;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class MesajOlustur extends Fragment {

    EditText editTextmesajAdi,editTextAciklama;
    Button mesaj_olustur_button;
    RecyclerView recviewmesaj;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    ArrayList<ModelMesaj> mesajList;

    public MesajOlustur() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_mesaj_olustur, container, false);

        editTextmesajAdi = view.findViewById(R.id.editTextmesajAdi);
        editTextAciklama = view.findViewById(R.id.editTextAciklama);
        mesaj_olustur_button = view.findViewById(R.id.mesaj_olustur_button);
        recviewmesaj = view.findViewById(R.id.recviewmesaj);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        mesajList = new ArrayList<>();

        mesaj_olustur_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mesajAdi = editTextmesajAdi.getText().toString();
                String mesajAciklama = editTextAciklama.getText().toString();

                if (mesajAdi.isEmpty() || mesajAciklama.isEmpty()){
                   Toast.makeText(getContext(), "Lütfen tüm alanları doldurunuz", Toast.LENGTH_SHORT).show();
                   return ;
                }
                MesajOlustur(mesajAdi,mesajAciklama);

            }
        });
        MesajlariAl();
        return view;
    }


    public void MesajOlustur(String mesajAdi, String mesajAciklama){
        String userId = firebaseAuth.getCurrentUser().getUid();

        firebaseFirestore.collection("/users/"+ userId + "/messages").add(new HashMap<String, String>(){
            {
                put("mesajAdi",mesajAdi);
                put("mesajAciklama",mesajAciklama);
            }
        }).addOnSuccessListener(documentReference -> {
            Toast.makeText(getContext(), "Mesaj başarıyla oluşturuldu", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Mesaj oluşturulamadı", Toast.LENGTH_SHORT).show();
        });
    }

    public void MesajlariAl(){
        String userId = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore.collection("/users/"+ userId + "/messages").get().addOnSuccessListener(queryDocumentSnapshots -> {
            mesajList.clear();
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                ModelMesaj modelMesaj = new ModelMesaj(documentSnapshot.getString("mesajAdi"),documentSnapshot.getString("mesajAciklama"),documentSnapshot.getId());
                modelMesaj.setUid(documentSnapshot.getId());
                mesajList.add(modelMesaj);
            }

            recviewmesaj.setAdapter(new MesajOlusturAdapter(mesajList));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recviewmesaj.setLayoutManager(linearLayoutManager);
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Mesajlar alınamadı", Toast.LENGTH_SHORT).show();
        });
    }
}
