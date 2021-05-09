package edu.self;

import java.util.Date;

public class Transaction {
    private double amount;
    private Date timestamp;
    private String memo;


    public String getSummaryLine() {
        if (this.amount >= 0) {
            return String.format("%s : $%.02f : %s", this.timestamp.toString(), this.amount, this.memo);
        } else {
            return String.format("%s : $(%.02f) : %s", this.timestamp.toString(), -this.amount, this.memo);
        }
    }

    public double getAmount() {
        return this.amount;
    }
}
