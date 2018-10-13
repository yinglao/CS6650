package client;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.SynchronizedDescriptiveStatistics;

/**
 * Client class.
 */
public class Client {

  public static void runPhase(int numberOfThreads, int numberOfIterations, String BASE_URI) {

    ExecutorService phase = Executors.newFixedThreadPool(numberOfThreads);
    List<Callable<Integer>> threads = new ArrayList<>();
    SynchronizedDescriptiveStatistics latencies = new SynchronizedDescriptiveStatistics();

    for (int i = 0; i < numberOfThreads; i++) {
      threads.add(new UnitTask(BASE_URI, numberOfIterations, latencies));
    }
    try{
      long initialTime = System.currentTimeMillis();
      List<Future<Integer>> timePerThread = phase.invokeAll(threads);
      phase.shutdown();
      int totalTime = (int)(System.currentTimeMillis() - initialTime);

      int[] latencyData = new int[numberOfThreads];
      for (int i = 0; i < timePerThread.size(); i++) {
        Future<Integer> future = timePerThread.get(i);
        latencyData[i] = (future.get());
      }
      System.out.println("Phase use time:" + totalTime + " ms");
      System.out.println("Total Number of requests sent: " + numberOfIterations * numberOfThreads + " ms");
      System.out.println("Total Number of successful requests: " + latencies.getN());
      System.out.println("Mean latency: " + latencies.getMean() + " ms");
      System.out.println("Median latency: " + latencies.getPercentile(50) + " ms");
      System.out.println("95 percentile latency: " + latencies.getPercentile(95) + " ms");
      System.out.println("99 percentile latency: " + latencies.getPercentile(99) + " ms");
      System.out.println("<<<<<<<<<<<<<<<<<<<<<<< Phase End ");

    } catch (InterruptedException e) {
      System.out.println(e.getCause());
    } catch (ExecutionException e) {
      System.out.println(e.getCause());
    }
  }

  public static void main(String[] args) throws IOException {
    int maxNumberOfThreads = 10;
    int numberOfIterations = 5;
    String BASE_URI = "http://webapp-env.jxuabpefqu.us-east-2.elasticbeanstalk.com/webapi";
    Double[] factors = {0.1, 0.5, 1.0, 0.25};
    String[] phaseNames = {"Warm-up", "Loading", "Peak", "Cooldown"};
    for (int i = 0; i < 4; i++) {
      System.out.println(phaseNames[i] + " phase Start >>>>>>>>>>>>>>>>>");
      runPhase((int)(maxNumberOfThreads * factors[i]), numberOfIterations, BASE_URI);
    }

  }



}

