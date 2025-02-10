package com.github.raphaelfontoura.poolThreadsExecutorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutorDemo {

  public static void main(String[] args) {
    ExecutorService service = Executors.newSingleThreadExecutor(); 
    
    for (int i = 0; i < 10; i++) {
      service.execute(new Task(i));
    }
    service.shutdown();
    
  }

}

class Task implements Runnable {
  private final int taskId;

  public Task(int taskId) {
    this.taskId = taskId;
  }

  @Override
  public void run() {
    
    System.out.println("Task with ID " + taskId + " being executed by Thread " + Thread.currentThread().getName());
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    
  }

}
