package com.swaroop.udemy.vertx_starter.worker;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerVerticle extends VerticleBase {

  private static final Logger LOG = LoggerFactory.getLogger(WorkerVerticle.class);


  @Override
  public Future<?> start() throws InterruptedException {
    LOG.debug("Deployed as worker verticle");
    Thread.sleep(5000);
    return Future.succeededFuture();
  }
}
