package com.example.client;

import java.util.concurrent.ThreadLocalRandom;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.example.stepcount.model.StepData;

/**
 * Hello world!
 */
public class App {

  public static void main(String[] args) {

    Response response;
    String responseMsg;
    String BASE_URI = "http://localhost:8080/webapi";

    Client c = ClientBuilder.newClient();
//    WebTarget target = c.target(BASE_URI);
//    StepData stepData = new StepData(5,5,5,5);

//    response = target.request().post(Entity.entity(stepData, MediaType.APPLICATION_JSON));


    long start = System.currentTimeMillis();
    int userId1 = ThreadLocalRandom.current().nextInt(0, 100000);
    int timeInterval1 = ThreadLocalRandom.current().nextInt(0, 3);
    int stepCount1 = ThreadLocalRandom.current().nextInt(0, 5001);
    StepData stepData = new StepData(userId1,1,timeInterval1,stepCount1);
    long beforeResponse;
    int latency;


    WebTarget target = c.target(BASE_URI);
    System.out.println(BASE_URI);
    beforeResponse = System.currentTimeMillis();
    response = target.request().post(Entity.entity(stepData, MediaType.APPLICATION_JSON));
    latency = (int) (System.currentTimeMillis() - beforeResponse);
    response.close();




//        Response response = builder.post(Entity.entity("post", MediaType.TEXT_PLAIN));
//        int latency = (int) (System.currentTimeMillis() - beforeResponse);
//        this.latencies.add(latency); // int -> double

  }
}
