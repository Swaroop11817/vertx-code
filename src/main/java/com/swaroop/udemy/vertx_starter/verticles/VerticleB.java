package com.swaroop.udemy.vertx_starter.verticles;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerticleB extends VerticleBase {

  private static final Logger LOG = LoggerFactory.getLogger(VerticleB.class);


  @Override
  public Future<?> start() {
    LOG.debug("Start {}",  getClass().getName());
    return Future.succeededFuture();
  }
}
