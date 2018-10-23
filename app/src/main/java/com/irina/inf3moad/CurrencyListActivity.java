package com.irina.inf3moad;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class CurrencyListActivity extends AppCompatActivity {

    private ExchangeRateDatabase exchangeRateDatabase = new ExchangeRateDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        initList();
    }


    private void initList(){
        ListView currencyListView = findViewById(R.id.currencies_lst);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.exchangeRateDatabase.getCurrencies());
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        currencyListView.setAdapter(spinnerArrayAdapter);
    }
}
