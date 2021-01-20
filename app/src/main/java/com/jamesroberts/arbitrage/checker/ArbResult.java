package com.jamesroberts.arbitrage.checker;

import java.text.DecimalFormat;

public class ArbResult {

    String KRAKEN_PRICE;
    String LUNO_PRICE;
    String VALR_PRICE;

    String LUNO_ARB;
    String VALR_ARB;

    String LUNO_PROFIT;
    String VALR_PROFIT;

    public ArbResult(float KRAKEN_PRICE, float LUNO_PRICE,
                     float VALR_PRICE, float LUNO_ARB,
                     float VALR_ARB, double LUNO_PROFIT,
                     double VALR_PROFIT) {
        DecimalFormat df = new DecimalFormat("##.00");
        this.KRAKEN_PRICE = String.valueOf(df.format(KRAKEN_PRICE));
        this.LUNO_PRICE = String.valueOf(df.format(LUNO_PRICE));
        this.VALR_PRICE = String.valueOf(df.format(VALR_PRICE));
        this.LUNO_ARB = String.valueOf(df.format(LUNO_ARB));
        this.VALR_ARB = String.valueOf(df.format(VALR_ARB));
        this.LUNO_PROFIT = String.valueOf(df.format(LUNO_PROFIT));
        this.VALR_PROFIT = String.valueOf(df.format(VALR_PROFIT));
    }
}
