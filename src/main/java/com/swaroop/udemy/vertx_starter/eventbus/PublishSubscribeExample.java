package com.swaroop.udemy.vertx_starter.eventbus;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class PublishSubscribeExample {

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new Subscriber1());
    vertx.deployVerticle(Subscriber2.class.getName(), new DeploymentOptions().setInstances(2));
    vertx.deployVerticle(new Publish());
  }

  public static class Publish extends VerticleBase{
    @Override
    public Future<Void> start(){
      vertx.setPeriodic(Duration.ofSeconds(10).toMillis(),id ->{
        vertx.eventBus().publish(Publish.class.getName(),"A message for everyone!");
      });

      return Future.succeededFuture();
    }
  }

  public static class Subscriber1 extends VerticleBase{
    private static final Logger LOG = LoggerFactory.getLogger(Subscriber1.class);

    @Override
    public Future<Void> start(){
      vertx.eventBus().consumer(Publish.class.getName(),message ->{
        LOG.debug("Received: {}", message.body());
      });
      return Future.succeededFuture();
    }
  }

  public static class Subscriber2 extends VerticleBase{
    private static final Logger LOG = LoggerFactory.getLogger(Subscriber2.class);

    @Override
    public Future<Void> start(){
      vertx.eventBus().consumer(Publish.class.getName(),message ->{
        LOG.debug("Received 2: {}", message.body());
      });
      return Future.succeededFuture();
    }
  }
}
