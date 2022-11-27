package de.cas.adventofcode.java19;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class Task implements Callable<Integer> {

  private final int number;

  public Task(int number) {
    this.number = number;
  }

  @Override
  public Integer call() {
    System.out.printf(
        "Thread %s - Task %d waiting...%n", Thread.currentThread().getName(), number);

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      System.out.printf(
          "Thread %s - Task %d canceled.%n", Thread.currentThread().getName(), number);
      return -1;
    }

    System.out.printf(
        "Thread %s - Task %d finished.%n", Thread.currentThread().getName(), number);
    return ThreadLocalRandom.current().nextInt(100);
  }
}