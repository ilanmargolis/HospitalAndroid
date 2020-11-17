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
import com.example.hospital.activities.SetorDadosActivity;
import com.example.hospital.model.Setor;
import com.example.hospital.util.Utils;

import java.io.Serializable;
import java.util.List;

public class SetorAdapter extends RecyclerView.Adapter<SetorAdapter.SetorHolder> {

    private Context context;
    private List<Setor> setorList;
    private final LayoutInflater layoutInflater;
    private LinearLayout llSetor;

    public SetorAdapter(Context context, List<Setor> setorList) {
        this.context = context;
        this.setorList = setorList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SetorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.layout_setor, parent, false);

        return new SetorHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SetorHolder holder, int i) {

        llSetor.setBackgroundColor(Utils.zebrarGrid(context, i));

        holder.tvAdapterSetorNome.setText(setorList.get(i).getNome());
        holder.tvAdapterSetorRamal.setText(setorList.get(i).getRamal());
    }

    @Override
    public int getItemCount() {
        return this.setorList != null ? this.setorList.size() : 0;
    }

    public class SetorHolder extends RecyclerView.ViewHolder {
        TextView tvAdapterSetorNome, tvAdapterSetorRamal;

        public SetorHolder(@NonNull View itemView) {
            super(itemView);

            tvAdapterSetorNome = (TextView) itemView.findViewById(R.id.tvAdapterSetorNome);
            tvAdapterSetorRamal = (TextView) itemView.findViewById(R.id.tvAdapterSetorRamal);
            llSetor = (LinearLayout) itemView.findViewById(R.id.llSetor);

            llSetor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Setor setor = setorList.get(getAdapterPosition());

                    Intent intent = new Intent(context, SetorDadosActivity.class);
                    intent.putExtra("setor", (Serializable) setor);
                    context.startActivity(intent);
                }
            });
        }
    }
}
