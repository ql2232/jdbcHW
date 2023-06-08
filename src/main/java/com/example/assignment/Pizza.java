package com.example.assignment;

import org.springframework.stereotype.Component;

@Component
public class Pizza {
    int id;
    String name;
    double price;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
