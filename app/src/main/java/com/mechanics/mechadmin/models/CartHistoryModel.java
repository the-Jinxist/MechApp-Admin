package com.mechanics.mechadmin.models;

import java.util.ArrayList;

public class CartHistoryModel {

    private String TransId;
    private String price;
    private String image;
    private String status;
    private String cusName;
    private String cusUid;
    private String cusNumber;
    private String cusEmail;
    private String Streetname;
    private ArrayList<String> items;
    private ArrayList<String> sellers;
    private ArrayList<String> itemNumbers;

    public ArrayList<String> getItemNumbers() {
        return itemNumbers;
    }

    public ArrayList<String> getItems() {
        return items;
    }



    public String getTransId() {
        return TransId;
    }


    public String getCusNumber() {
        return cusNumber;
    }


    public String getCusEmail() {
        return cusEmail;
    }


    public String getStreetname() {
        return Streetname;
    }


    public String getCusUid() {
        return cusUid;
    }


    public CartHistoryModel(String TransId, ArrayList<String> items, String price,
                            ArrayList<String> sellers, String image, String status) {
        this.items = items;
        this.price = price;
        this.sellers = sellers;
        this.image = image;
        this.TransId = TransId;
        this.status = status;
    }

    public CartHistoryModel(String name, String price, ArrayList<String> sellers, String image,
                            String cusUid, String cusEmail, String cusNumber, String streetname,
                            ArrayList<String> items, ArrayList<String> itemNumbers, String transId) {
        this.cusName = name;
        this.price = price;
        this.sellers = sellers;
        this.image = image;
        this.cusUid = cusUid;
        this.cusEmail = cusEmail;
        this.items = items;
        this.cusNumber = cusNumber;
        this.Streetname = streetname;
        this.itemNumbers = itemNumbers;
        this.TransId = transId;
    }

    public String getCusName() {
        return cusName;
    }


    public String getPrice() {
        return price;
    }


    public ArrayList<String> getSellers() {
        return sellers;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

 }
