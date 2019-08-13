package com.jamesroberts.arbitrage.checker;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText exchangeRate = (EditText) findViewById(R.id.etExchangeRate);
        Button button = (Button) findViewById(R.id.btnSubmit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                try {
                    final TextView lblChecking = (TextView) findViewById(R.id.lblChecking);
                    lblChecking.setText("Checking...");
                    lblChecking.post(new Runnable() {
                        public void run() {
                            try {
                                EditText exchangeRate = (EditText) findViewById(R.id.etExchangeRate);
                                float rate = Float.parseFloat(exchangeRate.getText().toString());
                                Arbitrage arb = new Arbitrage();
                                ArrayList result = arb.getData(rate);
                                lblChecking.setText(" ");
                                String kraken = result.get(0).toString();
                                String exchange_rate = result.get(1).toString();
                                String luno = result.get(2).toString();
                                String arb_percent = result.get(3).toString();
                                String gain = result.get(4).toString();
                                String estimation = result.get(5).toString();
                                String profit = result.get(6).toString();

                                TextView lblKraken = (TextView) findViewById(R.id.lblKraken_price);
                                lblKraken.setText("Kraken price (EUR/BTC) :          € " + kraken);
                                TextView lblRate = (TextView) findViewById(R.id.lblExchange_rate);
                                lblRate.setText("EUR to ZAR exchange rate:      R " + exchange_rate);
                                TextView lblLuno = (TextView) findViewById(R.id.lblLunoPrice);
                                lblLuno.setText("Luno price (ZAR/BTC) :             R " + luno);
                                TextView lblPerc = (TextView) findViewById(R.id.lblArbPerc);
                                lblPerc.setText("Arbitrage Percentage :              " + arb_percent + " %");
                                TextView lblGain = (TextView) findViewById(R.id.lblGain);
                                lblGain.setText("Percentage Gain after fees :    " + gain + " %");
                                TextView lblProfit = (TextView) findViewById(R.id.lblProfit);
                                lblProfit.setText("Estimated Profit on R50,000:   R " + profit);
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
