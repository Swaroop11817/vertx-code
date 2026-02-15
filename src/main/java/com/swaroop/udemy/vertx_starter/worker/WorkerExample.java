package com.swaroop.udemy.vertx_starter.worker;

import io.vertx.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerExample extends VerticleBase {

  public static final Logger LOG = LoggerFactory.getLogger(WorkerExample.class);

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new WorkerExample());
  }

  @Override
  public Future<Void> start(){
    vertx.deployVerticle(new WorkerVerticle(),
                     new DeploymentOptions()
                       .setThreadingModel(ThreadingModel.WORKER)
                       .setWorkerPoolSize(1)
                       .setWorkerPoolName("my-worker-verticle"));
     vertx.executeBlocking(() -> {
       LOG.debug("Executing blocking code");
       try{
         Thread.sleep(5000);
       }
       catch(InterruptedException e){
         LOG.error("Failed: ",e);
       }
       return null;
     }).onComplete(result -> {
         if(result.succeeded()){
           LOG.debug("Blocking call done");
         }
         else {
           LOG.debug("Blocking call failed due to:", result.cause());
         }
         });

    return Future.succeededFuture();
  }

}



