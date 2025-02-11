package com.github.raphaelfontoura.concurrentCollections;

import java.util.concurrent.CountDownLatch;

public class Restaurant {

  public static void main(String[] args) {
    int numberOfChefs = 3;
    CountDownLatch latch = new CountDownLatch(numberOfChefs);

    new Thread(new Chef("Chef A", "Pizza", latch)).start();
    new Thread(new Chef("Chef B", "Pasta", latch)).start();
    new Thread(new Chef("Chef C", "Salad", latch)).start();

    try {
      latch.await();
      System.out.println("All dishes are ready!");
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}

class Chef implements Runnable {

  private final String name;
  private final String dish;
  private final CountDownLatch latch;

  public Chef(String name, String dish, CountDownLatch latch) {
    this.name = name;
    this.dish = dish;
    this.latch = latch;
  }

  @Override
  public void run() {
    try {
      System.out.println("Chef " + name + " is preparing " + dish);
      Thread.sleep(2000);
      System.out.println("Chef " + name + " has finished preparing " + dish);
      latch.countDown();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
