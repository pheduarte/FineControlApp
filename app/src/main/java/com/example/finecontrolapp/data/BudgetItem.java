package com.example.finecontrolapp.data;

public class BudgetItem {
    private final int iconRes;
    private final String title;
    private final String amount;

    public BudgetItem(int iconRes, String title, String amount) {
        this.iconRes = iconRes;
        this.title = title;
        this.amount = amount;
    }

    public int getIconRes() { return iconRes;}
    public String getTitle() { return title;}
    public String getAmount() { return amount;}

}
