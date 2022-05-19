package com.inviu.c2c.model;

import akka.actor.typed.ActorRef;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;

import java.util.HashMap;
import java.util.Map;

public class AccountAggregate extends AbstractBehavior<AccountAggregate.AccountMessage> {
    interface AccountMessage {}

    public record GetPortfolio(ActorRef<Portfolio> replyTo) implements AccountMessage {}
    public record AddHoldingToPortfolio(String ticker, int amount) implements AccountMessage {}

    public record Portfolio(Map<String, Integer> holdingList) {}

    private Account account;

    public AccountAggregate(ActorContext<AccountMessage> context) {
        super(context);
        this.account = new Account(new HashMap<>());
    }

    @Override
    public Receive<AccountMessage> createReceive() {
        return newReceiveBuilder()
                .onMessage(GetPortfolio.class, message -> {
                    message.replyTo().tell(account.getPortfolio());
                    return this;
                })
                .onMessage(AddHoldingToPortfolio.class, message -> {
                    account = account.addHoldingToPortfolio(message.ticker(), message.amount());
                    return this;
                })
                .build();
    }
}
