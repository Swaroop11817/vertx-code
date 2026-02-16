package com.swaroop.udemy.vertx_starter.eventbus.customcodec;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingPongExample extends VerticleBase {

  private static final Logger LOG = LoggerFactory.getLogger(PingPongExample.class);

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new PingVerticle()).onComplete(ar->{
      if(ar.failed()){
        LOG.error("err", ar.cause());
      }
    });
    vertx.deployVerticle(new PongVerticle()).onComplete(ar->{
      if(ar.failed()){
        LOG.error("err", ar.cause());
      }
    });
  }

  static class PingVerticle extends VerticleBase {

    private static final Logger LOG = LoggerFactory.getLogger(PingVerticle.class);

    public static final String ADDRESS =PingVerticle.class.getName();

    @Override
    public Future<Void> start(){
      var eventBus = vertx.eventBus();
      final Ping message = new Ping("Hello",true);
      LOG.debug("Sending {}", message);
      eventBus.registerDefaultCodec(Ping.class, new LocalMessageCodec<>(Ping.class));
      eventBus.<Pong>request(ADDRESS,message).onComplete(
        reply->{
          if(reply.failed()){
            LOG.debug("Failed: ", reply.cause());
            return;
          }
          LOG.debug("Response {}",reply.result().body());
        }
      );
      return Future.succeededFuture();

    }
  }

  static class PongVerticle extends VerticleBase{
    private static final Logger LOG = LoggerFactory.getLogger(PongVerticle.class);
    @Override
    public Future<Void> start(){
      vertx.eventBus().registerDefaultCodec(Pong.class, new LocalMessageCodec<>(Pong.class));
      vertx.eventBus().<Ping>consumer(PingVerticle.ADDRESS).handler(
        message -> {
          LOG.debug("Received Message: {}",message.body());
          message.reply(new Pong(0));
        }
      ).exceptionHandler(error -> {
        LOG.error("Error: ",error);
      });
      return Future.succeededFuture();
    }

  }
}
