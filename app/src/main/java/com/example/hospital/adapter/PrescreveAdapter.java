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
import com.example.hospital.activities.MedicamentoDadosActivity;
import com.example.hospital.model.Prescreve;
import com.example.hospital.util.Utils;

import java.io.Serializable;
import java.util.List;

public class PrescreveAdapter extends RecyclerView.Adapter<PrescreveAdapter.PrescreveHolder> {

    private Context context;
    private List<Prescreve> prescreveList;
    private final LayoutInflater layoutInflater;
    private LinearLayout llPrescreve;

    public PrescreveAdapter(Context context, List<Prescreve> prescreveList) {
        this.context = context;
        this.prescreveList = prescreveList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PrescreveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.layout_prescreve, parent, false);

        return new PrescreveHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PrescreveHolder holder, int i) {

        llPrescreve.setBackgroundColor(Utils.zebrarGrid(context, i));

        holder.tvAdapterPrescreveMedicamento.setText(prescreveList.get(i).getMedicamento().getNome());
        holder.tvAdapterPrescreveDosagem.setText(String.valueOf(prescreveList.get(i).getDosagem()) + " " +
                                                 prescreveList.get(i).getMedicamento().getTerminologia().getSigla());
        holder.tvAdapterPrescreveHorario.setText(prescreveList.get(i).getHorario());
    }

    @Override
    public int getItemCount() {
        return this.prescreveList != null ? this.prescreveList.size() : 0;
    }

    public class PrescreveHolder extends RecyclerView.ViewHolder {
        TextView tvAdapterPrescreveMedicamento, tvAdapterPrescreveDosagem, tvAdapterPrescreveHorario;

        public PrescreveHolder(@NonNull View itemView) {
            super(itemView);

            tvAdapterPrescreveMedicamento = (TextView) itemView.findViewById(R.id.tvAdapterPrescreveMedicamento);
            tvAdapterPrescreveDosagem = (TextView) itemView.findViewById(R.id.tvAdapterPrescreveDosagem);
            tvAdapterPrescreveHorario = (TextView) itemView.findViewById(R.id.tvAdapterPrescreveHorario);
            llPrescreve = (LinearLayout) itemView.findViewById(R.id.llPrescreve);

            llPrescreve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MedicamentoDadosActivity.class);
                    intent.putExtra("prescreve", (Serializable) prescreveList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
