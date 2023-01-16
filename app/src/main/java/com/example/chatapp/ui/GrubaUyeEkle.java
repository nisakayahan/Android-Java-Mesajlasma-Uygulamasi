package com.example.chatapp.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapp.ModelGrup;
import com.example.chatapp.ModelMesaj;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class GrubaUyeEkle extends Fragment {

    TextView baslik_uye_ekle,baslik_secili_grup;
    RecyclerView recycleuyeEkle, recycleSeciliGrup;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ArrayList<ModelGrup> grupList;
    ModelGrup selectedGrup;

    public GrubaUyeEkle() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gruba_uye_ekle, container, false);

        baslik_uye_ekle = view.findViewById(R.id.baslik_uye_ekle);
        baslik_secili_grup = view.findViewById(R.id.baslik_secili_grup);
        recycleuyeEkle = view.findViewById(R.id.recycleuyeEkle);
        recycleSeciliGrup = view.findViewById(R.id.recycleSeciliGrup);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        grupList = new ArrayList<>();

        KisileriAl();
        return view;
    }
    public void KisileriAl (){
        String userId = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore.collection("/users/"+ userId + "/messages").get().addOnSuccessListener(queryDocumentSnapshots -> {
            grupList.clear();
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                ModelGrup modelGrup = new ModelGrup(documentSnapshot.getString("grupAdi"), documentSnapshot.getString("aciklama"), documentSnapshot.getString("grupSimge"), (List<String>)documentSnapshot.get("number"), documentSnapshot.getId());
//                modelGrup.setUid(documentSnapshot.getId());
                grupList.add(new ModelGrup());
            }

            recycleuyeEkle.setAdapter(new GrupOlusturAdapter(grupList));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            recycleuyeEkle.setLayoutManager(linearLayoutManager);
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Mesajlar alınamadı", Toast.LENGTH_SHORT).show();
        });
    }
}