package com.example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.ws.rs.client.ClientBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 */
public class Main {

  // Base URI the Grizzly HTTP server will listen on
  public static final String BASE_URI = "http://webapp-env.jxuabpefqu.us-east-2.elasticbeanstalk.com/webapi";
  public static AtomicInteger completeThreads = new AtomicInteger();
  public static int targetNumOfThreads = 0;
  public static int numOfIteration = 100;
  public static AtomicInteger phaseCount = new AtomicInteger();
  public static double[] factor = {0.1,0.5,1.0,0.25};
  public static String[] phaseName = {"Warmup", "Loading", "Peak", "Cooldown"};
//  public static Long[] completeTime = {0L,0L,0L,0L};
  public static long currentTime = System.currentTimeMillis();
  public static int maxNumOfThreads = 100;
  public static long startTime = 0L;
  public static AtomicInteger requestCount;
  public static AtomicInteger responseCount;


  public static void phase(int numOfThreads) {
    Main.completeThreads.set(0);
    Main.targetNumOfThreads = numOfThreads;
    for (int i = 0; i < numOfThreads; i++) {
      Thread th = new Thread(new SimpleThread(i, Main.numOfIteration));
      th.start();
    }
  }

  public static void printSummary() {
    System.out.println("==========================================");
    System.out.println("Total number of request sent: " + requestCount);
    System.out.println("Total number of successful responses: " + responseCount);
    System.out.printf("Test Wall Time: %.2f s\n ", (double)(System.currentTimeMillis() - Main.startTime)/1000 );

  }

  public static void main(String[] args) throws IOException {

//    int maxNumOfThreads = Integer.parseInt(args[0]);
    int maxNumOfThreads = 50;
//    int numOfIterationPerThread = Integer.parseInt(args[1]);
    int numOfIterationPerThread = 10;
//    String ipAddress = args[2];
//    int port = Integer.parseInt(args[3]);
    Main.requestCount = new AtomicInteger();
    Main.responseCount = new AtomicInteger();
    Calendar cal = Calendar.getInstance();
    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
    Date startTime = cal.getTime();
    System.out.println("Client starting......Time:"  + dateFormat.format(startTime));
    Main.currentTime = System.currentTimeMillis();
    Main.startTime = System.currentTimeMillis();


    // Warmup Phase
    Main.phase(maxNumOfThreads/10);
    System.out.println("Warmup Phase: All threads running...");
//    for (int i = 0; i < maxNumOfThreads * 0.1; i++) {
//      Thread th = new Thread(new SimpleThread(i, numOfIterationPerThread));
//      th.start();
//    }




    //Loading Phase
//    Main.phase(maxNumOfThreads/2);
//    System.out.println("Loading Phase: All threads running...");
//    for (int i = 0; i < maxNumOfThreads * 0.5; i++) {
//      Thread th = new Thread(new SimpleThread(i, numOfIterationPerThread));
//      th.start();
//    }


    //Peak Phase
//    Main.phase(maxNumOfThreads);
//    System.out.println("Peak Phase: All threads running...");
//    for (int i = 0; i < maxNumOfThreads; i++) {
//      Thread th = new Thread(new SimpleThread(i, numOfIterationPerThread));
//      th.start();
//    }



    //Cooldown Phase
//    Main.phase(maxNumOfThreads/4);
//    System.out.println("Cooldown Phase: All threads running...");
//    for (int i = 0; i < maxNumOfThreads * 0.25; i++) {
//      Thread th = new Thread(new SimpleThread(i, numOfIterationPerThread));
//      th.start();
//    }



  }
}

