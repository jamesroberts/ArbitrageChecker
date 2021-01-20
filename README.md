# ArbitrageChecker
An android app that check the bitcoin arbitrage rates between the Luno and Kraken Exchanges

This app presents an interface that allows a user to input a EUR/ZAR exchange rate and shows
the various arbitrage details on screen.

Update: Allow the user to select a capital amount of either R50,000 or R100,000

The details include:
- The kraken XBT/EUR price (Fetched using the Kraken API)
- The exchange rate that the user has entered
- The XBT/ZAR price (Fetched from the Luno API)
- The raw percentage difference in the two prices.
- The estimates profit percentage after fees have been accounted for
- The estimated profit in Rands

