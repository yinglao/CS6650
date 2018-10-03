package client;

import static java.lang.System.currentTimeMillis;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Callable;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UnitTask implements Callable {


  private String ip;
  private int numOfIteration;


  public UnitTask(String ip, int numOfIteration) {
    this.ip = ip;
    this.numOfIteration = numOfIteration;
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
//      System.out.println(responseMsg);
      Response response = builder.post(Entity.entity("post", MediaType.TEXT_PLAIN));
//      System.out.println(response.readEntity(String.class));

    }
    int latency = (int)(System.currentTimeMillis() - start);
    return latency;
  }


}
