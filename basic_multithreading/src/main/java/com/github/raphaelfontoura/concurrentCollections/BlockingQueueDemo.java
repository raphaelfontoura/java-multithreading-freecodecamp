package com.github.raphaelfontoura.concurrentCollections;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueDemo {
  static final int QUEUE_CAPACITY = 10;
  static BlockingQueue<Integer> taskQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);

  public static void main(String[] args) {
    Thread producer = new Thread(() -> {
      try {
        for (int i = 0; i < 10; i++) {
          taskQueue.put(i);
          System.out.println("Task produced: " + i);
          Thread.sleep(100);
        }
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    });

    Thread consumerOne = new Thread(() -> {
      try {
        while (true) {
          int task = taskQueue.take();
          processTask(task, "ConsumerOne");
        }
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    });

    Thread consumerTwo = new Thread(() -> {
      try {
        while (true) {
          int task = taskQueue.take();
          processTask(task, "ConsumerTwo");
        }
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    });

    producer.start();
    consumerOne.start();
    consumerTwo.start();
    
  }

  private static void processTask(int task, String consumerName) throws InterruptedException {
    System.out.println(consumerName + " is processing task: " + task);
    Thread.sleep(1000);
    System.out.println("Task consumed by " + consumerName + ": " + task);
  }

}
