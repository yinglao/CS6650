package edu.neu.ccs.bsds.lamport;

import java.util.HashMap;

public interface ProcessInterface {

  void send(ProcessInterface otherProcess);

  void receive(ProcessInterface otherProcess);

  LamportClock getClock();

  HashMap<Integer, Integer> getLog();

}
