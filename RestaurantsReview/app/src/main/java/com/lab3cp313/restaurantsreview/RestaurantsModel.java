package com.lab3cp313.restaurantsreview;

public class RestaurantsModel {
    private final String name, address;
    private int id;

    public RestaurantsModel(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getId() {
        return id;
    }
}
