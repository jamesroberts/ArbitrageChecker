package com.jamesroberts.arbitrage.checker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    // UI Elements
    public EditText capital;
    public EditText rate;
    public EditText fees;
    public Button btnSubmit;
    public ConstraintLayout details;
    public TextView krakenPrice;
    public TextView lunoPrice;
    public TextView valrPrice;
    public TextView lunoArb;
    public TextView valrArb;
    public TextView lunoProfit;
    public TextView valrProfit;
    public ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        // Set up state and UI
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeUI();

        // Create object that will hold all details
        final Arbitrage arb = new Arbitrage();

        // Fetch prices in the background
        details.post(new Runnable() {
            @Override
            public void run() {
                try {
                    arb.setPrices();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "There was an error fetching prices", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                try {
                    details.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                float cap = Float.parseFloat(capital.getText().toString());
                                arb.setCapital(cap);

                                float exchangeRate = Float.parseFloat(rate.getText().toString());
                                arb.setRate(exchangeRate);

                                float exchangeFees = Float.parseFloat(fees.getText().toString());
                                arb.setFees(exchangeFees);

                                ArbResult result = arb.calculate();

                                krakenPrice.setText("€ " + result.KRAKEN_PRICE);
                                lunoPrice.setText("R " + result.LUNO_PRICE);
                                valrPrice.setText("R " + result.VALR_PRICE);

                                lunoArb.setText(result.LUNO_ARB + " %");
                                valrArb.setText(result.VALR_ARB + " %");
                                lunoProfit.setText("R " + result.LUNO_PROFIT);
                                valrProfit.setText("R " + result.VALR_PROFIT);
                            } catch (Exception error){
                                Toast.makeText(MainActivity.this, "There was an error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }catch (Exception error){
                    System.out.println(error.toString());
                    System.out.println(error.getStackTrace());
                    Toast.makeText(MainActivity.this, "There was an error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initializeUI() {
        capital = findViewById(R.id.etCapital);
        rate = findViewById(R.id.etFxRate);
        fees = findViewById(R.id.etFees);
        btnSubmit = findViewById(R.id.btnSubmit);
        details = findViewById(R.id.details);
        krakenPrice = findViewById(R.id.txtKrakenPrice);
        lunoPrice = findViewById(R.id.txtLunoPrice);
        valrPrice = findViewById(R.id.txtVALRPrice);
        lunoArb = findViewById(R.id.txtLunoArb);
        valrArb = findViewById(R.id.txtVALRArb);
        lunoProfit = findViewById(R.id.txtLunoProfit);
        valrProfit = findViewById(R.id.txtVALRProfit);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
