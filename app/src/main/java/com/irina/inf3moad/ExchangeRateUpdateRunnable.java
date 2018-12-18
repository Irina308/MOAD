package com.irina.inf3moad;

import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class ExchangeRateUpdateRunnable implements Runnable {

    ExchangeRateDatabase exchangeRateDatabase;

    MainActivity mainActivity;


    public ExchangeRateUpdateRunnable(MainActivity mainActivity, ExchangeRateDatabase exchangeRateDatabase) {
        this.mainActivity = mainActivity;
        this.exchangeRateDatabase = exchangeRateDatabase;
    }

    @Override
    public void run() {
        Log.i("CurrencyUpdate", "Start runnable");
        synchronized (ExchangeRateUpdateRunnable.this) {
            List<ExchangeRate> updatedExchangeRates = this.mainActivity.queryRates();

            ///// Sleep, just for testing purpose
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.updateCurrencies(updatedExchangeRates);
        }
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mainActivity, "Currencies updated", Toast.LENGTH_SHORT).show();
            }
        });
        Log.i("CurrencyUpdate", "End of runnable class");

    }

    private void updateCurrencies(List<ExchangeRate> updatedRates) {
        for (ExchangeRate updatedRate : updatedRates) {
            this.exchangeRateDatabase.setExchangeRate(updatedRate);
        }
    }
}
