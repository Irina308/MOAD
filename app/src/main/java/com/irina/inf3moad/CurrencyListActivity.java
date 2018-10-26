package com.irina.inf3moad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.Arrays;

public class CurrencyListActivity extends AppCompatActivity {

    private ExchangeRateDatabase exchangeRateDatabase = new ExchangeRateDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initList();
    }


    private void initList(){
        ListView currencyListView = findViewById(R.id.currencies_lst);

        ExchangeRateAdapter myAdapter = new ExchangeRateAdapter(Arrays.asList(this.exchangeRateDatabase.getExchangeRates()));
        currencyListView.setAdapter(myAdapter);
    }
}
