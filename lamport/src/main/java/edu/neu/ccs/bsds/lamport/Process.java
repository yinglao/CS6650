package edu.neu.ccs.bsds.lamport;

import java.util.HashMap;

public class Process implements ProcessInterface{
  private LamportClock clock;
  private HashMap<Integer, Integer> log;
  private int event;

  public Process(LamportClock clock) {
    this.clock = clock;
    this.log = new HashMap<>();
    this.log.put(this.event, this.clock.getTimer());
  }

  public int getEvent() {
    return event;
  }

  public LamportClock getClock() {
    return clock;
  }

  @Override
  public HashMap<Integer, Integer> getLog() {
    return log;
  }

  @Override
  public void receive(ProcessInterface otherProcess) {
    this.clock.update(otherProcess.getClock());
    this.event += 1;
    this.log.put(this.event, this.clock.getTimer());
  }

  @Override
  public void send(ProcessInterface otherProcess) {
    this.clock.increment();
    this.event += 1;
    this.log.put(this.event, this.clock.getTimer());
  }

  public void instruct() {
    this.clock.increment();
    this.event += 1;
    this.log.put(this.event, this.clock.getTimer());
  }

}
