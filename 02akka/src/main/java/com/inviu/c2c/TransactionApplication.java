package com.inviu.c2c;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;

public class TransactionApplication {
    public static void main(String[] args) {
        ActorSystem<Object> system = ActorSystem.create(Behaviors.empty(), "c2c");
    }
}
