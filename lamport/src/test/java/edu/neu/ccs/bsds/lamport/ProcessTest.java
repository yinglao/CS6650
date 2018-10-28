package edu.neu.ccs.bsds.lamport;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ProcessTest {
  private Process process1;
  private Process process2;
  private Process process3;
  @Before
  public void setUp() throws Exception {
    process1 = new Process(new LamportClock());
    process2 = new Process(new LamportClock());
    process3 = new Process(new LamportClock());
    process1.instruct();
    process1.send(process2);
    process2.receive(process1);
    process2.instruct();
    process2.send(process3);
    process3.receive(process2);


  }

  @Test
  public void getEvent() {
  }

  @Test
  public void getClock() {
  }

  @Test
  public void getLog() {
    System.out.println(this.process1.getLog().toString());
    System.out.println(this.process2.getLog().toString());
    System.out.println(this.process3.getLog().toString());
  }

  @Test
  public void receive() {
  }

  @Test
  public void send() {
  }

  @Test
  public void instruct() {
  }
}