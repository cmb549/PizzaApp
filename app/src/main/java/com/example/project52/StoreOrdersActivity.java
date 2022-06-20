package com.example.project52;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class StoreOrdersActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner phoneNoDropDown;
    private TextView orderTotal;
    private TextView orderText;

    private ArrayList<String> phones = new ArrayList<>();
    private ArrayAdapter phonesAdapter;

    private StoreOrders orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_orders);

        setTitle("Store Orders");

        Intent intent = getIntent();
        orders = (StoreOrders)intent.getSerializableExtra("orders");

        phoneNoDropDown = findViewById(R.id.phoneNoDropDown);
        orderTotal = findViewById(R.id.orderTotal);
        orderText = findViewById(R.id.orderText);

        for(int i = 0; i < orders.getPizzaOrders().size(); i++){
            phones.add(orders.getPizzaOrders().get(i).getPhone());
        }
        phonesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, phones);
        phoneNoDropDown.setAdapter(phonesAdapter);
        phoneNoDropDown.setOnItemSelectedListener(this);
        Order currentOrder = orders.getPizzaOrders().get(0);
        orderText.setText(currentOrder.toString());
        orderTotal.setText(String.valueOf(Math.round(currentOrder.totalCost() * 1.06625 * 100.0)/100.0));
    }

    public void cancelOrder (View view){
        if(phones.size() <= 0){
            Toast.makeText(getBaseContext(), "No orders left", Toast.LENGTH_SHORT).show();
            return;
        }
        orders.removeOrder(new Order(phoneNoDropDown.getSelectedItem().toString()));
//        orderText.setText("");
//        orderTotal.setText("");
        phonesAdapter.remove(phoneNoDropDown.getSelectedItem().toString());
        phonesAdapter.notifyDataSetChanged();
        if(phonesAdapter.isEmpty()){
            orderText.setText("");
            orderTotal.setText("");
        } else {
            phoneNoDropDown.setSelection(0);
            Order currentOrder = orders.getPizzaOrders().get(0);
            orderText.setText(currentOrder.toString());
            orderTotal.setText(String.valueOf(Math.round(currentOrder.totalCost() * 1.06625 * 100.0)/100.0));
        }
//        if(phoneNoDropDown.getSelectedItem() == null){
//            orderText.setText("");
//            orderTotal.setText("");
//        } else {
//            int index = orders.contains(new Order(phoneNoDropDown.getSelectedItem().toString()));
//            Order currentOrder = orders.getPizzaOrders().get(index);
//            orderText.setText(currentOrder.toString());
//            orderTotal.setText(String.valueOf(Math.round(currentOrder.totalCost() * 1.06625 * 100.0)/100.0));
//
//        }
        Intent intent = new Intent();
        intent.putExtra("orders", orders);
        setResult(RESULT_OK, intent);
        //finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(phoneNoDropDown.getSelectedItem() == null){
            return;
        }
        int index = orders.contains(new Order(phoneNoDropDown.getSelectedItem().toString()));
        Order currentOrder = orders.getPizzaOrders().get(index);
        orderText.setText(currentOrder.toString());
        orderTotal.setText(String.valueOf(Math.round(currentOrder.totalCost() * 1.06625 * 100.0)/100.0));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}