package com.example;

import org.junit.Test;

import com.example.message.SetRequest;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;

public class AkkademyDbTest {

  ActorSystem actorSystem = ActorSystem.create();

  @Test
  public void itShouldPlaceKeyValueFromSetMessageIntoMap() {
    SetRequest setRequest = new SetRequest("test", "test");
    //TestActorRef 相比 ActorRef:
    // Attention：ActorRef无类型
    ActorRef actorRef = actorSystem.actorOf(Props.create(AkkademyDb::new));
    TestActorRef<AkkademyDb> actorTestActorRef = TestActorRef.create(actorSystem, Props.create(AkkademyDb.class));
    //将消息放入 Actor 的邮箱中，实际上是个异步调用
    //1. 但是 TestActorRef actor API 是同步的，不用考虑并发问题
    actorTestActorRef.tell(setRequest, ActorRef.noSender());
    //2. 通过 TestActorRef 可以拿到真实的对象: actorTestActorRef.underlyingActor();
    AkkademyDb akkademyDb = actorTestActorRef.underlyingActor();
    org.junit.Assert.assertEquals(akkademyDb.map.get("test"), "test");
  }
}
