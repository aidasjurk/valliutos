package com.example.asynctaskwithapiexample;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private EditText currencyInput;
    private ListView currencyList;
    private ArrayAdapter<String> adapter;
    private List<String> allCurrencyData; // All fetched data
    private List<String> displayedCurrencyData; // Filtered data for display

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencyInput = findViewById(R.id.currencyInput);
        currencyList = findViewById(R.id.currencyList);
        allCurrencyData = new ArrayList<>();
        displayedCurrencyData = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayedCurrencyData);
        currencyList.setAdapter(adapter);

        loadCurrencyData();

        currencyInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used in this implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not used in this implementation
            }

            @Override
            public void afterTextChanged(Editable s) {
                filterCurrencyData(s.toString());
            }
        });
    }

    private void loadCurrencyData() {
        // The URL from which to fetch the currency data
        String url = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

        // Create DataLoader and execute it with the URL
        new DataLoader(new DataLoader.DataLoaderListener() {
            @Override
            public void onDataLoaded(List<String> data) {
                updateCurrencyData(data);
            }
        }).execute(url);
    }

    public void updateCurrencyData(List<String> data) {
        allCurrencyData.clear();
        if (data != null) {
            allCurrencyData.addAll(data);
        }
        filterCurrencyData(currencyInput.getText().toString());
    }

    private void filterCurrencyData(String query) {
        displayedCurrencyData.clear();
        if (query.isEmpty()) {
            displayedCurrencyData.addAll(allCurrencyData);
        } else {
            displayedCurrencyData.addAll(allCurrencyData.stream()
                    .filter(currency -> currency.toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList()));
        }
        adapter.notifyDataSetChanged();
    }
}
