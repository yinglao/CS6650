package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.SynchronizedDescriptiveStatistics;

/**
 * ClientTest class.
 */
public class ClientTest {

  public static void runPhase(Client client, int numberOfThreads, int numberOfIterations, String BASE_URI) {

    ExecutorService phase = Executors.newFixedThreadPool(numberOfThreads);
//    List<Runnable> threads = new ArrayList<>();
//    SynchronizedDescriptiveStatistics latencies = new SynchronizedDescriptiveStatistics();
//    CopyOnWriteArrayList<ArrayList<Integer>> latenciesCollection = new CopyOnWriteArrayList<>();
    List<ArrayList<Integer>> latenciesCollection = Collections.synchronizedList(new ArrayList<>());

    long initialTime = System.currentTimeMillis();
    for (int i = 0; i < numberOfThreads; i++) {
      ArrayList<Integer> latencies = new ArrayList<>();
      phase.submit(new UnitTask(BASE_URI, numberOfIterations, latencies, latenciesCollection, client));
    }


    try {
      phase.shutdown();
      phase.awaitTermination(30L, TimeUnit.SECONDS);

//      List<Future<Integer>> timePerThread = phase.invokeAll(threads);
      int totalTime = (int) (System.currentTimeMillis() - initialTime);

//      int totalTime = (int) (System.currentTimeMillis() - initialTime);

//      int[] latencyData = new int[numberOfThreads];
//      for (int i = 0; i < timePerThread.size(); i++) {
//        Future<Integer> future = timePerThread.get(i);
//        latencyData[i] = (future.get());
//      }
//      List<Object> flat =
//          list.stream()
//              .flatMap(List::stream)
//              .collect(Collectors.toList());
      List<Integer> allLatencies = latenciesCollection.stream().flatMap(List::stream).collect(Collectors.toList());
//      String[] array = list.toArray(new String[0]);
//      Integer[] latenciesArray = allLatencies.toArray(new Integer[0]);
      DescriptiveStatistics latencies = new DescriptiveStatistics();
      for (Integer latency: allLatencies) {
        latencies.addValue(latency);
      }

      System.out.println("number of latency catch: "+ allLatencies.size());
      System.out.println("Phase use time:" + totalTime + " ms");
      System.out.println(
          "Total Number of requests sent: " + numberOfIterations * numberOfThreads);
      System.out.println("Total Number of successful requests: " + latencies.getN());
      System.out.println("Mean latency: " + latencies.getMean() + " ms");
      System.out.println("Median latency: " + latencies.getPercentile(50) + " ms");
      System.out.println("95 percentile latency: " + latencies.getPercentile(95) + " ms");
      System.out.println("99 percentile latency: " + latencies.getPercentile(99) + " ms");
      System.out.println("<<<<<<<<<<<<<<<<<<<<<<< Phase End ");

    } catch (InterruptedException e) {
      System.out.println(e.getCause());
    }
//    catch (ExecutionException e) {
//      System.out.println(e.getCause());
//    }
  }

  public static void main(String[] args) throws IOException {
    int maxNumberOfThreads = 100;
    int numberOfIterations = 100;
    String BASE_URI = "http://ec2-18-236-89-52.us-west-2.compute.amazonaws.com:8080/webapp/webapi";
    Double[] factors = {0.1, 0.5, 1.0, 0.25};
    String[] phaseNames = {"Warm-up", "Loading", "Peak", "Cooldown"};
    Client c = ClientBuilder.newClient();
    for (int i = 0; i < 4; i++) {
      System.out.println(phaseNames[i] + " phase Start >>>>>>>>>>>>>>>>>");
      runPhase(c, (int) (maxNumberOfThreads * factors[i]), numberOfIterations, BASE_URI);
    }

  }


}

