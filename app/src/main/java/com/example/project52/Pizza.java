package com.example.project52;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Pizza implements Serializable {
    public static final int MAX_TOPPINGS = 7;
    protected ArrayList<Topping> toppings = new ArrayList<Topping>();
    protected Size size;
    public abstract double price();
}