package com.example.project52;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class CurrentOrderActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private TextView phoneNoEditText;
    private TextView subtotalEditText;
    private TextView salesTaxEditText;
    private TextView orderTotalEditText;
    private ListView orderView;

    private Order currentOrder;

    private ArrayList<String> pizzas = new ArrayList<>();
    private ArrayAdapter pizzaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order);

        setTitle("Current Order");
        Intent intent = getIntent();
        currentOrder = (Order)intent.getSerializableExtra("order");

        phoneNoEditText = findViewById(R.id.phoneNoEditText);
        subtotalEditText = findViewById(R.id.subtotalEditText);
        salesTaxEditText = findViewById(R.id.salesTaxEditText);
        orderTotalEditText = findViewById(R.id.orderTotalEditText);
        orderView = findViewById(R.id.orderView);

        for(int i = 0; i < currentOrder.getOrders().size(); i++){
            pizzas.add(currentOrder.getOrders().get(i).toString());
        }
        pizzaAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, pizzas);
        orderView.setAdapter(pizzaAdapter);
        orderView.setOnItemClickListener(this);

        phoneNoEditText.setText(currentOrder.getPhone());
        subtotalEditText.setText(String.valueOf(currentOrder.totalCost()));
        salesTaxEditText.setText(String.valueOf(Math.round(currentOrder.totalCost() * 0.06625 * 100.0)/100.0));
        orderTotalEditText.setText(String.valueOf(Math.round(currentOrder.totalCost() * 1.06625 * 100.0)/100.0));


    }

    public void orderPizza(View view){
        if(pizzaAdapter.getCount() <= 0){
            Toast.makeText(getBaseContext(), "No pizza orders placed", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("order", currentOrder);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.orderView){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Do you want to remove this order??").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if(pizzaAdapter.getCount() <= 0){
                        Toast.makeText(getBaseContext(), "No orders left to remove", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(position == -1){
                        Toast.makeText(getBaseContext(), "No order selected", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    currentOrder.removePizza(currentOrder.getOrders().get(position));
                    pizzaAdapter.remove(orderView.getItemAtPosition(position));
                    pizzaAdapter.notifyDataSetChanged();
                    subtotalEditText.setText(String.valueOf(currentOrder.totalCost()));
                    salesTaxEditText.setText(String.valueOf(Math.round(currentOrder.totalCost() * 0.06625 * 100.0)/100.0));
                    orderTotalEditText.setText(String.valueOf(Math.round(currentOrder.totalCost() * 1.06625 * 100.0)/100.0));
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog = alert.create();
            dialog.show();
        }

    }


}