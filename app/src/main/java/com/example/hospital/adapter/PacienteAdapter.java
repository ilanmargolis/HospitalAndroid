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
import com.example.hospital.model.Paciente;

import java.io.Serializable;
import java.util.List;

public class PacienteAdapter extends RecyclerView.Adapter<PacienteAdapter.PacienteHolder> {

    private Context context;
    private List<Paciente> pacienteList;
    private final LayoutInflater layoutInflater;

    public PacienteAdapter(Context context, List<Paciente> pacienteList) {
        this.context = context;
        this.pacienteList = pacienteList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PacienteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.layout_paciente, parent, false);

        return new PacienteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PacienteHolder holder, int i) {
        holder.tvPaciente.setText(pacienteList.get(i).getNome());
    }

    @Override
    public int getItemCount() {
        return this.pacienteList != null ? this.pacienteList.size() : 0;
    }

    public class PacienteHolder extends RecyclerView.ViewHolder {
        TextView tvPaciente;

        public PacienteHolder(@NonNull View itemView) {
            super(itemView);

            tvPaciente = (TextView) itemView.findViewById(R.id.tvPaciente);

            tvPaciente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UnidadeDadosActivity.class);
                    intent.putExtra("paciente", (Serializable) pacienteList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
