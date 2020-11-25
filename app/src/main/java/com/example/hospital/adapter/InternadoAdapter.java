package com.example.hospital.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital.R;
import com.example.hospital.activities.AltaDadosActivity;
import com.example.hospital.activities.PrescreverDadosActivity;
import com.example.hospital.activities.TransferenciaDadosActivity;
import com.example.hospital.model.Internado;
import com.example.hospital.model.Medico;
import com.example.hospital.util.Utils;

import java.io.Serializable;
import java.util.List;

public class InternadoAdapter extends RecyclerView.Adapter<InternadoAdapter.InternadoHolder> {

    private Context context;
    private List<Internado> internadoList;
    private final LayoutInflater layoutInflater;
    private LinearLayout llAdapterInternadoLeito;
    private Medico medico;
    private String tela;

    public InternadoAdapter(Context context, List<Internado> internadoList, Medico medico, String tela) {
        this.context = context;
        this.internadoList = internadoList;
        this.medico = medico;
        this.tela = tela;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public InternadoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.layout_internado, parent, false);

        return new InternadoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InternadoHolder holder, int i) {

        holder.tvAdapterInternadoPacienteNome.setText(internadoList.get(i).getPaciente().getNome().toString());
        holder.tvAdapterInterndoDtInternacao.setText("entrada: " + Utils.dateToString(internadoList.get(i).getDataPrevisaoAlta(), "dd/MM/yyyy"));
        holder.tvAdapterInternadoLeito.setText(internadoList.get(i).getLeito().getCodigo());
    }

    @Override
    public int getItemCount() {
        return this.internadoList != null ? this.internadoList.size() : 0;
    }

    public class InternadoHolder extends RecyclerView.ViewHolder {
        TextView tvAdapterInternadoPacienteNome, tvAdapterInterndoDtInternacao, tvAdapterInternadoLeito;

        public InternadoHolder(@NonNull View itemView) {
            super(itemView);

            tvAdapterInternadoPacienteNome = (TextView) itemView.findViewById(R.id.tvAdapterInternadoPacienteNome);
            tvAdapterInterndoDtInternacao = (TextView) itemView.findViewById(R.id.tvAdapterInterndoDtInternacao);
            tvAdapterInternadoLeito = (TextView) itemView.findViewById(R.id.tvAdapterInternadoLeito);
            llAdapterInternadoLeito = (LinearLayout) itemView.findViewById(R.id.llAdapterInternadoLeito);

            llAdapterInternadoLeito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;

                    if (medico !=  null) {
                        if (tela.equals(context.getResources().getString(R.string.tela_alta))) {
                            intent = new Intent(context, AltaDadosActivity.class);
                        } else {
                            intent = new Intent(context, PrescreverDadosActivity.class);
                        }

                        intent.putExtra("medico", (Serializable) medico);
                    } else {
                        intent = new Intent(context, TransferenciaDadosActivity.class);
                    }

                    intent.putExtra("internado", (Serializable) internadoList.get(getAdapterPosition()));

                    context.startActivity(intent);
                }
            });
        }
    }

}