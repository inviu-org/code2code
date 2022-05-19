package com.inviu.c2c.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class AccountTest {
    @Test
    void itHoldsAnEmptyPortfolio() {
        Account account = new Account(Map.of());

        Assertions.assertThat(account.getPortfolio().holdingList())
                .isEmpty();
    }
}
