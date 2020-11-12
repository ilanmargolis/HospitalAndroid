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
import com.example.hospital.model.Medicamento;
import com.example.hospital.util.Utils;

import java.io.Serializable;
import java.util.List;

public class MedicamentoAdapter extends RecyclerView.Adapter<MedicamentoAdapter.MedicamentoHolder> {

    private Context context;
    private List<Medicamento> medicamentoList;
    private final LayoutInflater layoutInflater;

    public MedicamentoAdapter(Context context, List<Medicamento> medicamentoList) {
        this.context = context;
        this.medicamentoList = medicamentoList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MedicamentoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.layout_medicamento, parent, false);

        return new MedicamentoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentoHolder holder, int i) {
        holder.tvMedicamentoNome.setText(medicamentoList.get(i).getNome());
        holder.tvMedicamentoTermo.setText(medicamentoList.get(i).getTerminologia().getSigla());
        holder.getTvMedicamentoValidade.setText("val: " + Utils.dateToString(medicamentoList.get(i).getDataValidade(), "dd/MM/yyyy"));
    }

    @Override
    public int getItemCount() {
        return this.medicamentoList != null ? this.medicamentoList.size() : 0;
    }

    public class MedicamentoHolder extends RecyclerView.ViewHolder {
        TextView tvMedicamentoNome, tvMedicamentoTermo, getTvMedicamentoValidade;
        LinearLayout llMedicamento;

        public MedicamentoHolder(@NonNull View itemView) {
            super(itemView);

            tvMedicamentoNome = (TextView) itemView.findViewById(R.id.tvMedicamentoNome);
            tvMedicamentoTermo = (TextView) itemView.findViewById(R.id.tvMedicamentoTermo);
            getTvMedicamentoValidade = (TextView) itemView.findViewById(R.id.tvMedicamentoValidade);
            llMedicamento = (LinearLayout) itemView.findViewById(R.id.llMedicamento);

            llMedicamento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MedicamentoDadosActivity.class);
                    intent.putExtra("medicamento", (Serializable) medicamentoList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
