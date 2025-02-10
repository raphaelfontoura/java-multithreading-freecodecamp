package com.github.raphaelfontoura.basicMultithreading;

public class ThreadPriorityExample {

  public static void main(String[] args) {
    System.out.println(Thread.currentThread().getName() + " is running");
    System.out.println(Thread.currentThread().getPriority());

    Thread one = new Thread(() -> {
      System.out.println(Thread.currentThread().getName() + " is running");
    });
    
    one.setPriority(Thread.MAX_PRIORITY);
    one.start();
  }


}
