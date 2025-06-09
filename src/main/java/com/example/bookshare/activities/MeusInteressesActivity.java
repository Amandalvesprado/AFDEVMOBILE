package com.example.bookshare.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookshare.R;
import com.example.bookshare.adapters.LivroAdapter;
import com.example.bookshare.models.Interesse;
import com.example.bookshare.models.Livro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MeusInteressesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LivroAdapter livroAdapter;
    private List<Livro> listaLivros; // Agora é List<Livro>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_interesses);

        recyclerView = findViewById(R.id.recyclerViewInteresses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaLivros = new ArrayList<>();
        livroAdapter = new LivroAdapter(this, listaLivros);
        recyclerView.setAdapter(livroAdapter);

        carregarInteresses();
    }

    private void carregarInteresses() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance().collection("interesses")
                .whereEqualTo("userIdInteressado", userId) // campo correto no interesse
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    listaLivros.clear();

                    List<String> titulos = new ArrayList<>();

                    // Pegando os títulos dos livros de interesse
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Interesse interesse = doc.toObject(Interesse.class);
                        if (interesse != null && interesse.getLivroId() != null) {
                            titulos.add(interesse.getLivroId());
                        }
                    }

                    // Agora buscar os livros que possuem esses títulos
                    if (titulos.isEmpty()) {
                        livroAdapter.notifyDataSetChanged();
                        return;
                    }

                    // Consulta para livros com título em lista de interesses
                    FirebaseFirestore.getInstance().collection("livros")
                            .whereIn("titulo", titulos)
                            .get()
                            .addOnSuccessListener(livrosSnapshot -> {
                                for (DocumentSnapshot doc : livrosSnapshot) {
                                    Livro livro = doc.toObject(Livro.class);
                                    if (livro != null) {
                                        listaLivros.add(livro);
                                    }
                                }
                                livroAdapter.notifyDataSetChanged();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(this, "Erro ao carregar livros.", Toast.LENGTH_SHORT).show();
                            });

                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erro ao carregar interesses.", Toast.LENGTH_SHORT).show();
                });
    }
}
