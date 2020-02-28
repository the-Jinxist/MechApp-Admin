package com.mechanics.mechadmin.models;

import java.util.List;

public class MechModel {

    private String name;
    private String image;

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCacImageUrl() {
        return cacImageUrl;
    }

    public void setCacImageUrl(String cacImageUrl) {
        this.cacImageUrl = cacImageUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    private String workDone;
    private String street_name;
    private String number;
    private String uid;

    private String email, bankAccountName, bankAccountNumber, bankName,
            cacImageUrl, city, descript, locality, rating, reviews, webUrl;
    private List<String> specifications;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setSpecifications(List<String> specifications) {
        this.specifications = specifications;
    }

    public List<String> getSpecifications() {
        return specifications;
    }

    // Mech Constructor

    public MechModel(List<String> specifications, String name, String image, String workDone,
                     String street_name, String number, String uid,
                     String email, String bankAccountName, String bankAccountNumber,
                     String bankName, String cacImageUrl, String city, String descript,
                     String locality, String rating, String reviews, String webUrl) {
        this.specifications = specifications;
        this.name = name;
        this.image = image;
        this.workDone = workDone;
        this.street_name = street_name;
        this.number = number;
        this.uid = uid;
        this.email = email;
        this.bankAccountName = bankAccountName;
        this.bankAccountNumber = bankAccountNumber;
        this.bankName = bankName;
        this.cacImageUrl = cacImageUrl;
        this.city = city;
        this.descript = descript;
        this.locality = locality;
        this.rating = rating;
        this.reviews = reviews;
        this.webUrl = webUrl;
    }

    //Customer Constructor
    public MechModel(String name, String number, String email, String uid) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.uid = uid;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWorkDone() {
        return workDone;
    }

    public void setWorkDone(String workDone) {
        this.workDone = workDone;
    }
}
