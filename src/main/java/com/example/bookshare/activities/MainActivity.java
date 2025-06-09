package com.example.bookshare.activities;
import com.example.bookshare.models.Livro;
import com.example.bookshare.adapters.LivroAdapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.bookshare.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LivroAdapter livroAdapter;
    private List<Livro> listaLivros;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewLivros);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaLivros = new ArrayList<>();
        livroAdapter = new LivroAdapter(this, listaLivros);
        recyclerView.setAdapter(livroAdapter);

        db = FirebaseFirestore.getInstance();

        carregarLivros();
    }

    private void carregarLivros() {
        db.collection("livros").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    listaLivros.clear();
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        Livro livro = document.toObject(Livro.class);
                        listaLivros.add(livro);
                    }
                    livroAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Pode colocar Toast de erro
                });
    }
}

