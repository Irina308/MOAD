package com.irina.inf3moad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ExchangeRateDatabase exchangeRateDatabase = new ExchangeRateDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initSpinner();

        Toolbar cToolbar = (Toolbar) findViewById(R.id.c_toolbar);
        setSupportActionBar(cToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.curr_conv_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.curr_entry:
                Intent currListIntent = new Intent(MainActivity.this, CurrencyListActivity.class);
                startActivity(currListIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void calculateOnClick (View view) {
        Spinner fromVal = findViewById(R.id.fromVal_spn);
        Spinner toVal = findViewById(R.id.toVal_spn);
        double inputVal = Double.parseDouble(((EditText) findViewById(R.id.input_txt)).getText().toString());

        String fromValSelectedItem =((ExchangeRate) fromVal.getSelectedItem()).getCurrencyName(); // changed from String to Exchange Rate for Ex.2.5
        String toValSelectedItem =((ExchangeRate) toVal.getSelectedItem()).getCurrencyName();

        double result = this.exchangeRateDatabase.convert(inputVal, fromValSelectedItem, toValSelectedItem);

        ((TextView) findViewById(R.id.result_txt)).setText(String.valueOf(result));
    }

    private void initSpinner(){
        Spinner fromVal_spn = findViewById(R.id.fromVal_spn);
        Spinner toVal_spn = findViewById(R.id.toVal_spn);

        /* Exercise 2.2
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, this.exchangeRateDatabase.getCurrencies());
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromVal_spn.setAdapter(spinnerArrayAdapter);
        toVal_spn.setAdapter(spinnerArrayAdapter);
        */

        ExchangeRateAdapter myAdapter = new ExchangeRateAdapter(Arrays.asList(this.exchangeRateDatabase.getExchangeRates()));
        fromVal_spn.setAdapter(myAdapter);
        toVal_spn.setAdapter(myAdapter);
    }
}
