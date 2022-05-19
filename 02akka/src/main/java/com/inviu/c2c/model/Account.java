package com.inviu.c2c.model;

import com.inviu.c2c.model.AccountAggregate.Portfolio;

import java.util.HashMap;
import java.util.Map;

public class Account {
    private final Map<String, Integer> holdingList;

    public Account(Map<String, Integer> holdingList) {
        this.holdingList = holdingList;
    }

    public Portfolio getPortfolio() {
        return new Portfolio(this.holdingList);
    }

    public Account addHoldingToPortfolio(String ticker, int amount) {
        HashMap<String, Integer> newHoldings = new HashMap<>(this.holdingList);
        newHoldings.compute(
                ticker,
                (key, previous) -> amount + (previous != null ? previous : 0));

        return new Account(newHoldings);
    }
}
