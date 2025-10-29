package com.example.finecontrolapp.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "transactions",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "email",         // column in User
                childColumns = "userEmail",      // column in Transaction
                onDelete = ForeignKey.CASCADE    // delete all transactions if user is deleted
        ),
        indices = {@Index(value = "userEmail")} // speeds up queries
)

public class Transactions {

    @PrimaryKey(autoGenerate = true)
    public int transactionID;
    @ColumnInfo(name = "userEmail")
    public String userEmail;
    @ColumnInfo(name = "description")
    public String description;
    @ColumnInfo(name = "type")
    public String type;
    @ColumnInfo(name = "amount")
    public double amount;
    @ColumnInfo(name = "category")
    public String category;
    @ColumnInfo(name = "date")
    public String date;



    public Transactions(String description, String type, double amount, String category, String date, String userEmail) {

        this.description = description;
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.userEmail = userEmail;

    }

}