package com.swaroop.udemy.vertx_starter.verticles;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerticleN extends VerticleBase {

  private static final Logger LOG = LoggerFactory.getLogger(VerticleN.class);

  @Override
  public Future<?> start() {
    LOG.debug("Start {}", getClass().getName() + " with config " +config().toString());
    return Future.succeededFuture();
  }
}

