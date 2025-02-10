package com.github.raphaelfontoura.basicMultithreading;

public class RunnableThreadExample {

  public static void main(String[] args) {
    Thread one = new Thread(new ThreadOne());
    Thread two = new Thread(new ThreadTwo());
    Thread three = new Thread(new Runnable() {
      @Override
      public void run() {
        for (int i = 0; i < 10; i++) {
          System.out.println("From thread three: " + i);
        }
      }
    });

    one.start();
    two.start();
    three.start();
  }

}

class ThreadOne implements Runnable {
  @Override
  public void run() {
    for (int i = 0; i < 10; i++) {
      System.out.println("From thread one: " + i);
    }
  }
}

class ThreadTwo implements Runnable {
  @Override
  public void run() {
    for (int i = 0; i < 10; i++) {
      System.out.println("From thread two: " + i);
    }
  }
}