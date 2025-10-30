package com.pheduarte.finecontrol.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.pheduarte.finecontrol.R;
import com.pheduarte.finecontrol.data.BudgetItem;

import java.util.List;
import java.util.Locale;

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

        // Parse amount safely
        double amount = 0.0;
        try {
            amount = Double.parseDouble(item.getAmount().replace("$", "").trim());
        } catch (NumberFormatException ignored) {}

        // Format value with sign and color
        String formattedAmount = String.format(Locale.getDefault(), "%.2f", Math.abs(amount));

        if (amount > 0) {
            // Positive = green and plus sign
            holder.txtAmount.setText(String.format("$%s", formattedAmount));
            holder.txtAmount.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.greenIncome));
        } else if (amount < 0) {
            // Negative = red and minus sign
            holder.txtAmount.setText(String.format("-$%s", formattedAmount));
            holder.txtAmount.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.redExpense));
        } else {
            // Zero = neutral
            holder.txtAmount.setText("$0.00");
            holder.txtAmount.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.black_overlay));
        }
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

    public void updateAmountForCategory(String category, String newAmount) {
        for (BudgetItem item : budgetList) {
            if (item.getTitle().equalsIgnoreCase(category)) {
                int index = budgetList.indexOf(item);
                budgetList.set(index, new BudgetItem(item.getIconRes(), item.getTitle(), newAmount));
                notifyItemChanged(index);
                break;
            }
        }
    }
}
