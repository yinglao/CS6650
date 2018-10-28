package edu.neu.ccs.bsds.lamport;

public class LamportClock {
  private int timer;


  public int increment() {
    this.timer += 1;
    return this.timer;
  }

  public int getTimer() {
    return timer;
  }

  public int update(LamportClock other) {
    this.timer = Math.max(this.timer, other.getTimer()) + 1;
    return timer;
  }

}
