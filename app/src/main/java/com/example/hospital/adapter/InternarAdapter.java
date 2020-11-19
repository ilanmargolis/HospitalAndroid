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
import com.example.hospital.activities.InternarDadosActivity;
import com.example.hospital.model.Leito;
import com.example.hospital.util.Utils;

import java.io.Serializable;
import java.util.List;

public class InternarAdapter extends RecyclerView.Adapter<InternarAdapter.LeitoHolder> {

    private Context context;
    private List<Leito> leitoList;
    private final LayoutInflater layoutInflater;
    private LinearLayout llInternarLeito;

    public InternarAdapter(Context context, List<Leito> leitoList) {
        this.context = context;
        this.leitoList = leitoList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public LeitoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.layout_internar, parent, false);

        return new LeitoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LeitoHolder holder, int i) {

        llInternarLeito.setBackgroundColor(Utils.zebrarGrid(context, i));

        holder.tvAdapterInternarLeitoNome.setText(leitoList.get(i).getCodigo());
    }

    @Override
    public int getItemCount() {
        return this.leitoList != null ? this.leitoList.size() : 0;
    }

    public class LeitoHolder extends RecyclerView.ViewHolder {
        TextView tvAdapterInternarLeitoNome;

        public LeitoHolder(@NonNull View itemView) {
            super(itemView);

            tvAdapterInternarLeitoNome = (TextView) itemView.findViewById(R.id.tvAdapterInternarLeitoNome);
            llInternarLeito = (LinearLayout) itemView.findViewById(R.id.llInternarLeito);

            llInternarLeito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, InternarDadosActivity.class);
                    intent.putExtra("internar", (Serializable) leitoList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}