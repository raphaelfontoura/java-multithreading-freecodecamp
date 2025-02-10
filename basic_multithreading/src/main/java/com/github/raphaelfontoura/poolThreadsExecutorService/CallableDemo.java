package com.github.raphaelfontoura.poolThreadsExecutorService;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CallableDemo {

  public static void main(String[] args) {
    try (ExecutorService service = Executors.newFixedThreadPool(2)) {
      Future<Integer> result = service.submit(new ReturnValueTask());

      System.out.println(result.get(5, TimeUnit.SECONDS));
      System.out.println("Main thread execution completed");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}

class ReturnValueTask implements Callable<Integer> {
  @Override
  public Integer call() throws Exception {
    Thread.sleep(2000);
    return 12;
  }
}
