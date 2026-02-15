package com.swaroop.udemy.vertx_starter.eventloops;

import io.vertx.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class EventLoopExample extends VerticleBase {

  private static final Logger LOG = LoggerFactory.getLogger(EventLoopExample.class);

  public static void main(String[] args) {
    var vertx = Vertx.vertx(
      new VertxOptions()
        .setMaxEventLoopExecuteTime(500)
        .setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS)
        .setBlockedThreadCheckInterval(1)
        .setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS)
    );
  //  vertx.deployVerticle(new EventLoopExample());
    vertx.deployVerticle(EventLoopExample.class.getName(),
                   new DeploymentOptions().setInstances(4));
  }

  @Override
  public Future<?> start() throws InterruptedException {
  LOG.debug("Start {}", getClass().getName());
  Thread.sleep(5000);
  return Future.succeededFuture();
  }
}
