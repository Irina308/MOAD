package com.irina.inf3moad;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ExchangeRateDatabase exchangeRateDatabase = new ExchangeRateDatabase();

    ShareActionProvider shareActionProvider;

    ExchangeRateAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);

        this.initSpinner();

        Toolbar cToolbar = findViewById(R.id.c_toolbar);
        setSupportActionBar(cToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.curr_conv_menu, menu);
        MenuItem shareitem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareitem);
        setShareText(null);
        return true;
    }

    private void setShareText(String text) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        if (text != null)
            {shareIntent.putExtra(Intent.EXTRA_TEXT, text);}
        shareActionProvider.setShareIntent(shareIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.curr_entry:
                Intent currListIntent = new Intent(MainActivity.this, CurrencyListActivity.class);
                startActivity(currListIntent);
                return true;

            case R.id.refresh_rates:
               // List<ExchangeRate> updatedRates = this.queryRates();

                ExchangeRateUpdateRunnable runnable = new ExchangeRateUpdateRunnable(this, this.exchangeRateDatabase);
                new Thread(runnable).start();

              //  this.updateCurrencies(updatedRates);
              //  this.myAdapter.notifyDataSetChanged(); // Falls die Werte sich nicht automatisch updaten
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
        setShareText(inputVal + " " + fromValSelectedItem + " are " + result + " " + toValSelectedItem );
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

        myAdapter = new ExchangeRateAdapter(Arrays.asList(this.exchangeRateDatabase.getExchangeRates()));
        fromVal_spn.setAdapter(myAdapter);
        toVal_spn.setAdapter(myAdapter);
    }

    public List<ExchangeRate> queryRates() {
        String queryString = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
        List<ExchangeRate> ml = new ArrayList<>();

        try {
            URL url = new URL(queryString);
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();

            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(is, urlConnection.getContentEncoding());

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();
                try {
                    String attributeName = parser.getAttributeName(0);
                } catch (Exception e) {};
                if(eventType == XmlPullParser.START_TAG && "Cube".equals(parser.getName()) && parser.getAttributeValue(null, "currency") != null) {
                    ExchangeRate rate = new ExchangeRate(
                            parser.getAttributeValue(null, "currency"),
                            null,
                            Double.valueOf(parser.getAttributeValue(null, "rate"))
                    );
                    ml.add(rate);
                }
                eventType = parser.next();
            }

        } catch (Exception ex){
            Log.e("MovieApp", "cant query omdb");
            ex.printStackTrace();
        }
        return ml;
    }


}
