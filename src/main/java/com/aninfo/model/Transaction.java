package com.aninfo.model;

import javax.persistence.*;

@Entity
public class Transaction {
    // aca se seteaan los atributos (gets), no se hace logica
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionID;
    private Long cbu;
    private Double value;
    private String type; // withdraw o deposit


    public Transaction(){}

    public Transaction(String type,  Long cbu, Double transactionValue){

        this.cbu = cbu;
        this.value = transactionValue;
        this.type = type;
    }

    public Long getTransactionID() {return transactionID;}
    public void setTransactionID(Long transactionID) {this.transactionID = transactionID;}

    public double getAmount() {return value;}
    public void setAmount(double amount) {this.value = amount;}

    public long getCbu() { return cbu; }
    public void setCbu(long cbu) {this.cbu = cbu;}

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

}