package com.example.bookshare.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookshare.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CadastroLivroActivity extends AppCompatActivity {

    private EditText edtTitulo, edtAutor, edtGenero;
    private Button btnCadastrarLivro;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_livro);

        edtTitulo = findViewById(R.id.edtTitulo);
        edtAutor = findViewById(R.id.edtAutor);
        edtGenero = findViewById(R.id.edtGenero);
        btnCadastrarLivro = findViewById(R.id.btnCadastrarLivro);

        db = FirebaseFirestore.getInstance();

        btnCadastrarLivro.setOnClickListener(v -> cadastrarLivro());
    }

    private void cadastrarLivro() {
        String titulo = edtTitulo.getText().toString().trim();
        String autor = edtAutor.getText().toString().trim();
        String genero = edtGenero.getText().toString().trim();

        if (TextUtils.isEmpty(titulo) || TextUtils.isEmpty(autor) || TextUtils.isEmpty(genero)) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Salvando livro...");
        progressDialog.show();

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> livro = new HashMap<>();
        livro.put("titulo", titulo);
        livro.put("autor", autor);
        livro.put("genero", genero);
        livro.put("userId", userId);

        db.collection("livros").add(livro)
                .addOnSuccessListener(documentReference -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Livro cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Erro ao salvar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
