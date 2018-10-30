package client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Hello world!
 */
public class App {

  public static void main(String[] args) {
    Client c = ClientBuilder.newClient();
    WebTarget target = c.target("http://localhost:8080");
    Response response = target.path("single/1/1").request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(Response.class);
//        Invocation.Builder builder = target.path("myresource").request(MediaType.TEXT_PLAIN);
//    Integer responseMsg = target.path("current/1").request().get(Integer.class);

    System.out.println(response.readEntity(String.class));


//        Response response = builder.post(Entity.entity("post", MediaType.TEXT_PLAIN));
//        int latency = (int) (System.currentTimeMillis() - beforeResponse);
//        this.latencies.add(latency); // int -> double
//        response.close();
  }
}
