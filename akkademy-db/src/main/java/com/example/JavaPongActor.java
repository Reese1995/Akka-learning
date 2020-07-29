package com.example;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Status;
import akka.japi.pf.ReceiveBuilder;

/**
 * @author Reese
 */
public class JavaPongActor extends AbstractActor {

    private final String response;

    public static Props props(String response) {
        return Props.create(JavaPongActor.class, response);
    }

    private JavaPongActor(String response) {
        this.response = response;
    }

    @Override
    public Receive createReceive() {
        //匹配模式：
        //1 match(class, function)：
        //2 matchEquals(object, function)
        //3 match(class, predicate, function)：
        ReceiveBuilder.create().match(String.class, "ping"::equals, s -> sender().tell("pong", ActorRef.noSender()))
          .build();

        //sender(): 响应的对象既可能是一个 Actor，也可能是来自于 Actor 系统外部的请求。
        // 返回的消息会直接发送到该 Actor 的收件信箱中
        //
        return ReceiveBuilder.create().matchEquals("ping", s -> {
            sender().tell(response, self());
            //一旦actor中发生异常，就会通知其监督者
        }).matchAny(x -> sender().tell(new Status.Failure(new Exception("unknown message")), ActorRef.noSender()))
          .build();
    }
}
