package com.example.project52;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class StoreOrders implements Serializable {

    private ArrayList<Order> pizzaOrders = new ArrayList<Order>();

    public StoreOrders(){

    }

    public void addOrder(Order tempOrder){
        pizzaOrders.add(tempOrder);
    }

    public boolean removeOrder(Order tempOrder){
        if (pizzaOrders.isEmpty()) {
            return false;
        }
        //System.out.println("Pizza order: " + pizzaOrders.size());
        pizzaOrders.remove(tempOrder);
        //System.out.println("Pizza order: " + pizzaOrders.size());
        return true;
    }

    public ArrayList<Order> getPizzaOrders(){
        return pizzaOrders;
    }

    public int contains(Order tempOrder){
        for(int i = 0; i < pizzaOrders.size(); i++){
            if(tempOrder.getPhone().equals(pizzaOrders.get(i).getPhone())){
                return i;
            }
        }
        return -1;
    }

    public void export(File newFile) throws IOException {
        FileWriter writer = new FileWriter(newFile);
        for(int i = 0; i < pizzaOrders.size(); i++){
            writer.write(pizzaOrders.get(i).toString() + "\n");
        }
        writer.close();
    }
}
