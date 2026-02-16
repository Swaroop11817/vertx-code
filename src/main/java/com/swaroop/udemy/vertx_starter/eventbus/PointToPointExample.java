package com.swaroop.udemy.vertx_starter.eventbus;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PointToPointExample {

  public static void main(String[] args) {
     var vertx = Vertx.vertx();
     vertx.deployVerticle(new Receiver());
     vertx.deployVerticle(new Sender());
  }

  static class Sender extends VerticleBase{

    @Override
    public Future<Void> start(){
      vertx.setPeriodic(1000, id -> {
        vertx.eventBus().send(Sender.class.getName(), "Sending a message..");
      });
      return Future.succeededFuture();
    }
  }

  static class Receiver extends VerticleBase{
    private static final Logger LOG = LoggerFactory.getLogger(Receiver.class);


    @Override
    public Future<Void> start(){
      vertx.eventBus().consumer(Sender.class.getName()).handler(message ->{
        LOG.debug("Received {}",message.body());
      });
      return Future.succeededFuture();
    }
  }

}
