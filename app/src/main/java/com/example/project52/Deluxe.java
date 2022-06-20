package com.example.project52;

import java.io.Serializable;

public class Deluxe extends Pizza implements Serializable {

    private static final int DEFAULT_TOPPINGS = 5;

    public Deluxe(Size pizza){
        size = pizza;
        toppings.add(Topping.Pepperoni);
        toppings.add(Topping.Sausage);
        toppings.add(Topping.Peppers);
        toppings.add(Topping.Mushrooms);
        toppings.add(Topping.Beef);
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
        String result = "Type: Deluxe, Size: " + size.toString() + ", Price: " + price();
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
                pizzaPrice = 14.99;
                break;
            case Large:
                pizzaPrice = 16.99;
                break;
            default:
                pizzaPrice = 12.99;
        }
        if((toppings.size() - DEFAULT_TOPPINGS) > 0){
            pizzaPrice += 1.49*(toppings.size() - DEFAULT_TOPPINGS);
        }
        return Math.round(pizzaPrice*100.0)/100.0;
    }
}
