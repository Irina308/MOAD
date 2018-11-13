package com.irina.inf3moad;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Arrays;

public class CurrencyListActivity extends AppCompatActivity {

    private ExchangeRateDatabase exchangeRateDatabase = new ExchangeRateDatabase();

    public ExchangeRateAdapter exchangeRateAdapter;

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

        this.exchangeRateAdapter = new ExchangeRateAdapter(Arrays.asList(this.exchangeRateDatabase.getExchangeRates()));
        currencyListView.setAdapter(this.exchangeRateAdapter);
        currencyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExchangeRate selectedRate = (ExchangeRate) exchangeRateAdapter.getItem(position);
                String capitolCity = selectedRate.getCapital();
                Intent countryIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0`?q=" + capitolCity));
                startActivity(countryIntent);

            }
        });
    }
}
