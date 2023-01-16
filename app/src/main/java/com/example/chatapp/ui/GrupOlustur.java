package com.example.chatapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chatapp.ModelGrup;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GrupOlustur extends Fragment {

    EditText editTextAciklama,editTextGrupAdi;
    Button button_grup_olustur;
    RecyclerView recviewGrup;
    ImageView imageGrupSimge;

    Uri filePath;

    FirebaseAuth auth;
    FirebaseFirestore fireBaseFireStore;
    FirebaseStorage firebaseStorage;

    ArrayList<ModelGrup> grupListesi;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_grup_olustur, container, false);
        editTextAciklama = view.findViewById(R.id.editTextAciklama);
        editTextGrupAdi = view.findViewById(R.id.editTextGrupAdi);
        button_grup_olustur = view.findViewById(R.id.button_grup_olustur);
        recviewGrup = view.findViewById(R.id.recviewGrup);
        imageGrupSimge = view.findViewById(R.id.imageGrupSimge);

        auth = FirebaseAuth.getInstance();
        fireBaseFireStore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        grupListesi = new ArrayList<>();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK) {
                        Intent data = result.getData();
                        filePath = data.getData();
                        imageGrupSimge.setImageURI(filePath);
                    }
                }
            );
        imageGrupSimge.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activityResultLauncher.launch(intent);
        });


        button_grup_olustur.setOnClickListener(v -> {
           String grupAdi = editTextGrupAdi.getText().toString();
           String aciklama = editTextAciklama.getText().toString();

           if(grupAdi.isEmpty() ){
               editTextGrupAdi.setError("Grup adı boş olamaz");
               return;
        }
           if(aciklama.isEmpty()){
               editTextAciklama.setError("Açıklama boş olamaz");
               return;
           }
           if(filePath != null){
               StorageReference storageReference = firebaseStorage.getReference().child("grup_simgeleri");
                storageReference.putFile(filePath).addOnSuccessListener(taskSnapshot -> {
                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        String downloadUrl = uri.toString();
                        GrubuOlustur(downloadUrl,grupAdi,aciklama);

                    });
                });
           }


//              else{
//                HashMap<String,Object> hashMap = new HashMap<>();
//                hashMap.put("grupAdi",grupAdi);
//                hashMap.put("aciklama",aciklama);
//                hashMap.put("grupSimge",filePath.toString());
//
//                fireBaseFireStore.collection("Gruplar").add(hashMap).addOnSuccessListener(documentReference -> {
//                     Toast.makeText(getContext(), "Grup oluşturuldu", Toast.LENGTH_SHORT).show();
//                }).addOnFailureListener(e -> {
//                     Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                });

          });
        VerileriCek();
       return view;
         }

    //  Grubu oluştur
         private void GrubuOlustur(String grupAdi, String aciklama, String grupSimge) {
                //Grubun id sini al
                 String userId = auth.getCurrentUser().getUid();
                 fireBaseFireStore.collection("/users/" + userId + "/groups/").add(
                         new HashMap<String, Object>() {
                             {
                                 put("grupAdi", grupAdi);
                                 put("aciklama", aciklama);
                                 put("grupSimge", grupSimge);
                                 put("numbers", new ArrayList<String>());
                             }
                         }
                 ).addOnSuccessListener(documentReference1 -> {
                     Toast.makeText(getContext(), "Grup oluşturuldu", Toast.LENGTH_SHORT).show();

                     documentReference1.get().addOnSuccessListener(documentSnapshot -> {
                         ModelGrup modelGrup = new ModelGrup(grupSimge, grupAdi, aciklama, documentSnapshot.getId(), new ArrayList<String>());
                            grupListesi.add(modelGrup);
                            recviewGrup.getAdapter().notifyItemInserted(grupListesi.size() - 1);
                         String grupId = documentSnapshot.getId();
                         fireBaseFireStore.collection("/users/" + userId + "/groups/").document(grupId).update("grupId", grupId);
                     });
                 }).addOnFailureListener(e -> {
                     Toast.makeText(getContext(), "Grup Olusturulamadi", Toast.LENGTH_SHORT).show();
                 });
             }

          private void VerileriCek(){
                 String userId = auth.getCurrentUser().getUid();
              fireBaseFireStore.collection("/users/" + userId + "/groups/").get().addOnSuccessListener(queryDocumentSnapshots -> {
                  grupListesi.clear();
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                        ModelGrup modelGrup = new ModelGrup(documentSnapshot.getString("grupAdi"), documentSnapshot.getString("aciklama"), documentSnapshot.getString("grupSimge"), (List<String>)documentSnapshot.get("number"), documentSnapshot.getId());
                        grupListesi.add(modelGrup);

//                        recviewGrup.setLayoutManager(new LinearLayoutManager(getContext()));
//                        recviewGrup.setLayoutManager();
                    }
                  recviewGrup.setAdapter(new GrupOlusturAdapter(grupListesi));
                  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                  recviewGrup.setLayoutManager(linearLayoutManager);
              });
          }
         }

