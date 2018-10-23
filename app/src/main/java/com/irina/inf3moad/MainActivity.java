package com.irina.inf3moad;

import android.service.autofill.CharSequenceTransformation;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    ExchangeRateDatabase exchangeRateDatabase = new ExchangeRateDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initSpinner();
    }

    public void calculateOnClick (View view) {
        Spinner fromVal = findViewById(R.id.fromVal_spn);
        Spinner toVal = findViewById(R.id.toVal_spn);
        double inputVal = Double.parseDouble(((EditText) findViewById(R.id.input_txt)).getText().toString());

        String fromValSelectedItem =(String) fromVal.getSelectedItem();
        String toValSelectedItem =(String) toVal.getSelectedItem();

        double result = this.exchangeRateDatabase.convert(inputVal, fromValSelectedItem, toValSelectedItem);

        ((TextView) findViewById(R.id.result_txt)).setText(String.valueOf(result));
    }

    private void initSpinner(){
        Spinner fromVal_spn = findViewById(R.id.fromVal_spn);
        Spinner toVal_spn = findViewById(R.id.toVal_spn);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, this.exchangeRateDatabase.getCurrencies());
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromVal_spn.setAdapter(spinnerArrayAdapter);
        toVal_spn.setAdapter(spinnerArrayAdapter);
    }
}