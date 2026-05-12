package com.example.image;

public class Product {

    public String id;
    public String description;

    public String imageBase64;

    public Product(){}

    public Product(String id, String descrption, String imageBase64){
        this.id = id;
        this.description = descrption;
        this.imageBase64 = imageBase64;
    }
}
