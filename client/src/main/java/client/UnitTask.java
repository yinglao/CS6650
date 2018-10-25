package client;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.math3.stat.descriptive.SynchronizedDescriptiveStatistics;

public class UnitTask implements Runnable {


  private String ip;
  private int numOfIteration;
  private ArrayList<Integer> latencies;
  private CopyOnWriteArrayList<ArrayList<Integer>> latenciesCollection;


  public UnitTask(String ip, int numOfIteration, ArrayList<Integer> latencies, CopyOnWriteArrayList<ArrayList<Integer>> latenciesCollection) {
    this.ip = ip;
    this.numOfIteration = numOfIteration;
    this.latencies = latencies;
//    System.out.println("Build thread success!");
    this.latenciesCollection = latenciesCollection;
  }

  @Override
  public void run() {
    long start = System.currentTimeMillis();
    Client c = ClientBuilder.newClient();
    WebTarget target = c.target(this.ip);
    Invocation.Builder builder = target.path("myresource").request(MediaType.TEXT_PLAIN);

    for (int i = 0; i < numOfIteration; i++) {
      String responseMsg = target.path("myresource").request().get(String.class);
      long beforeResponse = System.currentTimeMillis();
//      System.out.println(i);

      Response response = builder.post(Entity.entity("post", MediaType.TEXT_PLAIN));
      int latency = (int) (System.currentTimeMillis() - beforeResponse);
      this.latencies.add(latency); // int -> double
      response.close();

    }
    this.latenciesCollection.add(latencies);
//    int time = (int) (System.currentTimeMillis() - start);
//    return time;
  }


}
