package com.mechanics.mechadmin.models;

public class ShopItemModel {
    private String shop_item_ID, shop_item_name, shop_item_image, shop_item_price, shop_item_seller, shop_item_email, shop_item_phoneNo, shop_item_descrpt;

    public String getShop_item_ID() {
        return shop_item_ID;
    }

    public void setShop_item_ID(String shop_item_ID) {
        this.shop_item_ID = shop_item_ID;
    }

    public ShopItemModel(String shop_item_name, String shop_item_price,
                         String shop_item_seller, String shop_item_email,
                         String shop_item_phoneNo, String shop_item_descrpt,
                         String shop_item_image, String shop_item_ID) {
        this.shop_item_name = shop_item_name;
        this.shop_item_price = shop_item_price;
        this.shop_item_seller = shop_item_seller;
        this.shop_item_email = shop_item_email;
        this.shop_item_phoneNo = shop_item_phoneNo;
        this.shop_item_descrpt = shop_item_descrpt;
        this.shop_item_image = shop_item_image;
        this.shop_item_ID = shop_item_ID;
    }

    public String getShop_item_name() {
        return shop_item_name;
    }

    public String getShop_item_image() {
        return shop_item_image;
    }

    public void setShop_item_image(String shop_item_image) {
        this.shop_item_image = shop_item_image;
    }

    public void setShop_item_name(String shop_item_name) {
        this.shop_item_name = shop_item_name;
    }

    public String getShop_item_price() {
        return shop_item_price;
    }

    public void setShop_item_price(String shop_item_price) {
        this.shop_item_price = shop_item_price;
    }

    public String getShop_item_seller() {
        return shop_item_seller;
    }

    public void setShop_item_seller(String shop_item_seller) {
        this.shop_item_seller = shop_item_seller;
    }

    public String getShop_item_email() {
        return shop_item_email;
    }

    public void setShop_item_email(String shop_item_email) {
        this.shop_item_email = shop_item_email;
    }

    public String getShop_item_phoneNo() {
        return shop_item_phoneNo;
    }

    public void setShop_item_phoneNo(String shop_item_phoneNo) {
        this.shop_item_phoneNo = shop_item_phoneNo;
    }

    public String getShop_item_descrpt() {
        return shop_item_descrpt;
    }

    public void setShop_item_descrpt(String shop_item_descrpt) {
        this.shop_item_descrpt = shop_item_descrpt;
    }
}
