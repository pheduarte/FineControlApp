package com.example.finecontrolapp.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finecontrolapp.R;
import com.example.finecontrolapp.ui.main.data.BudgetItem;

import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewHolder> {

    private final List<BudgetItem> budgetList;

    public BudgetAdapter(List<BudgetItem> budgetList) {
            this.budgetList = budgetList;;
    }

    @NonNull
    @Override
    public BudgetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_budget, parent, false);
        return new BudgetAdapter.ViewHolder(view);
    }

    public int getItemCount() {
        return budgetList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetAdapter.ViewHolder holder, int position) {
        BudgetItem item = budgetList.get(position);
        holder.txtCategory.setText(item.getTitle());
        holder.img_icon_budget.setImageResource(item.getIconRes());
        holder.txtAmount.setText(item.getAmount());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_icon_budget;
        TextView txtCategory, txtAmount;

        ViewHolder(View itemView) {
            super(itemView);
            img_icon_budget = itemView.findViewById(R.id.img_icon_budget);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtAmount = itemView.findViewById(R.id.txtAmount);
        }
    }
}
