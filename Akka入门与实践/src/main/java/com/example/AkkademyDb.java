package com.example;

import java.util.HashMap;
import java.util.Map;

import com.example.exception.KeyNotFoundException;
import com.example.message.GetRequest;
import com.example.message.SetRequest;

import akka.actor.AbstractActor;
import akka.actor.Status;
import akka.actor.Status.Failure;
import akka.actor.Status.Success;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;

/**
 * @author Reese
 */
public class AkkademyDb extends AbstractActor {

    //为了方便测试，故此声明为 protected

    protected final LoggingAdapter log = Logging.getLogger(context().system(), this);
    protected final Map<String, Object> map = new HashMap<String, Object>();

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
          .match(SetRequest.class, message -> {
              log.info("received SetQuest message {}", message);
              map.put(message.key, message.value);
              sender().tell(new Success(message.key), self());
          })
          .match(GetRequest.class, message -> {
              log.info("received GetRequest message {}", message);
              Object value = map.get(message.key);
              value = value == null ? new Failure(new KeyNotFoundException(message.key)) : value;
              sender().tell(value, self());
          })
          .matchAny(o -> {
              sender().tell(new Status.Failure(new ClassNotFoundException()), self());
              log.info("received unknown message {}", o);
          }).build();
    }
}
