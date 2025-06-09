package com.example.bookshare.adapters;

import com.example.bookshare.models.Interesse;
import com.example.bookshare.models.Livro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.bookshare.R;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class LivroAdapter extends RecyclerView.Adapter<LivroAdapter.LivroViewHolder> {

    private Context context;
    private List<Livro> listaLivros;

    public LivroAdapter(Context context, List<Livro> listaLivros) {
        this.context = context;
        this.listaLivros = listaLivros;
    }

    @NonNull
    @Override

    public LivroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_main, parent, false);
        return new LivroViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull LivroViewHolder holder, int position) {
        Livro livro = listaLivros.get(position);

        holder.txtTitulo.setText(livro.getTitulo());
        holder.txtAutor.setText("Autor: " + livro.getAutor());
        holder.txtGenero.setText("Gênero: " + livro.getGenero());

        holder.btnInteresse.setOnClickListener(v -> {
            String userIdInteressado = FirebaseAuth.getInstance().getCurrentUser().getUid();

            Map<String, Object> interesse = new HashMap<>();
            interesse.put("tituloLivro", livro.getTitulo());
            interesse.put("userIdInteressado", userIdInteressado);

            FirebaseFirestore.getInstance().collection("interesses").add(interesse)
                    .addOnSuccessListener(docRef ->
                            Toast.makeText(context, "Interesse registrado!", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e ->
                            Toast.makeText(context, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });
    }

    @Override
    public int getItemCount() {
        return listaLivros.size();
    }

    public static class LivroViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitulo, txtAutor, txtGenero;
        Button btnInteresse;

        public LivroViewHolder(@NonNull View itemView) {
            super(itemView);
            // IDs do layout item_livro.xml (confirme os nomes)
            txtTitulo = itemView.findViewById(R.id.edtTitulo);
            txtAutor = itemView.findViewById(R.id.edtAutor);
            txtGenero = itemView.findViewById(R.id.edtGenero);
            btnInteresse = itemView.findViewById(R.id.btnLogin);
        }
    }
}
