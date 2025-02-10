package com.github.raphaelfontoura.threadSynchronisation;

public class SynchronisationDemo {

  private static int counter = 0;
  private static int counter1 = 0;
  private static int counter2 = 0;

  public static void main(String[] args) {
    Thread one = new Thread(() -> {
      for (int i = 0; i < 10_000; i++) {
        increment();
        increment1();
      }
    });

    Thread two = new Thread(() -> {
      for (int i = 0; i < 10_000; i++) {
        increment();
        increment2();
      }
    });

    one.start();
    two.start();

    try {
      one.join();
      two.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    System.out.println("Counter value: " + counter);
    System.out.println("Counter 1 and 2 value: " + counter1 + " -- " + counter2);
    
  }

  private synchronized static void increment() {
    counter++;
  }

  // the synchronized make a block to entirely class to be synchronized
  private synchronized static void increment1() {
    counter1++;
  }

  private synchronized static void increment2() {
    counter2++;
  }
}

/*
 * 1. Load
 * 2. Increment
 * 3. Set back the value
 * counter = 0; incrementValue = 1; Setting back the value to counter <- Thread 1
 * counter = 0; incrementValue = 1; <- Thread 2
 */
