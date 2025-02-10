package com.github.raphaelfontoura.threadSynchronisation;

public class WaitAndNotifyDemo {

  private static final Object LOCK = new Object();

  public static void main(String[] args) {
    Thread t1 = new Thread(() -> {
      try {
        one();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    });

    Thread t2 = new Thread(() -> {
      try {
        two();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    });

    t1.start();
    t2.start();
  }

  private static void one() throws InterruptedException {
    synchronized (LOCK) {
      System.out.println("Hello from method one ...");
      LOCK.wait();
      System.out.println("Back again from the method one");
    }
  }

  private static void two() throws InterruptedException {
    synchronized (LOCK) {
      System.out.println("Hello from method two ...");
      LOCK.notify();
      System.out.println("Hello from method two even after the notify ...");
    }
  }
}
