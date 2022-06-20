package com.example.project52;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class PizzaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener{
    private static final int MAX_TOPPINGS = 7;

    private ImageView pizzaImage;
    private TextView pizzaText;
    private TextView priceEditText;
    private Spinner sizeDropDown;
    private ListView additionalListView, currentListView;
    private ArrayAdapter sizeAdapter, additionalAdapter, currentAdapter;
    private ArrayList<Topping> addToppings = new ArrayList<Topping>(), pizzaToppings = new ArrayList<Topping>();
    private Pizza pizzaOrder;
    private Size[] sizes = {Size.Small, Size.Medium, Size.Large};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);

        setTitle("Pizza Builder");
        Intent intent = getIntent();
        //String phone = intent.getStringExtra("phone");
        String type = intent.getStringExtra("type");

        addToppings= new ArrayList<Topping>(Arrays.asList(Topping.Pineapple, Topping.Ham, Topping.Sausage,
                Topping.Peppers, Topping.Mushrooms, Topping.Beef, Topping.Chicken, Topping.Olives));
        pizzaToppings= new ArrayList<Topping>(Arrays.asList(Topping.Pepperoni));

        pizzaOrder = PizzaMaker.createPizza(type);
        pizzaImage = findViewById(R.id.pizzaImageView);
        pizzaText = findViewById(R.id.pizzaText);
        priceEditText = findViewById(R.id.priceEditText);
        additionalListView = findViewById(R.id.additionalListView);
        currentListView = findViewById(R.id.currentListView);

        if(type.equals("Deluxe")){
            //System.out.println("HERE      " + R.drawable.deluxepizza);
            pizzaImage.setImageResource(R.drawable.deluxepizza);
            pizzaText.setText("Deluxe");
            priceEditText.setText("12.99");
            addToppings.removeAll(Arrays.asList(Topping.Sausage, Topping.Peppers, Topping.Mushrooms, Topping.Beef));
            pizzaToppings.addAll(Arrays.asList(Topping.Sausage, Topping.Peppers, Topping.Mushrooms, Topping.Beef));
        } else if (type.equals("Hawaii")){
            pizzaImage.setImageResource(R.drawable.hawaiianpizza);
            pizzaText.setText("Hawaii");
            priceEditText.setText("10.99");
            addToppings.removeAll(Arrays.asList(Topping.Ham, Topping.Pineapple));
            pizzaToppings.addAll(Arrays.asList(Topping.Pineapple, Topping.Ham));
            pizzaToppings.remove(Topping.Pepperoni);
            addToppings.add(Topping.Pepperoni);
        } else if (type.equals("Pepperoni")){
            pizzaImage.setImageResource(R.drawable.pepperonipizza);
            pizzaText.setText("Pepperoni");
            priceEditText.setText("8.99");
        }

        additionalAdapter = new ArrayAdapter<Topping>(this, android.R.layout.simple_list_item_1, addToppings);
        currentAdapter = new ArrayAdapter<Topping>(this, android.R.layout.simple_list_item_1, pizzaToppings);
        additionalListView.setAdapter(additionalAdapter);
        currentListView.setAdapter(currentAdapter);
        additionalListView.setOnItemClickListener(this);
        currentListView.setOnItemClickListener(this);



        sizeDropDown = findViewById(R.id.sizeDropDown);
        sizeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sizes);
        sizeDropDown.setAdapter(sizeAdapter);
        sizeDropDown.setOnItemSelectedListener(this);

    }


    public void addToOrder(View view){
        Intent intent = new Intent();
        intent.putExtra("pizza", pizzaOrder);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        switch(parent.getId()){
            case R.id.additionalListView:
                if(position == -1){
                    Toast.makeText(getBaseContext(), "No topping selected", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(additionalListView.getCount() <= 0){
                    Toast.makeText(getBaseContext(), "Out of toppings", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(currentAdapter.getCount() >= MAX_TOPPINGS){
                    Toast.makeText(getBaseContext(), "Cannot add any more toppings", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentAdapter.add(additionalListView.getItemAtPosition(position));
                currentAdapter.notifyDataSetChanged();
                boolean fail = true;
                if(pizzaOrder instanceof Deluxe){
                    fail = ((Deluxe) pizzaOrder).addTopping(Topping.valueOf(additionalListView.getItemAtPosition(position).toString()));
                }
                if(pizzaOrder instanceof Hawaii){
                    fail = ((Hawaii) pizzaOrder).addTopping(Topping.valueOf(additionalListView.getItemAtPosition(position).toString()));
                }
                if(pizzaOrder instanceof Pepperoni){
                    fail = ((Pepperoni) pizzaOrder).addTopping(Topping.valueOf(additionalListView.getItemAtPosition(position).toString()));
                }
                if(!fail){
                    Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    return;
                }
                additionalAdapter.remove(additionalListView.getItemAtPosition(position));
                additionalAdapter.notifyDataSetChanged();
                priceEditText.setText(String.valueOf(pizzaOrder.price()));
                break;
            case R.id.currentListView:
                if(currentListView.getCount() <= 0){
                    Toast.makeText(getBaseContext(), "No more toppings to remove", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(position == -1){
                    Toast.makeText(getBaseContext(), "No topping selected", Toast.LENGTH_SHORT).show();
                    return;
                }
                additionalAdapter.add(currentListView.getItemAtPosition(position));
                additionalAdapter.notifyDataSetChanged();
                fail = true;
                if(pizzaOrder instanceof Deluxe){
                    fail = ((Deluxe) pizzaOrder).removeTopping(Topping.valueOf(currentListView.getItemAtPosition(position).toString()));
                }
                if(pizzaOrder instanceof Hawaii){
                    fail = ((Hawaii) pizzaOrder).removeTopping(Topping.valueOf(currentListView.getItemAtPosition(position).toString()));
                }
                if(pizzaOrder instanceof Pepperoni){
                    fail = ((Pepperoni) pizzaOrder).removeTopping(Topping.valueOf(currentListView.getItemAtPosition(position).toString()));
                }
                if(!fail){
                    Toast.makeText(getBaseContext(), "Cannot add any more toppings", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentAdapter.remove(currentListView.getItemAtPosition(position));
                currentAdapter.notifyDataSetChanged();
                priceEditText.setText(String.valueOf(pizzaOrder.price()));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()){
            case R.id.sizeDropDown:
                if(pizzaOrder instanceof Deluxe){
                    ((Deluxe) pizzaOrder).setSize(Size.valueOf(sizeDropDown.getSelectedItem().toString()));
                }
                if(pizzaOrder instanceof Hawaii){
                    ((Hawaii) pizzaOrder).setSize(Size.valueOf(sizeDropDown.getSelectedItem().toString()));
                }
                if(pizzaOrder instanceof Pepperoni){
                    ((Pepperoni) pizzaOrder).setSize(Size.valueOf(sizeDropDown.getSelectedItem().toString()));
                }
                priceEditText.setText(String.valueOf(pizzaOrder.price()));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}