package model;

import dao.*;

public class ATMTransaction {
    private ATMTransactionDAO dao;
    private int transactionID;
    private String date;
    private float amount;

    public ATMTransaction(ATMTransactionDAO dao, int TransactionID, String date, float amount)
    {
            this.dao = dao;
            this.transactionID = TransactionID;
            this.date = date;
            this.amount = amount;
    }

    public int getTransactionID(){ 
        return transactionID;
    }

    public String getDate(){ 
        return date;
    }
    public void setDate(String date){ 
        this.date = date;
    }
    public float getAmount(){ 
        return amount;
    }
    public void setAmount(float amount){ 
        this.amount = amount;
    }

}
