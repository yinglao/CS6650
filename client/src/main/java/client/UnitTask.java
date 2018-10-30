package client;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ThreadLocalRandom;

public class UnitTask implements Runnable {


  private String ip;
  private int numberOfIteration;
  private ArrayList<Integer> latencies;
  private List<ArrayList<Integer>> latenciesCollection;
  private Client client;
  private int startTime;
  private int endTime;
//  private CopyOnWriteArrayList<ArrayList<Integer>> latenciesCollection;


  public UnitTask(String ip, int numberOfIteration, ArrayList<Integer> latencies,
      List<ArrayList<Integer>> latenciesCollection, Client client,
      int startTime, int endTime) {
    this.ip = ip;
    this.numberOfIteration = numberOfIteration;
    this.latencies = latencies;
//    System.out.println("Build thread success!");
    this.latenciesCollection = latenciesCollection;
    this.client = client;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  @Override
  public void run() {
    long start = System.currentTimeMillis();
//    ClientTest c = ClientBuilder.newClient();

// nextInt is normally exclusive of the top value,
// so add 1 to make it inclusive

    WebTarget target = this.client.target(this.ip);

    for (int i = 0; i < numberOfIteration; i++) {

      int userId1 = ThreadLocalRandom.current().nextInt(0, 100000);
      int timeInterval1 = ThreadLocalRandom.current().nextInt(0, 100000);
      int stepCount1 = ThreadLocalRandom.current().nextInt(startTime, endTime + 1);
      int userId2 = ThreadLocalRandom.current().nextInt(0, 100000);
      int timeInterval2 = ThreadLocalRandom.current().nextInt(0, 100000);
      int stepCount2 = ThreadLocalRandom.current().nextInt(startTime, endTime + 1);
      int userId3 = ThreadLocalRandom.current().nextInt(0, 100000);
      int timeInterval3 = ThreadLocalRandom.current().nextInt(0, 100000);
      int stepCount3 = ThreadLocalRandom.current().nextInt(startTime, endTime + 1);

      long beforeResponse;
      int latency;
      Invocation.Builder builder;
      Response response;
      String responseMsg;

      builder = target.path("" + userId1 + "/" + timeInterval1 + "/1/" + stepCount1)
          .request(MediaType.TEXT_PLAIN);
      beforeResponse = System.currentTimeMillis();
      response = builder.post(Entity.entity("post", MediaType.TEXT_PLAIN));
      latency = (int) (System.currentTimeMillis() - beforeResponse);
      this.latencies.add(latency); // int -> double
      response.close();

      builder = target.path("" + userId2 + "/" + timeInterval2 + "/1/" + stepCount2)
          .request(MediaType.TEXT_PLAIN);
      beforeResponse = System.currentTimeMillis();
      response = builder.post(Entity.entity("post", MediaType.TEXT_PLAIN));
      latency = (int) (System.currentTimeMillis() - beforeResponse);
      this.latencies.add(latency); // int -> double
      response.close();



      beforeResponse = System.currentTimeMillis();
      responseMsg = target.path("current/" + userId1).request().get(String.class);
      latency = (int) (System.currentTimeMillis() - beforeResponse);
      this.latencies.add(latency); // int -> double

      beforeResponse = System.currentTimeMillis();
      responseMsg = target.path("current/" + userId1).request().get(String.class);
      latency = (int) (System.currentTimeMillis() - beforeResponse);
      this.latencies.add(latency); // int -> double

      builder = target.path("" + userId3 + "/" + timeInterval3 + "/1/" + stepCount3)
          .request(MediaType.TEXT_PLAIN);
      beforeResponse = System.currentTimeMillis();
      response = builder.post(Entity.entity("post", MediaType.TEXT_PLAIN));
      latency = (int) (System.currentTimeMillis() - beforeResponse);
      this.latencies.add(latency); // int -> double
      response.close();

    }
    this.latenciesCollection.add(latencies);
//    int time = (int) (System.currentTimeMillis() - start);
//    return time;
  }


}
