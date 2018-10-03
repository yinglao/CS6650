package client;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Main class.
 */
public class Main {

  public static void runPhase(int numberOfThreads, int numberOfIterations, String BASE_URI) {

    ExecutorService phase = Executors.newFixedThreadPool(numberOfThreads);
    List<Callable<Integer>> threads = new ArrayList<>();
    for (int i = 0; i < numberOfThreads; i++) {
      threads.add(new UnitTask(BASE_URI, numberOfIterations));
    }
    try{
      List<Future<Integer>> latencies = phase.invokeAll(threads);
      int totalTime = 0;
      for (Future<Integer> future : latencies) {
        totalTime += future.get();
      }
      System.out.println("Phase use time:" + totalTime + "ms");
      phase.shutdown();
    } catch (InterruptedException e) {
      System.out.println(e.getCause());
    } catch (ExecutionException e) {
      System.out.println(e.getCause());
    }
  }


  public static void main(String[] args) throws IOException {
    int maxNumberOfThreads = 10;
    int numberOfIterations = 2;
    String BASE_URI = "http://webapp-env.jxuabpefqu.us-east-2.elasticbeanstalk.com/webapi";
//    Thread th = new Thread(new UnitTask(BASE_URI, numberOfIterations));
//    th.start();
    ExecutorService phase = Executors.newFixedThreadPool(maxNumberOfThreads);
    List<Callable<Integer>> threads = new ArrayList<>();
    Double[] factors = {0.1, 0.5, 1.0, 0.25};
    for (int i = 0; i < 4; i++) {
      runPhase((int)(maxNumberOfThreads * factors[i]), numberOfIterations, BASE_URI);
    }



  }



}

