package com.example.hospital.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital.R;
import com.example.hospital.activities.UnidadeDadosActivity;
import com.example.hospital.model.Unidade;

import java.io.Serializable;
import java.util.List;

public class UnidadeAdapter extends RecyclerView.Adapter<com.example.hospital.adapter.UnidadeAdapter.UnidadeHolder> {

    private Context context;
    private List<Unidade> unidadeList;
    private final LayoutInflater layoutInflater;

    public UnidadeAdapter(Context context, List<Unidade> unidadeList) {
        this.context = context;
        this.unidadeList = unidadeList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public UnidadeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.layout_unidade, parent, false);

        return new UnidadeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UnidadeHolder holder, int i) {
        holder.tvUnidade.setText(unidadeList.get(i).getNome());
    }

    @Override
    public int getItemCount() {
        return this.unidadeList != null ? this.unidadeList.size() : 0;
    }

    public class UnidadeHolder extends RecyclerView.ViewHolder {
        TextView tvUnidade;

        public UnidadeHolder(@NonNull View itemView) {
            super(itemView);

            tvUnidade = (TextView) itemView.findViewById(R.id.tvUnidade);

            tvUnidade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UnidadeDadosActivity.class);
                    intent.putExtra("unidade", (Serializable) unidadeList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
