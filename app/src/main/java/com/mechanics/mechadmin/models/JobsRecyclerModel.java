package com.mechanics.mechadmin.models;

public class JobsRecyclerModel {
    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    private String UID;
    private String amount_paid;
    private String car_type;
    private String card_number;
    private String transact_time;
    private String transact_history;
    private String Image;
    private String Name;
    private String Number;
    private String MyOwnName;
    private String TransactionID;

    public String getTransConfirm() {
        return TransConfirm;
    }

    public void setTransConfirm(String transConfirm) {
        TransConfirm = transConfirm;
    }

    private String TransConfirm;

    public String getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(String transactionID) {
        TransactionID = transactionID;
    }

    public String getMyOwnName() {
        return MyOwnName;
    }

    public void setMyOwnName(String myOwnName) {
        this.MyOwnName = myOwnName;
    }

    public String getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(String amount_paid) {
        this.amount_paid = amount_paid;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getTransact_time() {
        return transact_time;
    }

    public void setTransact_time(String transact_time) {
        this.transact_time = transact_time;
    }

    public String getTransact_history() {
        return transact_history;
    }

    public void setTransact_history(String transact_history) {
        this.transact_history = transact_history;
    }

    public JobsRecyclerModel(String amount_paid, String car_type, String transact_time, String transact_history, String name, String number, String transactionID, String transConfirm) {
        this.amount_paid = amount_paid;
        this.car_type = car_type;
        this.transact_time = transact_time;
        this.transact_history = transact_history;
        Name = name;
        Number = number;
        TransactionID = transactionID;
        TransConfirm = transConfirm;
    }

    public JobsRecyclerModel(String amount_paid, String car_type, String transact_time, String transact_history, String image, String name, String number, String customerName, String transactionID, String transConfirm, String UID) {
        this.amount_paid = amount_paid;
        this.car_type = car_type;
        this.transact_time = transact_time;
        this.transact_history = transact_history;
        Image = image;
        Name = name;
        Number = number;
        this.MyOwnName = customerName;
        TransactionID = transactionID;
        TransConfirm = transConfirm;
        this.UID = UID;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }
}
