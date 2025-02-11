package com.github.raphaelfontoura.concurrentCollections;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MultiStageTour {

  private static final int NUM_TOURISTS = 5;
  private static final int NUM_STAGES = 3;

  private static final CyclicBarrier barrier = new CyclicBarrier(NUM_TOURISTS, () -> {
    System.out.println("Tour guide starts speaking...");
    try {
      Thread.sleep(5000);
      System.out.println("Tour guide finishes speaking.");
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  });

  public static void main(String[] args) {
    for (int i = 0; i < NUM_TOURISTS; i++) {
      new Thread(new Tourist(i)).start();
    }
  }

  static class Tourist implements Runnable {
    private final int touristId;

    public Tourist(int touristId) {
      this.touristId = touristId;
    }

    @Override
    public void run() {
      for (int i = 0; i < NUM_STAGES; i++) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        System.out.println("Tourist " + touristId + " arrives at stage " + (i + 1));

        try {
          barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
          throw new RuntimeException(e);
        }
      }
    }

  }

}
