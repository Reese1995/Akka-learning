package com.example;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @author Reese
 */
public class Application {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("akkademy");
        ActorRef actorRef = system.actorOf(Props.create(AkkademyDb.class), "akkademy-db");

    }

}
