package client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientResponse;

/**
 * Hello world!
 */
public class App {

  public static void main(String[] args) {
    Invocation.Builder builder;
    Response response;
    String responseMsg;
    String BASE_URI = "http://localhost:8080/webapi";

    Client c = ClientBuilder.newClient();
    WebTarget target = c.target(BASE_URI);

//    MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
//    formData.add("userId", "5");
//    formData.add("day", "5");
//    formData.add("timeInterval", "5");
//    formData.add("stepCount", "5");
//    Form form = new Form();
//    form.param("userId", "5");
//    form.param("day", "5");
//    form.param("timeInterval", "5");
//    form.param("stepCount", "5");

    response = target.request().post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED_TYPE));
//    MyJAXBBean bean =
//        target.request(MediaType.APPLICATION_JSON_TYPE)
//            .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED_TYPE),
//                MyJAXBBean.class);

    responseMsg = target.path("current/" + 1).request().get(String.class);

//    Response response = target.path("webapi/single/1/1").request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(Response.class);
//        Invocation.Builder builder = target.path("myresource").request(MediaType.TEXT_PLAIN);
//    Integer responseMsg = target.path("current/1").request().get(Integer.class);

    System.out.println(response.readEntity(String.class));
    System.out.println(responseMsg);
    response.close();




//        Response response = builder.post(Entity.entity("post", MediaType.TEXT_PLAIN));
//        int latency = (int) (System.currentTimeMillis() - beforeResponse);
//        this.latencies.add(latency); // int -> double

  }
}
