package com.example.project52;

//create an instance of subclasses based on the chosen flavor
public class PizzaMaker {
    public static Pizza createPizza(String flavor) {
        Pizza newPizza = null;
        if(flavor.equals("Pepperoni")){
            newPizza = new Pepperoni(Size.Small);
        } else if(flavor.equals("Deluxe")){
            newPizza = new Deluxe(Size.Small);
        } else if(flavor.equals("Hawaii")){
            newPizza = new Hawaii(Size.Small);
        }
        //write the code for creating different instances of subtypes of pizza
        return newPizza;
    }
}
