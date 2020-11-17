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
import com.example.hospital.activities.MedicoDadosActivity;
import com.example.hospital.model.Medico;
import com.example.hospital.util.Utils;

import java.io.Serializable;
import java.util.List;

public class MedicoAdapter extends RecyclerView.Adapter<MedicoAdapter.MedicoHolder> {

    private Context context;
    private List<Medico> medicoList;
    private final LayoutInflater layoutInflater;
    private LinearLayout llMedico;

    public MedicoAdapter(Context context, List<Medico> medicoList) {
        this.context = context;
        this.medicoList = medicoList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MedicoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.layout_medico, parent, false);

        return new MedicoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicoHolder holder, int i) {

        llMedico.setBackgroundColor(Utils.zebrarGrid(context, i));

        holder.tvAdapterMedicoNome.setText(medicoList.get(i).getNome());
        holder.tvAdapterMedicoCbos.setText(medicoList.get(i).getCbos().getDescricao());
    }

    @Override
    public int getItemCount() {
        return this.medicoList != null ? this.medicoList.size() : 0;
    }

    public class MedicoHolder extends RecyclerView.ViewHolder {
        TextView tvAdapterMedicoNome, tvAdapterMedicoCbos;

        public MedicoHolder(@NonNull View itemView) {
            super(itemView);

            tvAdapterMedicoNome = (TextView) itemView.findViewById(R.id.tvAdapterMedicoNome);
            tvAdapterMedicoCbos = (TextView) itemView.findViewById(R.id.tvAdapterMedicoCbos);
            llMedico = (LinearLayout) itemView.findViewById(R.id.llMedico);

            llMedico.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MedicoDadosActivity.class);
                    intent.putExtra("medico", (Serializable) medicoList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
