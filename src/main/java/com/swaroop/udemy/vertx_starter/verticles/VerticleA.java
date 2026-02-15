package com.swaroop.udemy.vertx_starter.verticles;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerticleA extends VerticleBase {

  private static final Logger LOG = LoggerFactory.getLogger(VerticleA.class);

  @Override
  public Future<?> start() {
    LOG.debug("Start {}",  getClass().getName());
    vertx.deployVerticle(new VerticleAA()).onComplete(whenDeployed->{
      LOG.debug("Deployed {}",  VerticleAA.class.getName());
      vertx.undeploy(whenDeployed.result());
    });
    vertx.deployVerticle(new VerticleAB()).onComplete(whenDeployed->{
      LOG.debug("Deployed {}", VerticleAB.class.getName());
    });;
    return Future.succeededFuture();
  }
}
