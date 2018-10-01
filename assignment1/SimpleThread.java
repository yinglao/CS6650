package com.example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class SimpleThread extends Thread implements Runnable {

  private int id;
  private int numOfIteration;


  public SimpleThread(int id, int numOfIteration) {
    this.id = id;
    this.numOfIteration = numOfIteration;
  }

  @Override
  public void run() {
//    System.out.println("Thread " + this.id);
    Client c = ClientBuilder.newClient();
    WebTarget target = c.target(Main.BASE_URI);
    Invocation.Builder builder = target.path("myresource").request(MediaType.TEXT_PLAIN);
    Calendar cal = Calendar.getInstance();
    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");

    for (int i = 0; i < numOfIteration; i++){
      Main.requestCount.incrementAndGet();
      String responseMsg = target.path("myresource").request().get(String.class);
      Main.responseCount.incrementAndGet();
      Main.requestCount.incrementAndGet();
      Response response = builder.post(Entity.entity("post", MediaType.TEXT_PLAIN));
      Main.responseCount.incrementAndGet();
    }
    if (Main.completeThreads.incrementAndGet() == Main.targetNumOfThreads) {
      System.out.println(Main.phaseName[Main.phaseCount.get()] + "Phase Complete at:" + dateFormat.format(cal.getTime()));
      long duration = System.currentTimeMillis() - Main.currentTime;
      Main.currentTime = System.currentTimeMillis();
      System.out.println(Main.phaseName[Main.phaseCount.get()] + "use time :" + duration  + "ms");
      System.out.println(Main.phaseCount.get());
      if (Main.phaseCount.get() < 3){
        int nextNumOfThreads =(int)(Main.factor[Main.phaseCount.get()] * Main.maxNumOfThreads);
        Main.phase(nextNumOfThreads);
        Main.phaseCount.incrementAndGet();
      } else {
        Main.printSummary();
      }

    }


  }
}
