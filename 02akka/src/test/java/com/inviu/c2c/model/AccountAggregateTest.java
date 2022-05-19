package com.inviu.c2c.model;

import akka.actor.testkit.typed.javadsl.ActorTestKit;
import akka.actor.testkit.typed.javadsl.TestProbe;
import akka.actor.typed.ActorRef;
import akka.actor.typed.javadsl.Behaviors;
import com.inviu.c2c.model.AccountAggregate.AccountMessage;
import com.inviu.c2c.model.AccountAggregate.AddHoldingToPortfolio;
import com.inviu.c2c.model.AccountAggregate.GetPortfolio;
import com.inviu.c2c.model.AccountAggregate.Portfolio;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Map;

class AccountAggregateTest {
    ActorTestKit testKit = ActorTestKit.create("c2c-akka");

    @Test
    void itReturnsEmptyHoldingsWhenItHasNone() {
        ActorRef<AccountMessage> account = testKit.spawn(Behaviors.setup(AccountAggregate::new));
        TestProbe<Portfolio> testProbe = testKit.createTestProbe();

        account.tell(new GetPortfolio(testProbe.getRef()));

        testProbe.expectMessage(Duration.ofSeconds(5), new Portfolio(Map.of()));
    }

    @Test
    void itReturnsTheUpdatedPortfolioWhenHoldingsAreAdded() {
        ActorRef<AccountMessage> account = testKit.spawn(Behaviors.setup(AccountAggregate::new));
        TestProbe<Portfolio> testProbe = testKit.createTestProbe();

        account.tell(new AddHoldingToPortfolio("COME", 5));
        account.tell(new GetPortfolio(testProbe.getRef()));

        Assertions.assertThat(testProbe.receiveMessage().holdingList())
                .containsExactly(Map.entry("COME", 5));
    }
}
