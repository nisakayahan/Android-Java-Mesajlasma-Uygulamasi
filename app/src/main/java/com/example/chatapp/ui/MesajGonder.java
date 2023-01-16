package com.example.chatapp.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.chatapp.ModelMesaj;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class MesajGonder extends Fragment {

    RecyclerView recyclemesajgonder1,recyclemesajgonder2;
    Button buttonmesajyolla;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    ArrayList<ModelMesaj> mesajList;
    ModelMesaj seciliModelMesaj;

    public MesajGonder() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_mesaj_gonder, container, false);

         recyclemesajgonder1 = view.findViewById(R.id.recyclemesajgonder1);
         recyclemesajgonder2 = view.findViewById(R.id.recyclemesajgonder2);
         buttonmesajyolla = view.findViewById(R.id.buttonmesajyolla);

            firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseAuth = FirebaseAuth.getInstance();

            mesajList = new ArrayList<>();

            MesajlariCek();
        return view;
    }

    public void MesajlariCek(){
        String userId = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore.collection("/users/"+ userId + "/messages").get().addOnSuccessListener(queryDocumentSnapshots -> {
            mesajList.clear();
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                ModelMesaj modelMesaj = new ModelMesaj(documentSnapshot.getString("mesajAdi"),documentSnapshot.getString("mesajAciklama"),documentSnapshot.getId());
                modelMesaj.setUid(documentSnapshot.getId());
                mesajList.add(modelMesaj);
            }

            recyclemesajgonder2.setAdapter(new MesajOlusturAdapter(mesajList));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclemesajgonder2.setLayoutManager(linearLayoutManager);
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Mesajlar alınamadı", Toast.LENGTH_SHORT).show();
        });
    }
}









