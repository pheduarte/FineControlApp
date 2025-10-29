package com.example.finecontrolapp.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finecontrolapp.R;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<Transactions> transactionList = new ArrayList<>();

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transactions transaction = transactionList.get(position);

        Double amount = transaction.amount;
        String formattedAmount = String.format("%.2f", Math.abs(amount));

        if ("income".equalsIgnoreCase(transaction.type)) {
            holder.txtAmount.setText(String.format("$%s", formattedAmount));
            holder.txtAmount.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.greenIncome));
        } else if ("expense".equalsIgnoreCase(transaction.type)) {
            holder.txtAmount.setText(String.format("-$%s", formattedAmount));
            holder.txtAmount.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.redExpense));
        } else {
            holder.txtAmount.setText(String.format("$%s", formattedAmount));
            holder.txtAmount.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.black_overlay));
        }

        holder.txtCategory.setText(transaction.category);
        holder.txtDescription.setText(transaction.description);
        holder.txtDate.setText(transaction.date);
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public void setTransactions(List<Transactions> transactions) {
        this.transactionList = transactions;
        notifyDataSetChanged();
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategory, txtDescription, txtAmount, txtDate;

        TransactionViewHolder(View itemView) {
            super(itemView);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}
