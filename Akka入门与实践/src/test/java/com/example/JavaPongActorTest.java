package com.example;

import static akka.pattern.Patterns.ask;
import static scala.compat.java8.FutureConverters.toJava;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import scala.concurrent.Future;

public class JavaPongActorTest {

    ActorSystem actorSystem = ActorSystem.create();

    public ActorRef howToCreateActor() {
        ActorRef pong = actorSystem.actorOf(JavaPongActor.props("pong"));

        //每个Actor在创建的时候否都会有一个路径
        ActorPath path = pong.path();

        //如果知道 Actor 的路径，就可以使用 actorSelection 来获取指向该 Actor的 ActorSelection（无论该 Actor 在本地还是远程）
        ActorSelection actorSelection = actorSystem.actorSelection(path);
        return pong;
    }

    @Test
    public void shouldReplyPingWithPong() throws Exception {
        Future<Object> ping = ask(howToCreateActor(), "ping", 1000);
        CompletionStage<Object> future = toJava(ping);
        CompletableFuture<Object> future1 = (CompletableFuture<Object>) future;
        assert future1.get(1000, TimeUnit.MILLISECONDS).equals("pong");
    }

    @Test(expected = ExecutionException.class)
    public void shouldReplyToUnknownMessageWithFailure() throws Exception {
        //TODO
        Future ping = ask(howToCreateActor(), "xxxxx", 1000);
        CompletionStage<String> future = toJava(ping);
        CompletableFuture<String> future1 = (CompletableFuture<String>) future;
        future1.get(1000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void printToConsole() throws Exception {
        askPong("Ping").thenAccept(x -> System.out.println("replied with: " + x));

        CompletionStage<CompletionStage<String>> completionStageCompletionStage = askPong("Ping").
          thenApply(x -> askPong("pong"));

        CompletionStage<String> stringCompletionStage = askPong("Ping").
          thenCompose(x -> askPong("pong"));

        askPong("XXXXXXX").handle((x, ex) -> {
            if (ex != null) {
                System.out.println("Error: " + ex);
            } else {
                System.out.println("replied with: " + x);
            }
            return null;
        });

        askPong("XXXXXXX").exceptionally(r -> "Error");
        Thread.sleep(1000);
    }

    public CompletionStage<String> askPong(String message) {
        Future ask = ask(howToCreateActor(), message, 1000);
        return toJava(ask);
    }

}
