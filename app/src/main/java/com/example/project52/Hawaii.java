package com.example.project52;

import java.io.Serializable;

public class Hawaii extends Pizza implements Serializable {

    private static final int DEFAULT_TOPPINGS = 2;

    public Hawaii(Size pizza){
        size = pizza;
        toppings.add(Topping.Pineapple);
        toppings.add(Topping.Ham);
    }

    public boolean addTopping(Topping topping){
        if(toppings.size() >= MAX_TOPPINGS){
            return false;
        }
        if(toppings.contains(topping)){
            return false;
        }
        toppings.add(topping);
        return true;
    }

    public boolean removeTopping(Topping topping){
        if(toppings.size() <= 0){
            return false;
        }
        if(!toppings.contains(topping)){
            return false;
        }
        toppings.remove(topping);
        return true;
    }

    public void setSize(Size pizzaSize){
        size = pizzaSize;
    }

    @Override
    public String toString(){
        String result = "Type: Hawaiian, Size: " + size.toString() + ", Price: " + price();
        for(int i = 0; i < toppings.size(); i++){
            result += ", " + toppings.get(i).toString();
        }
        return result;
    }

    @Override
    public double price() {
        double pizzaPrice;
        switch(size){
            case Medium:
                pizzaPrice = 12.99;
                break;
            case Large:
                pizzaPrice = 14.99;
                break;
            default:
                pizzaPrice = 10.99;
        }
        if((toppings.size() - DEFAULT_TOPPINGS) > 0){
            pizzaPrice += 1.49*(toppings.size() - DEFAULT_TOPPINGS);
        }
        return Math.round(pizzaPrice*100.0)/100.0;
    }
}
