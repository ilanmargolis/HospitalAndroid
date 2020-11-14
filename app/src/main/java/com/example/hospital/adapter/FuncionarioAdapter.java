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
import com.example.hospital.activities.FuncionarioDadosActivity;
import com.example.hospital.model.Funcionario;

import java.io.Serializable;
import java.util.List;

public class FuncionarioAdapter extends RecyclerView.Adapter<FuncionarioAdapter.FuncionarioHolder> {

    private Context context;
    private List<Funcionario> funcionarioList;
    private final LayoutInflater layoutInflater;

    public FuncionarioAdapter(Context context, List<Funcionario> funcionarioList) {
        this.context = context;
        this.funcionarioList = funcionarioList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public FuncionarioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.layout_funcionario, parent, false);

        return new FuncionarioHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FuncionarioHolder holder, int i) {

        holder.tvAdapterFuncionarioNome.setText(funcionarioList.get(i).getNome());
        holder.tvAdapterFuncionarioSetor.setText(funcionarioList.get(i).getSetor().getNome().substring(0, 5));

//                holder.tvAdapterFuncionarioSetor.setTextColor(context.getColor(R.color.vermelho));
    }

    @Override
    public int getItemCount() {
        return this.funcionarioList != null ? this.funcionarioList.size() : 0;
    }

    public class FuncionarioHolder extends RecyclerView.ViewHolder {
        TextView tvAdapterFuncionarioNome, tvAdapterFuncionarioSetor;
        LinearLayout llFuncionario;

        public FuncionarioHolder(@NonNull View itemView) {
            super(itemView);

            tvAdapterFuncionarioNome = (TextView) itemView.findViewById(R.id.tvAdapterFuncionarioNome);
            tvAdapterFuncionarioSetor = (TextView) itemView.findViewById(R.id.tvAdapterFuncionarioSetor);
            llFuncionario = (LinearLayout) itemView.findViewById(R.id.llFuncionario);

            llFuncionario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FuncionarioDadosActivity.class);
                    intent.putExtra("funcionario", (Serializable) funcionarioList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
