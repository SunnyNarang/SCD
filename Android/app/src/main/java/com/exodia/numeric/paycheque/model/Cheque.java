package com.exodia.numeric.paycheque.model;

/**
 * Created by hp on 4/21/2017.
 */

public class Cheque {
    private String account_no,name,amount,issued_to,recieverPhone,senderPhone,cheque_id;

    public Cheque(String cheque_id,String account_no, String name, String amount, String issued_to,String recieverPhone,String senderPhone) {
        this.account_no = account_no;
        this.name = name;
        this.amount = amount;
        this.issued_to = issued_to;
        this.recieverPhone = recieverPhone;
        this.senderPhone = senderPhone;
        this.cheque_id = cheque_id;
    }

    public String getAccount_no() {
        return account_no;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public String getIssued_to() {
        return issued_to;
    }
    public String getRecieverPhone(){
        return recieverPhone;
    }
    public String getSenderPhone(){
        return senderPhone;
    }

    public String getCheque_id() {
        return cheque_id;
    }
}
