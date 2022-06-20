package com.example.project52;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private EditText phoneNo;
    private StoreOrders orders = new StoreOrders();
    Order currentOrder;
    String currentOrderNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("RU Pizzeria");

        // obtain user's phone number using findViewById
        phoneNo = findViewById(R.id.phoneNoInput);
    }


    public void startOrder(View view) {
        if(currentOrderNumber == null || !currentOrderNumber.equals(phoneNo.getText().toString())){
            currentOrderNumber = phoneNo.getText().toString();
            currentOrder = new Order(phoneNo.getText().toString());
        }
        if(orders.contains(new Order(phoneNo.getText().toString())) != -1){
            Toast.makeText(getBaseContext(), "Phone Number already exists", Toast.LENGTH_SHORT).show();
            return;
        }
        String inputtedNo = phoneNo.getText().toString();
        String type = "Deluxe";
        switch(view.getId()){
            case R.id.orderHawaiian:
                type = "Hawaii";
                break;
            case R.id.orderPepperoni:
                type = "Pepperoni";
        }

        if (inputtedNo.isEmpty()) {
            Toast.makeText(getBaseContext(), "Please enter your phone number", Toast.LENGTH_SHORT).show();
        }

        else if(!(inputtedNo.matches("[0-9]+") && inputtedNo.length() == 10)){
            Toast.makeText(getBaseContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, PizzaActivity.class);
            //intent.putExtra("phone", inputtedNo);
            intent.putExtra("type", type);
            startActivityForResult(intent, 0);

            //finish();
        }
    }

    public void currentOrderClick(View view){
        if(currentOrder == null || currentOrderNumber == null){
            Toast.makeText(getBaseContext(), "Please order a pizza first", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, CurrentOrderActivity.class);
        intent.putExtra("order", currentOrder);
        startActivityForResult(intent, 1);

    }

    public void storeOrderClick(View view){
        if(orders.getPizzaOrders().size() <= 0){
            Toast.makeText(getBaseContext(), "Please place an order first.", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, StoreOrdersActivity.class);
        intent.putExtra("orders", orders);
        startActivityForResult(intent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if(resultCode == RESULT_OK) {
                Pizza tempPizza = (Pizza) data.getSerializableExtra("pizza");
                currentOrder.addPizza(tempPizza);
            }
        }
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Order tempOrder = (Order) data.getSerializableExtra("order");
                orders.addOrder(tempOrder);
                currentOrderNumber = null;
                currentOrder = null;
            }
        }
        if(requestCode == 2){
            if(resultCode == RESULT_OK){
                StoreOrders tempOrders = (StoreOrders) data.getSerializableExtra("orders");
                orders = tempOrders;
            }
        }
    }
}