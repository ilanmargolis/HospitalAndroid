package com.example.hospital.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital.R;
import com.example.hospital.activities.LeitoDadosActivity;
import com.example.hospital.model.Leito;

import java.io.Serializable;
import java.util.List;

public class LeitoAdapter extends RecyclerView.Adapter<LeitoAdapter.LeitoHolder> {

    private Context context;
    private List<Leito> leitoList;
    private final LayoutInflater layoutInflater;

    public LeitoAdapter(Context context, List<Leito> leitoList) {
        this.context = context;
        this.leitoList = leitoList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public LeitoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.layout_leito, parent, false);

        return new LeitoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LeitoHolder holder, int i) {
        holder.tvAdapterLeitoNome.setText(leitoList.get(i).getCodigo());
        holder.tvAdapterLeitoUnidade.setText(leitoList.get(i).getUnidade().getNome());
        holder.tvAdapterLeitoSetor.setText(leitoList.get(i).getSetor().getNome());
    }

    @Override
    public int getItemCount() {
        return this.leitoList != null ? this.leitoList.size() : 0;
    }

    public class LeitoHolder extends RecyclerView.ViewHolder {
        TextView tvAdapterLeitoNome, tvAdapterLeitoUnidade, tvAdapterLeitoSetor;
        LinearLayout llLeito;

        public LeitoHolder(@NonNull View itemView) {
            super(itemView);

            tvAdapterLeitoNome = (TextView) itemView.findViewById(R.id.tvAdapterLeitoNome);
            tvAdapterLeitoUnidade = (TextView) itemView.findViewById(R.id.tvAdapterLeitoUnidade);
            tvAdapterLeitoSetor = (TextView) itemView.findViewById(R.id.tvAdapterLeitoSetor);
            llLeito = (LinearLayout) itemView.findViewById(R.id.llLeito);

            llLeito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LeitoDadosActivity.class);
                    intent.putExtra("leito", (Serializable) leitoList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
