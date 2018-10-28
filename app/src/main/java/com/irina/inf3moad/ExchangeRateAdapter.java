package com.irina.inf3moad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ExchangeRateAdapter extends BaseAdapter {

    private List<ExchangeRate> data;
    public ExchangeRateAdapter(List<ExchangeRate> data) {this.data = data;}

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        ExchangeRate entry = data.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_entry, null, false); //R.layout.list_entry == meine neu erstellte list_entry.xml
        }


        TextView currencyNameTextView = convertView.findViewById(R.id.currency_name_txt);
        currencyNameTextView.setText(entry.getCurrencyName());

        String flagDrawableName = "flag_"+ entry.getCurrencyName().toLowerCase();

        ImageView flagIconImageView = convertView.findViewById(R.id.flag_ico);
        int identifier = convertView.getResources().getIdentifier(flagDrawableName, "drawable", context.getPackageName()); // "com.irina.inf3moad:drawable/" null null
        flagIconImageView.setImageResource(identifier);

        TextView currencyRateTextView = convertView.findViewById(R.id.currency_value_txt);
        currencyRateTextView.setText(String.valueOf(entry.getRateForOneEuro()));

        return convertView;
    }
}
