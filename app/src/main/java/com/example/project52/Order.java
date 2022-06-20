package com.example.project52;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    private String phone;
    private ArrayList<Pizza> orders = new ArrayList<Pizza>();

    public Order(String phoneNumber){
//        if(!(phoneNumber.matches("[0-9]+") && phoneNumber.length() == 10)){
//            return;
//        }
        phone = phoneNumber;
    }

    public void addPizza(Pizza tempPizza){
        //System.out.println(orders.size());
        orders.add(tempPizza);
    }

    public boolean removePizza(Pizza tempPizza){
        if(orders.isEmpty()){
            return false;
        }
        orders.remove(tempPizza);
        return true;
    }

    public double totalCost(){
        double cost = 0;
        for(int i = 0; i < orders.size(); i++){
            cost += orders.get(i).price();
        }
        return Math.round(cost*100.0)/100.0;
    }

    public ArrayList<Pizza> getOrders(){
        return orders;
    }

    public String getPhone(){
        return phone;
    }

    @Override
    public boolean equals(Object o){
        Order otherOrder = (Order) o;
        if(phone.equals(otherOrder.getPhone())){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        String result = "Phone number: " + phone + "\n";
        for(int i = 0; i < orders.size(); i++){
            result += orders.get(i).toString() + "\n";
        }
        return result;
    }
}
