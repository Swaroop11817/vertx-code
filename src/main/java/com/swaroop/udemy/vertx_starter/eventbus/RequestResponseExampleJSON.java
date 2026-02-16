package com.swaroop.udemy.vertx_starter.eventbus;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestResponseExampleJSON {

  private static final Logger LOG = LoggerFactory.getLogger(RequestResponseExampleJSON.class);


  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new RequestVerticle());
    vertx.deployVerticle(new ResponseVerticle());
  }

  static class RequestVerticle extends VerticleBase {

    public static final String MY_REQUEST_ADDRESS = "my.request.address";

    @Override
    public Future<Void> start(){
      var eventBus = vertx.eventBus();
      final var message = new JsonObject()
                       .put("message","Hello World!")
                       .put("version",1);
      LOG.debug("Sending {}", message);
      eventBus.request(MY_REQUEST_ADDRESS,message).onComplete(
        reply->{
          LOG.debug("Response {}",reply.result().body());
        }
      );
      return Future.succeededFuture();

    }
  }

  static class ResponseVerticle extends VerticleBase{

    @Override
    public Future<Void> start(){
      vertx.eventBus().<JsonObject>consumer(RequestResponseExample.RequestVerticle.MY_REQUEST_ADDRESS).handler(
        message -> {
          LOG.debug("Received Message: {}",message.body());
          message.reply(new JsonArray().add("one").add("two").add("three"));
        }
      );
      return Future.succeededFuture();
    }

  }
}
