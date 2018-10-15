package client;

import java.util.concurrent.Callable;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.math3.stat.descriptive.SynchronizedDescriptiveStatistics;

public class UnitTask implements Callable {


  private String ip;
  private int numOfIteration;
  private SynchronizedDescriptiveStatistics latencies;


  public UnitTask(String ip, int numOfIteration, SynchronizedDescriptiveStatistics latencies) {
    this.ip = ip;
    this.numOfIteration = numOfIteration;
    this.latencies = latencies;
//    System.out.println("Build thread success!");
  }

  @Override
  public Integer call() {
    long start = System.currentTimeMillis();
    Client c = ClientBuilder.newClient();
    WebTarget target = c.target(this.ip);
    Invocation.Builder builder = target.path("myresource").request(MediaType.TEXT_PLAIN);

    for (int i = 0; i < numOfIteration; i++) {
      String responseMsg = target.path("myresource").request().get(String.class);
      long beforeResponse = System.currentTimeMillis();

      Response response = builder.post(Entity.entity("post", MediaType.TEXT_PLAIN));
      int latency = (int) (System.currentTimeMillis() - beforeResponse);
      this.latencies.addValue(latency); // int -> double

    }
    int time = (int) (System.currentTimeMillis() - start);
    return time;
  }


}
