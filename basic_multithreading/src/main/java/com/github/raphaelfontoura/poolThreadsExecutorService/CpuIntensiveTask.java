package com.github.raphaelfontoura.poolThreadsExecutorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CpuIntensiveTask {

  public static void main(String[] args) {
    int cores = Runtime.getRuntime().availableProcessors();
    System.out.println("Number of cores: " + cores);
    try (ExecutorService service = Executors.newFixedThreadPool(cores)) {
      for (int i = 0; i < 20; i++) {
        service.execute(new CpuTask());
      }
    }
  }
}

class CpuTask implements Runnable {
  @Override
  public void run() {
    System.out.println("Some CPU intensive task is being executed by " + Thread.currentThread().getName());
  }
}
