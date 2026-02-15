package com.swaroop.udemy.vertx_starter.verticles;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class MainVerticle extends VerticleBase {

  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

  public static void main(String[] args) {
    final Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new MainVerticle());
  }

  @Override
  public Future<?> start() {
    LOG.debug("Start {}" + getClass().getName());
    vertx.deployVerticle(new VerticleA());
    vertx.deployVerticle(new VerticleB());
    vertx.deployVerticle(VerticleN.class.getName(),
                          new DeploymentOptions().setInstances(4)
                            .setConfig(new JsonObject()
                              .put("id", UUID.randomUUID().toString())
                              .put("name", VerticleN.class.getSimpleName()))
                            );
    return Future.succeededFuture();
  }

}
